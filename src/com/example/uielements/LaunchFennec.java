package com.example.uielements;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LaunchFennec extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_runtime);


        findViewById(R.id.find_runtime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.example.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                i.setType("application/webapp");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    // Prompt user to install a version of Firefox
                    Log.i("FIREFOX", "No activities found");
                    getFirefox();

                }
            }
        });


        findViewById(R.id.no_runtime_found_play_store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLayout(true);
            }
        });


        findViewById(R.id.no_runtime_found_no_play_store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLayout(false);
            }
        });


        findViewById(R.id.no_runtime_found_device_specific).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFirefox();
            }
        });

    }

    private void getFirefox() {
        boolean googlePlayStoreInstalled = isGooglePlayInstalled(getBaseContext());
        setLayout(googlePlayStoreInstalled);

    }

    private void setLayout(final boolean googlePlayStoreInstalled) {
        setContentView(R.layout.get_fennec);
        int buttonText = googlePlayStoreInstalled ? R.string.runtime_not_found_play_store : R.string.runtime_not_found_no_play_store;
        ((Button) findViewById(R.id.next)).setText(buttonText);
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                if(googlePlayStoreInstalled) {
                    i.setData(Uri.parse("market://details?id=org.mozilla.firefox"));
                } else {
                    i.setData(Uri.parse("http://www.mozilla.org/mobile"));
                }
                startActivity(i);
            }
        });
    }

    public static boolean isGooglePlayInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try
        {
            PackageInfo info = pm.getPackageInfo("com.android.vending", PackageManager.GET_ACTIVITIES);
            String label = (String) info.applicationInfo.loadLabel(pm);
            app_installed = (label != null && !label.equals("Market"));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed;
    }
}