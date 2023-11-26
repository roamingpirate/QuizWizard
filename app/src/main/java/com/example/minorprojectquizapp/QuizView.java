package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class QuizView extends AppCompatActivity {
    ArrayList<String> QuestionList;
    ArrayList<ArrayList<String>> Options;
    ArrayList<String> Answers;
    ArrayList<String> Difficulty;
    String QuizName;
    String QuizType;
    String validdate;
    ArrayList<QuizData> Qdata;


    ArrayList<String> QuizNameV=new ArrayList<String>();
    ArrayList<String> QuizTypeV=new ArrayList<String>();
    ArrayList<String> ValidDateV=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_view);
        Qdata=new ArrayList<QuizData>();

        DatabaseReference db= FirebaseDatabase.getInstance().getReference("TeacherQuizCollection");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(DataSnapshot UserSnapshot: snapshot.getChildren())
                {
                    DataSnapshot QuizSnapShot=UserSnapshot.child("Quiz");
                    for(DataSnapshot QuizSS: QuizSnapShot.getChildren())
                    {
                        try {
                            QuizData qs1 = QuizSS.getValue(QuizData.class);
                            Qdata.add(qs1);
                            Log.d("dataa", "onDataChange: "+QuizSS.getValue(QuizData.class).quizName);
                        }
                        catch (Exception e)
                        {
                            Log.d("dataa", "onDataChange: "+e.toString());
                        }
                    }
                }

                for(int i=0;i<Qdata.size();i++)
                {
                    QuizNameV.add(Qdata.get(i).getQuizName());
                    QuizTypeV.add(Qdata.get(i).getQuizType());
                    ValidDateV.add(Qdata.get(i).getValiddate());
                }
                Log.d("Apppp", "onDataChange: "+Qdata.size());


                RecyclerView QuizList=findViewById(R.id.QuizList);
                QuizList.setLayoutManager(new LinearLayoutManager(QuizView.this));
                QuizList.setAdapter(new QuizViewAdapter(QuizNameV,QuizTypeV,ValidDateV,Boolean.FALSE,QuizView.this,Qdata));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("dataa", error.getDetails());
            }
        });
    }
}