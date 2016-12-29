package android.livespace.com.ecobankmerchant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.livespace.com.ecobankmerchant.Utilities.NetworkUtils;
import android.livespace.com.ecobankmerchant.dataobjects.AccountActivationProcessor;
import android.livespace.com.ecobankmerchant.dataobjects.AccountActivationStatusRequest;
import android.livespace.com.ecobankmerchant.dataobjects.AccountActivationStatusResponse;
import static android.livespace.com.ecobankmerchant.Utilities.SMSOtp.*;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class Accountactivation extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final int REQUEST_READ_CONTACTS = 0;
    private UserLoginTask mAuthTask = null;
    private EditText mMerchantIDView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountactivation);
        // Set up the login form.
        mMerchantIDView = (EditText) findViewById(R.id.merchantid);
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







    /**
     * Callback received when a permissions request has been completed.
     */

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mMerchantIDView.setError(null);


        // Store values at the time of the login attempt.
        String mMid = mMerchantIDView.getText().toString();


        boolean cancel = false;
        View focusView = null;



        // Check for a valid email address.
        if (TextUtils.isEmpty(mMid)) {
            mMerchantIDView.setError(getString(R.string.error_field_required));
            focusView = mMerchantIDView;
            cancel = true;
        } else if (!isValidMerchant(mMid)) {
            mMerchantIDView.setError(getString(R.string.error_invalid_email));
            focusView = mMerchantIDView;
            cancel = true;
        }

        if(!NetworkUtils.isNetworkAvailable(getBaseContext())){

            mMerchantIDView.setError(getString(R.string.networkfailtryagain));
            focusView = mMerchantIDView;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {

            showProgress(true);

            System.out.println("Running a Merchant Activation request on the merchant ID"+mMid);
            mAuthTask = new UserLoginTask(mMid);
            mAuthTask.execute((Void) null);
        }
    }

    private void gotopasswordset(String mid,String otp,String phn,String email){
        final String fmid = mid;
        final String fotp = otp;
        final String phone = phn;
        final String mail =email;
        Intent pwdintent = new Intent(getBaseContext(),setpassword.class);
        pwdintent.putExtra("MERCHANTID",fmid);
        pwdintent.putExtra("OTP",fotp);
        pwdintent.putExtra("PHONE",phone);
        pwdintent.putExtra("EMAIL",mail);

        startActivity(pwdintent);

    }
    private boolean isValidMerchant(String merchantid) {

        if(merchantid.length() >= 8){
            return true;
        }else{
            return false;
        }


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

        //addEmailsToAutoComplete(emails);
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
    public class UserLoginTask extends AsyncTask<Void, Void, AccountActivationStatusResponse> {

        private final String mMid;
        AccountActivationStatusResponse aresp = new AccountActivationStatusResponse();
        AccountActivationStatusRequest areq = new AccountActivationStatusRequest();
        UserLoginTask(String mid) {
            mMid = mid;
            areq.setMerchantid(mid);

        }

        @Override
        protected AccountActivationStatusResponse doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                //Thread.sleep(2000);
                AccountActivationStatusRequest areq = new AccountActivationStatusRequest();
                areq.setMerchantid(mMid);
                areq.setChannel("MOBILE");
                System.out.println("About to proces account activation");
                System.out.println("Network Connectivity Status:" + NetworkUtils.isNetworkAvailable(getBaseContext()));

                aresp = AccountActivationProcessor.processmerchantid(areq);
                System.out.println("what i got back" + aresp.getResponsemessage());
                if(aresp.getResponsecode().equalsIgnoreCase("000")){
                    String phone = aresp.getPhonenumber() != null ?aresp.getPhonenumber():"";
                    String email = aresp.getEmailaddress() != null ? aresp.getEmailaddress():"";
                    String smsotp =aresp.getOtp() != null ? aresp.getOtp():"";
                   // sendOtp(phone,smsotp);
                }
            } catch (Exception e) {
                String errorresult = aresp.getResponsecode()!=null?aresp.getResponsecode():"96";
                if(!errorresult.equalsIgnoreCase("000")){
                    aresp.setResponsecode("96");
                }
                return aresp;
            }



            // TODO: register the new account here.
            return aresp;
        }

        @Override
        protected void onPostExecute(final AccountActivationStatusResponse success) {
            String phone = "";
            String email ="";
            String smsotp ="";
            mAuthTask = null;
            showProgress(false);
            String responsecode = success.getResponsecode() != null ? success.getResponsecode():"96";
            if (responsecode.equalsIgnoreCase("000")) {
                phone = success.getPhonenumber() != null ?success.getPhonenumber():"";
                email = success.getEmailaddress() != null ? success.getEmailaddress():"";
                smsotp =success.getOtp() != null ? success.getOtp():"";
                //sendOtp(phone,smsotp);
                gotopasswordset(mMid,smsotp,phone,email);
                finish();
            } else {
                mMerchantIDView.setError(getString(R.string.error_invalid_merchant_id));
                mMerchantIDView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

