package com.example.charter.config;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.charter.R;
import com.example.charter.repository.barDataRepository;

import java.util.HashMap;
import java.util.Map;

public class barConfigActivity extends AppCompatActivity {
    EditText dsc, font, title;
    Spinner titlesSpinner;
    Map<String, String> titles = new HashMap<>();
    int spinnerSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_config);
        dsc = findViewById(R.id.dscBarConfig);
        font = findViewById(R.id.barConfigFont);
        title = findViewById(R.id.barConfigTitle);
        titlesSpinner = findViewById(R.id.barConfSpinner);
        spinnerSize = barDataRepository.getInstance().getDataSets().size();
        String list[] = new String[spinnerSize];
        for (int i=0;i<spinnerSize;i++){
            list[i] = "Seria danych " + (i+1);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing
            }
        });
    }

    public void submitChanges(View view){
        Intent intent = new Intent();
        intent.putExtra("dsc",dsc.getText().toString());
        // titles
        for (int i=0;i<spinnerSize;i++){
            intent.putExtra("barTitle"+i,titles.get("Seria danych " + (i+1)));
        }

        //font
        float size = 8f;
        if(!font.getText().toString().isEmpty()){
            size = Float.parseFloat(font.getText().toString());
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