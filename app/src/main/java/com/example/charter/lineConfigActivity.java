package com.example.charter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class lineConfigActivity extends AppCompatActivity {
    EditText description, title, fontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_config);
        description = findViewById(R.id.lineDesc);
        title = findViewById(R.id.lineTitle);
        fontSize = findViewById(R.id.lineFontSize);
    }

    public void submitChanges(View view){
        Intent intent = new Intent();
        intent.putExtra("dsc",description.getText().toString());
        intent.putExtra("lineTitle",title.getText().toString());
       // lineDataRepository.description = description.getText();
        float size = Float.parseFloat(fontSize.getText().toString());
        intent.putExtra("fontSize",size);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void cancelChanges(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}