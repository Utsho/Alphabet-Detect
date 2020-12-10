package com.example.alphabetgameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.nio.MappedByteBuffer;
import java.util.List;
import java.util.Random;

public class RecentActivity extends AppCompatActivity {

    ImageView views[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        views = new ImageView[26];

        for(int loop=0;loop<26;loop++)
        {
            char ch = (char) ('a' + loop);
            String id = ch+"1";
            int res = getResources().getIdentifier(id, "id", getPackageName()); //This line is necessary to "convert" a string (e.g. "i1", "i2" etc.) to a resource ID
            views[loop] = (ImageView) findViewById(res);
        }

        views[0].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+0));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+1));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+2));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+3));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[4].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+4));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[5].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+5));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[6].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+6));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[7].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+7));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[8].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+8));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[9].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+9));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[10].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+10));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[11].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+11));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[12].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+12));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[13].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+13));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[14].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+14));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[15].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+15));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[16].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+16));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[17].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+17));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[18].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+18));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[19].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+19));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[20].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+20));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[21].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+21));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[22].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+22));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[23].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+23));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[24].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+24));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });

        views[25].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent myIntent = new Intent(view.getContext(), ThreeActivity.class);
                myIntent.putExtra("letter",(char)('A'+25));
                startActivityForResult(myIntent, 4);
                return true;
            }
        });
    }
}