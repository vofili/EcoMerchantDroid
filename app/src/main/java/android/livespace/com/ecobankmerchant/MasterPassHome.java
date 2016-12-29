package android.livespace.com.ecobankmerchant;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.google.zxing.BarcodeFormat;

import static android.content.Context.WINDOW_SERVICE;


public class MasterPassHome extends Fragment {
    public static String MERCHANT_SETTINGS = "com.ecobankmerchant.merchant.preferences";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SharedPreferences mPrefs;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static View rootmasterpassview;
    private OnFragmentInteractionListener mListener;

    public MasterPassHome() {
        // Required empty public constructor
    }



    public static MasterPassHome newInstance(String param1, String param2) {
        MasterPassHome fragment = new MasterPassHome();
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
        //initialize the merchant details
//        getActivity().getActionBar().setIcon(R.drawable.ghana);
//        getActivity().getActionBar().setHomeButtonEnabled(true);
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        mPrefs= this.getActivity().getSharedPreferences(MERCHANT_SETTINGS,Activity.MODE_PRIVATE);
        //SharedPreferences.Editor mPrefsEdit = mPrefs.edit();

        String MERCHANT_FIRSTNAME="",MERCHANT_NAME="",MERCHANT_ID="",MERCHANT_MIDDLENAME="",MERCHANT_EMAIL="",MERCHANT_DOB="",
                MERCHANT_AREA="",MERCHANT_ADDR="";
        String MERCHANT_COUNTRY="",MERCHANT_CITY="",MERCHANT_MNO="",TERMINAL_ID="",TERMINAL_QR="",TERMINAL_SCHEME="";
        // Inflate the layout for this fragment

        rootmasterpassview =  inflater.inflate(R.layout.fragment_master_pass_home, container, false);
        //get handles on view elements
        TextView merchantid;
        TextView merchantlga;
        TextView merchantaddress;
        TextView merchantname;
        TextView termid;
        TextView merchantnamehome;

        //get Data from Merchant Lookup on login
       // Bundle mldata = getArguments();
        if(mPrefs != null){
//            System.out.println("Retrieved Masterpass MID: " +mldata.getString("MERCHANT_ID"));
//            System.out.println("Retrieved TERMINAL ID: " +mldata.getString("TERMINAL_ID"));
//            System.out.println("Retrieved TERMINAL QR: " +mldata.getString("TERMINAL_QR"));
            //mPrefs.getString()
            FirebaseCrash.log("Retrieved Masterpass MID: " +mPrefs.getString("MERCHANT_ID",""));
            FirebaseCrash.log("Retrieved Masterpass Terminal: " +mPrefs.getString("MERCHANT_ID",""));
            FirebaseCrash.log("Retrieved Masterpass Terminal QR: " +mPrefs.getString("MERCHANT_ID",""));
            MERCHANT_FIRSTNAME = mPrefs.getString("MERCHANT_FIRSTNAME","") != null ?  mPrefs.getString("MERCHANT_FIRSTNAME","") :"";
            MERCHANT_ID = mPrefs.getString("MERCHANT_ID","") ;
            MERCHANT_NAME = mPrefs.getString("MERCHANT_NAME","") != null? mPrefs.getString("MERCHANT_NAME","") :"" ;
            MERCHANT_MIDDLENAME = mPrefs.getString("MERCHANT_MIDDLENAME","") != null? mPrefs.getString("MERCHANT_MIDDLENAME",""):"";
            MERCHANT_EMAIL = mPrefs.getString("MERCHANT_EMAIL","") != null ? mPrefs.getString("MERCHANT_EMAIL","") :"";
            MERCHANT_DOB = mPrefs.getString("MERCHANT_DOB","");
            MERCHANT_AREA = mPrefs.getString("MERCHANT_AREA","");
            MERCHANT_ADDR = mPrefs.getString("MERCHANT_ADDR","");
            MERCHANT_COUNTRY = mPrefs.getString("MERCHANT_COUNTRY","");
            MERCHANT_CITY = mPrefs.getString("MERCHANT_CITY","");
            MERCHANT_MNO =mPrefs.getString("MERCHANT_MNO","");
            TERMINAL_ID = mPrefs.getString("TERMINAL_ID","");
            TERMINAL_QR = mPrefs.getString("TERMINAL_QR","");
            TERMINAL_SCHEME = mPrefs.getString("TERMINAL_SCHEME","");

//            merchantnamehome = (TextView) getActivity().findViewById(R.id.merchantidname);
//            merchantnamehome.setText(MERCHANT_FIRSTNAME + " " + MERCHANT_MIDDLENAME);


        }

        //Set other text Views to Merchant Specific
        termid = (TextView) rootmasterpassview.findViewById(R.id.termid);
        termid.setText(TERMINAL_ID);
        merchantname = (TextView) rootmasterpassview.findViewById(R.id.merchantname);
        merchantname.setText(MERCHANT_MIDDLENAME+" "+MERCHANT_FIRSTNAME);
        merchantaddress = (TextView) rootmasterpassview.findViewById(R.id.merchantaddress);
        merchantaddress.setText(MERCHANT_ADDR);
        merchantlga = (TextView) rootmasterpassview.findViewById(R.id.merchantlga);
        merchantlga.setText(MERCHANT_AREA + " "+MERCHANT_CITY+" "+MERCHANT_COUNTRY);




        //Find screen size

        WindowManager manager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3/4;
//        System.out.println("smaller dimension:" + smallerDimension);
//        System.out.println("width:" + width);
//        System.out.println("height:" + height);
        String qrInputText = TERMINAL_QR;

        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();

            ImageView myImage = (ImageView)  rootmasterpassview.findViewById(R.id.qrcode);
            myImage.setImageBitmap(bitmap);


        } catch (Exception e) {
            FirebaseCrash.log("Retrieved Masterpass MID: " +mPrefs.getString("MERCHANT_ID",""));
            e.printStackTrace();
        }
        return rootmasterpassview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //Encode with a QR Code image



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
}
