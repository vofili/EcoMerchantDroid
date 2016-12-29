package android.livespace.com.ecobankmerchant.dataobjects;

import java.util.List;

/**
 * Created by val on 12/7/16.
 */

public class TransactionsResponse {

    public TransactionsHeader getHostHeaderInfo() {
        return hostHeaderInfo;
    }

    public void setHostHeaderInfo(TransactionsHeader hostHeaderInfo) {
        this.hostHeaderInfo = hostHeaderInfo;
    }

    public List<Transactions> getMerchantTransactionInfo() {
        return MerchantTransactionInfo;
    }

    public void setMerchantTransactionInfo(List<Transactions> merchantTransactionInfo) {
        MerchantTransactionInfo = merchantTransactionInfo;
    }

            TransactionsHeader hostHeaderInfo;
            List<Transactions> MerchantTransactionInfo;

}
