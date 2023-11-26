package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Teacher_Interface extends AppCompatActivity {

    TextView QuizCreate,QuizView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_interface);

        QuizCreate=findViewById(R.id.createQuizz);
        QuizView=findViewById(R.id.ViewQuizz);

        QuizCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Teacher_Interface.this, QuizCreateInt.class);
                Teacher_Interface.this.startActivity(myIntent);
            }
        });
    }
}