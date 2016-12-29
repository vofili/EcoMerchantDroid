package android.livespace.com.ecobankmerchant.dataobjects;

/**
 * Created by val on 12/8/16.
 */

public class TransResponseObj {
    TransactionsResponse MerchantTransactionHistoryResponse;

    public TransactionsResponse getMerchantTransactionHistoryResponse() {
        return MerchantTransactionHistoryResponse;
    }

    public void setMerchantTransactionHistoryResponse(TransactionsResponse merchantTransactionHistoryResponse) {
        MerchantTransactionHistoryResponse = merchantTransactionHistoryResponse;
    }
}
