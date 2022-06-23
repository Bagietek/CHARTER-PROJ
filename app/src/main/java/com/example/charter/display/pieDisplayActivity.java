package com.example.charter.display;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.charter.R;
import com.example.charter.config.pieConfigActivity;
import com.example.charter.repository.pieDataRepository;
import com.example.charter.saveActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.io.File;

public class pieDisplayActivity extends AppCompatActivity {
    PieChart pieChart;
    final int editCode = 5;
    private final int saveCode = 15;

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
        }else if(requestCode == saveCode){
            if(resultCode == Activity.RESULT_OK){
                String path = data.getStringExtra("path");
                if(path.isEmpty()){
                    Toast.makeText(this,"Błąd zapisywania",Toast.LENGTH_SHORT).show();
                    return;
                }
                File file = new File( Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/" +  path+".png");
                //Log.d("saveTest",file.getPath());
                if(file.exists()){
                    Toast.makeText(this,"Istnieje już zdjęcie o tej nazwie",Toast.LENGTH_LONG).show();
                    //Log.d("saveTest","nie zapisaned");
                    return;
                }
                pieChart.saveToGallery(path);
                Toast.makeText(this,"Zapisano w galerii",Toast.LENGTH_SHORT).show();
                //Log.d("saveTest","zapisaned");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.saveButton:
                intent = new Intent(this, saveActivity.class);
                startActivityForResult(intent,saveCode);
                return true;

            case R.id.configureButton:
                intent = new Intent(this, pieConfigActivity.class);
                startActivityForResult(intent,editCode);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}