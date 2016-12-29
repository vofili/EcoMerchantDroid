package android.livespace.com.ecobankmerchant.Utilities;


import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.*;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.livespace.com.ecobankmerchant.Utilities.Configuration.CONN_TIMEOUT;


public class ConnectionClient {


    public static OkHttpClient getSecureHttpClient() {
//        FirebaseCrash.log("Connection Time out configuration: " +CONN_TIMEOUT);
        // Create a trust manager that does not validate certificate chains
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sc.getSocketFactory();


            OkHttpClient.Builder okclientbld = new OkHttpClient.Builder()
                    .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);

            okclientbld.sslSocketFactory(sslSocketFactory);
            okclientbld.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            client = okclientbld.build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }


}
