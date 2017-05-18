package com.raiden.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


/**
 * Created by João on 03/05/2017.
 */

public class HangarMenu extends AppCompatActivity implements View.OnClickListener{
    private HorizontalScrollView mScrollView;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangar_menu);

        mScrollView = (HorizontalScrollView) findViewById(R.id.mScrollView);
        mLinearLayout = (LinearLayout) findViewById(R.id.mScrollLayout);
        mHangarCard image = new mHangarCard(this);
        image.setImageResource(R.drawable.settings_button);
        mLinearLayout.addView(image, 0);

    }


    @Override
    public void onClick(View v) {

    }
}
