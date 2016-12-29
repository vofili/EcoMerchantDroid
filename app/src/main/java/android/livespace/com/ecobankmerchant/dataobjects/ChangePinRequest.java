package android.livespace.com.ecobankmerchant.dataobjects;

/**
 * Created by val on 12/9/16.
 */

public class ChangePinRequest {


    String pinone;
    String pintwo;
    String pinold;
    String otp;
    String merchantid;
    String channel;

    String authkey;

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPinold() {
        return pinold;
    }

    public void setPinold(String pinold) {
        this.pinold = pinold;
    }


    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }



    public String getPintwo() {
        return pintwo;
    }

    public void setPintwo(String pintwo) {
        this.pintwo = pintwo;
    }

    public String getPinone() {
        return pinone;
    }

    public void setPinone(String pinone) {
        this.pinone = pinone;
    }




}
