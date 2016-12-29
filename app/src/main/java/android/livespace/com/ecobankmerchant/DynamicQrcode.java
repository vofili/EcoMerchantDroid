package android.livespace.com.ecobankmerchant;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.livespace.com.ecobankmerchant.Utilities.Code;
import android.livespace.com.ecobankmerchant.Utilities.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.test.ActivityUnitTestCase;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;

import java.text.SimpleDateFormat;

import static android.content.Context.WINDOW_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DynamicQrcode.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DynamicQrcode#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DynamicQrcode extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String MERCHANT_SETTINGS = "com.ecobankmerchant.merchant.preferences";

    private String mParam1;
    private String mParam2;
    private String staticQr;
    public View dynamicview;
    private EditText minvoice;
    private EditText mamount;
    private EditText mtip;
    private OnFragmentInteractionListener mListener;

    public DynamicQrcode() {
        // Required empty public constructor
    }

    SharedPreferences msetting = getContext().getSharedPreferences(MERCHANT_SETTINGS,Activity.MODE_PRIVATE);


    // TODO: Rename and change types and number of parameters
    public static DynamicQrcode newInstance(String param1, String param2) {
        DynamicQrcode fragment = new DynamicQrcode();
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
        staticQr = msetting.getString("temrinalqr","");
        dynamicview =  inflater.inflate(R.layout.fragment_dynamic_qrcode, container, false);
      //EditText txtamount;
        Button qrcodebutton = (Button) dynamicview.findViewById(R.id.qr_generate_button);
        minvoice = (EditText) dynamicview.findViewById(R.id.invoiceno);
        mtip = (EditText) dynamicview.findViewById(R.id.tip);
        mamount = (EditText) dynamicview.findViewById(R.id.amount);

        qrcodebutton.setOnClickListener(new View.OnClickListener() {
                String invtxt;
                String tiptxt;
                String amttxt;
                String dynamicQr;
                String merchantid;
                String staticqrcode =staticQr;
                ImageView qrImg;
            //Retrieve the Customer's Static QR

            @Override
            public void onClick(View v) {
                invtxt = minvoice.getText().toString() != null ? minvoice.getText().toString():"";
                tiptxt = mtip.getText().toString() != null ? mtip.getText().toString():"";
                amttxt = mamount.getText().toString() != null ? mamount.getText().toString():"";
                dynamicQr= Code.generateDynamic(staticqrcode,tiptxt,amttxt,invtxt);
                qrImg = (ImageView) dynamicview.findViewById(R.id.qrcode);

            }
        });

        return dynamicview;
    }

    public void showDynamic(String inv,String amt,String tip){


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void createDialogItem(){

        final Dialog trnDialog = new Dialog(getContext());
        // trnDialog.setTitle("Transaction Detail");
        // Button okbtn = (Button) trnDialog.findViewById(R.id.okbutton);

        trnDialog.setCanceledOnTouchOutside(true);
        trnDialog.setContentView(R.layout.qrgeneratedmessage);
        Window www = trnDialog.getWindow();
        www.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        //www.setLayout(WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT);
        trnDialog.setCanceledOnTouchOutside(true);

        trnDialog.show();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
