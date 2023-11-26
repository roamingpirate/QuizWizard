package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class PdfActivity extends AppCompatActivity {
    ArrayList<String> QuestionList;
    ArrayList<ArrayList<String>> Options;
    ArrayList<String> Answers;
    ArrayList<String> Difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");

        FillValues(value);
        try{
            CreatePdf();
        }
        catch (Exception e)
        {

        }




    }

    void FillValues(String value)
    {
        try
        {
            JSONObject Output=new JSONObject(value);
            JSONObject quiz=Output.getJSONObject("quiz");
            JSONArray Questions=quiz.getJSONArray("questions");
            for(int i=0;i<Questions.length();i++)
            {
                JSONObject quesObj=Questions.getJSONObject(i);
                QuestionList.add(quesObj.getString("question"));

                Answers.add(quesObj.getString("answer"));
                Difficulty.add(quesObj.getString("difficulty"));

                JSONArray optionArr=quesObj.getJSONArray("options");
                Options.add(new ArrayList<>());
                for(int j=0;j<4;j++)
                {
                    Options.get(i).add(optionArr.getString(j));
                }

            }
            //out.setText(Options.get(0).get(0));
        }
        catch (Exception e)
        {

        }
    }

    void CreatePdf() throws FileNotFoundException
    {
        //TODO Auto-generated method stub
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vindroid";;

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);


            File file = new File(dir, "sample.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //open the document
            document.open();


            Paragraph p1 = new Paragraph("Sample PDF CREATION USING IText");
            Font paraFont= new Font(Font.FontFamily.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            //add paragraph to document
            document.add(p1);
        }
        catch (Exception e){
        }
        finally
        {
            document.close();
        }

    }
}

