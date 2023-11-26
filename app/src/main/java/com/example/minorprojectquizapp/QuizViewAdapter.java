package com.example.minorprojectquizapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class QuizViewAdapter extends RecyclerView.Adapter<QuizViewAdapter.QuizViewViewHolder> {

    ArrayList<String> QuizName;
    ArrayList<String> QuizType;
    ArrayList<String> ValidDate;
    Context context;
    ArrayList<QuizData> quizData;
    Boolean isTeacher;

    public QuizViewAdapter(ArrayList<String> QuizName,ArrayList<String> QuizType,ArrayList<String> ValidDate,Boolean isTeacher,Context context,ArrayList<QuizData> quizdata){
        this.QuizName=QuizName;
        this.QuizType=QuizType;
        this.ValidDate=ValidDate;
        this.isTeacher=isTeacher;
        this.context=context;
        this.quizData=quizdata;
    }

    @Override
    public QuizViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.quiz_view_item,viewGroup,false);
        return new QuizViewViewHolder(view);

    }

    @Override
    public void onBindViewHolder(QuizViewViewHolder quizViewViewHolder, int i) {
            quizViewViewHolder.QuizNameTV.setText("Quiz Name: "+QuizName.get(i));
            quizViewViewHolder.QuizTypeTV.setText("Quiz Type: "+QuizType.get(i));
            quizViewViewHolder.ValidDateTV.setText("Valid Date: "+ ValidDate.get(i));

            if(isTeacher.equals(Boolean.FALSE))
            {
                quizViewViewHolder.lay.setOnClickListener(v -> {
                    Intent myIntent = new Intent(context, QuizActivity.class);
                    myIntent.putExtra("QuestionList",quizData.get(i).getQuestionList());
                    myIntent.putExtra("Options",quizData.get(i).getOptions());
                    myIntent.putExtra("Answers",quizData.get(i).getAnswers());
                    context.startActivity(myIntent);
                });
            }
    }

    @Override
    public int getItemCount() {
        return QuizName.size();
    }

    public class QuizViewViewHolder extends RecyclerView.ViewHolder{
        TextView QuizNameTV,QuizTypeTV,ValidDateTV;
        LinearLayout lay;
        public QuizViewViewHolder(View itemView) {
            super(itemView);
            QuizNameTV=(TextView) itemView.findViewById(R.id.QNitem);
            QuizTypeTV=(TextView) itemView.findViewById(R.id.QTtype);
            ValidDateTV=(TextView) itemView.findViewById(R.id.VDitem);
            lay=(LinearLayout) itemView.findViewById(R.id.layouttt);
        }
    }
}
