package com.example.charter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Button loadData;
    Spinner chartTypes;

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

    public void loadData(View view){

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
}