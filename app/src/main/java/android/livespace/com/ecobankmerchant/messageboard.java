package android.livespace.com.ecobankmerchant;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

public class messageboard extends AppCompatActivity {

    public Context vContent;
    private ViewFlipper mImgFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageboard);


        mImgFlipper = (ViewFlipper) findViewById(R.id.msgimages);
        mImgFlipper.setAutoStart(true);
        mImgFlipper.setFlipInterval(2000);


        mImgFlipper.startFlipping();


    }
}
