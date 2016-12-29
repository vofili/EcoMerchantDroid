package android.livespace.com.ecobankmerchant;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;

import static android.content.Context.WINDOW_SERVICE;
import static android.livespace.com.ecobankmerchant.Utilities.Code.generateDynamic;



public class NewDynamicQR extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mProgressView;
    private View mLoginFormView;
    private View dynamicqrview;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button mgenerateqrbutton;
    EditText mInvno;
    EditText mTipamt;
    EditText mTxAmt;
    String staticqr = "";
    private OnFragmentInteractionListener mListener;
    public static String MERCHANT_SETTINGS = "com.ecobankmerchant.merchant.preferences";
    SharedPreferences mPrefs;

    public NewDynamicQR() {
        // Required empty public constructor
    }

    public static NewDynamicQR newInstance(String param1, String param2) {
        NewDynamicQR fragment = new NewDynamicQR();
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
        // Inflate the layout for this fragment
        boolean generateFlag = false;
        mPrefs= this.getActivity().getSharedPreferences(MERCHANT_SETTINGS,Activity.MODE_PRIVATE);
        //Bundle mldata = getArguments();

        if(mPrefs != null) {
            System.out.println("Retrieved Masterpass MID: " + mPrefs.getString("MERCHANT_ID",""));
            System.out.println("Retrieved TERMINAL ID: " + mPrefs.getString("TERMINAL_ID",""));
            System.out.println("Retrieved TERMINAL QR: " + mPrefs.getString("TERMINAL_QR",""));
        }else{
            System.out.println("Bundle Data Not Passed");
        }

        dynamicqrview = inflater.inflate(R.layout.fragment_new_dynamic_qr, container, false);

        mgenerateqrbutton = (Button) dynamicqrview.findViewById(R.id.generateqrbutton);
        staticqr = mPrefs.getString("TERMINAL_QR","");



        mInvno = (EditText) dynamicqrview.findViewById(R.id.invoiceno);
        mTipamt = (EditText) dynamicqrview.findViewById(R.id.tip);
        mTxAmt =  (EditText) dynamicqrview.findViewById(R.id.amount);

        System.out.println("Dynamic QR Gen QR:" + staticqr);
        genStatic(staticqr);
        mgenerateqrbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((mTxAmt.getText().toString() != null) && (mTxAmt.getText().toString().length() > 0)) {
                    // genDynamic(staticqr);

                    ProgressDialog pd = new ProgressDialog(getContext());
                    pd.setTitle("Dynamic QR Code ");
                    pd.setMessage("A QR code is being generated \n Amount:" + mTxAmt.getText());
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.setCanceledOnTouchOutside(true);

                    pd.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if ((mTxAmt.getText().toString() != null) && (mTxAmt.getText().toString().length() > 0)) {
                                genDynamic(staticqr);
                            } else {
                                mTxAmt.setError("The Purchase Amount is Required");
                                mTxAmt.requestFocus();
                            }

                        }
                    });

                    pd.setButton(Dialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });

                    pd.show();

                }else{
                mTxAmt.setError("The Purchase Amount is Required");
                mTxAmt.requestFocus();
            }
            }
        });


        return dynamicqrview;
    }


    public void genDynamic(String stat){
        String invno="",txtip="",txamt="";

        invno = mInvno.getText().toString();
        txtip = mTipamt.getText().toString();
        txamt = mTxAmt.getText().toString();

        System.out.println("Dynamic QR Gen Inv:" + invno);
        System.out.println("Dynamic QR Gen Tip:" + txtip);
        System.out.println("Dynamic QR Gen Amt:" + txamt);

        String dyString =  generateDynamic(staticqr,txtip,txamt,invno);

        System.out.println("Static  to be USED" + stat);
        System.out.println(" Dynamic to be USED" + dyString);

        WindowManager manager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        System.out.println("smaller dimension:" + smallerDimension);
        System.out.println("width:" + width);
        System.out.println("height:" + height);


        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(dyString,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();

            ImageView myImage = (ImageView) dynamicqrview.findViewById(R.id.qrcode);
            myImage.setImageBitmap(bitmap);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void genStatic(String stat){


        //String dyString =  generateDynamic(staticqr,txtip,txamt,invno);
        String dyString = stat;

        System.out.println("Static  to be USED" + stat);
        System.out.println(" Dynamic to be USED" + dyString);

        WindowManager manager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        System.out.println("smaller dimension:" + smallerDimension);
        System.out.println("width:" + width);
        System.out.println("height:" + height);


        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(dyString,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();

            ImageView myImage = (ImageView) dynamicqrview.findViewById(R.id.qrcode);
            myImage.setImageBitmap(bitmap);


        } catch (Exception e) {
            e.printStackTrace();
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
}
