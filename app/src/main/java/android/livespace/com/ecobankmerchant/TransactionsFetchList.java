package android.livespace.com.ecobankmerchant;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransactionsFetchList extends ListActivity {
//
//    private FancyAdapter mFancyAdapter;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.fancy_list);
//
//            mFancyAdapter = new FancyAdapter(Cheeses.CHEESES);
//            setListAdapter(mFancyAdapter);
//        }
//
//        private class FancyAdapter extends BaseAdapter {
//
//            private String[] mData;
//
//            public FancyAdapter(String[] data) {
//                mData = data;
//            }
//
//            @Override
//            public int getCount() {
//                return mData.length;
//            }
//
//            @Override
//            public String getItem(int position) {
//                return mData[position];
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return position;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//
//                TextView result;
//
//                if (convertView == null) {
//                    result = (TextView) getLayoutInflater().inflate(R.layout.text_item, parent, false);
//                } else {
//                    result = (TextView) convertView;
//                }
//
//                final String cheese = getItem(position);
//                result.setText(cheese);
//
//                return result;
//            }
//
//        }
}
