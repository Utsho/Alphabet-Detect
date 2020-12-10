package com.example.alphabetgameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;

public class CustomRecentActivity extends AppCompatActivity {

    String[][] s1;
    String[][] s2;
    Bitmap[][] img;
    ListView lv = null;
    SQLiteDatabase alpdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_recent);

        alpdb =  this.openOrCreateDatabase("ALPDB", Context.MODE_PRIVATE, null);

        s1 = new String[26][3];
        s2 = new String[26][3];
        img = new Bitmap[26][3];

        for(int k=0;k<26;k++)
        {
            char ch = (char) ('A' + k);
            String quer = "SELECT * FROM ALPHABET WHERE IMAGE_OF=\""+ch+"\"";
            Cursor cursor = alpdb.rawQuery(quer,null);

            int i=0;
            while (cursor.moveToNext()) {
                byte[] byteArray = cursor.getBlob(4);
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
                int timeNeeded=cursor.getInt(1);
                String label=cursor.getString(0);
                String date=cursor.getString(2);
                String s="";
                for(int p=0;p<date.length();p++)
                {
                    if(date.charAt(p)=='G')break;
                    s += date.charAt(p);
                }
                s1[k][i] = "Label: "+label;
                s2[k][i] = "Time needed: "+timeNeeded+" seconds\n Taken On: "+s;
                img[k][i] = bm;

                i++;
                if(i==3)break;
            }
        }

        lv = (ListView) findViewById(R.id.lstview);
        lv.setAdapter(new CustomAdapter( this, s1, s2, img));
    }
}