package android.livespace.com.ecobankmerchant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotificationPaid extends AppCompatActivity {

    TextView mAmount;
    TextView mRef;
    TextView mNarration;
    TextView mTerminal;
    TextView mMerchant;
    TextView mDateTime;
    TextView mCustomername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_paid);

        Bundle notebundle = getIntent().getExtras();

        String noteref="",noteamount="",notenarration="",notecurrency="",
                notedatetime="",notemerchantid="",noteccy="",noteterminal="";

        mAmount = (TextView) findViewById(R.id.notificationamount);
        mRef = (TextView) findViewById(R.id.noteref);
        mNarration = (TextView) findViewById(R.id.notificationpostdescription);
        mDateTime = (TextView) findViewById(R.id.notedate);
        if(notebundle != null) {
            noteref = notebundle.getString("REF");
            noteamount=notebundle.getString("AMT");
            notenarration=notebundle.getString("NARRATION");
            notecurrency = notebundle.getString("CCY");
            notedatetime=notebundle.getString("DATE");
            notemerchantid=notebundle.getString("MERCHANTID");
            noteterminal=notebundle.getString("TERMINALID");

            mRef.setText(noteref);
            mNarration.setText(notenarration);
            mAmount.setText(notecurrency+" "+noteamount);
            mDateTime.setText(notedatetime);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        gobuttononnotifier();

    }

    public void createPayNotification(String terminalid){


    }

    public void gohome(){
//            finish();
//            System.exit(0);
        Intent newhome = new Intent(this,Home.class);
        startActivity(newhome);
    }
    public void gobuttononnotifier(){

        Button gobut = (Button) findViewById(R.id.takemehome);
        gobut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gohome();
            }
        });
    }

}
