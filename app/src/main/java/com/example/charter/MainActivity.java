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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.acl.Permission;

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

    // menu icon creation
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }*/

    // file selector
    public void loadData(View view){
        // permission check
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

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
                // jebany cyrk XD
                final String path[] = filePath.getPath().split(":");
                String json = path[1];

                String jsonData;
                try {
                    //InputStream is = openFileInput(json);
                    Log.d("json",json);
                    File file = new File(json);
                    if(!file.canRead()){
                        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                            Log.d("json","nie mam uprawnienia");
                        }
                        Log.d("json","nie umie czytać sadge");
                    }

                    FileReader fr = new FileReader(file);
                    Log.d("json","FR pass");
                    BufferedReader bufferedReader = new BufferedReader(fr);
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

                    switch(jsonObject.getString("type")){
                        case "line":
                            JSONArray array = jsonObject.getJSONArray("lines");
                            for (int i = 0;i<array.length();i++){
                                Log.d("json",array.toString());
                            }
                            break;
                        default:
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            default:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this,"Access allowed",Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    public void permissionChange(View view){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
    }


}