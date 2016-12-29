package android.livespace.com.ecobankmerchant.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by val on 12/1/16.
 */
public class Transactions {


    String amount;
            String terminalId;
            String ccy;
            String cbaReferenceNo;
            String productDetail;
            String tranDate;
            String narration;
            String merchantAccountNo;
            String tranType;
            String invoiceNo;

    public String getDbcr() {
        return dbcr;
    }

    public void setDbcr(String dbcr) {
        this.dbcr = dbcr;
    }

    String dbcr;



    public int describeContents() {
        return 0;
    }
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getCbaReferenceNo() {
        return cbaReferenceNo;
    }

    public void setCbaReferenceNo(String cbaReferenceNo) {
        this.cbaReferenceNo = cbaReferenceNo;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getMerchantAccountNo() {
        return merchantAccountNo;
    }

    public void setMerchantAccountNo(String merchantAccountNo) {
        this.merchantAccountNo = merchantAccountNo;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
}
