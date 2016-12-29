package android.livespace.com.ecobankmerchant;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by val on 12/3/16.
 */

public class paymentNotificationInstantId  extends FirebaseInstanceIdService {

    static String PUSH_SETTINGS="ecobankmerchant.appPreferences",PUSH_DETAIL="ecobankmerchant.appPreferences";

    @Override
    public void onTokenRefresh() {
        Log.v("Refresh mesg","Get updated InstanceID token");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.v("Token Refresh", "Refreshed token: " + refreshedToken);

        SharedPreferences xx = getSharedPreferences(PUSH_DETAIL,Activity.MODE_PRIVATE);
        SharedPreferences.Editor xxeditor = xx.edit();


        SharedPreferences pushPrefs = getSharedPreferences(PUSH_SETTINGS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor pusheditor = pushPrefs.edit();
        pusheditor.putString("fcmtoken",refreshedToken);
pusheditor.commit();
        // appeditor.putBoolean("isactivated",true);
//        appeditor.putInt("notimeslogin",4);
//        appeditor.putFloat("balance",20.90f);
//        appeditor.putString("merchant name","Obi Lawrence");
        //appeditor.commit();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(refreshedToken);
    }
}
