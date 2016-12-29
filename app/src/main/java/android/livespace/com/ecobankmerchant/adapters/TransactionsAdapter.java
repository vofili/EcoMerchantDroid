package android.livespace.com.ecobankmerchant.adapters;

import android.content.Context;
import android.graphics.Color;
import android.livespace.com.ecobankmerchant.R;
import android.livespace.com.ecobankmerchant.dataobjects.Transactions;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import static java.lang.Math.abs;

/**
 * Created by val on 12/7/16.
 */

public class TransactionsAdapter extends ArrayAdapter<Transactions> {

    private final Context context;
    private final ArrayList <Transactions> values;


    public TransactionsAdapter(Context context,ArrayList values){
            super(context,-1,values);
            this.context = context;
            this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater infl = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View rowv = infl.inflate(R.layout.transactionitemview,parent,false);
        DecimalFormat trnamtdf = new DecimalFormat("#,###.00");
        TextView txttype = (TextView) rowv.findViewById(R.id.transtype);
        //TextView txttid = (TextView) rowv.findViewById(R.id.tid);
        TextView txttrndate = (TextView) rowv.findViewById(R.id.transdate);
        TextView transref = (TextView) rowv.findViewById(R.id.transref);
        TextView txtdesc = (TextView) rowv.findViewById(R.id.transdesc);
        TextView  txtnarr = (TextView) rowv.findViewById(R.id.transnarr);
        TextView txttrnccy = (TextView) rowv.findViewById(R.id.transccy);


        txtdesc.setText(values.get(position).getProductDetail());
        if(values.get(position).getNarration().length()>55) {
            txtnarr.setText(values.get(position).getNarration().substring(0, 54));
        } else{
            txtnarr.setText(values.get(position).getNarration());
        }
        transref.setText(values.get(position).getCbaReferenceNo());
        txttrndate.setText(values.get(position).getTranDate().replaceAll("[A-Z]"," "));

        //txttid.setText(values.get(position).getTerminalId());
        if(values.get(position).getDbcr().equalsIgnoreCase("D")){
            TextView txttrnamt = (TextView) rowv.findViewById(R.id.transamt);
            txttrnccy.setText(values.get(position).getCcy());
            txttrnccy.setTextColor(getContext().getResources().getColor(R.color.material_red));
            txttype.setText("DEBIT");
            txttype.setTextColor(getContext().getResources().getColor(R.color.material_red));
            txttrnamt.setText("-"+ trnamtdf.format(abs(Double.parseDouble(new BigDecimal(values.get(position).getAmount()).setScale(2,BigDecimal.ROUND_HALF_DOWN).toString()))));
            txttrnamt.setTextColor(getContext().getResources().getColor(R.color.material_red));
        }else {
            TextView txttrnamt = (TextView) rowv.findViewById(R.id.transamt);
            txttrnccy.setText(values.get(position).getCcy());
            txttype.setText("CREDIT");
            txttrnamt.setText(trnamtdf.format(abs(Double.parseDouble(new BigDecimal(values.get(position).getAmount()).setScale(2,BigDecimal.ROUND_HALF_DOWN).toString()))));
            txttrnamt.setTextColor(getContext().getResources().getColor(R.color.accent));
        }
        return rowv;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }
}
