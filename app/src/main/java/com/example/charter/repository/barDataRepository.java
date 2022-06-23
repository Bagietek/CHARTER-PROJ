package com.example.charter.repository;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class barDataRepository {
    private static barDataRepository instance;
    private ArrayList<BarEntry> entries;
    private ArrayList<IBarDataSet> dataSets;
    public static float groupSpace;
    public static float barSpace;
    public static float barWidth;
    public static boolean multiBar;

    protected barDataRepository(){
        entries = new ArrayList<>();
        dataSets = new ArrayList<>();
    }

    public static barDataRepository getInstance(){
        if(instance == null){
            instance = new barDataRepository();
        }
        return instance;
    }

    public void clear(){
        this.dataSets.clear();
        this.entries.clear();
    }

    public static void calculateSpace(int bars){
        switch (bars){
            case 1:
                multiBar = false;
                barWidth = 0.3f;
                break;
            case 2:
                multiBar = true;
                groupSpace = 0.3f;
                barSpace = 0f;
                barWidth = 0.3f;
                break;
            case 3:
                multiBar = true;
                groupSpace = 0.2f;
                barSpace = 0f;
                barWidth = 0.3f;
                break;
            default:
                break;
        }
    }

    public ArrayList<BarEntry> getEntries() {
        return entries;
    }

    public ArrayList<IBarDataSet> getDataSets() {
        return dataSets;
    }

    public float getGroupSpace() {
        return groupSpace;
    }

    public float getBarSpace() {
        return barSpace;
    }

    public float getBarWidth() {
        return barWidth;
    }

    public static boolean isMultiBar() {
        return multiBar;
    }
}
