package com.raiden.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.raiden.game.MainActivity.mGoogleApiClient;

public class PlayLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("IP");
        myRef.setValue("Ola do aplicativo");

		startGame();
	}

	private void startGame(){
		if(mGoogleApiClient != null)
			if(mGoogleApiClient.isConnected())
				Games.Achievements.unlock(mGoogleApiClient, getResources().getString(R.string.achievement_first_run));;
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = true;
		config.useImmersiveMode = true;
		config.useWakelock = true;
		initialize(Arena.getInstance(), config);
	}
}
