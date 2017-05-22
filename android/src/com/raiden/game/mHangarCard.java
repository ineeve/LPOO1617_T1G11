package com.raiden.game;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;


public class mHangarCard extends android.support.v7.widget.AppCompatImageView {
    int margin_default;
    int margin_zoomed;
    int margin;
    int width;
    int height;

    public mHangarCard(Context context) {
        super(context);
        this.setBackgroundResource(R.drawable.menu_buttons);
        width = (int) getResources().getDimension(R.dimen.cardhangar_width);
        height = (int) getResources().getDimension(R.dimen.cardhangar_height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        margin_default = (int) getResources().getDimension(R.dimen.cardhangar_margin_default);
        margin = margin_default;
        margin_zoomed = (int) getResources().getDimension(R.dimen.cardhangar_margin_zoomed);
        layoutParams.setMargins(margin_default, margin_default, margin_default, margin_default);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        this.setLayoutParams(layoutParams);
    }

    public void resize(int width, int height){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        if(margin == margin_default) {
            layoutParams.setMargins(margin_zoomed, margin_zoomed, margin_zoomed, margin_zoomed);
            margin = margin_zoomed;
        }
        else {
            layoutParams.setMargins(margin_default, margin_default, margin_default, margin_default);
            margin = margin_default;
        }
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        this.setLayoutParams(layoutParams);
    }
}
