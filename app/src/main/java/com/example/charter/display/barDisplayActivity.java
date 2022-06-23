package com.example.charter.display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.charter.R;
import com.example.charter.repository.barDataRepository;
import com.example.charter.config.barConfigActivity;
import com.example.charter.saveActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

import java.io.File;

public class barDisplayActivity extends AppCompatActivity {
    BarChart barChart;
    private final int editCode = 10;
    private final int saveCode = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_display);
        Toolbar toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        barChart = findViewById(R.id.barChart_view);

        BarData data = new BarData(barDataRepository.getInstance().getDataSets());
        data.setBarWidth(barDataRepository.barWidth);
        barChart.setData(data);
        if(barDataRepository.isMultiBar()){
            barChart.groupBars(0f,barDataRepository.groupSpace,barDataRepository.barSpace);
        }

        //barChart.getBarData().getDataSetByIndex(0).setLabel("testing analny");
        //barChart.notifyDataSetChanged();

        barChart.invalidate();
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
                barChart.getDescription().setText(data.getStringExtra("dsc"));

                for (int i=0;i<barDataRepository.getInstance().getDataSets().size();i++){
                    Log.d("bar",data.getStringExtra("barTitle"+i));
                    barChart.getBarData().getDataSetByIndex(i).setLabel(data.getStringExtra("barTitle"+i));
                    barChart.getData().getDataSetByIndex(i).setValueTextSize(data.getFloatExtra("fontSize",8f));
                }

                barChart.notifyDataSetChanged();
                barChart.invalidate();
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
                barChart.saveToGallery(path);
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
                intent = new Intent(this, saveActivity.class);
                startActivityForResult(intent,saveCode);
                return true;

            case R.id.configureButton:
                intent = new Intent(this, barConfigActivity.class);
                startActivityForResult(intent,editCode);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}

/*
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        ArrayList<BarEntry> entries3 = new ArrayList<>();
        for (int i =0;i<6;i++){
            values.add(i*100.1);
        }
        for (int i = 0; i < values.size(); i++) {
            BarEntry barEntry = new BarEntry(i, values.get(i).floatValue());
            entries.add(barEntry);
            BarEntry barEntry1 = new BarEntry(i,values.get(i).floatValue()+50f);
            entries2.add(barEntry1);
            BarEntry barEntry2 = new BarEntry(i,values.get(i).floatValue()+100f);
            entries3.add(barEntry2);
        }
        BarDataSet barDataSet = new BarDataSet(entries, "Static testing chart");
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        barDataSet.setColor(Color.BLACK);
        dataSets.add(barDataSet);
        BarDataSet barDataSet1 = new BarDataSet(entries2,"");
        barDataSet1.setColor(Color.YELLOW);
        dataSets.add(barDataSet1);
        BarDataSet barDataSet2 = new BarDataSet(entries3,"");
        barDataSet2.setColor(Color.BLUE);
        dataSets.add(barDataSet2);
        float groupSpace = 0.2f;
        float barSpace = 0f;
        float barWidth = 0.3f;

        BarData data = new BarData(dataSets);
        data.setBarWidth(barWidth);
       //
        //data.setValueTextSize(24f);
        //data.getDataSetByIndex(0).setLabel("testing");
        //data.

        // (barSpace + barWidth) * 2 + groupSpace = 1
        barChart.groupBars(0f,groupSpace,barSpace);
*/