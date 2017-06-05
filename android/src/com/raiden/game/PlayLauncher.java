package com.raiden.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = true;
		config.useImmersiveMode = true;
		Arena arena = Arena.getInstance();
		arena.setBroadcast(GoogleServices.getInstance());
		initialize(arena, config);
	}
}
