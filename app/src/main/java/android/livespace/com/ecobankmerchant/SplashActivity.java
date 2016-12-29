package android.livespace.com.ecobankmerchant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.livespace.com.ecobankmerchant.dataobjects.AccountActivationStatus;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.livespace.com.ecobankmerchant.Utilities.Configuration.DEBUG_LOG;
import static android.livespace.com.ecobankmerchant.Utilities.Configuration.SPLASH_INTERVAL;

public class SplashActivity extends Activity {
    public static String  firebasetoken;
    public  static String MERCHANT_SETTINGS = "com.ecobankmerchant.merchant.preferences";
    private static String authkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String noteref="",noteamount="",notenarration="",notecurrency="",
                notedatetime="",notemerchantid="",noteccy="",noteterminal="";
        Bundle noteextras = getIntent().getExtras();
        SharedPreferences mprefs = getSharedPreferences(MERCHANT_SETTINGS, Activity.MODE_PRIVATE);
        authkey = mprefs.getString("AUTH_KEY", "") != null ? mprefs.getString("AUTH_KEY", "") :"";
        if((noteextras != null) && noteextras.getString("reference")!=null && noteextras.getString("amount") !=null ) {

            Log.v("Login Descisions","The App was called with populated notification Extras and an Auth key:"+authkey);
            noteref = noteextras.getString("reference") != null ? noteextras.getString("reference"):"";
            noteamount = noteextras.getString("amount") != null ? noteextras.getString("amount"):"";
            notenarration = noteextras.getString("narration") != null ? noteextras.getString("narration") :"";
            notecurrency= noteextras.getString("currency") != null ?  noteextras.getString("currency") :"";
            notedatetime = noteextras.getString("datetime") !=null ? noteextras.getString("datetime"):"" ;
            notemerchantid = noteextras.getString("merchantid") != null ? noteextras.getString("merchantid"):"";
            noteterminal = noteextras.getString("terminalid") != null ?noteextras.getString("terminalid"):"";

            System.out.println("Notification reference: " + noteref);
            System.out.println("Notification amount: " + noteamount);
            System.out.println("Notification narration: " + notenarration);
            System.out.println("Notification currency: " + notecurrency);
            System.out.println("Notification date: " + notecurrency);
            System.out.println("Notification merchant id: " + notemerchantid);
            System.out.println("Notification terminal id: " + noteterminal);

            Intent gotonote = new Intent(this,NotificationPaid.class);
            gotonote.putExtra("REF",noteref);
            gotonote.putExtra("AMT",noteamount);
            gotonote.putExtra("NARRATION",notenarration);
            gotonote.putExtra("CCY",notecurrency);
            gotonote.putExtra("DATE",notecurrency);
            gotonote.putExtra("MERCHANTID",notenarration);
            gotonote.putExtra("TERMINALID",noteterminal);
            startActivity(gotonote);
            finish();
        } else if(authkey.length()==0) {


            Log.v("Login Descisions", "The App was called with no notification Extras and No Auth key:" + authkey);
            String mytoken = FirebaseInstanceId.getInstance().getToken();
            // System.out.println("I tried to get this token" + mytoken);
            //GoogleApiAvailability.makeGooglePlayServicesAvailable();
            try{
                this.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setContentView(R.layout.splashscreen);
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                boolean isWifiConn = networkInfo.isConnected();
                networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                boolean isMobileConn = networkInfo.isConnected();
                System.out.println("Wifi connected: " + isWifiConn);
                System.out.println("Mobile connected: " + isMobileConn);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // if(checkactivated) {
                        Intent main = new Intent(SplashActivity.this, LoginActivity.class);
                        main.putExtra("firebasetoken", firebasetoken);
                        startActivity(main);
                        finish();
                        //  }

                    }
                }, SPLASH_INTERVAL);


                }catch(Exception e){
                        FirebaseCrash.log(e.getMessage());
                }

            }else{
                Intent main = new Intent(SplashActivity.this, Home.class);
                // main.putExtra("firebasetoken", firebasetoken);
                startActivity(main);
                finish();
            }

    }


}
