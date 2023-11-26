package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SuccessLogin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_login);

        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");
        String Age = intent.getStringExtra("Age");
        String Code = intent.getStringExtra("Code");

        TextView nametv=findViewById(R.id.namefill);
        TextView typetv=findViewById(R.id.type);

        nametv.setText(Name);
        typetv.setText("Signed in as "+(Code.equals("1")?"Teacher":"Student"));

        TextView cont=findViewById(R.id.continu);

        cont.setOnClickListener((v)->{
            Intent myIntent;

            if(Code.equals("1"))
            {
                myIntent= new Intent(SuccessLogin.this, Teacher_Interface.class);
            }
            else{
                myIntent= new Intent(SuccessLogin.this, StudentInt.class);
            }
            myIntent.putExtra("Code",Code);
            myIntent.putExtra("Name",Name);
            myIntent.putExtra("Age",Age);
            SuccessLogin.this.startActivity(myIntent);
        });

    }

}