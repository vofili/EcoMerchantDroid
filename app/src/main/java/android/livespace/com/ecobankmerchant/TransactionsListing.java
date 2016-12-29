package android.livespace.com.ecobankmerchant;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.livespace.com.ecobankmerchant.adapters.TransactionsAdapter;
import android.livespace.com.ecobankmerchant.dataobjects.TransResponseObj;
import android.livespace.com.ecobankmerchant.dataobjects.Transactions;
import android.livespace.com.ecobankmerchant.dataobjects.TransactionsProcessor;
import android.livespace.com.ecobankmerchant.dataobjects.TransactionsRequest;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionsListing.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionsListing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsListing extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String TXN_PREFERENCES = "com.ecobankmerchant.transaction.preferences";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    SwipeRefreshLayout myswipe;


    public TransactionsListing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionsListing.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionsListing newInstance(String param1, String param2) {
        TransactionsListing fragment = new TransactionsListing();
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
        View view =  inflater.inflate(R.layout.fragment_transactions_listing, container, false);

       ArrayList<android.livespace.com.ecobankmerchant.dataobjects.Transactions> mytxns
               = new ArrayList<android.livespace.com.ecobankmerchant.dataobjects.Transactions>();

        String prefsJson = "";
        SharedPreferences trnfetchPrefs = getActivity().getSharedPreferences(TXN_PREFERENCES, Activity.MODE_PRIVATE);
        prefsJson = trnfetchPrefs.getString("FETCHED_TXNS","");
        System.out.println(" Retrieved String from prefs:" + prefsJson);
        Gson jObj = new Gson();
        TransResponseObj prefsObj = new  TransResponseObj();
        prefsObj = jObj.fromJson(prefsJson,TransResponseObj.class);


        android.livespace.com.ecobankmerchant.dataobjects.Transactions emptytrn = new android.livespace.com.ecobankmerchant.dataobjects.Transactions();

//        t1.setAmount(prefsObj.getMerchantTransactionHistoryResponse().getMerchantTransactionInfo().get(0).getAmount());
//        t1.setTranType(prefsObj.getMerchantTransactionHistoryResponse().getMerchantTransactionInfo().get(0).getTranType());
//        t1.setNarration(prefsObj.getMerchantTransactionHistoryResponse().getMerchantTransactionInfo().get(0).getNarration());
//        t1.setProductDetail(prefsObj.getMerchantTransactionHistoryResponse().getMerchantTransactionInfo().get(0).getProductDetail());
//        t1.setTerminalId(prefsObj.getMerchantTransactionHistoryResponse().getMerchantTransactionInfo().get(0).getTerminalId());
//        t1.setCbaReferenceNo(prefsObj.getMerchantTransactionHistoryResponse().getMerchantTransactionInfo().get(0).getCbaReferenceNo());
//        t1.setTranDate(prefsObj.getMerchantTransactionHistoryResponse().getMerchantTransactionInfo().get(0).getTranDate());
////
//        android.livespace.com.ecobankmerchant.dataobjects.Transactions t2 = new android.livespace.com.ecobankmerchant.dataobjects.Transactions();
//        t2.setAmount("450.00");
//        t2.setTranType("CREDIT");
//        t2.setNarration("Dinner at Radison Blu Hotel!");
//        t2.setProductDetail("Money Send Payment");
//        t2.setTerminalId("90332011");
//        t2.setCbaReferenceNo("99383405959955");
//        t2.setTranDate("2016-12-06 04:56:09");



     //   mytxns.add(t1);
//        mytxns.add(t2);
        //mytxns.add(t3);



        Iterator<android.livespace.com.ecobankmerchant.dataobjects.Transactions> txItr = prefsObj.getMerchantTransactionHistoryResponse().getMerchantTransactionInfo().iterator();
        if(!txItr.hasNext()){
            emptytrn.setAmount("");

        emptytrn.setTranType("");
        emptytrn.setNarration("No Transactions Found");
        emptytrn.setProductDetail("");
        emptytrn.setTerminalId("");
        emptytrn.setCbaReferenceNo("");
        emptytrn.setTranDate("");
            mytxns.add(emptytrn);
        }else{
            while(txItr.hasNext()){
                mytxns.add(txItr.next());
                // System.out.println("successfully added:"+txItr.next().getCbaReferenceNo());
            }
        }


        ListView trnlistview = (ListView) view.findViewById(R.id.list);

        TransactionsAdapter trnadapter = new TransactionsAdapter(getActivity(),mytxns);

        trnlistview.setAdapter(trnadapter);

        trnlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item clicked",Integer.toString(position));
                //createDialogItem(view);

            }
        });

        myswipe = (SwipeRefreshLayout) view.findViewById(R.id.swipetrn);

        myswipe.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                transactionupdate();
            }
        });

        return view;
    }


    @Override
    public void onRefresh() {
        transactionupdate();
        myswipe.setRefreshing(false);
    }

    public void createDialogItem(View  vw){

        final Dialog trnDialog = new Dialog(getContext());
       // trnDialog.setTitle("Transaction Detail");

        trnDialog.setCanceledOnTouchOutside(true);
        trnDialog.setContentView(R.layout.transactiondetailview);
        Window www = trnDialog.getWindow();
        www.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        //www.setLayout(WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT);
        trnDialog.setCanceledOnTouchOutside(true);

        trnDialog.show();
    }

    public void createAlertDialogItem(){

    }

    public void transactionupdate() {
        try{
            Thread.sleep(6l);

        }catch(Exception e){
            e.printStackTrace();
        }
        myswipe.setRefreshing(false);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public class Transactions {

        String merchantid;
        String terminalid;
        String transactiontime;
        String amount;
        String status;
        String type;
        String trnrefno;
        String currency;
        String scheme;

        public String getMerchantid() {
            return merchantid;
        }

        public void setMerchantid(String merchantid) {
            this.merchantid = merchantid;
        }

        public String getTerminalid() {
            return terminalid;
        }

        public void setTerminalid(String terminalid) {
            this.terminalid = terminalid;
        }

        public String getTransactiontime() {
            return transactiontime;
        }

        public void setTransactiontime(String transactiontime) {
            this.transactiontime = transactiontime;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTrnrefno() {
            return trnrefno;
        }

        public void setTrnrefno(String trnrefno) {
            this.trnrefno = trnrefno;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }
    }



}
