package android.livespace.com.ecobankmerchant.dataobjects;

/**
 * Created by val on 12/6/16.
 */

public class MerchantLookupRequest {

    String merchantid;
    String affiliatecode;
    String schemetype;
    String terminalid;
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

    public String getAffiliatecode() {
        return affiliatecode;
    }

    public void setAffiliatecode(String affiliatecode) {
        this.affiliatecode = affiliatecode;
    }

    public String getSchemetype() {
        return schemetype;
    }

    public void setSchemetype(String schemetype) {
        this.schemetype = schemetype;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }
}
