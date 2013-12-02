package com.example.uielements;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

public class UnknownSourcesActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_unknown_sources);
        findViewById(R.id.pre_honeycomb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.enable_unknown_sources_pre_11);
                assignButtonAction();
            }
        });
        findViewById(R.id.post_gingerbread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.enable_unknown_sources_post_10);
                assignButtonAction();
            }
        });
        findViewById(R.id.device_specific).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.enable_unknown_sources);
                assignButtonAction();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNonPlayAppInstallationAllowed()) {
            Toast.makeText(this, "installation from 'Unknown Sources' enabled", Toast.LENGTH_SHORT).show();
        //    setContentView(R.layout.unknown_sources_enabled);
        }
    }

    private void assignButtonAction() {
        View continueButton = findViewById(R.id.next);
        if(continueButton != null) {
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showUnknownSources();
                }
            });
        }
    }

    private void showUnknownSources() {
        int sdk = new Integer(Build.VERSION.SDK).intValue();
        if (sdk < 11) { //Android 3
            startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
        } else {
            startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
        }
    }

    private void startActivityIfUnknownSourcesUnchecked() {
        if (!isNonPlayAppInstallationAllowed()) {
            Intent intent = new Intent(this, UnknownSourcesActivity.class);
            startActivity(intent);
        }
    }

    private boolean isNonPlayAppInstallationAllowed() {
        try {
            return Settings.Secure.getInt(getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS) == 1;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

}
