package android.livespace.com.ecobankmerchant.dataobjects;

/**
 * Created by val on 12/7/16.
 */

public class AccountActivationStatusRequest {


    String pinone;
    String pintwo;
    String otp;
    String merchantid;
    String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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
