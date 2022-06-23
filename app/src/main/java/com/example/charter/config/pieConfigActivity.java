package com.example.charter.config;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.charter.R;
import com.example.charter.display.pieDisplayActivity;

public class pieConfigActivity extends AppCompatActivity {
    CheckBox percentValues;
    EditText dsc, font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_config);
        percentValues = findViewById(R.id.usePercent);
        dsc = findViewById(R.id.dscConfig);
        font = findViewById(R.id.pieFontSize);
    }

    public void submitChanges(View view){
        //PieChart pieChart = findViewById(R.id.pieChart_view);
        //pieChart.setUsePercentValues(findViewById(R.id.usePercent).isActivated());

        Intent intent = new Intent(this, pieDisplayActivity.class);
        intent.putExtra("percentageValue",percentValues.isChecked());
        intent.putExtra("dsc",dsc.getText().toString());
        if(!font.getText().toString().isEmpty()){
            intent.putExtra("font",Float.parseFloat(font.getText().toString()));
        }
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void cancelChanges(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}