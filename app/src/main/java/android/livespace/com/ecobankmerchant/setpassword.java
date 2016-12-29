package android.livespace.com.ecobankmerchant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.livespace.com.ecobankmerchant.Utilities.NetworkUtils;
import android.livespace.com.ecobankmerchant.dataobjects.AccountActivationStatusRequest;
import android.livespace.com.ecobankmerchant.dataobjects.AccountActivationStatusResponse;
import android.livespace.com.ecobankmerchant.dataobjects.ResetPinProcessor;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.livespace.com.ecobankmerchant.Utilities.SMSOtp.*;
import static android.livespace.com.ecobankmerchant.dataobjects.AccountActivationProcessor.processmerchantotp;
import static android.livespace.com.ecobankmerchant.dataobjects.ResetPinProcessor.resetaccountpin;

/**
 * A login screen that offers login via email/password.
 */
public class setpassword extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText monetimepwd;
    private EditText mpinone;
    private EditText mpintwo;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String otp="",merchantid="",phone="",email="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);
        // Set up the login form.
        monetimepwd = (EditText) findViewById(R.id.otpwd);
        mpinone = (EditText) findViewById(R.id.pinone);
        mpintwo = (EditText) findViewById(R.id.pintwo);

        Intent intent = getIntent();
        Bundle bb = intent.getExtras();
        otp = bb.getString("OTP");
        merchantid = bb.getString("MERCHANTID");
        phone = bb.getString("PHONE");


       // Toast.makeText(this,"Please use this token code "+otp+" to complete this authorisation.", Toast.LENGTH_LONG).show();

        mpinone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.pinconfirmbutton);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }



    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
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
        Intent intent = getIntent();
        Bundle bb = intent.getExtras();
        String merchantid = bb.getString("MERCHANTID");
        System.out.println("Setting Password for merchant ID: "+ merchantid);
        // Reset errors.
        monetimepwd.setError(null);
        mpinone.setError(null);

        // Store values at the time of the login attempt.
        String onetimepassword = monetimepwd.getText().toString();
        String passwordone = mpinone.getText().toString();
        String passwordtwo = mpintwo.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!(passwordone != null) || !(passwordone.length() >= 6)) {
            mpinone.setError(getString(R.string.error_invalid_password));
            focusView = mpinone;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!(passwordone.equalsIgnoreCase(passwordtwo))) {
            mpintwo.setError(getString(R.string.reset_pin_must_match));
            focusView = mpintwo;
            cancel = true;
        }

        if(!NetworkUtils.isNetworkAvailable(getBaseContext())){

            monetimepwd.setError(getString(R.string.networkfailtryagain));
            focusView = monetimepwd;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(onetimepassword)) {
            monetimepwd.setError(getString(R.string.title_activity_accountactivationotp));
            focusView = monetimepwd;
            cancel = true;
        } else if (!isPinValid(passwordone)) {
            mpinone.setError(getString(R.string.error_invalid_pin));
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

//            Intent newhome = new Intent(this,LoginActivity.class);
//            startActivity(newhome);
            mAuthTask = new UserLoginTask(merchantid, onetimepassword,passwordone);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPinValid(String pin) {

        return (pin.length() >= 6);
    }

    private boolean isPinEqual(String pinone,String pintwo) {

        return (pinone.equalsIgnoreCase(pintwo));
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
        private final String mOtp;
        private final String mPwdone;


        UserLoginTask(String mid, String otp,String pin) {
            mMid = mid;
            mOtp = otp;
            mPwdone = pin;
        }

        @Override
        protected AccountActivationStatusResponse doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            AccountActivationStatusResponse aresp = new AccountActivationStatusResponse();
            AccountActivationStatusRequest areq = new AccountActivationStatusRequest();
            areq.setMerchantid(mMid);
            areq.setOtp(mOtp);
            areq.setPinone(mPwdone);
            areq.setPintwo(mPwdone);
            areq.setChannel("MOBILE");
            try {
                // Simulate network access.
                //Thread.sleep(2000);
                //
                aresp = ResetPinProcessor.resetaccountpin(areq);

                return aresp;
            } catch (Exception e) {
                String errorresult= aresp.getResponsecode() != null ? aresp.getResponsecode():"96";
                if(errorresult.equalsIgnoreCase("96")){
                    aresp.setResponsemessage("An Error occurred while setting the PIN");
                }
                return aresp;
            }


        }

        @Override
        protected void onPostExecute(final AccountActivationStatusResponse success) {
            mAuthTask = null;
            showProgress(false);
            String responsecode = success.getResponsecode() != null ? success.getResponsecode():"96";
            if (responsecode.equalsIgnoreCase("000")) {
                System.out.println("Password successfully, Account Successfully activated changed "+ success.getResponsemessage());
                Intent gotosuccess = new Intent(getBaseContext(),Success.class);
                startActivity(gotosuccess);
                finish();
            } else {
                monetimepwd.setError(getString(R.string.error_on_set_pin));
                monetimepwd.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

