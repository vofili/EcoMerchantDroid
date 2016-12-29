package android.livespace.com.ecobankmerchant.dataobjects;

/**
 * Created by val on 12/7/16.
 */

public class MerchantHeader {
    String responseMessage;
    String responseCode;
    String sourceCode;
    String requestId;
    String affiliateCode;

    public String getCountrycallcenter() {
        return countrycallcenter;
    }

    public void setCountrycallcenter(String countrycallcenter) {
        this.countrycallcenter = countrycallcenter;
    }

    String countrycallcenter;



    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAffiliateCode() {
        return affiliateCode;
    }

    public void setAffiliateCode(String affiliateCode) {
        this.affiliateCode = affiliateCode;
    }
}
