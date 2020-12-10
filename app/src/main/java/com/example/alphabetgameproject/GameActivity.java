package com.example.alphabetgameproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class GameActivity extends Activity {
    SQLiteDatabase alpdb;
    ImageView views[];
    boolean selected[];
    Random random;
    int letter, firstTime = 0;
    Animation anim;
    TextToSpeech textToSpeech;
    MediaPlayer player;
    Button ex;
    TextView timer;
    boolean correct;
    private static final int REQUEST_CAPTURE_IMAGE = 100;

    Interpreter tflite;
    MappedByteBuffer tfliteModel;
    TensorImage inputImageBuffer;
    int imageX, imageY;
    TensorBuffer outputProbBuffer;
    TensorProcessor probProcessor;
    static final float IMAGE_MEAN = 0.0f;
    static final float IMAGE_STD = 1.0f;
    static final float PROBABILITY_MEAN = 0.0f;
    static final float PROBABILITY_STD = 255.0f;
    List<String> labels;

    Date time, time2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(firstTime == 0)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);

            alpdb =  this.openOrCreateDatabase("ALPDB", Context.MODE_PRIVATE, null);

            time = new Date(System.currentTimeMillis());
            timer = findViewById(R.id.timer);

            ex = findViewById(R.id.exit);

            random = new Random();
            views = new ImageView[26];
            selected = new boolean[26];

            for(int loop=0;loop<26;loop++)
            {
                char ch = (char) ('a' + loop);
                int res = getResources().getIdentifier(String.valueOf(ch), "id", getPackageName()); //This line is necessary to "convert" a string (e.g. "i1", "i2" etc.) to a resource ID
                views[loop] = (ImageView) findViewById(res);
            }

            try{
                AssetFileDescriptor fileDescriptor = this.getAssets().openFd("mobilenet_v1_1.0_224_quant.tflite");
                FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
                FileChannel fileChannel = inputStream.getChannel();
                long startoffset = fileDescriptor.getStartOffset();
                long declaredLength = fileDescriptor.getDeclaredLength();
                tflite = new Interpreter(fileChannel.map(FileChannel.MapMode.READ_ONLY,startoffset,declaredLength));
            }catch (IOException e){
                e.printStackTrace();
            }

            //anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            //anim.setInterpolator(new LinearInterpolator());
            //anim.setRepeatCount(Animation.INFINITE);
            //anim.setDuration(700);

            anim = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
            anim.setDuration(300); // duration - half a second
            anim.setInterpolator(new LinearInterpolator()); // do not alter animation rate
            anim.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
            anim.setRepeatMode(Animation.REVERSE);

            for(int i=0;i<26;i++)selected[i]=false;

            letter = random.nextInt(26);
            selected[letter] = true;

            textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
                        textToSpeech.setLanguage(Locale.ENGLISH);
                        char selectedLetter = (char) ('A'+letter);
                        textToSpeech.speak("You got: "+selectedLetter, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                }
            });
            try {
                Thread.sleep(2100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            views[letter].startAnimation(anim);
        }

        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        views[0].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 0)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 1)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 2)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 3)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[4].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 4)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[5].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 5)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[6].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 6)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[7].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 7)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[8].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 8)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[9].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 9)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[10].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 10)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[11].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 11)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[12].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 12)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[13].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 13)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[14].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 14)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[15].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 15)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[16].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 16)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[17].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 17)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[18].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 18)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[19].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 19)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[20].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 20)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[21].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 21)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[22].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 22)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[23].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 23)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[24].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 24)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

        views[25].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(letter == 25)
                {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

                }
                return true;
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            int time_needed;
            String label="";
           final Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

           ByteArrayOutputStream bout = new ByteArrayOutputStream();
           imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bout);
           byte[] imageInByte = bout.toByteArray();

            time2 = new Date(System.currentTimeMillis());
            long seconds = (time2.getTime()-time.getTime())/1000;
            timer.setText("Seconds Passed: "+seconds);

           int imageTensorIndex = 0;
           int[] imageShape = tflite.getInputTensor(imageTensorIndex).shape();
           imageY = imageShape[1];
           imageX = imageShape[2];
           DataType dataType = tflite.getInputTensor(imageTensorIndex).dataType();

           int probTensorIndex = 0;
           int[] probShape = tflite.getOutputTensor(probTensorIndex).shape();
           DataType dataType1 = tflite.getOutputTensor(probTensorIndex).dataType();

           inputImageBuffer = new TensorImage(dataType);
           outputProbBuffer = TensorBuffer.createFixedSize(probShape,dataType1);
           probProcessor = new TensorProcessor.Builder().add(new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD)).build();

           inputImageBuffer.load(imageBitmap);

           int cropSize = Math.min(imageBitmap.getWidth(), imageBitmap.getHeight());

           ImageProcessor imageProcessor = new ImageProcessor.Builder()
                   .add(new ResizeWithCropOrPadOp(cropSize, cropSize))
                   .add(new ResizeOp(imageX, imageY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                   .add(new NormalizeOp(IMAGE_MEAN, IMAGE_STD))
                   .build();
           inputImageBuffer = imageProcessor.process(inputImageBuffer);
           tflite.run(inputImageBuffer.getBuffer(),outputProbBuffer.getBuffer().rewind());

           try{
               labels = FileUtil.loadLabels(this,"labels_mobilenet_quant_v1_224.txt");
           }catch (Exception e){
               e.printStackTrace();
           }
           Map<String, Float> labeledProbability = new TensorLabel(labels, probProcessor.process(outputProbBuffer)).getMapWithFloatValue();
           //float maxValueInMap =(Collections.max(labeledProbability.values()));
            PriorityQueue<Float> queue = new PriorityQueue<>(5, Collections.<Float>reverseOrder());
            for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
                queue.add(entry.getValue());
            }

            boolean startsWith = false;
            Iterator iterator = queue.iterator();
            int i=0;
            while (iterator.hasNext()) {
                float val = (float) iterator.next();
                String lab = "";
                for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
                    if (entry.getValue()==val) {
                        lab = entry.getKey();
                    }
                }
                i++;
                char check = (char) ('a' + letter);
                char check1= (char) ('A' + letter);
                if(lab.startsWith(String.valueOf(check)) || lab.startsWith(String.valueOf(check1)))
                {
                    label = lab;
                    startsWith = true;
                    break;
                }
                if(i==5)break;
            }
            if(label.length()<2)
            {
                iterator = queue.iterator();
                while (iterator.hasNext()) {
                    float val = (float) iterator.next();
                    String lab = "";
                    for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
                        if (entry.getValue()==val) {
                            lab = entry.getKey();
                        }
                    }
                    label=lab;
                    break;
                }
            }
            //label = "dog"
            ContentValues contentValues = new ContentValues();
            contentValues.put("LABEL",label);
            contentValues.put("TIME_NEEDED",(int)seconds);
            contentValues.put("TIME_OF_CAPTURE", String.valueOf(time2));
            contentValues.put("IMAGE_OF", String.valueOf((char) ('A' + letter)));
            contentValues.put("IMAGE", imageInByte);

            alpdb.insert("ALPHABET", null, contentValues);

            views[letter].setImageBitmap(imageBitmap);
            if(startsWith )correct = true;
            if(!startsWith)correct = false;

            firstTime++;
            if(firstTime < 26 && firstTime > 0)
            {
                if(correct)
                {
                    player = MediaPlayer.create(getApplicationContext(),R.raw.yay);
                    player.setLooping(false);
                    player.start();
                    try {
                        Thread.sleep(4100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    player = MediaPlayer.create(getApplicationContext(),R.raw.sry);
                    player.setLooping(false);
                    player.start();
                    try {
                        Thread.sleep(2100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                views[letter].setAnimation(null);

                letter = random.nextInt(26);
                while(selected[letter] != false)
                {
                    letter = random.nextInt(26);
                }

                selected[letter] = true;
                views[letter].setAnimation(anim);
            }

            textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
                        textToSpeech.setLanguage(Locale.ENGLISH);
                        char selectedLetter = (char) ('A'+letter);
                        textToSpeech.speak("You got: "+selectedLetter, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                }
            });
            try {
                Thread.sleep(2100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time = new Date(System.currentTimeMillis());

            return;
        }

    }
}