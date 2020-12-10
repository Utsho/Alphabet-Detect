package com.example.alphabetgameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PlotActivity extends AppCompatActivity {

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;
    SQLiteDatabase alpdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        alpdb =  this.openOrCreateDatabase("ALPDB", Context.MODE_PRIVATE, null);

        barChart = findViewById(R.id.BarChart);
        getEntries();
        barDataSet = new BarDataSet(barEntries, "");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(18f);
    }

    private void getEntries() {
        barEntries = new ArrayList<>();
        for(int i=0;i<26;i++)
        {
            char ch = (char) ('A'+i);
            String quer = "SELECT * FROM ALPHABET WHERE IMAGE_OF=\""+ch+"\"";
            Cursor cursor = alpdb.rawQuery(quer,null);
            int total_seconds = 0;
            int count=0;
            while (cursor.moveToNext()) {
                int second = cursor.getInt(1);
                total_seconds += second;
                count++;
            }
            if(count!=0)barEntries.add(new BarEntry((char)ch,(int)total_seconds/count));
            else barEntries.add(new BarEntry((char)ch,(int)0));
        }
    }
}