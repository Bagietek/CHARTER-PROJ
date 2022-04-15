package com.example.charter;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pieDataRepository {
    private static pieDataRepository instance;
    ArrayList<PieEntry> entries;
    Map<String, Integer> typeAmountMap;
    ArrayList<Integer> colors;

    protected pieDataRepository() {
        entries = new ArrayList<>();
        typeAmountMap = new HashMap<>();
        colors = new ArrayList<>();
    }

    public static pieDataRepository getInstance(){
        if(instance == null){
            instance = new pieDataRepository();
        }
        return instance;
    }

    public ArrayList<PieEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<PieEntry> entries) {
        this.entries = entries;
    }

    public Map<String, Integer> getTypeAmountMap() {
        return typeAmountMap;
    }

    public void setTypeAmountMap(Map<String, Integer> typeAmountMap) {
        this.typeAmountMap = typeAmountMap;
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
    }
}
