package com.bellalhrlux.onlinequizeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity {
    private List<Question> questionList=new ArrayList<>();
    private List<Question.Result> getResult;
    private ListView questionLV;
    private List<Integer> checkItem=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionLV=findViewById(R.id.questionLV);


        ApiInterface apiInterface= Retrofit_Inits.getRetrofitInits("https://opentdb.com/").create(ApiInterface.class);
        apiInterface.getQustionApi().enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    getResult=response.body().getResults();
                    for(int i=0;i<getResult.size();i++)
                    {
                        checkItem.add(0);
                        Log.e("","item");
                    }
                    QuestionAdapter qAdapter=new QuestionAdapter(MainActivity.this,getResult,checkItem);
                    questionLV.setAdapter(qAdapter);

                }
                else {
                    Toast.makeText(MainActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}
