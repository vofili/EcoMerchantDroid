package android.livespace.com.ecobankmerchant;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import static java.security.AccessController.getContext;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewDynamicQR.OnFragmentInteractionListener,
        DynamicQrcode.OnFragmentInteractionListener,
        TransactionGet.OnFragmentInteractionListener,
        LogOut.OnFragmentInteractionListener,
        TransactionsListing.OnFragmentInteractionListener,
        MasterPassHome.OnFragmentInteractionListener,
        MVisaHome.OnFragmentInteractionListener,NoTransactions.OnFragmentInteractionListener {
    public static String MERCHANT_SETTINGS = "com.ecobankmerchant.merchant.preferences";
    SharedPreferences mPrefs;
    ImageView countryImg;
    TextView mMerchantName;
    View hdrView;
    String countryflag="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Get Data for merchant lookup  from bundle
        //Bundle mlextras = getIntent().getExtras();
        mPrefs = getSharedPreferences(MERCHANT_SETTINGS, Activity.MODE_PRIVATE);

        String scheme = mPrefs.getString("TERMINAL_SCHEME","MASTERCARD") != null ? mPrefs.getString("TERMINAL_SCHEME","MASTERCARD") :"MASTERCARD";
        //System.out.println("The retrieved scheme flag "+scheme);
        String  merchname = mPrefs.getString("MERCHANT_NAME","") != null ? mPrefs.getString("MERCHANT_NAME",""):"";
        countryflag = mPrefs.getString("AFFILIATECODE","") != null ? mPrefs.getString("AFFILIATECODE",""):"";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //System.out.println("The retrieved country flag "+countryflag);

        setSupportActionBar(toolbar);
        paymentNotificationInstantId xnoteid = new paymentNotificationInstantId();

        Fragment fragment = null;
        Class fragmentClass = null;
        if(scheme.equalsIgnoreCase("MASTERCARD")){
            fragmentClass = MasterPassHome.class;
        }else{
            fragmentClass = MVisaHome.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
       // fragment.setArguments(mlextras);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.homeframelayout, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        hdrView =  navigationView.getHeaderView(0);
        TextView merchantname = (TextView) hdrView.findViewById(R.id.merchantidname);
        TextView merchantcountryname = (TextView) hdrView.findViewById(R.id.headertitle);
        merchantname.setText(merchname);
        ImageView merchcountry = (ImageView)hdrView.findViewById(R.id.countryView);

        if(countryflag.equalsIgnoreCase("EKE")){
             merchcountry.setImageResource(R.drawable.kenya);
            merchantcountryname.setText("Kenya");
        }else if(countryflag.equalsIgnoreCase("ETG")){
             merchcountry.setImageResource(R.drawable.togo);
            merchantcountryname.setText("Togo");
        }else if(countryflag.equalsIgnoreCase("EGH")){
        merchcountry.setImageResource(R.drawable.ghana);
            merchantcountryname.setText("Ghana");
         }else if(countryflag.equalsIgnoreCase("ENG")){
        merchcountry.setImageResource(R.drawable.nigeria);
            merchantcountryname.setText("Nigeria");
         }

         else if(countryflag.equalsIgnoreCase("ESL")){
             merchcountry.setImageResource(R.drawable.sierra_leone);
            merchantcountryname.setText("Sierra Leone");
         }else if(countryflag.equalsIgnoreCase("EZW")){
             merchcountry.setImageResource(R.drawable.zimbabwe);
            merchantcountryname.setText("Zimbabwe");
         }else if(countryflag.equalsIgnoreCase("ERW")){
             merchcountry.setImageResource(R.drawable.rwanda);
            merchantcountryname.setText("Rwanda");
         }else if(countryflag.equalsIgnoreCase("ESD")){
             merchcountry.setImageResource(R.drawable.south_sudan);
            merchantcountryname.setText("South Sudan");
         }else if(countryflag.equalsIgnoreCase("ETZ")){
             merchcountry.setImageResource(R.drawable.tanzania);
            merchantcountryname.setText("Tanzania");
         }

         else if(countryflag.equalsIgnoreCase("EBI")){
             merchcountry.setImageResource(R.drawable.burundi);
            merchantcountryname.setText("Burundi");
         }else if(countryflag.equalsIgnoreCase("EUG")){
             merchcountry.setImageResource(R.drawable.uganda);
            merchantcountryname.setText("Uganda");
         }else if(countryflag.equalsIgnoreCase("EGM")){
             merchcountry.setImageResource(R.drawable.gambia);
            merchantcountryname.setText("Gambia");
         }else if(countryflag.equalsIgnoreCase("EGN")){
             merchcountry.setImageResource(R.drawable.guinea);
            merchantcountryname.setText("Guinea");
         }else if(countryflag.equalsIgnoreCase("ELR")){
             merchcountry.setImageResource(R.drawable.liberia);
            merchantcountryname.setText("Liberia");
         }

         else if(countryflag.equalsIgnoreCase("EZW")){
             merchcountry.setImageResource(R.drawable.zimbabwe);
            merchantcountryname.setText("Zimbabwe");
         }else if(countryflag.equalsIgnoreCase("EZM")){
             merchcountry.setImageResource(R.drawable.zambia);
            merchantcountryname.setText("Zambia");
         }else if(countryflag.equalsIgnoreCase("EMZ")){
             merchcountry.setImageResource(R.drawable.mozambique);
            merchantcountryname.setText("Mozambique");
         }else if(countryflag.equalsIgnoreCase("EMW")){
             merchcountry.setImageResource(R.drawable.malawi);
            merchantcountryname.setText("Malawi");
         }else if(countryflag.equalsIgnoreCase("ECD")){
             merchcountry.setImageResource(R.drawable.congo_drc);
            merchantcountryname.setText("Congo DRC");
         }
         else if(countryflag.equalsIgnoreCase("ETD"))
         {
             merchcountry.setImageResource(R.drawable.chad);
             merchantcountryname.setText("Chad");
         }else if(countryflag.equalsIgnoreCase("EST")){
             merchcountry.setImageResource(R.drawable.sao_tome_and_principe);
            merchantcountryname.setText("Ghana");
         }else if(countryflag.equalsIgnoreCase("Sao Tome and Principe")){
             merchcountry.setImageResource(R.drawable.equatorial_guinea);
         }else if(countryflag.equalsIgnoreCase("EGA")){
             merchcountry.setImageResource(R.drawable.gabon);
            merchantcountryname.setText("Gabon");
         }else if(countryflag.equalsIgnoreCase("ECM")){
             merchcountry.setImageResource(R.drawable.cameroon);
            merchantcountryname.setText("Cameroon");
         }

         else if(countryflag.equalsIgnoreCase("ECG")){
             merchcountry.setImageResource(R.drawable.congo);
            merchantcountryname.setText("Congo");
         }else if(countryflag.equalsIgnoreCase("ECF")){
             merchcountry.setImageResource(R.drawable.central_african_republic);
            merchantcountryname.setText("Central African Republic");
         }else if(countryflag.equalsIgnoreCase("ESN")){
             merchcountry.setImageResource(R.drawable.senegal);
            merchantcountryname.setText("Senegal");
         }else if(countryflag.equalsIgnoreCase("ENE")){
             merchcountry.setImageResource(R.drawable.niger);
            merchantcountryname.setText("Niger");
         }else if(countryflag.equalsIgnoreCase("EML")){
             merchcountry.setImageResource(R.drawable.mali);
            merchantcountryname.setText("Mali");
         }

         else if(countryflag.equalsIgnoreCase("EGW")){
             merchcountry.setImageResource(R.drawable.guinea_bissau);
            merchantcountryname.setText("Guinea Bissau");
         }else if(countryflag.equalsIgnoreCase("ECI")){
             merchcountry.setImageResource(R.drawable.cote_d_ivoire);
            merchantcountryname.setText("Cote D Ivoire");
         }else if(countryflag.equalsIgnoreCase("ECV")){
             merchcountry.setImageResource(R.drawable.cape_verde);
            merchantcountryname.setText("Cape Verde");
         }else if(countryflag.equalsIgnoreCase("EBJ")){
             merchcountry.setImageResource(R.drawable.benin);
            merchantcountryname.setText("Benin");
         }else if(countryflag.equalsIgnoreCase("EBF")){
             merchcountry.setImageResource(R.drawable.burkina_faso);
            merchantcountryname.setText("Burkina Faso");
         }


        navigationView.setNavigationItemSelectedListener(this);

//        countryImg = (ImageView) findViewById(R.id.logoimageView);
//        countryImg.setImageResource(R.drawable.ghana);
        //R.drawable.ghana);

    }


    @Override
    protected void onResume() {
        Log.v("App Resumed","The app has called on resume");
        super.onResume();

    }

    @Override
    protected void onRestart() {
        Log.v("App Restarted","The app has called on Restart");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Log.v("App Stopped","The app has called on stop");
        super.onStop();
    }

    public void getNotification(){
        int ntid=001;
        long vibratelen[] = {1000,1000,1000,1000};
        NotificationManager pmntMgr  = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder pmntBld = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ecobankmerchantappicon)
                .setContentTitle("Payment Notification")
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE)
                //.setVibrate(vibratelen)
                .setSound(alarmSound)
                .setContentText("Dear Merchant, a payment was just recieved from customer");
                Intent resint = new Intent(this,NotificationPaid.class);

                PendingIntent pendint = PendingIntent.getActivity(this,0,resint,PendingIntent.FLAG_UPDATE_CURRENT);
                pmntBld.setContentIntent(pendint);
                pmntMgr.notify(ntid,pmntBld.build());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void goToLogout(){
//        Intent xout = new Intent(this,LoginActivity.class);
//        startActivity(xout);
        Intent loginscreen=new Intent(this,LoginActivity.class);
        loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginscreen);
        //this.finish();

    }
    public void gotoChangePassword(){
        Intent chgpwd = new Intent(this,ChangePassword.class);
        startActivity(chgpwd);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.change_password) {

            gotoChangePassword();
        }
//        else if (id == R.id.change_password){
//            gotoChangePassword();
//        }
//        else if(id == R.id.menuitem){
//            Intent test = new Intent(this,Refinedtransactionsactivity.class);
//            startActivity(test);
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    Fragment contentFrag = null;
    Class fragmentClass;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.masterpasshome) {
            fragmentClass = MasterPassHome.class;
        } else if (id == R.id.mvisahome) {
            fragmentClass = MVisaHome.class;

        } else if (id == R.id.transactions) {
            fragmentClass = TransactionGet.class;
            //fragmentClass = TransactionsListing.class;

        }  else if (id == R.id.generateqrcode) {
            fragmentClass = NewDynamicQR.class;
            //fragmentClass = TransactionsListing.class;

        } else if (id == R.id.logout) {
            // Handle the log out action
            fragmentClass = LogOut.class;
            //goToLogout();
        }

        //retrieve bundle and pass Bundle args
        Bundle mlargs = getIntent().getExtras();

        try {
            contentFrag = (Fragment) fragmentClass.newInstance();
            //contentFrag.setArguments(mlargs);
        }catch(Exception ex){
            Log.v("Fatal Error" ,ex.getMessage());
        }

        FragmentManager contentfragmgr = getSupportFragmentManager();
        contentfragmgr.beginTransaction()
                        .replace(R.id.homeframelayout,contentFrag).commit();

        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
