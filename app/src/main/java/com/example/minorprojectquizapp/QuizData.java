package com.example.minorprojectquizapp;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class QuizData {
     ArrayList<String> questionList;
     ArrayList<ArrayList<String>> options;
    ArrayList<String> answers;
    String quizName;
     String quizType;
     String validdate;

    public QuizData(){

    }

    public QuizData(ArrayList<String> QuestionList,ArrayList<ArrayList<String>> Options,ArrayList<String> Answers,String QuizName,String QuizType,String validdate){
        this.questionList=QuestionList;
        this.answers=Answers;
        this.options=Options;
        this.quizName=QuizName;
        this.quizType=QuizType;
        this.validdate=validdate;
    }

    public ArrayList<String> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<String> questionList) {
        this.questionList = questionList;
    }

    public ArrayList<ArrayList<String>> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<ArrayList<String>> options) {
        this.options = options;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public String getValiddate() {
        return validdate;
    }

    public void setValiddate(String validdate) {
        this.validdate = validdate;
    }
}
