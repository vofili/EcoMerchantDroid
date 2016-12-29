package android.livespace.com.ecobankmerchant;

import java.text.SimpleDateFormat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.livespace.com.ecobankmerchant.Utilities.Configuration;
import android.livespace.com.ecobankmerchant.dataobjects.AuthenticateProcessor;
import android.livespace.com.ecobankmerchant.dataobjects.AuthenticateRequest;
import android.livespace.com.ecobankmerchant.dataobjects.AuthenticateResponse;
import android.livespace.com.ecobankmerchant.dataobjects.Jsonutil;
import android.livespace.com.ecobankmerchant.dataobjects.TransResponseObj;
import android.livespace.com.ecobankmerchant.dataobjects.TransactionsProcessor;
import android.livespace.com.ecobankmerchant.dataobjects.TransactionsRequest;
import android.livespace.com.ecobankmerchant.dataobjects.TransactionsResponse;
import android.livespace.com.ecobankmerchant.view.FontEditText;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.livespace.com.ecobankmerchant.dataobjects.Jsonutil.requestTrnData;



public class TransactionGet extends Fragment implements TransactionsListing.OnFragmentInteractionListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String MERCHANT_SETTINGS = "com.ecobankmerchant.merchant.preferences";
    public static String TXN_PREFERENCES = "com.ecobankmerchant.transaction.preferences";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View focus;
    private GetTransactionTask mTransTask = null;
    Class fragClass;

    private OnFragmentInteractionListener mListener;
    public  ImageButton startbtn,endbtn,starttimebut,endtimebut;
    public  Button gettrnbutton;
    FontEditText mstartdt;
    FontEditText menddt;
    FontEditText starttimetxt;
    FontEditText endtimetxt;
    View mTransFormView;
    View mProgressView;
    View mSubmitButton;
    View mlblfetchheading;
    Switch timeSwitchBtn;
    String setDayTimeFlag;
    private String transpagesize= Configuration.transpagesize;

    EditText endTimeTxt;
    EditText startTimeTxt;

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

            mlblfetchheading.setVisibility(show ? View.GONE : View.VISIBLE);
            mlblfetchheading.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mlblfetchheading.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mTransFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mTransFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mTransFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mSubmitButton.setVisibility(show ? View.GONE : View.VISIBLE);
            mSubmitButton.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSubmitButton.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSubmitButton.setVisibility(show ? View.VISIBLE :View.GONE);
            mTransFormView.setVisibility(show ? View.VISIBLE : View.GONE);
            mlblfetchheading.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public TransactionGet() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TransactionGet newInstance(String param1, String param2) {
        TransactionGet fragment = new TransactionGet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        fragClass = TransactionsListing.class;
        setDayTimeFlag="FALSE";

        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_transaction_get, container, false);
        starttimebut = (ImageButton) vw.findViewById(R.id.starttimebutton);
        starttimetxt = (FontEditText) vw.findViewById(R.id.starttimefield);

        endtimebut = (ImageButton) vw.findViewById(R.id.endtimebutton);
        endtimetxt = (FontEditText) vw.findViewById(R.id.end_time_field);

        menddt = (FontEditText) vw.findViewById(R.id.enddatefield);
        endbtn = (ImageButton) vw.findViewById(R.id.enddatebutton);

        mstartdt =(FontEditText) vw.findViewById(R.id.startdatefield);
        startbtn = (ImageButton) vw.findViewById(R.id.startdatebutton);

        //logic for switching the Time View on or Off
        timeSwitchBtn = (Switch)vw.findViewById(R.id.datetimeswitch);
        timeSwitchBtn.setChecked(true);
        timeSwitchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    starttimebut.setVisibility(View.VISIBLE);
                    starttimetxt.setVisibility(View.VISIBLE);
                    endtimebut.setVisibility(View.VISIBLE);
                    endtimetxt.setVisibility(View.VISIBLE);
                    setDayTimeFlag = "TIME";

                }else{
                    starttimebut.setVisibility(View.INVISIBLE);
                    starttimetxt.setVisibility(View.INVISIBLE);
                    endtimebut.setVisibility(View.INVISIBLE);
                    endtimetxt.setVisibility(View.INVISIBLE);
                    setDayTimeFlag = "DATE";
                }
            }
        });

        mProgressView = vw.findViewById(R.id.transactions_progress);
        mTransFormView = vw.findViewById(R.id.transaction_form);
        mSubmitButton = vw.findViewById(R.id.fetchtransactions);
        mlblfetchheading = vw.findViewById(R.id.lblfetchheading);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Date Picker ","Date Start Button Clicked");
                DialogFragment dpf = new DatePickerFragment();
                dpf.show( getActivity().getSupportFragmentManager(),"datePicker");
            }
        });


        endbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Date Picker ","Date Start Button Clicked");
                DialogFragment dpf = new EndDatePickerFragment();
                dpf.show( getActivity().getSupportFragmentManager(),"datePicker");

            }
        });


        endtimebut.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment tpf = new EndTimePickerFragment();
                tpf.show(getActivity().getSupportFragmentManager(),"endtimePicker");
            }
        });


        starttimebut.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment tpf = new TimePickerFragment();
                tpf.show(getActivity().getSupportFragmentManager(),"starttimePicker");
            }
        });



        gettrnbutton = (Button) vw.findViewById(R.id.fetchtransactions);
        gettrnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Proceed to fetch transactions");
                gotottransactionlisting(setDayTimeFlag);
            }
        });


        return vw;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    public void gotottransactionlisting(String ftflag){
//        if(mTransTask!=null){
//            return;
//        }

        boolean cancel=false ;
        System.out.println("Now running gotottransactionlisting");

        //Validate the form to ensure date is entered
        String vstartdt = mstartdt.getText().toString();

        String venddt =  menddt.getText().toString();

        if(!(vstartdt != null) || vstartdt.length()<8){
            mstartdt.setError("Start Date is not defined");
            focus=mstartdt;
            cancel=true;
            return;
        }

        if(!(venddt != null) || venddt.length() < 8){
            menddt.setError("End Date is not defined");
            focus=mstartdt;
            cancel=true;
            return;
        }
        if(cancel){
            focus.requestFocus();
            return;
        }else {
            System.out.println("gotottransactionlisting validated as true");
            showProgress(true);
            SharedPreferences mprefs = getActivity().getSharedPreferences(MERCHANT_SETTINGS, Activity.MODE_PRIVATE);
            String getstartdate = "";
            String getenddate = "";
            EditText stedit = (EditText) getActivity().findViewById(R.id.startdatefield);
            EditText ededit = (EditText) getActivity().findViewById(R.id.enddatefield);


            if (ftflag.equalsIgnoreCase("TIME")) {
                EditText edtime = (EditText) getActivity().findViewById(R.id.end_time_field);
                EditText sttime = (EditText) getActivity().findViewById(R.id.starttimefield);
                getstartdate = stedit.getText().toString().concat(" "+sttime.getText().toString());
                getenddate = ededit.getText().toString().concat(" "+edtime.getText().toString());

            } else {
                getstartdate = stedit.getText().toString();
                getenddate = ededit.getText().toString();
            }


            String getmerchantid = mprefs.getString("MERCHANT_ID", "");
            String getauthkey = mprefs.getString("AUTH_KEY", "");
            String getaffiliate = mprefs.getString("requestingaffiliate","EGH");
            String getpagesize = transpagesize;
            System.out.println("The start date of the transaction " + stedit.getText().toString());
            System.out.println("The end date of the transaction " + ededit.getText().toString());
            System.out.println("The associated Merchant ID of the logged in User: " + getmerchantid);
            System.out.println("The associated auth Key of the logged in User: " + getauthkey);

            //Create the transaction fetch request to the Mobile API


            mTransTask = new TransactionGet.GetTransactionTask(getmerchantid, getaffiliate, getstartdate, getenddate, getauthkey, ftflag);
            mTransTask.execute((Void) null);
        }

    }
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            //System.out.println("Year:" + year + "month:" + month + "day:" + day);
            //((ViewGroup)getView().getParent()).getId()
            Calendar calendar = Calendar.getInstance();
            EditText stedit = (EditText) getActivity().findViewById(R.id.startdatefield);
            EditText ededit = (EditText) getActivity().findViewById(R.id.enddatefield);
            calendar.set(year, month, day);
            java.util.Date enddatedate = new java.util.Date();
            String dtstring = sdf.format(calendar.getTime());
            String endstring = sdf.format(enddatedate);
            stedit.setText(dtstring);
            ededit.setText(endstring);

        }
    }

    public static class EndDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            //System.out.println("Year:" + year + "month:" + month + "day:" + day);
            //((ViewGroup)getView().getParent()).getId()
            Calendar calendar = Calendar.getInstance();
            EditText ededit = (EditText) getActivity().findViewById(R.id.enddatefield);
            calendar.set(year, month, day);
            String dtstring = sdf.format(calendar.getTime());
            ededit.setText(dtstring);


        }
    }



    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int seconds = c.get(Calendar.SECOND);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            EditText stedit = (EditText) getActivity().findViewById(R.id.starttimefield);
            EditText ededit = (EditText) getActivity().findViewById(R.id.end_time_field);
           calendar.set(0,0,0,hourOfDay,minute,0);
           // calendar.set(year, month, day);
//            java.util.Date enddatedate = new java.util.Date();
//            String dtstring = sdf.format(calendar.getTime());
//            String endstr = sdf.format(enddatedate);
            stedit.setText(hourOfDay + ":"+minute+":00");

//            ededit.setText(endstr);
        }

    }

    public static class SimpleTimeFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return super.onCreateDialog(savedInstanceState);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    }
    public static class EndTimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        SimpleDateFormat datefrg = new SimpleDateFormat("kk:mm:ss");
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();

            EditText ededit = (EditText) getActivity().findViewById(R.id.end_time_field);
         Calendar cal = Calendar.getInstance();
            cal.get(Calendar.HOUR);
            ededit.setText(datefrg.format(new Date()));

        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    public class GetTransactionTask extends AsyncTask<Void, Void, TransResponseObj> {

        private final String merchantid;
        private final String affiliate;
        private final String startdate;
        private final String enddate;
        private final String authkey;
        private final String daytimeflag;
        GetTransactionTask(String mid, String aff,String start,String end,String auth,String dtflag) {
            merchantid = mid;
            affiliate = aff;
            startdate = start;
            enddate = end;
            authkey = auth;
            daytimeflag = dtflag;
        }
        TransResponseObj tresp= new TransResponseObj();
        TransactionsRequest treq = new TransactionsRequest();

        @Override
        protected TransResponseObj doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {

                 if (daytimeflag.equalsIgnoreCase("TIME")) {
                     treq.setQuerytype("TIMERANGE");
                 }
                else {
                    treq.setQuerytype("DATERANGE");
                }
                treq.setAffiliatecode(affiliate);
                treq.setMerchantid(merchantid);
                treq.setPagesize(transpagesize);
                treq.setStartdate(startdate);
                treq.setEnddate(enddate);
                treq.setAuthorization(authkey);
                treq.setChannel("MOBILE");
                tresp =  TransactionsProcessor.process(treq);
                //String responsejson = Jsonutil.requestTrnData();
                Gson trnJson = new Gson();
                //System.out.println("Transactions returend from  direct Websvc call Backend"+ responsejson);
                String trnJsonStr = trnJson.toJson(tresp,TransResponseObj.class);
                System.out.println("transactions Object sent to Mobile backend: " + trnJsonStr);
                SharedPreferences trnPrefs = getActivity().getSharedPreferences(TXN_PREFERENCES,Activity.MODE_PRIVATE);
                SharedPreferences.Editor trnPrefsEditor = trnPrefs.edit();
                trnPrefsEditor.putString("FETCHED_TXNS",trnJsonStr);
                trnPrefsEditor.commit();


            } catch (Exception e) {
                e.printStackTrace();
            }


            return tresp;

        }


        @Override
        protected void onPostExecute(final TransResponseObj success) {
            TransactionsListing trngetfrag = new TransactionsListing();
             NoTransactions notrngetfrag = new NoTransactions();
            showProgress(false);
            try {
                String newresult = success.getMerchantTransactionHistoryResponse().getHostHeaderInfo().getResponseCode() != null ?
                        success.getMerchantTransactionHistoryResponse().getHostHeaderInfo().getResponseCode() : "96";
                if (newresult.equalsIgnoreCase("000")) {
                    System.out.println("Response From Webservice Endpoint:" + newresult);
                    FragmentManager fmgr = getActivity().getSupportFragmentManager();
                    FragmentTransaction ftrn = fmgr.beginTransaction();
                    //ftrn.replace(R.id.transactionsgetlayout ,trngetfrag);
                    ftrn.replace(((ViewGroup) getView().getParent()).getId(), trngetfrag);
                    ftrn.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    ftrn.addToBackStack(null);
                    ftrn.commit();


                } else if (newresult.equalsIgnoreCase("E50")) {
                    System.out.println("Response From Webservice Endpoint:" + newresult);
                    FragmentManager fmgr = getActivity().getSupportFragmentManager();
                    FragmentTransaction ftrn = fmgr.beginTransaction();
                    //ftrn.replace(R.id.transactionsgetlayout ,trngetfrag);
                    ftrn.replace(((ViewGroup) getView().getParent()).getId(), notrngetfrag);
                    ftrn.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    ftrn.commit();

                    System.out.println("No Results found for the Specified transaction");


                } else if (newresult.equalsIgnoreCase("96")) {
                    FragmentManager fmgr = getActivity().getSupportFragmentManager();
                    FragmentTransaction ftrn = fmgr.beginTransaction();
                    //ftrn.replace(R.id.transactionsgetlayout ,trngetfrag);
                    ftrn.replace(((ViewGroup) getView().getParent()).getId(), notrngetfrag);
                    ftrn.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    ftrn.commit();
                   // mstartdt.setError(getString(R.string.systemerror));
                    System.out.println("Error occurred during processing  96");
                    // gotoerrortransactions();
                }
            }catch(Exception x){
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                FragmentTransaction ftrn = fmgr.beginTransaction();
                //ftrn.replace(R.id.transactionsgetlayout ,trngetfrag);
                ftrn.replace(((ViewGroup) getView().getParent()).getId(), notrngetfrag);
                ftrn.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ftrn.commit();
                mstartdt.setError(getString(R.string.err_processing));
                System.out.println("Fatal error occurred during processing");
            }

        }

        @Override
        protected void onCancelled() {

        }



    }
}
