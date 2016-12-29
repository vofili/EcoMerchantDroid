package android.livespace.com.ecobankmerchant.dataobjects;

import java.util.List;

/**
 * Created by val on 12/7/16.
 */

public class Merchant {

    String middleName;
    String area;
    String email;
    String dob;
    String merchantName;
    String merchantId;
    String firstName;
    String addressLine1;
    String mobileNo;
    String country;
    String city;
    String merchantAccount;

    List <Terminal> TerminalInfo;
    MerchantHeader hostHeaderInfo;

    public List<Terminal> getTerminalInfo() {
        return TerminalInfo;
    }

    public void setTerminalInfo(List<Terminal> terminalInfo) {
        TerminalInfo = terminalInfo;
    }


    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public MerchantHeader getHostHeaderInfo() {
        return hostHeaderInfo;
    }

    public void setHostHeaderInfo(MerchantHeader hostHeaderInfo) {
        this.hostHeaderInfo = hostHeaderInfo;
    }



}
