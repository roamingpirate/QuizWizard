package com.example.minorprojectquizapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView topic;
    TextView EQ,MQ,HQ;
    Button send;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topic=findViewById(R.id.topic);
        EQ=findViewById(R.id.easy);
        MQ=findViewById(R.id.medium);
        HQ=findViewById(R.id.hard);
        send=findViewById(R.id.send);


        send.setOnClickListener((v) -> {
            String Question=GetQuizQuery();
            callAPI(Question);


        });
    }

    String GetQuizQuery()
    {
        Integer Enum=Integer.parseInt(EQ.getText().toString());
        Integer Mnum=Integer.parseInt(MQ.getText().toString());
        Integer Hnum=Integer.parseInt(HQ.getText().toString());
        Integer Totalnum=Enum+Mnum+Hnum;

        String topicc=topic.getText().toString();
        String Easy=EQ.getText().toString();
        String Medium=MQ.getText().toString();
        String Hard=HQ.getText().toString();
        String total=Totalnum.toString();
        //String Result="create an mcq quiz on "+topicc+" with "+total+" question "+Easy+" easy "+Medium+" medium "+" and "+Hard+" hard in json format";
      //  String Result="create a quiz on pyhton with with 20 questions in json format. break this completion into 5 parts. give output of 1st part here and remember the response and give me later parts when i ask";
        String Result="Give second part of python quiz";
        //Toast.makeText(getApplicationContext(),Result,Toast.LENGTH_LONG).show();
        return Result;
    }



    void callAPI(String input)
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",input);
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure( Call call,  IOException e) {
                getResponse("Failure :"+e.toString());
            }

            @Override
            public void onResponse( Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject  jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        getResponse(result.trim());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }else {
                    getResponse("Failed to load response due to " + response.body().string());
                }
            }
        });

    }

    void getResponse(String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // topic.setText("hello");
                Intent myIntent = new Intent(MainActivity.this, outputActivity.class);
                myIntent.putExtra("key", response); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
    }



}