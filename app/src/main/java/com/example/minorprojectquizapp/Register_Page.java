package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register_Page extends AppCompatActivity {
    EditText Email_,Password_,Name_,Age_;
    TextView Register,Login;
    FirebaseAuth FAuth;
    FirebaseFirestore FStore;
    String code;
    String NameS,AgeS;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        Intent intent = getIntent();
        code = intent.getStringExtra("Code");

        Email_=findViewById(R.id.Email);
        Password_=findViewById(R.id.Password);
        Name_=findViewById(R.id.Name);
        Age_=findViewById(R.id.Age);

        Register=findViewById(R.id.RegisterBtn);
        Login=findViewById(R.id.LoginBtn);

        FAuth=FirebaseAuth.getInstance();
        FStore=FirebaseFirestore.getInstance();



        Register.setOnClickListener((v)->{
            String Email=String.valueOf(Email_.getText());
            String Password=String.valueOf(Password_.getText());
            NameS =String.valueOf(Name_.getText());
            AgeS=String.valueOf(Age_.getText());

            FAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Register_Page.this,"Account created! Login Now",Toast.LENGTH_LONG).show();
                        UserID=FAuth.getCurrentUser().getUid();
                        FAuth.signOut();
                        setData();

                    }
                    else{

                       Toast.makeText(Register_Page.this,"Error Creating User!",Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(Register_Page.this,"Error Creating User! "+e.toString(),Toast.LENGTH_LONG).show();
                    Log.e("RegisterError",e.toString());
                }
            });
        });

        Login.setOnClickListener((v)->{
            Intent myIntent = new Intent(Register_Page.this, Login_Page.class);
            myIntent.putExtra("Code",code);
            Register_Page.this.startActivity(myIntent);
        });

    }

    void setData()
    {

        DocumentReference df=FStore.collection("Users").document(UserID);
        Map<String,Object> user=new HashMap<>();
        user.put("Name",NameS);
        user.put("Age",AgeS);
        user.put("Code",code);
        df.set(user);

    }


}