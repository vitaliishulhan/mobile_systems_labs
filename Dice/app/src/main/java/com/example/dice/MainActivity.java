package com.example.dice;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import java.util.Random;

public class MainActivity extends Activity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private Dice[] dices = null;
    private final static int SHAKE_TRESHOLD = 7;

    private void generateRandomNumber() {

        Random randomGenerator = new Random();

        for (Dice dice : dices) {
            int randomNum = randomGenerator.nextInt(6) + 1;
            dice.setValue(randomNum);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.choose_dialog);

        final RadioGroup radioGroup = d.findViewById(R.id.chooseRadioGroup);

        Button setBtn = d.findViewById(R.id.setBtn);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.dice1RadioBtn:
                        dices = new Dice[1];
                        dices[0] = new Dice(MainActivity.this,480);

                        RelativeLayout.LayoutParams oneParams = new RelativeLayout.LayoutParams(480,480);
                        dices[0].setLayoutParams(oneParams);

                        RelativeLayout relativeLayoutFor1 = new RelativeLayout(MainActivity.this);

                        relativeLayoutFor1.setGravity(Gravity.CENTER);
                        relativeLayoutFor1.addView(dices[0]);

                        MainActivity.this.addContentView(relativeLayoutFor1,new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));

                        d.dismiss();
                        break;
                    case R.id.dice2RadioBtn:
                        dices = new Dice[2];

                        RelativeLayout wrapFor2 = new RelativeLayout(MainActivity.this);
                        RelativeLayout relativeLayoutFor2 = new RelativeLayout(MainActivity.this);

                        wrapFor2.addView(relativeLayoutFor2);
                        wrapFor2.setGravity(Gravity.CENTER);

                        for (int i = 0; i < 2; i++) {
                            dices[i] = new Dice(MainActivity.this,480);
                        }

                        dices[0].setId(R.id.dice0);
                        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(480,480);
                        margin.bottomMargin = 50;

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(480,480);
                        params.addRule(RelativeLayout.BELOW,R.id.dice0);

                        dices[0].setLayoutParams(margin);
                        dices[1].setLayoutParams(params);

                        relativeLayoutFor2.addView(dices[0]);
                        relativeLayoutFor2.addView(dices[1]);

                        MainActivity.this.addContentView(wrapFor2, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));

                        d.dismiss();
                        break;
                    case R.id.dice3RadioBtn:
                        dices = new Dice[3];

                        RelativeLayout wrapFor3 = new RelativeLayout(MainActivity.this);
                        RelativeLayout relativeLayoutFor3 = new RelativeLayout(MainActivity.this);

                        wrapFor3.addView(relativeLayoutFor3);
                        wrapFor3.setGravity(Gravity.CENTER);

                        for (int i = 0; i < 3; i++) {
                            dices[i] = new Dice(MainActivity.this,360);
                        }

                        dices[0].setId(R.id.dice0);
                        dices[1].setId(R.id.dice1);

                        ViewGroup.MarginLayoutParams paramsFor1 = new ViewGroup.MarginLayoutParams(360,360);
                        paramsFor1.bottomMargin = 50;

                        RelativeLayout.LayoutParams paramsFor2 = new RelativeLayout.LayoutParams(360,360);
                        paramsFor2.addRule(RelativeLayout.BELOW,R.id.dice0);

                        RelativeLayout.LayoutParams paramsFor3 = new RelativeLayout.LayoutParams(360,360);
                        paramsFor3.topMargin = 50;
                        paramsFor3.addRule(RelativeLayout.BELOW,R.id.dice1);

                        dices[0].setLayoutParams(paramsFor1);
                        dices[1].setLayoutParams(paramsFor2);
                        dices[2].setLayoutParams(paramsFor3);

                        relativeLayoutFor3.addView(dices[0]);
                        relativeLayoutFor3.addView(dices[1]);
                        relativeLayoutFor3.addView(dices[2]);

                        MainActivity.this.addContentView(wrapFor3, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));

                        d.dismiss();
                }
            }
        });


        d.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(radioGroup.getCheckedRadioButtonId() == -1) {
                    d.show();
                }
            }
        });
        d.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (dices != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
            if (acceleration > SHAKE_TRESHOLD)
                generateRandomNumber();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {/*not used*/}
}