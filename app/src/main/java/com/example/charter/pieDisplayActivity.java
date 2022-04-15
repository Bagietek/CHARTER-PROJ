package com.example.charter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pieDisplayActivity extends AppCompatActivity {
    PieChart pieChart;
    final int editCode = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_display);
        Toolbar toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        pieChart = findViewById(R.id.pieChart_view);
        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieDataRepository.getInstance().getEntries(), "");
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(pieDataRepository.getInstance().getColors());
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    // menu icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == editCode && resultCode == Activity.RESULT_OK){
                // show percentage values
                pieChart.setUsePercentValues(data.getBooleanExtra("percentageValue",false));
                // set description
                Description dsc = new Description();
                dsc.setText(data.getStringExtra("dsc"));
                for (int i=0;i<pieChart.getData().getDataSetCount();i++){
                    pieChart.getData().getDataSetByIndex(i).setValueTextSize(data.getFloatExtra("font",8f));
                }
                pieChart.setDescription(dsc);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.saveButton:
                //todo:save button
                return true;

            case R.id.configureButton:
                intent = new Intent(this,pieConfigActivity.class);
                startActivityForResult(intent,editCode);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}