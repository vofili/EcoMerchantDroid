package android.livespace.com.ecobankmerchant.dataobjects;

import android.livespace.com.ecobankmerchant.Utilities.Configuration;
import android.livespace.com.ecobankmerchant.Utilities.ConnectionClient;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.livespace.com.ecobankmerchant.Utilities.Configuration.CONN_TIMEOUT;
import static android.livespace.com.ecobankmerchant.Utilities.Configuration.merchantactivationendpoint;

/**
 * Created by val on 12/9/16.
 */

public class ChangePinProcessor {



    public static ChangePinResponse processmerchantid(ChangePinRequest chgpinreq){
        ChangePinResponse chgpinresp = new ChangePinResponse();
        Gson requestJson = new Gson();
        Gson responseJson = new Gson();

//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(CONN_TIMEOUT,TimeUnit.SECONDS)
//
//                .build();
        OkHttpClient client = ConnectionClient.getSecureHttpClient();

        String requestJsonStr ="";
        String responseJsonStr ="";
        requestJsonStr = requestJson.toJson(chgpinreq,ChangePinRequest.class);
        System.out.println("Serialised Request Object for Change Pin"+requestJsonStr);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJsonStr);
        Request request = new Request.Builder()
                .url(merchantactivationendpoint)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "a777e098-5e06-122c-7607-f7aab0fd4235")
                .build();

        try {
            Response response = client.newCall(request).execute();
            responseJsonStr=response.body().string();
          //  System.out.println("the Serialised response Json "+responseJsonStr);
            chgpinresp = responseJson.fromJson(responseJsonStr,ChangePinResponse.class);
        } catch (Exception e) {
            FirebaseCrash.log(e.getMessage());
            e.printStackTrace();
        }

        return chgpinresp;
    }








    public static ChangePinResponse processmerchantotp(ChangePinRequest chgpinreq){
        ChangePinResponse chgpinresp = new ChangePinResponse();
        Gson requestJson = new Gson();
        Gson responseJson = new Gson();

//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(CONN_TIMEOUT,TimeUnit.SECONDS)
//        .build();

        OkHttpClient client = ConnectionClient.getSecureHttpClient();

        String responseJsonStr ="";
        String requestJsonStr ="";
        requestJsonStr = requestJson.toJson(chgpinreq,ChangePinRequest.class);
        System.out.println("Serialised Request Object for Change Pin"+requestJsonStr);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJsonStr);
        Request request = new Request.Builder()
                .url(Configuration.merchantchangepin)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "a777e098-5e06-122c-7607-f7aab0fd4235")
                .build();

        try {
            Response response = client.newCall(request).execute();
            responseJsonStr = response.body().string();
            System.out.println("Response Object for Change Pin"+responseJsonStr);
            chgpinresp = responseJson.fromJson(responseJsonStr,ChangePinResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chgpinresp;
    }



}
