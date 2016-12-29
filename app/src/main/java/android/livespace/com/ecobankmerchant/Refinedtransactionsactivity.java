package android.livespace.com.ecobankmerchant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Refinedtransactionsactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_refinedtransactionsactivity);
        ListView trnlist  = (ListView) findViewById(R.id.txnlist);

        String[] mytxns = {"Red","Blue","Orange","Purple","Violet","Magenta"};
        ArrayAdapter<String> myarr = new ArrayAdapter<String>(this,R.layout.refinedlistitem,mytxns);
        trnlist.setAdapter(myarr);
    }
}
