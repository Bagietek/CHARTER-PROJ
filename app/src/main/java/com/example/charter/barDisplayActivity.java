package com.example.charter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class barDisplayActivity extends AppCompatActivity {
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_display);
        Toolbar toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        barChart = findViewById(R.id.barChart_view);

        // todo: move to repository
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i =0;i<6;i++){
            values.add(i*100.1);
        }
        for (int i = 0; i < values.size(); i++) {
            BarEntry barEntry = new BarEntry(i, values.get(i).floatValue());
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Static testing chart");

        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        barChart.invalidate();
    }

    // menu icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
}