package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Boolean.TRUE;

public class QuizActivity extends AppCompatActivity {
    ArrayList<String> QuestionList;
    ArrayList<ArrayList<String>> Options;
    ArrayList<String> Answers;
    ArrayList<String> Difficulty;
    ArrayList<String> SelectedOption;

    TextView Question;
    ArrayList<RadioButton> OptionBtn;
    RadioGroup OptionGrp;
    Button Back;
    Button Next;
    Button Submit;
    TextView ScoreTV;
    LinearLayout QuizLayout;
    LinearLayout ScoreLayout;

    Integer CurrQuestion;
    Integer TotalQsn;
    Integer Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Question=findViewById(R.id.question);
        OptionBtn=new ArrayList<RadioButton>();
        OptionBtn.add(findViewById(R.id.Option1));
        OptionBtn.add(findViewById(R.id.Option2));
        OptionBtn.add(findViewById(R.id.Option3));
        OptionBtn.add(findViewById(R.id.Option4));
        OptionGrp=findViewById(R.id.options);
        Back=findViewById(R.id.previous);
        Next=findViewById(R.id.next);
        Submit=findViewById(R.id.submit);
        ScoreTV=findViewById(R.id.scoretext);
        QuizLayout=findViewById(R.id.quizlayout);
        ScoreLayout=findViewById(R.id.score);

        QuestionList=new ArrayList<String>();
        Answers=new ArrayList<String>();
        Options=new ArrayList<ArrayList<String>>();
        Difficulty=new ArrayList<String>();
        SelectedOption=new ArrayList<String>();

        Intent intent = getIntent();
        QuestionList = intent.getStringArrayListExtra("QuestionList");
        Answers=intent.getStringArrayListExtra("Answers");
        Options = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("Options");



        CurrQuestion=1;
        TotalQsn=QuestionList.size();
        Score=0;
        UpdateQuestion(CurrQuestion);
        CheckBound();

        QuizLayout.setVisibility(View.VISIBLE);
        ScoreLayout.setVisibility(View.GONE);

        for(int i=0;i<TotalQsn;i++)
        {
            SelectedOption.add("");
        }

        Back.setOnClickListener((v)->{
            saveOption();
            CurrQuestion--;
            CheckBound();
            if(CurrQuestion>0){
            UpdateQuestion(CurrQuestion);
            UpdateOption();}

        });

        Next.setOnClickListener((v)->{
            saveOption();
            CurrQuestion++;
            CheckBound();
            if(CurrQuestion<=TotalQsn) {
                UpdateQuestion(CurrQuestion);
                UpdateOption();
            }
        });

        Submit.setOnClickListener((v)->{
            CalculateScore();
            QuizLayout.setVisibility(View.GONE);
            ScoreLayout.setVisibility(View.VISIBLE);
            ScoreTV.setText(Score.toString());
        });

    }



    void UpdateQuestion(int QNo)
    {
        Question.setText("Q."+QNo+QuestionList.get(QNo-1));
        for(int i=0;i<4;i++)
        {
            OptionBtn.get(i).setText(Options.get(QNo-1).get(i));
        }
    }

    void CheckBound()
    {
        if(CurrQuestion==TotalQsn){
            Next.setVisibility(View.INVISIBLE);
        }
        else{
            Next.setVisibility(View.VISIBLE);
        }

        if(CurrQuestion==1)
        {
            Back.setVisibility(View.INVISIBLE);
        }
        else{
            Back.setVisibility(View.VISIBLE);
        }
    }

    void saveOption()
    {
        Integer rgID=OptionGrp.getCheckedRadioButtonId();
        if(rgID!=-1) {
            RadioButton r = findViewById(rgID);
            SelectedOption.set(CurrQuestion - 1, r.getText().toString());
        }
    }

    void UpdateOption()
    {
        String S=SelectedOption.get(CurrQuestion-1);
        OptionGrp.clearCheck();
        for(int i=0;i<4;i++)
        {
            if(OptionBtn.get(i).getText().toString()==S)
            {
                OptionBtn.get(i).setChecked(TRUE);
            }
        }
    }

    void CalculateScore()
    {
        Integer s=0;
        for(int i=0;i<TotalQsn;i++)
        {
            if(SelectedOption.get(i).equals(Answers.get(i)))
            {
               s++;
            }
        }
        Score=s;
    }

}