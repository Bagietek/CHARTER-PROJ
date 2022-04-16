package com.example.charter;

import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class radarDataRepository {
    private static radarDataRepository instance;
    private RadarData radarData;
    private IndexAxisValueFormatter axisFormat;

    protected radarDataRepository(){
        radarData = new RadarData();
        axisFormat = new IndexAxisValueFormatter();
    }

    public static radarDataRepository getInstance() {
        if(instance == null){
            instance = new radarDataRepository();
        }
        return instance;
    }

    public void clearData(){
        this.radarData.clearValues();
    }

    public RadarData getRadarData() {
        return radarData;
    }

    public void setRadarData(RadarData radarData) {
        this.radarData = radarData;
    }

    public IndexAxisValueFormatter getAxisFormat() {
        return axisFormat;
    }

    public void setAxisFormat(IndexAxisValueFormatter axisFormat) {
        this.axisFormat = axisFormat;
    }
}
