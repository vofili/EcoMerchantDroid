package android.livespace.com.ecobankmerchant.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by val on 12/8/16.
 */

public class SMSOtp {


    public static String  getCleanNumbers(String country, String phone){
        String countrycode="";
        switch(country){
            case "EGH":
                    countrycode="233";
                    break;
            case "ENG":
                    countrycode="234";
                    break;
            case "ETG":
                    countrycode="228";
                    break;
            default:
                    countrycode="0";

        }

       if (phone.length()==10){
           phone = countrycode+phone.substring(1);

       }
        return phone;
    }

    public static String sendOtp(String phone,String secureotp){
        String responsecode="91";


        return responsecode;

    }
}
