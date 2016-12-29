package android.livespace.com.ecobankmerchant.dataobjects;

/**
 * Created by val on 12/7/16.
 */

public class AuthenticateResponse {

    Merchant FetchMerchantInfoResponse;
    String authorization;
    String responsemessage;
    String responsecode;


    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public String getResponsemessage() {
        return responsemessage;
    }

    public void setResponsemessage(String responsemessage) {
        this.responsemessage = responsemessage;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public Merchant getFetchMerchantInfoResponse() {
        return FetchMerchantInfoResponse;
    }

    public void setFetchMerchantInfoResponse(Merchant fetchMerchantInfoResponse) {
        FetchMerchantInfoResponse = fetchMerchantInfoResponse;
    }
}
