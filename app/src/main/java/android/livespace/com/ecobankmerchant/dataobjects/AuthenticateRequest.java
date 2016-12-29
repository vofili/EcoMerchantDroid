package android.livespace.com.ecobankmerchant.dataobjects;

/**
 * Created by val on 12/7/16.
 */

public class AuthenticateRequest {

    String merchantid;
    String affiliatecode;
    String password;
    String terminalid;
    String schemetype;
    String channel;
    String fcmtoken;
    String registrationtoken;
    String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getRegistrationtoken() {
        return registrationtoken;
    }

    public void setRegistrationtoken(String registrationtoken) {
        this.registrationtoken = registrationtoken;
    }



    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public String getSchemetype() {
        return schemetype;
    }

    public void setSchemetype(String schemetype) {
        this.schemetype = schemetype;
    }


    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getAffiliatecode() {
        return affiliatecode;
    }

    public void setAffiliatecode(String affiliatecode) {
        this.affiliatecode = affiliatecode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
