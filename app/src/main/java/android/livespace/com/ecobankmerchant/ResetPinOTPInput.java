package android.livespace.com.ecobankmerchant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.livespace.com.ecobankmerchant.dataobjects.AccountActivationStatusRequest;
import android.livespace.com.ecobankmerchant.dataobjects.AccountActivationStatusResponse;
import android.livespace.com.ecobankmerchant.dataobjects.ResetPinProcessor;
import android.livespace.com.ecobankmerchant.dataobjects.ResetPinRequest;
import android.livespace.com.ecobankmerchant.dataobjects.ResetPinResponse;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import static android.livespace.com.ecobankmerchant.dataobjects.AccountActivationProcessor.processmerchantotp;

/**
 * A login screen that offers login via email/password.
 */
public class ResetPinOTPInput extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;



    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText motp;
    private EditText mpinone;
    private EditText mpintwo;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin_otpinput);
        String otp="",merchantid="",phone="",email="";
        // Set up the login form.
        motp = (EditText) findViewById(R.id.otpwd);
        mpinone = (EditText) findViewById(R.id.pinone);
        mpintwo = (EditText) findViewById(R.id.pintwo);

        Intent intent = getIntent();
        Bundle bb = intent.getExtras();

        merchantid = bb.getString("MERCHANTID");
        phone = bb.getString("PHONE");

        Button motpbutton = (Button) findViewById(R.id.pinconfirmbutton);
        motpbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }




    private void resetActivate(){
        Log.v("PIN reset","Confirm OTP request initiated");
        Intent activateint = new Intent(this,ResetPassword.class);
        startActivity(activateint);
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        motp.setError(null);
        Intent intent = getIntent();
        Bundle bb = intent.getExtras();
        String otp = bb.getString("OTP");
        String merchantid = bb.getString("MERCHANTID");
        System.out.println("Setting Password for merchant ID: "+ merchantid);

        // Store values at the time of the login attempt.
        String mid="";
        String password = motp.getText().toString();
        String pone = mpinone.getText().toString() != null ?  mpinone.getText().toString() :"";
        String ptwo = mpintwo.getText().toString() != null ?  mpintwo.getText().toString() :"";
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            motp.setError(getString(R.string.error_invalid_password));
            focusView = motp;
            cancel = true;
        }
        if(pone.length() ==0 ){
            mpinone.setError(getString(R.string.error_invalid_password));
            focusView = mpinone;
            cancel = true;
        }

        if(ptwo.length() ==0 ){
            mpintwo.setError(getString(R.string.error_invalid_password));
            focusView = mpinone;
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
            //resetActivate();
            mAuthTask = new UserLoginTask(mid, password,pone);
            mAuthTask.execute((Void) null);

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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

       // addEmailsToAutoComplete(emails);
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, ResetPinResponse> {

        private final String mMid;
        private final String mOtp;
        private final String mPwdone;
        ResetPinResponse rpinresp = new ResetPinResponse();
        ResetPinRequest rpinreq = new ResetPinRequest();

        UserLoginTask(String mid, String otp,String pin) {
            mMid = mid;
            mOtp = otp;
            mPwdone = pin;
        }

        @Override
        protected ResetPinResponse doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            ResetPinRequest rpinreq = new ResetPinRequest();
            rpinreq.setMerchantid(mMid);
            rpinreq.setOtp(mOtp);
            rpinreq.setPinone(mPwdone);
            rpinreq.setPintwo(mPwdone);
            try {
                // Simulate network access.
                //Thread.sleep(2000);
                rpinresp = ResetPinProcessor.processmerchantotp(rpinreq);
                return rpinresp;
            } catch (Exception e) {

                String errorresult = rpinresp.getResponsecode()!=null?rpinresp.getResponsecode():"96";
                if(!errorresult.equalsIgnoreCase("000")){
                    rpinreq.setMerchantid("M"+rpinreq.getMerchantid());
                    System.out.println("Attempt to authenticate the merchant using alternated MID "+ rpinreq.getMerchantid());
                    rpinresp=  ResetPinProcessor.processmerchantotp(rpinreq);
                }

                return rpinresp;
            }




        }

        @Override
        protected void onPostExecute(final ResetPinResponse success) {
            mAuthTask = null;
            showProgress(false);
            String responsecode = success.getResponsecode() != null ? success.getResponsecode():"96";
            if (responsecode.equalsIgnoreCase("000")) {
                System.out.println("Password successfully reset, "+ success.getResponsemessage());
                Intent gotosuccess = new Intent(getBaseContext(),Success.class);
                startActivity(gotosuccess);
                finish();
            } else {
                mpinone.setError(getString(R.string.systemerror));
                mpinone.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

