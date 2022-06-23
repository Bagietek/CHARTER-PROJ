package com.example.charter.display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.charter.R;
import com.example.charter.config.lineConfigActivity;
import com.example.charter.repository.lineDataRepository;
import com.example.charter.saveActivity;
import com.github.mikephil.charting.charts.LineChart;

import java.io.File;

public class lineDispalyActivity extends AppCompatActivity {
    private final int editCode = 5;
    private final int saveCode = 6;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_dispaly);
        Toolbar toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        // line chart
        lineChart = findViewById(R.id.chart1);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setData(lineDataRepository.getInstance().getChartData());

    }


    // menu icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
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
                intent = new Intent(this, lineConfigActivity.class);
                startActivityForResult(intent,editCode);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == editCode) {
            if(resultCode == Activity.RESULT_OK){
                // chart description
                lineChart.getDescription().setText(data.getStringExtra("dsc"));
                // line title
               // lineChart.getData().getDataSetByIndex(0).setLabel(data.getStringExtra("lineTitle"));
                for (int i=0;i<lineDataRepository.getInstance().chartData.getDataSetCount();i++){
                    lineChart.getData().getDataSetByIndex(i).setLabel(data.getStringExtra("lineTitle"+i));
                    lineChart.getData().getDataSetByIndex(i).setValueTextSize(data.getFloatExtra("fontSize",8f));
                }
                // font colour for a line
                //lineChart.getData().getDataSetByIndex(0).setValueTextColor(Color.YELLOW);
                // filling
                //lineChart.getData().getDataSetByIndex(0).setDrawFilled(true);
                // font size

                lineChart.notifyDataSetChanged();
                lineChart.invalidate();

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
                lineChart.saveToGallery(path);
                Toast.makeText(this,"Zapisano w galerii",Toast.LENGTH_SHORT).show();
                //Log.d("saveTest","zapisaned");
            }
        }
    } //onActivityResult

}