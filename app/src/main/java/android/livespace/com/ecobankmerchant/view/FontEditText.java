package android.livespace.com.ecobankmerchant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by val on 12/6/16.
 */




    import android.content.Context;
    import android.util.AttributeSet;
    import android.widget.EditText;

    import android.livespace.com.ecobankmerchant.Utils.*;


    /**
     *
     */
    public class FontEditText extends EditText {

        public FontEditText(Context context) {
            super(context);
            FontTextView.init(this, context, null, 0);
        }

        public FontEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            FontTextView.init(this, context, attrs, 0);
        }

        public FontEditText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            FontTextView.init(this, context, attrs, defStyle);
        }

        public void setTypeface(String typeface) {
            FontUtil.setTypeface(this, typeface);
        }


}
