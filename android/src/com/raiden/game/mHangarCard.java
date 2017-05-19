package com.raiden.game;

import android.content.Context;
import android.widget.LinearLayout;


public class mHangarCard extends android.support.v7.widget.AppCompatImageView {
    public mHangarCard(Context context) {
        super(context);
        this.setBackgroundResource(R.drawable.menu_buttons);
        int width = (int) getResources().getDimension(R.dimen.cardhanger_width);
        int height = (int) getResources().getDimension(R.dimen.cardhanger_height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        this.setLayoutParams(layoutParams);
    }
}
