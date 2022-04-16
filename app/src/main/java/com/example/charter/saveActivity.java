package com.example.charter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class saveActivity extends AppCompatActivity {
    EditText path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        path = findViewById(R.id.savePath);
    }


    public void submitChanges(View view){
        Intent intent = new Intent();
        intent.putExtra("path",path.getText().toString());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void cancelChanges(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}