package android.livespace.com.ecobankmerchant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.livespace.com.ecobankmerchant.view.FontTextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class UpgradeVersion extends AppCompatActivity {

    public static String MERCHANT_SETTINGS = "com.ecobankmerchant.merchant.preferences";
    FontTextView mupgrade;
    SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_version);

        mupgrade = (FontTextView) findViewById(R.id.versionTryAgain);
        mupgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearallpref();
                Intent x = new Intent(getBaseContext(),LoginActivity.class );
                startActivity(x);
            }
        });
    }

    void clearallpref(){
        mPrefs = getSharedPreferences(MERCHANT_SETTINGS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mprefseditor = mPrefs.edit();
        mprefseditor.remove("AUTH_KEY");
    }
}
