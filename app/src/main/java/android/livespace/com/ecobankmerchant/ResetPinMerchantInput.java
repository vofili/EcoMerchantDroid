package android.livespace.com.ecobankmerchant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.livespace.com.ecobankmerchant.Utilities.NetworkUtils;
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

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.livespace.com.ecobankmerchant.Utilities.SMSOtp.sendOtp;

/**
 * A login screen that offers login via email/password.
 */
public class ResetPinMerchantInput extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    private UserLoginTask mAuthTask = null;

    // UI references.

    private EditText mid;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin_merchant_input);
        // Set up the login form.
        mid = (EditText) findViewById(R.id.merchantid);



        Button mpin_reset_merchant = (Button) findViewById(R.id.pin_reset_merchant);
        mpin_reset_merchant.setOnClickListener(new OnClickListener() {
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // populateAutoComplete();
            }
        }
    }


    private void gotopasswordset(String mid,String otp,String phn,String email){
       // Log.v("PIN reset","PIN reset request initiated");
       FirebaseCrash.log("PIN reset request initiated");
        final String fmid = mid;
        final String fotp = otp;
        final String phone = phn;
        final String mail =email;
        Intent pwdintent = new Intent(getBaseContext(),setpassword.class);
        pwdintent.putExtra("MERCHANTID",fmid);

        pwdintent.putExtra("PHONE",phone);
        pwdintent.putExtra("EMAIL",mail);

        startActivity(pwdintent);

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
        mid.setError(null);


        // Store values at the time of the login attempt.
        String merchantid = mid.getText().toString();


        boolean cancel = false;
        View focusView = null;



        // Check for a valid merchant ID.
        if (TextUtils.isEmpty(merchantid)) {
            mid.setError(getString(R.string.error_field_required));
            focusView = mid;
            cancel = true;
        } else if (merchantid.equalsIgnoreCase("")) {
            mid.setError(getString(R.string.error_invalid_email));
            focusView = mid;
            cancel = true;
        }

        if(!NetworkUtils.isNetworkAvailable(getBaseContext())){

            mid.setError(getString(R.string.networkfailtryagain));
            focusView = mid;
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
            mAuthTask = new UserLoginTask(merchantid);
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
    public class UserLoginTask extends AsyncTask<Void, Void, ResetPinResponse> {

        private final String mMid;
        ResetPinResponse rpinresp = new ResetPinResponse();
        ResetPinRequest rpinreq = new ResetPinRequest();
        UserLoginTask(String merchantid) {
            mMid= merchantid;

        }

        @Override
        protected ResetPinResponse doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {

                rpinreq.setMerchantid(mMid);
                rpinresp = ResetPinProcessor.processmerchantid(rpinreq);
                if(rpinresp.getResponsecode().equalsIgnoreCase("000")){
                    String phone = rpinresp.getPhonenumber() != null ?rpinresp.getPhonenumber():"";
                    String email = rpinresp.getEmailaddress() != null ? rpinresp.getEmailaddress():"";
                    String smsotp =rpinresp.getOtp() != null ? rpinresp.getOtp():"";
                    sendOtp(phone,smsotp);
                }
            } catch (Exception e) {
                String errorresult = rpinresp.getResponsecode()!= null ? rpinresp.getResponsecode(): "96";
//                if(!errorresult.equalsIgnoreCase("000")){
//                    rpinreq.setMerchantid("M"+rpinreq.getMerchantid());
//                    System.out.println("Attempt to authenticate the merchant using alternated MID "+ rpinreq.getMerchantid());
//                    rpinresp= ResetPinProcessor.processmerchantid(rpinreq);
//                }

                return rpinresp;
            }



            // TODO: register the new account here.
            return rpinresp;
        }

        @Override
        protected void onPostExecute(final ResetPinResponse success) {
            mAuthTask = null;
            showProgress(false);
            String phone="",email="",smsotp="";
            if (success.getResponsecode().equalsIgnoreCase("000")) {

                phone = success.getPhonenumber() != null ?success.getPhonenumber():"";
                email = success.getEmailaddress() != null ? success.getEmailaddress():"";
                smsotp =success.getOtp() != null ? success.getOtp():"";
                //sendOtp(phone,smsotp);
                gotopasswordset(mMid,smsotp,phone,email);
                finish();

            } else {
                mid.setError(getString(R.string.error_invalid_merchant_id));
                mid.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

