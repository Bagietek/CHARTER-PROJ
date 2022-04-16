package com.example.charter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

public class radarConfigActivity extends AppCompatActivity {
    Spinner titlesSpinner;
    EditText dsc, fontSize, title;
    CheckBox fill;
    Map<String, String> titles = new HashMap<>();
    Map<String, Boolean> fills = new HashMap<>();
    int spinnerSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_config);
        titlesSpinner = findViewById(R.id.radarSpinner);
        fill = findViewById(R.id.radarFillCheck);
        dsc = findViewById(R.id.radarDscConfig);
        fontSize = findViewById(R.id.radarFont);
        title = findViewById(R.id.radarLabelConfig);
        spinnerSize = radarDataRepository.getInstance().getRadarData().getDataSetCount();
        String[] list = new String[spinnerSize];
        for (int i=0;i<spinnerSize;i++){
            list[i] = "Seria danych " + (i+1);
            fills.put(list[i],false);
            titles.put(list[i],"");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titlesSpinner.setAdapter(adapter);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // save text to map
                titles.remove(titlesSpinner.getSelectedItem().toString());
                titles.put(titlesSpinner.getSelectedItem().toString(),title.getText().toString());
            }
        });

        titlesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // change the line description accordingly
                title.setText(titles.get(titlesSpinner.getSelectedItem().toString()));
                fill.setChecked(fills.get(titlesSpinner.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing
            }
        });

        fill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                fills.remove(titlesSpinner.getSelectedItem().toString());
                fills.put(titlesSpinner.getSelectedItem().toString(),fill.isChecked());
            }
        });
    }


    public void submitChanges(View view){
        Intent intent = new Intent();
        intent.putExtra("dsc",dsc.getText().toString());
        // titles
        for (int i=0;i<spinnerSize;i++){
            intent.putExtra("radarTitle"+i,titles.get("Seria danych " + (i+1)));
            intent.putExtra("fill"+i,fills.get("Seria danych " + (i+1)));
        }

        //font
        float size = 8f;
        if(!fontSize.getText().toString().isEmpty()){
            size = Float.parseFloat(fontSize.getText().toString());
        }
        intent.putExtra("fontSize",size);

        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void cancelChanges(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}