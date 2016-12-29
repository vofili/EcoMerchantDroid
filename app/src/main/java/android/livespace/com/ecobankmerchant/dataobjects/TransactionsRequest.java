package android.livespace.com.ecobankmerchant.dataobjects;

/**
 * Created by val on 12/7/16.
 */

public class TransactionsRequest {

    String affiliatecode;
    String merchantid;
    String startdate;
    String enddate;
    String querytype;
    String pagesize;
    String terminalid;
    String authorization;

    String channel;

    public String getAffiliatecode() {
        return affiliatecode;
    }

    public void setAffiliatecode(String affiliatecode) {
        this.affiliatecode = affiliatecode;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getQuerytype() {
        return querytype;
    }

    public void setQuerytype(String querytype) {
        this.querytype = querytype;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }




}
