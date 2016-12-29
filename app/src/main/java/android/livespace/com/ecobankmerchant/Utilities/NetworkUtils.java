package android.livespace.com.ecobankmerchant.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by val on 12/10/16.
 */

public class NetworkUtils {


    private static boolean isNetwokAvailable;



    public static boolean isNetworkAvailable(Context ctx) {

        if(ctx != null){
            ConnectivityManager connectionManager = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connectionManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo = connectionManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((wifiInfo!=null&&wifiInfo.isConnected()) ||(mobileInfo!=null&& mobileInfo.isConnected())) {
                isNetwokAvailable = true;
            } else {
                isNetwokAvailable = false;
            }
        }else{
            return false;
        }

        return isNetwokAvailable;
    }


    public static void main(String[] args){
        //System.out.println(isNetworkAvailable());
    }
}
