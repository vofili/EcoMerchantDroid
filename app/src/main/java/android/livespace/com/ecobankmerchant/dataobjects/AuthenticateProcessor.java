package android.livespace.com.ecobankmerchant.dataobjects;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import android.livespace.com.ecobankmerchant.Utilities.*;
import android.util.Log;

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

public class AuthenticateProcessor {



    public static AuthenticateResponse process(AuthenticateRequest areq){

        //OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS).build();
        OkHttpClient client = ConnectionClient.getSecureHttpClient();
        Gson requestJson = new Gson();
        Gson responseJson = new Gson();
        String requestJsonStr ="";
        String responseJsonStr="";
        String properresp="";
        int posstart=0;
        int posend=0;
        requestJsonStr = requestJson.toJson(areq,AuthenticateRequest.class);
//        FirebaseCrash.log("Raw Response Str from Autheticator "+responseJsonStr);
        Log.v("Authenticator","Serialised Request Object to Authenticator:"+requestJsonStr);
        AuthenticateResponse authresp = new AuthenticateResponse();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJsonStr);
        Request request = new Request.Builder()
                .url(Configuration.authenticationendpoint)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
            responseJsonStr = response.body().string().toString();
          //  System.out.println("Response Length:"+responseJsonStr.length());
           // System.out.println("Response Info:"+responseJsonStr);
           // System.out.println(requestJsonStr.toLowerCase().contains("terminal".toLowerCase()));
         //   System.out.println(requestJsonStr.toLowerCase().indexOf("terminal".toLowerCase()));
            try {
                posstart = responseJsonStr.indexOf("TerminalInfo", 1);
                posend = responseJsonStr.indexOf("merchantName", 1);
            }catch(Exception te){
                posstart=0;
                posend=0;
                te.printStackTrace();
            }
          //  System.out.println("Terminal Info:"+requestJsonStr.contains("terminal"));

                if (responseJsonStr.toLowerCase().contains("terminal") && (responseJsonStr.charAt(posstart) + 14 != '[' || responseJsonStr.charAt(posend - 3) != ']')) {

                 //   System.out.println(" Response String is a JSON Array for Terminal "+ responseJsonStr);
                    responseJsonStr = responseJsonStr.replace("\"TerminalInfo\":{", "\"TerminalInfo\":[{");
                    responseJsonStr = responseJsonStr.replace("},\"merchantName\"", "}],\"merchantName\"");
                    System.out.println(" Response String is a JSON array for the Terminal "+ responseJsonStr);

                }

            System.out.println("Raw Response Str from Authenticator "+responseJsonStr);
           // FirebaseCrash.log("Raw Response Str from Autheticator "+responseJsonStr);
           authresp = responseJson.fromJson(responseJsonStr,AuthenticateResponse.class);
        } catch (Exception e) {
          //  FirebaseCrash.report(new Exception("Auth Processor Error authentication error: " + e.getMessage()));
            e.printStackTrace();
           // Log.e("Auth Processor Error",e.getMessage());
        }
        return authresp;
    }

}
