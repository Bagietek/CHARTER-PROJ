package com.example.charter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.LineData;

public class lineDispalyActivity extends AppCompatActivity {
    private final int editCode = 5;
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
        /*ArrayList<Entry> values = new ArrayList<>();
        for (int i=0;i<50;i++){
            values.add(new Entry(i,i));
        }
        LineDataSet set1 = new LineDataSet(values,"sin(x)");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);*/


        lineDataRepository data = new lineDataRepository();
        lineChart.setData(data.getChartData());
        //lineChart.getLegend().setEnabled(false);
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

                return true;

            case R.id.configureButton:
                intent = new Intent(this,lineConfigActivity.class);
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
                lineChart.getData().getDataSetByIndex(0).setLabel(data.getStringExtra("lineTitle"));
                // font colour for a line
                //lineChart.getData().getDataSetByIndex(0).setValueTextColor(Color.YELLOW);
                // filling
                //lineChart.getData().getDataSetByIndex(0).setDrawFilled(true);
                // font size
                lineChart.getData().getDataSetByIndex(0).setValueTextSize(data.getFloatExtra("fontSize",8f));
                //Log.d("dsc",data.getStringExtra("dsc"));
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    } //onActivityResult

}