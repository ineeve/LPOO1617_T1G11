package com.raiden.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.raiden.game.model.GameModel;

import java.util.ArrayList;

public class score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);

        long score = getIntent().getLongExtra("SCORE",0);
        scoreLabel.setText(score + "");
    }

    public void tryAgain(View view){
        GameModel.getInstance().addPlayers(new ArrayList<Player>(){{
            add(new Player(Arena.getInstance().getmPlayerID()));
        }});
        startActivity(new Intent(this, PlayLauncher.class));
    }

    public void backPressed(View view){
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
