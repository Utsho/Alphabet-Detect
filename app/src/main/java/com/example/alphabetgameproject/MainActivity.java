package com.example.alphabetgameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button newGame, history;
    SQLiteDatabase alpdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alpdb =  this.openOrCreateDatabase("ALPDB", Context.MODE_PRIVATE, null);
        alpdb.execSQL("CREATE TABLE IF NOT EXISTS ALPHABET (LABEL TEXT, TIME_NEEDED INT,TIME_OF_CAPTURE TEXT,IMAGE_OF TEXT,IMAGE BLOB);");

        newGame = findViewById(R.id.game);
        history = findViewById(R.id.history);

        final MediaPlayer player = MediaPlayer.create(this,R.raw.intro);
        player.setLooping(true);
        player.start();

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                Intent myIntent = new Intent(view.getContext(), GameActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                Intent myIntent = new Intent(view.getContext(), HistoryActivity.class);
                startActivityForResult(myIntent, 1);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}