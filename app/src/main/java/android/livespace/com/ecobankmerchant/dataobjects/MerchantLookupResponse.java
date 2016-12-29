package android.livespace.com.ecobankmerchant.dataobjects;

/**
 * Created by val on 12/6/16.
 */

public class MerchantLookupResponse {

    Merchant FetchMerchantInfoResponse;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    String authorization;


    public Merchant getFetchMerchantInfoResponse() {
        return FetchMerchantInfoResponse;
    }

    public void setFetchMerchantInfoResponse(Merchant fetchMerchantInfoResponse) {
        FetchMerchantInfoResponse = fetchMerchantInfoResponse;
    }
}
