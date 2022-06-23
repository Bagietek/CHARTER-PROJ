package com.example.charter.config;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.charter.R;
import com.example.charter.repository.lineDataRepository;

import java.util.HashMap;
import java.util.Map;

public class lineConfigActivity extends AppCompatActivity {
    EditText description, title, fontSize;
    Spinner dataSets;
    Map<String, String> titles = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_config);
        description = findViewById(R.id.lineDesc);
        title = findViewById(R.id.lineTitle);
        fontSize = findViewById(R.id.lineFontSize);
        dataSets = findViewById(R.id.dataSetSpinner);
        // spinner for data lines
        final int size  = lineDataRepository.getInstance().chartData.getDataSetCount();

        String[] list = new String[size];
        for (int i=0;i<size;i++){
            list[i] = "Seria danych " + (i+1);
            titles.put(list[i],"");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataSets.setAdapter(adapter);

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
                switch (dataSets.getSelectedItem().toString()){
                    default:
                        //String temp = titles.get(dataSets.getSelectedItem().toString());
                        titles.remove(dataSets.getSelectedItem().toString());
                        titles.put(dataSets.getSelectedItem().toString(),title.getText().toString());
                        break;
                }
            }
        });


        dataSets.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // change the line description accordingly
                title.setText(titles.get(dataSets.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing
            }
        });
    }



    //
    public void submitChanges(View view){
        Intent intent = new Intent();
        intent.putExtra("dsc",description.getText().toString());
        // line titles
        for (int i=0;i<lineDataRepository.getInstance().chartData.getDataSetCount();i++){
            intent.putExtra("lineTitle"+i,titles.get("Seria danych " + (i+1)));
        }

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