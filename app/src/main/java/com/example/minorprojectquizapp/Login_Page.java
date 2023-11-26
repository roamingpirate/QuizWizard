package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Login_Page extends AppCompatActivity {

    EditText Email_,Password_;
    TextView Register,Login;
    FirebaseAuth FAuth;
    FirebaseFirestore FStore;
    ImageView Image;
    String Out;
    String code;
    String Name,Age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Intent intent = getIntent();
        code = intent.getStringExtra("Code");

        Email_=findViewById(R.id.Email_);
        Password_=findViewById(R.id.Password_);
        Out="-1";


        Register=findViewById(R.id.Register_Btn);
        Login=findViewById(R.id.Submit_Btn);

        FAuth=FirebaseAuth.getInstance();
        FStore=FirebaseFirestore.getInstance();


        if(FAuth.getCurrentUser()!=null)
        {
            GotoApp();
        }

        Image=findViewById(R.id.Img);

        if(code.equals("1")){
        Image.setImageResource(R.drawable.teacher);}
        else{
            Image.setImageResource(R.drawable.student);
        }

        Login.setOnClickListener((v)->{
            String Email=String.valueOf(Email_.getText());
            String Password=String.valueOf(Password_.getText());
            String Name;
            String Age;

            FAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        LoginActivities();
                    }
                    else{
                        Toast.makeText(Login_Page.this,"Error in Login",Toast.LENGTH_LONG).show();
                    }
                }
            });

        });

        Register.setOnClickListener((v)->{
            Intent myIntent = new Intent(Login_Page.this, Register_Page.class);
            myIntent.putExtra("Code",code);
            Login_Page.this.startActivity(myIntent);
        });

    }

    void LoginActivities()
    {
        String UserId=FAuth.getCurrentUser().getUid();

        DocumentReference df=FStore.collection("Users").document(UserId);
        df.addSnapshotListener(Login_Page.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                Out=value.getString("Code");
                Name=value.getString("Name");
                Age=value.getString("Age");
                if(Out.equals(code))
                {
                    GotoApp();
                }
                else{
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(Login_Page.this,"Invalid Credentials!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(Login_Page.this, Launch_Page.class);
        Login_Page.this.startActivity(myIntent);
    }

    void GotoApp()
    {
        Intent myIntent = new Intent(Login_Page.this, SuccessLogin.class);
        myIntent.putExtra("Code",code);
        myIntent.putExtra("Name",Name);
        myIntent.putExtra("Age",Age);
        Login_Page.this.startActivity(myIntent);
    }


}