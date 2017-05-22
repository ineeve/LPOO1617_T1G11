package com.raiden.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;


/**
 * Created by João on 03/05/2017.
 */

public class HangarMenu extends AppCompatActivity implements View.OnClickListener{
    private HorizontalScrollView mScrollView;
    private LinearLayout mLinearLayout;

    private ArrayList<mHangarCard> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangar_menu);

        mScrollView = (HorizontalScrollView) findViewById(R.id.mScrollView);
        mLinearLayout = (LinearLayout) findViewById(R.id.mScrollLayout);
        mHangarCard image = new mHangarCard(this);
        image.setImageResource(R.drawable.settings_button);
        image.setId(R.id.mCardHangar);
        mLinearLayout.addView(image, 0);
        mHangarCard image2 = new mHangarCard(this);
        image2.setImageResource(R.drawable.settings_button);
        image2.setId(R.id.mCardHangar2);
        mLinearLayout.addView(image2, 0);
        mHangarCard image3 = new mHangarCard(this);
        image3.setImageResource(R.drawable.settings_button);
        image3.setId(R.id.mCardHangar3);
        mLinearLayout.addView(image3, 0);
        mHangarCard image4 = new mHangarCard(this);
        image4.setImageResource(R.drawable.settings_button);
        image4.setId(R.id.mCardHangar4);
        mLinearLayout.addView(image4, 0);
        cards.add(image);
        cards.add(image2);
        cards.add(image3);
        cards.add(image4);
        image.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);

    }

    void setOtherCardsUnFocused(){
        for (mHangarCard card : cards) {
            if(card.isFocusable()) {
                card.setFocusable(false);
                int width = (int) (card.getWidth() / 1.2);
                int height = (int) (card.getHeight() / 1.2);
                card.resize(width, height);
            }
        }
    }

    void setOneCardFocused(View v){
        v.setFocusable(true);
        int width = (int) (v.getWidth() * 1.2);
        int height = (int) (v.getHeight() * 1.2);
        ((mHangarCard) v).resize(width, height);
    }

    void setOneCardUnFocused(View v){
        v.setFocusable(false);
        int width = (int) (v.getWidth() / 1.2);
        int height = (int) (v.getHeight() / 1.2);
        ((mHangarCard) v).resize(width, height);
    }

    @Override
    public void onClick(View v) {
        if(!v.isFocusable()){
            setOtherCardsUnFocused();
            setOneCardFocused(v);
        }
        else{
            setOneCardUnFocused(v);
        }
    }
}
