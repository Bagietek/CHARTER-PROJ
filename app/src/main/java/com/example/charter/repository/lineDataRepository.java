package com.example.charter.repository;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class lineDataRepository {
    private static lineDataRepository instance;
    ArrayList<Entry> chartRawData;
    ArrayList<ILineDataSet> lineData;
    public LineData chartData;



    protected lineDataRepository(){
        /*chartRawData = new ArrayList<>();
        for (int i=0;i<50;i++){
            chartRawData.add(new Entry(i,i));
        }
        LineDataSet set1 = new LineDataSet(chartRawData,"foobar");
        lineData = new ArrayList<>();
        lineData.add(set1);
        //set1.setColor(Color.BLACK);
        chartData = new LineData(lineData);
        */
        chartData = new LineData();
        chartRawData = new ArrayList<>();
        lineData = new ArrayList<>();
    }

    public static void loadData(LineData _chartData){
        if(instance == null){
            instance = new lineDataRepository();
        }
        instance.setChartData(_chartData);
    }

    // singleton for data
    public static lineDataRepository getInstance(){
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

    public void setChartData(LineData chartData) {
        this.chartData = chartData;
    }


}
