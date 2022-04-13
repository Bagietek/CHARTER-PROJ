package com.example.charter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class radarDisplayActivity extends AppCompatActivity {
    RadarChart radarChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_display);
        Toolbar toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        radarChart = findViewById(R.id.radarChart);
        // todo: move to repository
        // radar chart
        SparseIntArray factors = new SparseIntArray(5);
        SparseIntArray scores = new SparseIntArray(5);
        ArrayList radarEntries = new ArrayList<>();
        radarEntries.add(new RadarEntry(0, 0.21f));
        radarEntries.add(new RadarEntry(1, 0.12f));
        radarEntries.add(new RadarEntry(2, 0.20f));
        radarEntries.add(new RadarEntry(2, 0.52f));
        radarEntries.add(new RadarEntry(3, 0.29f));
        radarEntries.add(new RadarEntry(4, 0.62f));
        RadarDataSet radarDataSet = new RadarDataSet(radarEntries,"Static temp chart");
        RadarData radarData = new RadarData(radarDataSet);
        radarDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        radarDataSet.setValueTextColor(Color.BLACK);
        radarDataSet.setValueTextSize(18f);
        radarChart.setData(radarData);
    }

    // menu icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

}