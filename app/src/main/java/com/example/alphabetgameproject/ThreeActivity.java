package com.example.alphabetgameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ThreeActivity extends AppCompatActivity {
    ImageView im1,im2,im3;

    SQLiteDatabase alpdb;
    TextView t1,t2,t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        im1 = findViewById(R.id.im1);
        im2 = findViewById(R.id.im2);
        im3 = findViewById(R.id.im3);

        t1 = findViewById(R.id.txv1);
        t2 = findViewById(R.id.txv2);
        t3 = findViewById(R.id.txv3);

        alpdb =  this.openOrCreateDatabase("ALPDB", Context.MODE_PRIVATE, null);

        char letter = getIntent().getCharExtra("letter",'A');

        String quer = "SELECT * FROM ALPHABET WHERE IMAGE_OF=\""+letter+"\"";
        Cursor cursor = alpdb.rawQuery(quer,null);

        int i=0;
        while (cursor.moveToNext()) {
            byte[] byteArray = cursor.getBlob(4);
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
            int timeNeeded=cursor.getInt(1);
            String label=cursor.getString(0);
            String date=cursor.getString(2);
            i++;
            if(i==1){
                im1.setImageBitmap(bm);
                t1.setText("Time Needed(int seonds): "+timeNeeded+"\nLabel: "+label+"\nPhoto taken on: "+date);
            }
            if(i==2){
                im2.setImageBitmap(bm);
                t2.setText("Time Needed(int seonds): "+timeNeeded+"\nLabel: "+label+"\nPhoto taken on: "+date);
            }
            if(i==3){
                im3.setImageBitmap(bm);
                t3.setText("Time Needed(int seonds): "+timeNeeded+"\nLabel: "+label+"\nPhoto taken on: "+date);
                break;
            }
        }


    }
}