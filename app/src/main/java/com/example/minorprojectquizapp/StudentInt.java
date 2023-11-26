package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StudentInt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_int);

        TextView vq=findViewById(R.id.ViewQuizz);

        vq.setOnClickListener(v ->{
            Intent myIntent = new Intent(StudentInt.this, QuizView.class);
            StudentInt.this.startActivity(myIntent);
        });
    }
}