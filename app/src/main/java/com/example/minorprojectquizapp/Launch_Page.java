package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Launch_Page extends AppCompatActivity {

    TextView teacher,student;
    String code;
    FirebaseAuth FAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_page);

        FAuth=FirebaseAuth.getInstance();
        if(FAuth.getCurrentUser()!=null)
        {
          FAuth.signOut();
        }

        teacher=findViewById(R.id.teacher);
        student=findViewById(R.id.student);
        code="0";


        teacher.setOnClickListener((v)->{
            code="1";
            Intent myIntent = new Intent(Launch_Page.this, Login_Page.class);
            myIntent.putExtra("Code",code);
            Launch_Page.this.startActivity(myIntent);
        });

        student.setOnClickListener((v)->{
            code="0";
            Intent myIntent = new Intent(Launch_Page.this, Login_Page.class);
            myIntent.putExtra("Code",code);
            Launch_Page.this.startActivity(myIntent);
        });

    }


}