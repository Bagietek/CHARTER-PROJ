package com.example.charter;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class lineDataRepository {
    private static lineDataRepository instance;
    ArrayList<Entry> chartRawData;
    ArrayList<ILineDataSet> lineData;
    LineData chartData;


    // static constr
    protected lineDataRepository(){
        chartRawData = new ArrayList<>();
        for (int i=0;i<50;i++){
            chartRawData.add(new Entry(i,i));
        }
        LineDataSet set1 = new LineDataSet(chartRawData,"foobar");
        lineData = new ArrayList<>();
        lineData.add(set1);
        //set1.setColor(Color.BLACK);
        chartData = new LineData(lineData);

    }

    public void loadData(){
        // todo: constructor that pulls data from file
    }

    // singleton for data
    public lineDataRepository getInstance(){
        if(instance == null){
            instance = new lineDataRepository();
        }
        return instance;
    }
    // static temp data for testing
    /*public lineDataRepository(int limit, String label){
        if(limit<=0){
            limit = 50;
        }
        chartRawData = new ArrayList<>();
        for (int i=0;i<limit;i++){
            chartRawData.add(new Entry(i,i));
        }
        LineDataSet set1 = new LineDataSet(chartRawData,label);
        lineData = new ArrayList<>();
        lineData.add(set1);
        chartData = new LineData(lineData);
    }*/

    public ArrayList<Entry> getChartRawData() {
        return chartRawData;
    }

    public ArrayList<ILineDataSet> getLineData() {
        return lineData;
    }

    public LineData getChartData() {
        return chartData;
    }

}
