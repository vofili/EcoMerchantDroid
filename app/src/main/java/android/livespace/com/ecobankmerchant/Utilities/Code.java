package android.livespace.com.ecobankmerchant.Utilities;

/**
 * Created by val on 12/10/16.
 */

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Code {



    public static void main(String[] args){

        String staticcode="110015537010070037990A13CHINEDU A OBI0B0489310C02LA0D03NGA0E03566A9041003",tip="",amount="",inv="";

            System.out.println( generateDynamic(staticcode,"2.35","343", "988283828"));
    }

    String staticqr;
    String dynamicqr;





    public static String generateDynamic(String staticqr,String tip,String amount,String inv) {
        BigDecimal tipamt =BigDecimal.ZERO;
        BigDecimal invamt = BigDecimal.ZERO;
        BigDecimal txnamt =BigDecimal.ZERO;
        NumberFormat amt = new DecimalFormat("#000000000000");
        String tipstr = "";
        String invstr = "";
        txnamt = new BigDecimal(amount).multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).setScale(2, BigDecimal.ROUND_HALF_UP);
        if (tip.length() > 0){
             tipamt = new BigDecimal(tip).multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).setScale(2, BigDecimal.ROUND_HALF_UP);
            invstr = "A212"+amt.format(tipamt.doubleValue());

        }

        if (inv.length() > 0){
            //invamt = new BigDecimal(inv).multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).setScale(2, BigDecimal.ROUND_HALF_UP);
            invstr = "A3"+inv.length()+inv;

        }

        SimpleDateFormat dtformat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tsformat = new SimpleDateFormat("kk:mm:ss");
        String poimethod = staticqr.substring(0,2);
        String dynqr = "";
        String staticcrc= staticqr.substring(staticqr.indexOf("A9"));


        String tstamp = tsformat.format(new Date());
        String dstamp = dtformat.format(new Date());
        dynqr = (staticqr.substring(0,1)+"2"+staticqr.substring(2)+dstamp+"T"+tstamp
        +"A112"+amt.format(txnamt.doubleValue())+tipstr+invstr).replace(staticcrc,"").concat(staticcrc);

       // UUID idTwo = UUID.randomUUID();
       System.out.println("Static CRC"+staticcrc);
        return dynqr;
    }
}
