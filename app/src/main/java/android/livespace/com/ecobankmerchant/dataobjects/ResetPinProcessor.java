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
import static android.livespace.com.ecobankmerchant.Utilities.Configuration.merchantactivatepassword;

/**
 * Created by val on 12/9/16.
 */

public class ResetPinProcessor {


        public static void main(String args[]){
            AccountActivationStatusRequest yy = new AccountActivationStatusRequest();
            yy.setChannel("MOBILE");
            yy.setMerchantid("133645741");
            yy.setPinone("093093");
            yy.setPintwo("093093");
            yy.setOtp("999999");
            resetaccountpin(yy);

    }

    public static ResetPinResponse processmerchantid(ResetPinRequest rpinreq){
        ResetPinResponse rpinresp = new ResetPinResponse();
        Gson requestJson = new Gson();
        Gson responseJson = new Gson();
        String requestJsonStr ="";
        String responseJsonStr ="";
//        OkHttpClient client = new OkHttpClient.Builder().readTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
//                .connectTimeout(20,TimeUnit.SECONDS)
//                .writeTimeout(20,TimeUnit.SECONDS)
//                .build();

        OkHttpClient client = ConnectionClient.getSecureHttpClient();

        requestJsonStr = requestJson.toJson(rpinreq,ResetPinRequest.class);
        //System.out.println("Serialised Reset Pin Request Object"+requestJsonStr);

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
           // System.out.println("Serialised Reset Pin Response Object " + responseJsonStr);
            rpinresp = responseJson.fromJson(responseJsonStr,ResetPinResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rpinresp;
    }




    public static ResetPinResponse processmerchantotp(ResetPinRequest rpinreq){
        ResetPinResponse rpinresp = new ResetPinResponse();
        Gson requestJson = new Gson();
        Gson responseJson = new Gson();
        String requestJsonStr ="";
        String responseJsonStr ="";
//        OkHttpClient client = new OkHttpClient.Builder().readTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
//                .connectTimeout(20,TimeUnit.SECONDS)
//                .writeTimeout(20,TimeUnit.SECONDS)
//                .build();

        OkHttpClient client = ConnectionClient.getSecureHttpClient();

        requestJsonStr = requestJson.toJson(rpinreq,ResetPinRequest.class);
        System.out.println("Serialised Reset Pin Request Object"+requestJsonStr);

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
            System.out.println("Serialised Reset Pin Response Object" + responseJsonStr);
            rpinresp = responseJson.fromJson(responseJsonStr,ResetPinResponse.class);
        } catch (Exception e) {
            FirebaseCrash.log(e.getMessage());
            e.printStackTrace();
        }

        return rpinresp;
    }

        public static AccountActivationStatusResponse  resetaccountpin(AccountActivationStatusRequest req){
            String requestString ="";
            OkHttpClient client =  ConnectionClient.getSecureHttpClient();
            AccountActivationStatusResponse returnObj = new AccountActivationStatusResponse();
            Gson reqJson = new Gson();
            Gson respJson = new Gson();
            requestString = reqJson.toJson(req,AccountActivationStatusRequest.class);
            System.out.println("Request i am sending "+requestString);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, requestString);
            Request request = new Request.Builder()
                    .url(merchantactivatepassword)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")

                    .build();
            String res="";
            try {
                Response response = client.newCall(request).execute();
                res= response.body().string();

                returnObj =respJson.fromJson(res,AccountActivationStatusResponse.class);
                System.out.println("The result "+res );
                System.out.println("The object result "+returnObj.getResponsecode()+ " Messgae" + returnObj.getResponsemessage() );
            }catch(Exception e){
                FirebaseCrash.log(e.getMessage());
                e.printStackTrace();
            }
            return returnObj;
        }

}
