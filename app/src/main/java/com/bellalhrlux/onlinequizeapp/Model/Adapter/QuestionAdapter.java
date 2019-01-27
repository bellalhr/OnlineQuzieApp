package com.bellalhrlux.onlinequizeapp.Model.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bellalhrlux.onlinequizeapp.Model.Question;
import com.bellalhrlux.onlinequizeapp.R;

import java.util.List;

public class QuestionAdapter extends ArrayAdapter<Question.Result> {
    private List<Question.Result> resultList;
    private int[]checked;
    private Context context;
    private List<Integer> checkList;
    public QuestionAdapter(Context context, List<Question.Result> resultList,List<Integer> checkList) {
        super(context, R.layout.raw_question,resultList);
        this.resultList=resultList;
        this.context=context;
        this.checkList=checkList;


    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.raw_question,parent,false);
        TextView questionTV=convertView.findViewById(R.id.question);
        //Get question attributes
        RadioButton question1_RB=convertView.findViewById(R.id.question_1);
        RadioButton question2_RB=convertView.findViewById(R.id.question_2);
        RadioButton question3_RB=convertView.findViewById(R.id.question_3);
        RadioButton question4_RB=convertView.findViewById(R.id.question_4);

        questionTV.setText(resultList.get(position).getQuestion());
        RadioGroup radioGroup=convertView.findViewById(R.id.radioGroupQst);


        question1_RB.setText(resultList.get(position).getCorrectAnswer());
        question2_RB.setText(resultList.get(position).getIncorrectAnswers().get(0));
        question3_RB.setText(resultList.get(position).getIncorrectAnswers().get(1));
        question4_RB.setText(resultList.get(position).getIncorrectAnswers().get(2));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {

                    case R.id.question_1:
                        checkList.set(position,1);
                        Toast.makeText(context, "Correct", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.question_2:
                        checkList.set(position,2);
                        Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.question_3:
                        checkList.set(position,3);
                        Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.question_4:
                        checkList.set(position,4);
                        Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });



            if(checkList.get(position)!=0)
            {
                if(checkList.get(position)==1)
                    question1_RB.setChecked(true);
                else if(checkList.get(position)==2)
                    question2_RB.setChecked(true);
                else if(checkList.get(position)==3)
                    question3_RB.setChecked(true);
                else if(checkList.get(position)==4)
                    question4_RB.setChecked(true);

             }

        for(int i=0;i<checkList.size();i++)
        {
            Log.e("log",checkList.get(i)+"");
        }
        return convertView;
    }
}
