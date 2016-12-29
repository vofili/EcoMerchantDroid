package android.livespace.com.ecobankmerchant.dataobjects;

import android.livespace.com.ecobankmerchant.TransactionsListing;
import android.livespace.com.ecobankmerchant.Utilities.*;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.livespace.com.ecobankmerchant.Utilities.Configuration.CONN_TIMEOUT;
import static android.livespace.com.ecobankmerchant.Utilities.Configuration.transactionshistoryendpoint;

/**
 * Created by val on 12/7/16.
 */

public class TransactionsProcessor {

    public static TransResponseObj process(TransactionsRequest treq){
        TransResponseObj tresp = new TransResponseObj();

        OkHttpClient client = ConnectionClient.getSecureHttpClient();

        Gson requestJson = new Gson();
        Gson responseJson = new Gson();
        String requestJsonStr ="";
        requestJsonStr = requestJson.toJson(treq,TransactionsRequest.class);

        //System.out.println("Serialised Request Object for transactions:"+requestJsonStr);


        MediaType mediaType = MediaType.parse("application/json");
        //---//RequestBody body = RequestBody.create(mediaType, "{\r\n\"terminalid\":\"\",\r\n\"affiliatecode\":\"EGH\",\r\n\"merchantid\":\"332082184\",\r\n\"startdate\":\"2015-04-24\",\r\n\"enddate\":\"2015-04-30\",\r\n\"querytype\":\"DATERANGE\",\r\n\"pagesize\":\"10\",\r\n\"authorization\": \"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFY29iYW5rIiwiYXVkIjoiQWxsVXNlcnMiLCJpYXQiOjE0ODA5Njk1MTUsImV4cCI6MTQ4MTA1NTkxNSwiaW5mbyI6eyJ1c2VySWQiOiIxMjM0NTY3ODkwIn19.xSD7HYV5hMTwycjYmOV_e-pjYCKxouihu3gTwgc9UNU\"\r\n}\r\n\r\n");
        RequestBody body = RequestBody.create(mediaType, requestJsonStr);
        Request request = new Request.Builder()
                .url(Configuration.transactionshistoryendpoint)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "91de7fd2-bd43-939f-499e-15730649a303")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseStr = response.body().string();
            System.out.println("Transaction Response:" + responseStr);

            tresp = responseJson.fromJson(responseStr,TransResponseObj.class);
        }catch(Exception e){
            System.out.println("Error while getting transactions: " +e.getMessage());
            FirebaseCrash.log(e.getMessage());
            e.printStackTrace();
        }



        return tresp;
    }


}
