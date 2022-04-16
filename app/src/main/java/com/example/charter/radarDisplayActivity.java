package com.example.charter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.util.ArrayList;

public class radarDisplayActivity extends AppCompatActivity {
    RadarChart radarChart;
    private final int editCode = 12;
    private final int saveCode = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_display);
        Toolbar toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        radarChart = findViewById(R.id.radarChart);
        // radar chart
        /*
        ArrayList<RadarEntry> dataValues = new ArrayList<>();
        dataValues.add(new RadarEntry(5));
        dataValues.add(new RadarEntry(4));
        dataValues.add(new RadarEntry(3));
        dataValues.add(new RadarEntry(2));
        dataValues.add(new RadarEntry(1));
        ArrayList<RadarEntry> dataValues2 = new ArrayList<>();
        dataValues2.add(new RadarEntry(1));
        dataValues2.add(new RadarEntry(2));
        dataValues2.add(new RadarEntry(3));
        dataValues2.add(new RadarEntry(4));
        dataValues2.add(new RadarEntry(5));
        RadarDataSet dataSet1 = new RadarDataSet(dataValues,"data 1");
        RadarDataSet dataSet2 = new RadarDataSet(dataValues2,"data 2");
        dataSet1.setColor(Color.BLUE);
        dataSet2.setColor(Color.RED);
        dataSet2.setFillColor(Color.RED);
        dataSet2.setDrawFilled(true);
        RadarData radarData = new RadarData();
        radarData.addDataSet(dataSet1);
        radarData.addDataSet(dataSet2);
        String[] labels = {"BMW","Volkswagen","Volvo","Audi","Lamborghini"};
        ArrayList<String> test = new ArrayList<>();
        test.add("BMW");
        test.add("Volkswagen");
        test.add("Volvo");
        test.add("Audi");
        test.add("Lamborghini");
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(test));
        radarData.getDataSetByIndex(0).setLabel("testing");
        radarData.getDataSetByIndex(0).setHighlightEnabled(true);

        */
        //radarChart.setData(radarData);

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(radarDataRepository.getInstance().getAxisFormat());
        radarChart.setData(radarDataRepository.getInstance().getRadarData());
    }

    // menu icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == editCode) {
            if(resultCode == Activity.RESULT_OK){
                radarChart.getDescription().setText(data.getStringExtra("dsc"));
                for (int i=0;i<radarDataRepository.getInstance().getRadarData().getDataSetCount();i++){
                    radarChart.getData().getDataSetByIndex(i).setLabel(data.getStringExtra("radarTitle"+i));
                    radarChart.getData().getDataSetByIndex(i).setValueTextSize(data.getFloatExtra("fontSize",8f));
                    radarChart.getData().getDataSetByIndex(i).setDrawFilled(data.getBooleanExtra("fill"+i,false));
                }
                radarChart.notifyDataSetChanged();
                radarChart.invalidate();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
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
                radarChart.saveToGallery(path);
                Toast.makeText(this,"Zapisano w galerii",Toast.LENGTH_SHORT).show();
                //Log.d("saveTest","zapisaned");
            }
        }
    } //onActivityResult

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.saveButton:
                intent = new Intent(this,saveActivity.class);
                startActivityForResult(intent,saveCode);
                return true;

            case R.id.configureButton:
                intent = new Intent(this,radarConfigActivity.class);
                startActivityForResult(intent,editCode);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}