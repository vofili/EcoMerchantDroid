package android.livespace.com.ecobankmerchant;

import android.content.Intent;
import android.livespace.com.ecobankmerchant.view.FontTextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NetworkNoConnection extends AppCompatActivity {


    FontTextView ftv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_no_connection);

        ftv = (FontTextView) findViewById(R.id.networkTryAgain);
        ftv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void gotoSafeActivity(){
        Intent xLogin = new Intent(this,LoginActivity.class);
        startActivity(xLogin);
        finish();
    }
}
