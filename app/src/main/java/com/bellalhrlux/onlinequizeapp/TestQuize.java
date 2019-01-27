package com.bellalhrlux.onlinequizeapp;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bellalhrlux.onlinequizeapp.Model.Adapter.QuestionAdapter;
import com.bellalhrlux.onlinequizeapp.Model.ApiInterface;
import com.bellalhrlux.onlinequizeapp.Model.Question;
import com.bellalhrlux.onlinequizeapp.Model.Retrofit_Inits;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestQuize extends AppCompatActivity {
    private Button confirmBtn;
    private RadioGroup questionRG;
    private TextView completeQuizeTV,totalQuize;
    private List<Question> questionList=new ArrayList<>();
    private List<Question.Result> getResult=new ArrayList<>();
    private ListView questionLV;
    private List<Integer> checkItem=new ArrayList<>();
    private LinearLayout wrapperLL;
    private ProgressDialog dialog;
    int count=0;
    boolean isCheck=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_quize);

        completeQuizeTV=findViewById(R.id.completeQuize);
        questionRG=findViewById(R.id.radioGroupQst);
        totalQuize=findViewById(R.id.totalQuize);
        wrapperLL=findViewById(R.id.wrapperLL);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading......");
        dialog.show();



        confirmBtn=findViewById(R.id.confirmBtn);

        final TextView questionTV=findViewById(R.id.question);
        //Get question attributes
        final RadioButton question1_RB=findViewById(R.id.question_1);
        final RadioButton question2_RB=findViewById(R.id.question_2);
        final RadioButton question3_RB=findViewById(R.id.question_3);
        final RadioButton question4_RB=findViewById(R.id.question_4);



        ApiInterface apiInterface= Retrofit_Inits.getRetrofitInits("https://opentdb.com/").create(ApiInterface.class);
        apiInterface.getQustionApi().enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if(response.isSuccessful())
                {
                   // Toast.makeText(TestQuize.this, "Success", Toast.LENGTH_SHORT).show();
                    getResult=response.body().getResults();
                    totalQuize.setText(getResult.size()+"");

                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(getResult.size()>0)
                            {
                                dialog.dismiss();
                                wrapperLL.setVisibility(View.VISIBLE);
                                questionTV.setText(getResult.get(0).getQuestion());

                                question1_RB.setText(stripHtml(getResult.get(0).getCorrectAnswer()));
                                question2_RB.setText(getResult.get(0).getIncorrectAnswers().get(0));
                                question3_RB.setText(getResult.get(0).getIncorrectAnswers().get(1));
                                question4_RB.setText(getResult.get(0).getIncorrectAnswers().get(2));
                            }
                        }
                    },1000);

                    /*for(int i=0;i<getResult.size();i++)
                    {
                        checkItem.add(0);
                        Log.e("","item");
                    }*/
                    //QuestionAdapter qAdapter=new QuestionAdapter(TestQuize.this,getResult,checkItem);
                    //questionLV.setAdapter(qAdapter);

                }
                else {
                    Toast.makeText(TestQuize.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Toast.makeText(TestQuize.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCheckedButton())
                {
                    Toast.makeText(TestQuize.this, "Select your answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                //isCheck=false;

                count++;
                completeQuizeTV.setText(count+"");
                if(count<30)
                {
                    //Unchecked question option
                    question1_RB.setChecked(false);
                    question2_RB.setChecked(false);
                    question3_RB.setChecked(false);
                    question4_RB.setChecked(false);

                    questionTV.setText(stripHtml(getResult.get(count).getQuestion()));

                    question2_RB.setText(getResult.get(count).getIncorrectAnswers().get(0));
                    question1_RB.setText(getResult.get(count).getCorrectAnswer());
                    question3_RB.setText(getResult.get(count).getIncorrectAnswers().get(1));
                    question4_RB.setText(getResult.get(count).getIncorrectAnswers().get(2));
                }
                else {
                    Toast.makeText(TestQuize.this, "Complete your online quize!!", Toast.LENGTH_SHORT).show();
                    confirmBtn.setEnabled(false);
                }
            }
        });


        //questionLV=findViewById(R.id.questionLV);



    }

    public static String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }

    private boolean isCheckedButton()
    {
        questionRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {

                    case R.id.question_1:
                        isCheck=true;
                        break;
                    case R.id.question_2:
                        isCheck=true;
                        break;
                    case R.id.question_3:
                        isCheck=true;
                        break;
                    case R.id.question_4:
                        isCheck=true;
                        break;
                }
            }
        });

        return isCheck;
    }



}
