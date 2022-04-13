package com.example.charter;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class DataRepository {
    private final
    ArrayList<Entry> chartRawData;
    ArrayList<ILineDataSet> lineData;
    LineData chartData;

    // todo: constructor that pulls data from file

    // static temp data for testing
    public DataRepository(int limit, String label){
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
    }

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
