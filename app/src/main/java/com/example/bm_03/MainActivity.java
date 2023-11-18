package com.example.bm_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {
    Button btnMinus;
    Button btnPlus;
    Button btnSetup;
    TextView tvValue;

    int countValue = 0;
    int countUpStep = 1;
    int countDownStep = -1;

    ConfigClass configClass;
    Vibrator vibrator;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        btnSetup = findViewById(R.id.btn_setup);
        tvValue = findViewById(R.id.tv_value);

        configClass = ConfigClass.getInstance(getApplicationContext());

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ding);

        btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        configClass.loadData();
        countValue = configClass.currentValue;
        tvValue.setText(String.valueOf(countValue));
    }
    private void setCounterValue(int step) {
        if (countValue + step <= configClass.upperLimit && countValue + step >= configClass.lowerLimit ){
            countValue += step;
            tvValue.setText(String.valueOf(countValue));
            configClass.currentValue = countValue;
        } else {
            mediaPlayer.start();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        configClass.saveData();
    }

    public void clickMinus(View view) {
        setCounterValue(countDownStep);
    }

    public void clickPlus(View view) {
        setCounterValue(countUpStep);
    }
}