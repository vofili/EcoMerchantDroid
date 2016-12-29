package android.livespace.com.ecobankmerchant.dataobjects;


import android.livespace.com.ecobankmerchant.Utilities.Configuration;
import android.livespace.com.ecobankmerchant.Utilities.ConnectionClient;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import static android.livespace.com.ecobankmerchant.Utilities.Configuration.CONN_TIMEOUT;

public class MerchantLookupProcessor {

    public static MerchantLookupResponse process(MerchantLookupRequest mlreq) {

       // OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS).build();

        OkHttpClient client = ConnectionClient.getSecureHttpClient();

        Gson requestJson = new Gson();
        Gson responseJson = new Gson();
        String requestJsonStr ="";
        requestJsonStr = requestJson.toJson(mlreq,MerchantLookupRequest.class);
       // System.out.println("Serialised Request Object"+requestJsonStr);
        MerchantLookupResponse mlresp = new MerchantLookupResponse();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJsonStr);
        Request request = new Request.Builder()
                .url(Configuration.merchantlookupendpoint)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "a777e098-5e06-122c-7607-f7aab0fd4235")
                .build();


        try {
            Response response = client.newCall(request).execute();
            mlresp = responseJson.fromJson(response.body().string(),MerchantLookupResponse.class);
        } catch (Exception e) {
            FirebaseCrash.log(e.getMessage());
            e.printStackTrace();
        }
        return mlresp;
    }
}
