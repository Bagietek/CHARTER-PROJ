package com.example.charter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.github.mikephil.charting.charts.PieChart;

public class pieConfigActivity extends AppCompatActivity {
    CheckBox percerntValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_config);
        percerntValues = findViewById(R.id.usePercent);
    }

    public void submitChanges(View view){
        //PieChart pieChart = findViewById(R.id.pieChart_view);
        //pieChart.setUsePercentValues(findViewById(R.id.usePercent).isActivated());

        Intent intent = new Intent(this,pieDisplayActivity.class);
        intent.putExtra("percentageValue",percerntValues.isChecked());
        startActivityForResult(intent,2137);
        finish();
    }
}