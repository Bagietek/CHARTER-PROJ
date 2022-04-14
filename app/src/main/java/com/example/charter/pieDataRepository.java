package com.example.charter;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pieDataRepository {
    private static pieDataRepository instance;
    ArrayList<PieEntry> entries = new ArrayList<>();
    Map<String, Integer> typeAmountMap = new HashMap<>();
    ArrayList<Integer> colors = new ArrayList<>();

    protected pieDataRepository() {

    }

    public pieDataRepository getInstance(){
        if(instance == null){
            instance = new pieDataRepository();
        }
        return instance;
    }



}
