package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class outputActivity extends AppCompatActivity {
        Button quiz;
        Button pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Intent intent = getIntent();
        String value = intent.getStringExtra("key");
        Log.d("OUTPUT JSON", value);
        quiz=findViewById(R.id.TakeQuiz);
        pdf=findViewById(R.id.Pdf);

        quiz.setOnClickListener((v)->{
            Intent myIntent = new Intent(outputActivity.this, QuizActivity.class);
            myIntent.putExtra("key", value); //Optional parameters
            outputActivity.this.startActivity(myIntent);
        });


        pdf.setOnClickListener((v)->{
            Intent myIntent = new Intent(outputActivity.this, PdfActivity.class);
            myIntent.putExtra("key", value); //Optional parameters
            outputActivity.this.startActivity(myIntent);
        });
    }


}