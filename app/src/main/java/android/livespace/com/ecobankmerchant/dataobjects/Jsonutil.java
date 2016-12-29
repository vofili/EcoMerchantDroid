package android.livespace.com.ecobankmerchant.dataobjects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by val on 12/8/16.
 */

public class Jsonutil {



    public static String requestTrnData(){

        OkHttpClient client = new OkHttpClient();
        String res="";
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"affiliatecode\":\"ENG\",\"authorization\":\"eyJhbGciOiJIUzUxMiIsImN0eSI6InRleHRcL3BsYWluIn0.eyJ1c2VyaWQiOiIzMzIwODIxODQiLCJpc3N1ZWRhdGUiOiIyMDE2LTEyLTA4VDA1OjI3OjMzLjI3N1oiLCJleHBpcnlkYXRlIjoiMjAxNi0xMi0wOVQwNToyNzozMy4yNzdaIn0.dI8Xg-fVuS_bIkCKXYPQ3yIY8rVmuE2U0onZ_Lzs4PE0yd5RZWaeSU2guqT9g3_R50tdf4rawLucmAFa1-DEFQ\",\"enddate\":\"2016-12-08\",\"merchantid\":\"332082184\",\"pagesize\":\"10\",\"startdate\":\"2016-01-01\"}");
        Request request = new Request.Builder()
                .url("http://57.66.111.49:8088/v1/merchantapi/transactionhistory")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "09b083fd-3c35-2058-195d-ebe526e84adf")
                .build();
        try {
            Response response = client.newCall(request).execute();
            res = response.body().string();
        }catch(Exception e){
            e.getMessage();
        }
        return res;
    }
}
