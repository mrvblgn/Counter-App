package com.example.bm_03;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Switch;


public class SetupActivity extends AppCompatActivity {

    Button btnUpMinus;
    Button btnUpPlus;
    EditText etUpValue;
    Switch swUpVib;
    Switch swUpSound;

    int upValue = 10;
    Button btnDownMinus;
    Button btnDownPlus;
    EditText etDownValue;
    Switch swDownVib;
    Switch swDownSound;
    int downValue = 0;

    ConfigClass configClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        btnUpMinus = findViewById(R.id.btn_up_minus);
        btnUpPlus = findViewById(R.id.btn_up_plus);
        etUpValue = findViewById(R.id.et_up_value);
        swUpVib = findViewById(R.id.sw_up_vib);
        swUpSound = findViewById(R.id.sw_up_sound);

        btnDownMinus = findViewById(R.id.btn_down_minus);
        btnDownPlus = findViewById(R.id.btn_down_plus);
        etDownValue = findViewById(R.id.et_down_value);
        swDownVib = findViewById(R.id.sw_vib);
        swDownSound = findViewById(R.id.sw_sound);

        configClass = ConfigClass.getInstance(getApplicationContext());

        btnUpPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpValue(1);
            }
        });

        btnUpMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setUpValue(-1);
            }
        });

        btnDownPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDownValue(1);
            }
        });

        btnDownMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setDownValue(-1);
            }
        });
    }

    public void setUpValue(int step) {
        upValue += step;
        etUpValue.setText(String.valueOf(upValue));
    }

    public void setDownValue(int step) {
        downValue += step;
        etDownValue.setText(String.valueOf(downValue));
    }

    @Override
    protected void onPause() {
        super.onPause();
        configClass.setData(upValue, downValue, configClass.currentValue,
                            swUpVib.isChecked(), swDownVib.isChecked(), swUpSound.isChecked(), swDownVib.isChecked());
        configClass.saveData();
        Log.d("SETUP", "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        configClass.loadData();
        upValue = configClass.upperLimit;
        downValue = configClass.lowerLimit;
        etUpValue.setText(String.valueOf(configClass.upperLimit));
        etDownValue.setText(String.valueOf(configClass.lowerLimit));
        swUpVib.setChecked(configClass.upperVib);
        swDownVib.setChecked(configClass.lowerVib);
        swUpSound.setChecked(configClass.upperSound);
        swDownSound.setChecked(configClass.lowerSound);


    }
}