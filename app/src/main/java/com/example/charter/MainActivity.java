package com.example.charter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.PathUtils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button loadData;
    Spinner chartTypes;
    private final int fileCode = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        loadData = findViewById(R.id.loadDataButton);
        // spinner set up
        chartTypes = findViewById(R.id.typeSelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.chart_types, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chartTypes.setAdapter(adapter);

    }

    // file selector
    public void loadData(View view){
        // permission check
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MANAGE_EXTERNAL_STORAGE},1);

        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("application/json");
        chooseFile = Intent.createChooser(chooseFile, "Wybierz plik");
        startActivityForResult(chooseFile,fileCode);
    }

    // loading data from chosen file
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == fileCode) {
            if(resultCode == Activity.RESULT_OK){
                Uri uri = data.getData();
                File filePath = new File(uri.getPath());
                // getting the actual file path
                final String path[] = filePath.getPath().split(":");
                String json = path[1];
                String jsonData;

                try {

                    Log.d("json",json);
                    File file = new File(json);
                    if(!file.canRead()){
                        Toast.makeText(MainActivity.this,"Reading error!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    FileInputStream fis = new FileInputStream(json);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = bufferedReader.readLine();
                    while(line != null){
                        stringBuilder.append(line).append("\n");
                        line = bufferedReader.readLine();
                    }
                    bufferedReader.close();
                    jsonData = stringBuilder.toString();

                }catch(IOException ex) {
                    ex.printStackTrace();
                    return;
                }
                try{
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray array;
                    Intent intent;

                    switch(jsonObject.getString("type")){
                        case "radar":
                            radarDataRepository.getInstance().clearData();
                            array = jsonObject.getJSONArray("labels");
                            JSONArray lineArray = jsonObject.getJSONArray("lines");

                            /*if(array.length() != lineArray.getJSONArray(0).getJSONArray(0).length()){
                                Toast.makeText(MainActivity.this,"Corrupted data",Toast.LENGTH_SHORT).show();
                                return;
                            }*/
                            ArrayList<String> labels = new ArrayList<>();
                            for (int i=0;i<array.length();i++){
                                labels.add(array.getString(i));
                            }
                            radarDataRepository.getInstance().setAxisFormat(new IndexAxisValueFormatter(labels));
                            for (int i=0;i<lineArray.length();i++){
                                JSONObject tmp = new JSONObject(lineArray.get(i).toString());
                                JSONArray arrayY = tmp.getJSONArray("y");
                                ArrayList<RadarEntry> dataValues = new ArrayList<>();
                                for (int j=0;j<arrayY.length();j++){
                                    dataValues.add(new RadarEntry(arrayY.getInt(j)));
                                }
                                RadarDataSet dataSet = new RadarDataSet(dataValues,"");
                                dataSet.setColor(Color.parseColor(tmp.getString("colour")));
                                dataSet.setFillColor(Color.parseColor(tmp.getString("colour")));
                                radarDataRepository.getInstance().getRadarData().addDataSet(dataSet);
                            }
                            intent = new Intent(this,radarDisplayActivity.class);
                            startActivityForResult(intent,4);
                            break;
                        case "bar":
                            barDataRepository.getInstance().clear();
                            array = jsonObject.getJSONArray("lines");
                            if(array.length() > 3){
                                Toast.makeText(MainActivity.this,"Corrupted data",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //ArrayList<Double> values = new ArrayList<>();
                            for (int i=0;i<array.length();i++){
                                ArrayList<BarEntry> entriesBar = new ArrayList<>();
                                JSONObject tmp = new JSONObject(array.get(i).toString());
                                JSONArray arrayYBar = tmp.getJSONArray("y");
                                for (int j=0;j<arrayYBar.length();j++){
                                    //values.add(arrayYBar.getDouble(j));
                                    entriesBar.add(new BarEntry(j,Float.parseFloat(arrayYBar.getString(j))));
                                }
                                BarDataSet tmp2 = new BarDataSet(entriesBar,"");
                                tmp2.setColor(Color.parseColor(tmp.getString("colour")));
                                barDataRepository.getInstance().getDataSets().add(tmp2);
                            }
                            barDataRepository.calculateSpace(array.length());

                            intent = new Intent(this,barDisplayActivity.class);
                            startActivityForResult(intent,2);
                            /*ArrayList<BarEntry> entriesBar = new ArrayList<>();
                            JSONArray arrayXbar = bar.getJSONArray("x");
                            JSONArray arrayYbar = bar.getJSONArray("y");
                            if(arrayXbar.length() != arrayYbar.length()){
                                Toast.makeText(MainActivity.this,"Corrupted data",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            for (int i=0;i<arrayXbar.length();i++){
                                entriesBar.add(new BarEntry(arrayXbar.getInt(i),Float.parseFloat(arrayYbar.getString(i))));
                            }
                            barDataRepository.getInstance().setEntries(entriesBar);
                            BarDataSet barDataSet = new BarDataSet(entriesBar,"");
                            barDataSet.setColor(Color.parseColor(bar.getString("colour")));
                            barDataRepository.getInstance().setBarDataSet(barDataSet);
                            intent = new Intent(this,barDisplayActivity.class);
                            startActivityForResult(intent,2);*/
                            break;
                        case "pie":
                            array = jsonObject.getJSONArray("lines");

                            Map<String, Integer> typeAmountMap = new HashMap<>();
                            ArrayList<Integer> colors = new ArrayList<>();
                            for (int i =0;i<array.length();i++){
                                JSONObject temp = new JSONObject(array.get(i).toString());
                                typeAmountMap.put(temp.getString("name"),temp.getInt("y"));
                                colors.add(Color.parseColor(temp.getString("colour")));
                            }
                            ArrayList<PieEntry> entries = new ArrayList<>();
                            for(String type: typeAmountMap.keySet()){
                                entries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
                            }
                            pieDataRepository.getInstance().setEntries(entries);
                            pieDataRepository.getInstance().setColors(colors);
                            pieDataRepository.getInstance().setTypeAmountMap(typeAmountMap);
                            intent = new Intent(this,pieDisplayActivity.class);
                            startActivityForResult(intent,3);
                            break;
                        case "line":
                            array = jsonObject.getJSONArray("lines");
                            ArrayList<ILineDataSet> lineData = new ArrayList<>();

                            LineData setData;
                            Log.d("json",array.toString() + "\n");
                            for (int i = 0;i<array.length();i++){
                                Log.d("json",array.get(i).toString());
                                JSONObject temp = new JSONObject(array.get(i).toString());
                                JSONArray array1 = temp.getJSONArray("x");
                                JSONArray array2 = temp.getJSONArray("y");
                                if(array1.length() != array2.length()){
                                    Toast.makeText(MainActivity.this,"Corrupted data",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Log.d("json","ArrayX\n" + array1.toString());
                                Log.d("json","ArrayY\n"+ array2.toString());
                                ArrayList<Entry> chartRawData = new ArrayList<>();
                                for (int j=0;j<array1.length();j++){
                                    chartRawData.add(new Entry(Float.parseFloat(array1.getString(j)),Float.parseFloat(array2.getString(j))));
                                }
                                LineDataSet tmpLineData = new LineDataSet(chartRawData,"");

                                tmpLineData.setColor(Color.parseColor(temp.getString("colour")));
                                lineData.add(tmpLineData);
                            }
                            setData = new LineData(lineData);
                            lineDataRepository.loadData(setData);
                            // moving to chart
                            intent = new Intent(this, lineDispalyActivity.class);
                            startActivityForResult(intent,1);
                            break;
                        default:
                            Toast.makeText(MainActivity.this,"Nie wspierany typ danych",Toast.LENGTH_SHORT).show();
                            return;
                    }
                }catch (JSONException ex){
                    ex.printStackTrace();
                    return;
                }

            }

            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }

    // todo: remove i guess? or change for random data
    public void genChart(View view){
        Intent intent;
        switch (chartTypes.getSelectedItem().toString()) {
            case "Wykres radarowy":
                intent = new Intent(this,radarDisplayActivity.class);
                startActivityForResult(intent,4);
                break;
            case "Wykres kołowy":
                intent = new Intent(this,pieDisplayActivity.class);
                startActivityForResult(intent,3);
                break;
            case "Wykres słupkowy":
                intent = new Intent(this,barDisplayActivity.class);
                startActivityForResult(intent,2);
                break;
            default: // liniowy
                intent = new Intent(this, lineDispalyActivity.class);
                startActivityForResult(intent,1);
                break;
        }

    }
    // permission grant
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            default:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your storage", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


    // menu icon creation not used here
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }*/

}