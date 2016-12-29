package android.livespace.com.ecobankmerchant.Utilities;

/**
 * Created by val on 12/7/16.
 */

public class Configuration {
//Endpoints
//    public static String authenticationendpoint ="https://57.66.111.49:8088/v1/merchantapi/authentication";
//    public static String merchantlookupendpoint="https://57.66.111.49:8088/v1/merchantapi/terminal";
//    public static String transactionshistoryendpoint="https://57.66.111.49:8088/v1/merchantapi/transactionhistory";
//    public static String merchantactivationendpoint="https://57.66.111.49:8088/v1/merchantapi/activation";
//    public static String merchantactivatepassword="https://57.66.111.49:8088/v1/merchantapi/setpassword";
//    public static String merchantchangepin="https://57.66.111.49:8088/v1/merchantapi/changepassword";
//    public static String merchantresetpin="https://57.66.111.49:8088/v1/merchantapi/setpassword";


    public static String authenticationendpoint ="https://57.66.111.50:8088/v1/merchantapi/authentication";
    public static String merchantlookupendpoint="https://57.66.111.50:8088/v1/merchantapi/terminal";
    public static String transactionshistoryendpoint="https://57.66.111.50:8088/v1/merchantapi/transactionhistory";
    public static String merchantactivationendpoint="https://57.66.111.50:8088/v1/merchantapi/activation";
    public static String merchantactivatepassword="https://57.66.111.50:8088/v1/merchantapi/setpassword";
    public static String merchantchangepin="https://57.66.111.50:8088/v1/merchantapi/changepassword";
    public static String merchantresetpin="https://57.66.111.50:8088/v1/merchantapi/setpassword";


//App Settings
    public static int SPLASH_INTERVAL = 3000;

    //http connection settings
    public static int CONN_TIMEOUT=300;

    //User preferences
    public static String userlanguage;
    public static String versionid = "1.0";
//Logging Config
    public static String INFO_LOG;
    public static String DEBUG_LOG;
    public static String ERROR_LOG;
    public static String WARNING_LOG;

    public static String transactiondatetype;
    public static String transpagesize="100";
    public static String MERCHANT_SETTINGS = "com.ecobankmerchant.merchant.preferences";

    //Upgrade version URL
    public static String UPGRADE_URL="https://57.66.111.50/MerchantPortal/";
}
