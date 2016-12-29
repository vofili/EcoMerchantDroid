package android.livespace.com.ecobankmerchant.dataobjects;

import android.content.Context;
import android.livespace.com.ecobankmerchant.Utilities.Configuration;
import android.livespace.com.ecobankmerchant.Utilities.ConnectionClient;
import android.livespace.com.ecobankmerchant.Utilities.NetworkUtils;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.livespace.com.ecobankmerchant.Utilities.Configuration.CONN_TIMEOUT;

/**
 * Created by val on 12/7/16.
 */

public class AccountActivationProcessor {



    public static void main(String args[]){

        AccountActivationStatusResponse resp = new AccountActivationStatusResponse();
        AccountActivationStatusRequest xx= new AccountActivationStatusRequest();
        xx.setMerchantid("133645741");
        xx.setChannel("MOBILE");
        xx.setOtp("090909");
        xx.setPinone("191919");
        xx.setPinone("191919");
        resp = processmerchantotp(xx);


    }

    public static AccountActivationStatusResponse processmerchantid(AccountActivationStatusRequest areq){
        AccountActivationStatusResponse aresp = new AccountActivationStatusResponse();
        Gson requestJson = new Gson();
        Gson responseJson = new Gson();
        OkHttpClient client = ConnectionClient.getSecureHttpClient();
//       // OkHttpClient client = new OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS)
//                .connectTimeout(20,TimeUnit.SECONDS)
//                .writeTimeout(20,TimeUnit.SECONDS)
//                .build();
        String responseJsonStr ="";
        String requestJsonStr ="";
        requestJsonStr = requestJson.toJson(areq,AccountActivationStatusRequest.class);
       // System.out.println("Serialised Request Object processa merchant id"+requestJsonStr);
        FirebaseCrash.log("Serialised Request Object processa merchant id"+requestJsonStr);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJsonStr);
        Request request = new Request.Builder()
                .url(Configuration.merchantactivationendpoint)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "a777e098-5e06-122c-7607-f7aab0fd4235")
                .build();

        try {
            Response response = client.newCall(request).execute();
            responseJsonStr = response.body().string();
            //System.out.println("Raw Response String process merchant id: " + responseJsonStr);
            FirebaseCrash.log("Raw Response String process merchant id: " + responseJsonStr);
            aresp = responseJson.fromJson(responseJsonStr,AccountActivationStatusResponse.class);
           // System.out.println("Response Object: " + aresp.getResponsecode());
        } catch (Exception e) {
            FirebaseCrash.log(e.getMessage());
            e.printStackTrace();
        }

        return aresp;
    }




    public static AccountActivationStatusResponse processmerchantotp(AccountActivationStatusRequest areq){
        AccountActivationStatusResponse aresp = new AccountActivationStatusResponse();
        Gson requestJson = new Gson();
        Gson responseJson = new Gson();
        //OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONN_TIMEOUT,TimeUnit.SECONDS).build();
        OkHttpClient client = ConnectionClient.getSecureHttpClient();
        String requestJsonStr ="";
        String responseJsonStr="";
        requestJsonStr = requestJson.toJson(areq,AccountActivationStatusRequest.class);
        System.out.println("Serialised Request Object For Merchant OTP"+requestJsonStr);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJsonStr);
        Request request = new Request.Builder()
                .url(Configuration.merchantactivatepassword)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "a777e098-5e06-122c-7607-f7aab0fd4235")
                .build();

        try {
            Response response = client.newCall(request).execute();
            responseJsonStr = response.body().string();
            System.out.println("The Resulting response from webservice:"+response.body().string());
            aresp = responseJson.fromJson(responseJsonStr,AccountActivationStatusResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aresp;
    }

}
