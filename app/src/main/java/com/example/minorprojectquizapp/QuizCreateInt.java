package com.example.minorprojectquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QuizCreateInt extends AppCompatActivity {

    EditText QuizName,BookName,QuizCode;
    CheckBox isBook;
    RadioButton isPublic,isPrivate;
    RadioGroup RG;
    TextView BookNameT,QuizCodeT,DatePick,TimePick,CreateBtn;
    Spinner EasySpinner,MediumSpinner,HardSpinner;
    Boolean BookSelected,PublicSelected;
    Integer EC,MC,HC;
    Calendar calender;
    LocalDate Validdate;
    LocalTime Validtime;
    LocalDateTime ValidDateTime;
    String[] CountList={"3","4","5","8"};
    ArrayList<String> QuizQueries;
    Integer C;
    ArrayList<String> ResponseQueue;

    String QuizNameS;
    FirebaseAuth Fauth;
    FirebaseDatabase DB;
    DatabaseReference ref;

    ArrayList<String> QuestionList;
    ArrayList<ArrayList<String>> OptionsQ;
    ArrayList<String> Answers;
    ArrayList<String> Difficulty;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_create_int);

        QuizName=findViewById(R.id.QuizName);
        BookName=findViewById(R.id.BookName);
        QuizCode=findViewById(R.id.QuizCode);

        isBook=findViewById(R.id.isBook);
        isPrivate=findViewById(R.id.private_radio_button);
        isPublic=findViewById(R.id.public_radio_button);
        RG=findViewById(R.id.radio_group);

        BookNameT=findViewById(R.id.BookNameTitle);
        QuizCodeT=findViewById(R.id.QuizCodeTitle);
        DatePick=findViewById(R.id.datepick);
        TimePick=findViewById(R.id.timepick);
        CreateBtn=findViewById(R.id.createBtn);

        EasySpinner=findViewById(R.id.EasyCount);
        MediumSpinner=findViewById(R.id.MediumCount);
        HardSpinner=findViewById(R.id.HardCount);

        BookNameT.setVisibility(View.GONE);
        QuizCodeT.setVisibility(View.GONE);
        BookName.setVisibility(View.GONE);
        QuizCode.setVisibility(View.GONE);


        BookSelected=Boolean.FALSE;
        PublicSelected=Boolean.FALSE;

        EC=1;
        MC=1;
        HC=1;

        calender=Calendar.getInstance();
        Validdate=LocalDate.of(calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH));
        Validtime=LocalTime.of(calender.get(Calendar.HOUR_OF_DAY),calender.get(Calendar.MINUTE),0,0);

        Fauth=FirebaseAuth.getInstance();





        isBook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isBook.isChecked())
                {
                    BookName.setVisibility(View.VISIBLE);
                    BookNameT.setVisibility(View.VISIBLE);
                    BookSelected=Boolean.TRUE;
                }
                else{
                    BookName.setVisibility(View.GONE);
                    BookNameT.setVisibility(View.GONE);
                    BookSelected=Boolean.FALSE;
                }
            }
        });

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(isPrivate.isChecked())
                {
                    QuizCode.setVisibility(View.VISIBLE);
                    QuizCodeT.setVisibility(View.VISIBLE);
                    PublicSelected=Boolean.FALSE;
                }
                else{
                    QuizCode.setVisibility(View.GONE);
                    QuizCodeT.setVisibility(View.GONE);
                    PublicSelected=Boolean.TRUE;
                }
            }
        });

        DatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(QuizCreateInt.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Validdate=LocalDate.of(year, month, dayOfMonth);
                    }
                }, Validdate.getYear(), Validdate.getMonthValue(), Validdate.getDayOfMonth());
                dialog.show();
            }
        });


        TimePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog=new TimePickerDialog(QuizCreateInt.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Validtime=LocalTime.of(hourOfDay,minute,0,0);
                    }
                },Validtime.getHour(),Validtime.getMinute(), Boolean.TRUE);
                dialog.show();
            }
        });


        ArrayAdapter<String> CountListAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,CountList);
        CountListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        EasySpinner.setAdapter(CountListAdapter);
        MediumSpinner.setAdapter(CountListAdapter);
        HardSpinner.setAdapter(CountListAdapter);


        CreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizNameS=QuizName.getText().toString();
                String BookNameS=BookName.getText().toString();
                String EC=CountList[EasySpinner.getSelectedItemPosition()];
                String MC=CountList[MediumSpinner.getSelectedItemPosition()];
                String HC=CountList[HardSpinner.getSelectedItemPosition()];
                ValidDateTime=LocalDateTime.of(Validdate,Validtime);
                String QuizCodeS=QuizCode.getText().toString();

                   String Ques="Create an MCQ quiz on topic "+QuizNameS+" ";
                   if(BookSelected.equals(Boolean.TRUE)) {
                       Ques +="from book " + BookNameS+" ";
                   }
                   QuizQueries=new ArrayList<String>();
                   ResponseQueue=new ArrayList<String>();
                    String adi="questions should cover variety of questions and questions should be different from each other.JSON Object should contain an object array called questions containing mcq questions as json object.each question json object will have question value with key question, option array with key options and answer value with key answer and the answer value should be the correct value from options";
                    QuizQueries.add(Ques+"with only "+EC+" questions of Easy level in JSON format and "+adi);
                    QuizQueries.add(Ques+"with only "+MC+" questions of Medium level in JSON format and "+adi);
                    QuizQueries.add(Ques+"with only "+HC+" questions of Hard level in JSON format and "+adi);
                    C=0;

                Log.d("q1", "onClick: "+QuizQueries.get(0));
                Log.d("q2", "onClick: "+QuizQueries.get(1));
                Log.d("q3", "onClick: "+QuizQueries.get(2));
                callAPI(QuizQueries);

            }
        });

    }

    void callAPI(ArrayList<String> QuestionQueries)
    {
        String input=QuizQueries.get(C);
        C++;
        Log.i("Quiz Prompt",input);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","gpt-3.5-turbo");
            JSONArray messageArr = new JSONArray();
            JSONObject obj= new JSONObject();
            obj.put("role","user");
            obj.put("content",input);

            messageArr.put(obj);

            jsonBody.put("messages",messageArr);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","Bearer API-KEY")
                .post(body)
                .build();
        Log.d("check","Before client");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("API_CALL", "onClick: "+e.toString());
            }

            @Override
            public void onResponse( Call call, Response response) throws IOException {
                Log.d("Response", "onClick: ");
                if(response.isSuccessful()){
                    JSONObject  jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                        Log.d("API_CALL_ON_RESPONSE", "onClick: "+result);
                        ResponseQueue.add(result.trim());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    if(C<QuestionQueries.size())
                    {
                        callAPI(QuestionQueries);
                    }
                    else{
                        ActionOnResponse();
                    }
                }
                else{
                    Log.d("UNSUCCESFULL", "onClick: ");
                }
            }
        });

    }

    void ActionOnResponse(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                QuestionList=new ArrayList<String>();
                Answers=new ArrayList<String>();
                OptionsQ=new ArrayList<ArrayList<String>>();
                Difficulty=new ArrayList<String>();



                for(int j=0;j<3;j++)
                {
                    String value=ResponseQueue.get(j);
                    Log.d("RQ", "run: "+value);
                    setValueToDB(value);

                }

                String qt;
                if(PublicSelected.equals(Boolean.TRUE))
                {
                    qt="Public";
                }
                else{
                    qt="Private";
                }

                String Userid=Fauth.getUid();
                Log.d("check", "run: "+QuestionList.toString());
                QuizData qd= new QuizData(QuestionList,OptionsQ,Answers,QuizNameS,qt,(DateTimeFormatter.ISO_DATE).format(ValidDateTime));
                DB=FirebaseDatabase.getInstance();
                ref=DB.getReference("TeacherQuizCollection");
                DatabaseReference TQU=ref.child(Userid);

                DatabaseReference TQ=TQU.child("Quiz");
                TQ.push().setValue(qd);






            }
        });
    }

    void setValueToDB(String value)
    {
        try
        {
            JSONObject Output=new JSONObject(value);
            JSONArray Questions=Output.getJSONArray("questions");
            for(int i=0;i<Questions.length();i++)
            {
                JSONObject quesObj=Questions.getJSONObject(i);
                QuestionList.add(quesObj.getString("question"));
                Answers.add(quesObj.getString("answer"));

                JSONArray optionArr=quesObj.getJSONArray("options");
                ArrayList<String> ops=new ArrayList<String>();
                for(int k=0;k<4;k++)
                {
                    ops.add(optionArr.getString(k));

                }
                OptionsQ.add(ops);
                Log.d("opss", "setValueToDB: "+OptionsQ.get(i).toString());
            }
        }
        catch (Exception e)
        {

        }
    }


}
