package android.livespace.com.ecobankmerchant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.livespace.com.ecobankmerchant.dataobjects.ChangePinProcessor;
import android.livespace.com.ecobankmerchant.dataobjects.ChangePinRequest;
import android.livespace.com.ecobankmerchant.dataobjects.ChangePinResponse;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.livespace.com.ecobankmerchant.Utilities.SMSOtp.sendOtp;


public class ChangePassword extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final int REQUEST_READ_CONTACTS = 0;
    public static String MERCHANT_SETTINGS = "com.ecobankmerchant.merchant.preferences";

    private ChangePasswordTask mAuthTask = null;
    private OtpRequestTask mOtpTask = null;
    // UI references.
    private EditText monetimepwd;
    private EditText moldpin;
    private EditText mnewpinone;
    private EditText mnewpintwo;
    private View mProgressView;
    private View mLoginFormView;
    public String chgpinmid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences   chgpinprefs = getSharedPreferences(MERCHANT_SETTINGS,Activity.MODE_PRIVATE);
        chgpinmid = chgpinprefs.getString("MERCHANT_ID","xx");
        setContentView(R.layout.activity_change_password);


        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Authorisation Code");
        pd.setMessage("An Authorisation code will be sent to your email / phone to confirm this update");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCanceledOnTouchOutside(true);

        pd.setButton(Dialog.BUTTON_POSITIVE,"Confirm",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getonetimepassword(chgpinmid);
                Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
                mEmailSignInButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptLogin();
                    }
                });

                mLoginFormView = findViewById(R.id.login_form);
                mProgressView = findViewById(R.id.login_progress);
            }
        });

        pd.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });


        pd.show();

        //ConfirmChangeDialog();
        //Call the getotpmethod

        // Set up the login form.
        monetimepwd = (EditText)findViewById(R.id.onetimepwd);
        moldpin = (EditText) findViewById(R.id.oldpin);
        mnewpinone = (EditText) findViewById(R.id.newpinone);
        mnewpintwo = (EditText) findViewById(R.id.newpinone);

    }


    public void gotoSuccessPage(){
        Intent gotoSuccess = new Intent(this,Success.class);
        startActivity(gotoSuccess);
    }

    public void getonetimepassword(String chgpinmid){
        boolean response = false;
        String mid;

        ChangePinRequest cpreqObj = new ChangePinRequest();
        cpreqObj.setMerchantid(chgpinmid);
        cpreqObj.setChannel("MOBILE");
        mOtpTask = new OtpRequestTask(cpreqObj);
        mOtpTask.execute((Void) null);

    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        moldpin.setError(null);
        mnewpinone.setError(null);

        // Store values at the time of the login attempt.
        String oldpin = moldpin.getText().toString();
        String pwdone = mnewpinone.getText().toString();
        String otpassword = monetimepwd.getText().toString();
        String pwdtwo = mnewpintwo.getText().toString();
        boolean cancel = false;
        View focusView = null;


        // Check for a valid OTP, if the user entered one.
        if ((otpassword.length() < 6)) {
            monetimepwd.setError(getString(R.string.error_invalid_pin));
            focusView = monetimepwd;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if ((oldpin.length() < 6)) {
            mnewpinone.setError(getString(R.string.error_invalid_password));
            focusView = mnewpinone;
            cancel = true;
        }

        //Check for an empty password
        if ((oldpin.length() ==0)||!(oldpin != null)) {
            mnewpinone.setError(getString(R.string.error_invalid_password));
            focusView = mnewpinone;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(pwdone) ) {
            moldpin.setError(getString(R.string.error_invalid_password));
            focusView = moldpin;
            cancel = true;
        }

        // Check if the pin one and pin two entered do not match
        if (TextUtils.isEmpty(pwdtwo) || !(pwdtwo.equalsIgnoreCase(pwdone))) {
            mnewpintwo.setError(getString(R.string.reset_pin_must_match));
            focusView = mnewpintwo;
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
            mAuthTask = new ChangePasswordTask(otpassword, oldpin,pwdone,pwdtwo,chgpinmid);
            mAuthTask.execute((Void) null);
        }
    }

    public void ConfirmChangeDialog(){
        boolean result=false;
        AlertDialog.Builder  conD = new AlertDialog.Builder(this);
        conD.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        conD.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                   dialog.cancel();
            }
        });


        conD.setTitle("Confirm Account Settings Change");
        conD.setMessage("This action will update your login Credentials");
        conD.show();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length()  >= 6;
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

    public class OtpRequestTask extends AsyncTask<Void,Void,ChangePinResponse>{

        private String chgpinmid;
        ChangePinRequest cpreq = new ChangePinRequest();
        ChangePinResponse cpresp = new ChangePinResponse();
        OtpRequestTask(ChangePinRequest cr){
            chgpinmid = cr.getMerchantid();
        }

        @Override
        protected ChangePinResponse doInBackground(Void... params) {

            String methodresponse ="";
            cpreq.setMerchantid(chgpinmid);
            cpreq.setChannel("MOBILE");
            try {
                cpresp = ChangePinProcessor.processmerchantid(cpreq);
                if(cpresp.getResponsecode().equalsIgnoreCase("000")){
                    sendOtp(cpresp.getPhonenumber(),cpresp.getOtp());
                    System.out.println("Response from get merchant " + cpresp.getResponsecode());
                    System.out.println("OTP from get merchant " + cpresp.getOtp());
                }

            }catch(Exception e){
                methodresponse = cpresp.getResponsecode() != null ? cpresp.getResponsecode() :"96";
                cpresp.setResponsecode(methodresponse);
                cpresp.setResponsemessage("System Currently Unavailable");
                return cpresp;
            }
            return cpresp;
        }

        @Override
        protected void onPostExecute(ChangePinResponse success) {
            showProgress(false);

            if(success !=null){
                if(success.getResponsecode().equalsIgnoreCase("000")){
                    System.out.println("The Change pin otp and details have been supplied");

                }
            }else{
                success.setResponsemessage("96");
                success.setResponsemessage("The System failed to process!");
            }
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ChangePasswordTask extends AsyncTask<Void, Void, ChangePinResponse> {

        private final String onetimepwd;
        private final String oldpin;
        private final String newpinone;
        private final String newpintwo;
        private final String merchantid;

        ChangePasswordTask(String otp, String oldpwd,String newpwdone,String newpwdtwo,String mid) {
            onetimepwd = otp;
            oldpin = oldpwd;
            newpinone=newpwdone;
            newpintwo=newpwdtwo;
            merchantid=mid;
        }

        @Override
        protected ChangePinResponse doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            ChangePinResponse cresp = new ChangePinResponse();
            ChangePinRequest creq = new ChangePinRequest();
            String errorresult="";
            try {
                // Simulate network access.
                //Thread.sleep(2000);
                creq.setMerchantid(merchantid);
                creq.setOtp(onetimepwd);
                creq.setPinold(oldpin);
                creq.setPinone(newpinone);
                creq.setPintwo(newpintwo);


                cresp = ChangePinProcessor.processmerchantotp(creq);
            } catch (Exception x) {
                errorresult = cresp.getResponsecode() != null ? cresp.getResponsecode():"96";
                System.out.println("Error Occured: " + errorresult + x.getMessage());

            }



            // TODO: register the new account here.
            return cresp;
        }

        @Override
        protected void onPostExecute(final ChangePinResponse success) {
            mAuthTask = null;
            showProgress(false);
            String result="";
            result = success.getResponsecode() != null ? success.getResponsecode():"96";
            if (result.equalsIgnoreCase("000")) {
                System.out.println("Password Successfully changed navigate to login page and Invalidate Session");
                gotoSuccessPage();
                finish();
            } else {
                moldpin.setError(getString(R.string.error_incorrect_password));
                moldpin.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

