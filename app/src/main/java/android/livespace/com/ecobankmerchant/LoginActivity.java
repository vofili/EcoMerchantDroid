package android.livespace.com.ecobankmerchant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.livespace.com.ecobankmerchant.Utilities.Configuration;
import android.livespace.com.ecobankmerchant.Utilities.NetworkUtils;
import android.livespace.com.ecobankmerchant.dataobjects.AuthenticateProcessor;
import android.livespace.com.ecobankmerchant.dataobjects.AuthenticateRequest;
import android.livespace.com.ecobankmerchant.dataobjects.AuthenticateResponse;
import android.livespace.com.ecobankmerchant.dataobjects.Merchant;
import android.livespace.com.ecobankmerchant.dataobjects.MerchantHeader;
import android.livespace.com.ecobankmerchant.dataobjects.MerchantLookupProcessor;
import android.livespace.com.ecobankmerchant.dataobjects.MerchantLookupRequest;
import android.livespace.com.ecobankmerchant.dataobjects.MerchantLookupResponse;
import android.livespace.com.ecobankmerchant.dataobjects.MultiMerchant;
import android.livespace.com.ecobankmerchant.dataobjects.Terminal;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {



    public static String MERCHANT_SETTINGS = "com.ecobankmerchant.merchant.preferences";
    private UserLoginTask mAuthTask = null;
    //Slider
    ViewFlipper mloginView;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    String firebasetoken;
    String userlanguage;
    String versionID =Configuration.versionid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getText(R.string.loginactivityname));

        setContentView(R.layout.activity_login);
        mloginView = (ViewFlipper) findViewById(R.id.partnerlogo);
        mloginView.setAutoStart(true);
        mloginView.setFlipInterval(3000);
        mloginView.startFlipping();
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.merchantid);
        //populateAutoComplete();
        TransactionNoteService ts = new TransactionNoteService();
        firebasetoken = ts.getRegtoken();
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO do something
                    handled = true;
                    attemptLogin();
                }
                return handled;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //gotosimplelist();
                attemptLogin();
            }
        });

        Button mActivateButton = (Button) findViewById(R.id.activatebutton);
        mActivateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivate();
            }
        });

        Button mResetButton = (Button) findViewById(R.id.resetpinbutton);
        mResetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetActivate();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        boolean cancel = false;
        View focusView = null;
        String emailval = "";
        String password="";
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString() != null ? mEmailView.getText().toString() :"";

        password= mPasswordView.getText().toString();
        if(email.isEmpty()){
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }else{
             emailval = email;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email) ) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isMerchantIdValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_merchant_id));
            focusView = mEmailView;
            cancel = true;
        }

        if(!NetworkUtils.isNetworkAvailable(getBaseContext())){

            mEmailView.setError(getString(R.string.networkfailtryagain));
            focusView = mEmailView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            showProgress(true);
            //checkMerchantActivation(email);
            //email = "066538047";
            mAuthTask = new UserLoginTask(emailval, password,firebasetoken);
            mAuthTask.execute((Void) null);

        }
    }


//    public void checkMerchantActivation(String currmerchant){
//        //read preferences to check the merchant ID is activated
//
//        SharedPreferences merchantprefs = getSharedPreferences(MERCHANT_SETTINGS,Activity.MODE_PRIVATE);
//        String merchantfromprefs = merchantprefs.getString("activatedmerchant","");
//        if(!currmerchant.equalsIgnoreCase(merchantfromprefs)){
//            Intent actIntent = new Intent(this,Accountactivation.class);
//            startActivity(actIntent);
//        }
//
//    }

    private void gotoActivate(){
        Log.v("Activation status","Account activation request initiated");
        Intent activateint = new Intent(this,Accountactivation.class);
        startActivity(activateint);

    }

    private void gotoUpdateVersion(){
        Log.v("Invalid version status","Application upgrade required");
        Intent upgradeint = new Intent(this,UpgradeVersion.class);
        startActivity(upgradeint);
        finish();
    }


    private void resetActivate(){
        Log.v("PIN reset","PIN reset request initiated");
        Intent activateint = new Intent(this,ResetPinMerchantInput.class);
       // Intent activateint = new Intent(this,messageboard.class);
        startActivity(activateint);

    }

    public void gotoHome(AuthenticateResponse mresp){
        Merchant m = new Merchant();
        MerchantHeader mh = new MerchantHeader();
        //List <Terminal> tt = new ArrayList<Terminal>();
        Terminal tt = new Terminal();


        m  = mresp.getFetchMerchantInfoResponse();
        if(m != null){

            SharedPreferences mPrefs = getSharedPreferences(MERCHANT_SETTINGS,Activity.MODE_PRIVATE);
            SharedPreferences.Editor mPrefsEdit = mPrefs.edit();

            mh = m.getHostHeaderInfo();
                if(m.getTerminalInfo().get(0) != null) {
                    tt = m.getTerminalInfo().get(0);
                }
            Intent activatehome = new Intent(this,Home.class);
            activatehome.putExtra("MERCHANT_FIRSTNAME",m.getFirstName());
            activatehome.putExtra("MERCHANT_NAME",m.getMerchantName());
            activatehome.putExtra("MERCHANT_ID",m.getMerchantId());
            activatehome.putExtra("MERCHANT_MIDDLENAME",m.getMiddleName());
            activatehome.putExtra("MERCHANT_EMAIL",m.getEmail());
            activatehome.putExtra("MERCHANT_DOB",m.getDob());
            activatehome.putExtra("MERCHANT_AREA",m.getArea());
            activatehome.putExtra("MERCHANT_ADDR",m.getAddressLine1());
            activatehome.putExtra("MERCHANT_COUNTRY",m.getCountry());
            activatehome.putExtra("MERCHANT_CITY",m.getCity());
            activatehome.putExtra("MERCHANT_MNO",m.getMobileNo());
            activatehome.putExtra("AFFILIATECODE",mh.getAffiliateCode());

            //Store these settings to shared preferences
            mPrefsEdit.putString("MERCHANT_FIRSTNAME",m.getFirstName());
            mPrefsEdit.putString("MERCHANT_NAME",m.getMerchantName());
            mPrefsEdit.putString("MERCHANT_ID",m.getMerchantId());
            mPrefsEdit.putString("MERCHANT_MIDDLENAME",m.getMiddleName());
            mPrefsEdit.putString("MERCHANT_EMAIL",m.getMiddleName());
            mPrefsEdit.putString("MERCHANT_DOB",m.getDob());
            mPrefsEdit.putString("MERCHANT_AREA",m.getArea());
            mPrefsEdit.putString("MERCHANT_ADDR",m.getAddressLine1());
            mPrefsEdit.putString("MERCHANT_COUNTRY",m.getCountry());
            mPrefsEdit.putString("MERCHANT_CITY",m.getCity());
            mPrefsEdit.putString("MERCHANT_MNO",m.getMobileNo());
            mPrefsEdit.putString("AFFILIATECODE",mh.getAffiliateCode());
            mPrefsEdit.putString("activatedmerchantname",m.getMerchantName());
            mPrefsEdit.putString("activatedmerchant",m.getMerchantId());
            mPrefsEdit.putString("sessiontoken",mresp.getAuthorization());
            mPrefsEdit.putString("fcmtoken",firebasetoken);
            mPrefsEdit.putBoolean("isactivated",true);
            mPrefsEdit.putString("requestingaffiliate",mh.getAffiliateCode());
            mPrefsEdit.commit();
            if(tt != null){

               // Iterator<Terminal> txnitr = tt.iterator();
                //activatehome.putExtra("NO_OF_TERMINAL",tt.);
                //while(txnitr.hasNext()) {
                    activatehome.putExtra("TERMINAL_ID", tt.getTerminalId());
                    activatehome.putExtra("TERMINAL_QR", tt.getQrCode());
                    activatehome.putExtra("TERMINAL_SCHEME", tt.getSchemeType());
                    mPrefsEdit.putString("terminalqr",tt.getQrCode());
                mPrefsEdit.putString("TERMINAL_SCHEME", tt.getSchemeType());
                mPrefsEdit.putString("TERMINAL_ID", tt.getTerminalId());
                mPrefsEdit.putString("TERMINAL_QR", tt.getQrCode());

                System.out.println("Setting preferences for Terminal: "+"/"+ tt.getSchemeType() +"/"+ tt.getTerminalId()+"/"+tt.getQrCode() );
                }
            //}


            startActivity(activatehome);

        }


    }


    private boolean isMerchantIdValid(String mid) {
        if (mid.length() >= 8) {
            return true;
        }else{
            return false;
        }
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

      //  addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }



    public class UserLoginTask extends AsyncTask<Void, Void, AuthenticateResponse> {

        private final String merchantid;
        private final String mpassword;
        private final String ftoken;
        UserLoginTask( String email, String password,String ft) {
            merchantid = email;
            mpassword = password;
            ftoken = ft;
        }

        @Override
        protected AuthenticateResponse doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            AuthenticateResponse mresp = new AuthenticateResponse();
            AuthenticateResponse multimresp = new AuthenticateResponse();
            MultiMerchant ml = new MultiMerchant();

            String userlang="";
//            mresp.getFetchMerchantInfoResponse().getHostHeaderInfo().setResponseCode("96");
//            mresp.getFetchMerchantInfoResponse().getHostHeaderInfo().setResponseMessage("System Malfunction");
            AuthenticateRequest authreq = new AuthenticateRequest();
            userlang = (Locale.getDefault().getDisplayLanguage() != null) ? Locale.getDefault().getDisplayLanguage(): "EN";
            try {
                // Simulate network access.
                //Thread.sleep(2000);


                String schemeletter ="";
                authreq.setFcmtoken(ftoken);
                authreq.setSchemetype("MASTERCARD");
                authreq.setChannel("MOBILE");
                authreq.setMerchantid(merchantid);
                authreq.setPassword(mpassword);
                authreq.setAffiliatecode("");
                authreq.setVersion(versionID);
                authreq.setRegistrationtoken(userlang);
                //authreq.setAffiliatecode("EGH");
                authreq.setTerminalid("");
                Log.v("Auth BackGround Resp", authreq.toString());
                mresp = AuthenticateProcessor.process(authreq);
                Log.v("Auth BackGround Resp", mresp.toString());
            } catch (Exception e) {
                authreq.setMerchantid(merchantid);
                authreq.setPassword(mpassword);
                authreq.setAffiliatecode("");
                //authreq.setAffiliatecode("EGH");
                authreq.setTerminalid("");
                authreq.setFcmtoken(ftoken);
                authreq.setChannel("MOBILE");
                mresp = AuthenticateProcessor.process(authreq);
                //System.out.println("Error occured in Background Task" + e.getMessage());
                return mresp;
            }


            // TODO: register the new account here.
            return mresp;
        }

        @Override
        protected void onPostExecute(final AuthenticateResponse success) {
            mAuthTask = null;

            String newresult = success.getResponsecode() != null ? success.getResponsecode():"96";
            if (newresult.equalsIgnoreCase("000")) {
                //System.out.println("Response From Webservice Endpoint:" + success.getResponsemessage());
                if (success.getFetchMerchantInfoResponse().getHostHeaderInfo().getResponseCode().equalsIgnoreCase("000")){
                    //System.out.println("Response From Webservice Endpoint:" + success.getResponsemessage());
                SharedPreferences mprefs = getSharedPreferences(MERCHANT_SETTINGS, Activity.MODE_PRIVATE);
                SharedPreferences.Editor mprefseditor = mprefs.edit();
                mprefseditor.putString("AUTH_KEY", success.getAuthorization());
                mprefseditor.putString("MERCHANT_ID", success.getFetchMerchantInfoResponse().getMerchantId());
                    mprefseditor.putString("AUTH_KEY", success.getAuthorization());
                    mprefseditor.putString("TERMINAL_ID", success.getFetchMerchantInfoResponse().getTerminalInfo().get(0).getTerminalId());
                    mprefseditor.putString("TERMINAL_QR", success.getFetchMerchantInfoResponse().getTerminalInfo().get(0).getQrCode());
                    mprefseditor.putString("MERCHANT_FIRSTNAME",success.getFetchMerchantInfoResponse().getFirstName());
                    mprefseditor.putString("MERCHANT_NAME",success.getFetchMerchantInfoResponse().getMerchantName()) ;
                    mprefseditor.putString("MERCHANT_MIDDLENAME",success.getFetchMerchantInfoResponse().getMiddleName());
                    mprefseditor.putString("MERCHANT_EMAIL",success.getFetchMerchantInfoResponse().getEmail()) ;
                    mprefseditor.putString("MERCHANT_DOB",success.getFetchMerchantInfoResponse().getDob());
                    mprefseditor.putString("MERCHANT_AREA",success.getFetchMerchantInfoResponse().getArea());
                    mprefseditor.putString("MERCHANT_ADDR",success.getFetchMerchantInfoResponse().getAddressLine1());
                    mprefseditor.putString("MERCHANT_COUNTRY",success.getFetchMerchantInfoResponse().getCountry());
                    mprefseditor.putString("MERCHANT_CITY",success.getFetchMerchantInfoResponse().getCity());
                mprefseditor.commit();
                gotoHome(success);
                showProgress(false);
                finish();
                }else {
                    showProgress(false);
                    mPasswordView.setError(success.getFetchMerchantInfoResponse().getHostHeaderInfo().getResponseMessage());
                    mPasswordView.requestFocus();
                }
            } else if(newresult.equalsIgnoreCase("E14")){
                gotoUpdateVersion();
            } else {
                showProgress(false);
                if(success.getResponsemessage()!= null){
                 mPasswordView.setError(success.getResponsemessage());
                }else{
                    mPasswordView.setError(getString(R.string.error_on_login_credentials));
                }
                //System.out.println("Error Occured" + success.getResponsecode() + " Error message "+success.getResponsemessage());

                mPasswordView.requestFocus();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
    public class TransactionNoteService extends FirebaseInstanceIdService {
        public String getRegtoken() {
            return regtoken;
        }

        public void setRegtoken(String regtoken) {
            this.regtoken = regtoken;
        }

        String regtoken;
        public TransactionNoteService(){
            super();
            onTokenRefresh();

        }
        @Override
        public void onTokenRefresh() {
            String refreshedtoken = FirebaseInstanceId.getInstance().getToken();
            this.regtoken = refreshedtoken;
            //System.out.println("Refreshed token--> " + refreshedtoken);
            FirebaseCrash.log("firebase refresh token"+refreshedtoken);
        }

    }
}

