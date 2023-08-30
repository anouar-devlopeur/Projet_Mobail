package com.example.miniproject101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.RadioAccessSpecifier;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extrascore";
    TextView Score, Count ,Question ;
    RadioGroup rgp;
    RadioButton r1,r2,r3,r4;
    Button btn;

    List<Question> questionList;
    int questionCounter;
    int quetionCountTotal;
    Question currentQuestion;

    int score;
    boolean answered;

    long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Question =findViewById(R.id.QUZ);
        Count = findViewById(R.id.numQestion);
        Score = findViewById(R.id.score);
        rgp = findViewById(R.id.rgp);
        r1 =findViewById(R.id.r1);
        r2 =findViewById(R.id.r2);
        r3 =findViewById(R.id.r3);
        r4 =findViewById(R.id.r4);
        btn = findViewById(R.id.save);


        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getQuestions();
        quetionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        ShowMextQuestion();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(r1.isChecked() || r2.isChecked() || r3.isChecked() || r4.isChecked()){
                        CheckAnswer();
                    }else{
                        Toast.makeText(QuizActivity.this,"select an answer",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    ShowMextQuestion();
                }
            }
        });
    }

    private void ShowMextQuestion() {
        rgp.clearCheck();

        if(questionCounter < quetionCountTotal){
            currentQuestion = questionList.get(questionCounter);
            Question.setText(currentQuestion.getQuestion());
            r1.setText(currentQuestion.getOption1());
            r2.setText(currentQuestion.getOption2());
            r3.setText(currentQuestion.getOption3());
            r4.setText(currentQuestion.getOption4());
            questionCounter++;

            Count.setText("Question: " + questionCounter + "/" + quetionCountTotal);
            answered = false;
            btn.setText("Confirm");
        }else {
            finishQuiz();
        }
    }

    private void CheckAnswer(){
        final MediaPlayer correct = MediaPlayer.create(this,R.raw.correct);
        final MediaPlayer incorrect = MediaPlayer.create(this,R.raw.incorrect);
        answered = true;
        RadioButton rbSelected = findViewById(rgp.getCheckedRadioButtonId());
        int answenb = rgp.indexOfChild(rbSelected)+1;
        if(answenb == currentQuestion.getAsnwerNb()){
            correct.start();
            score ++ ;
            Score.setText("Score: "+score);
        }else{
            incorrect.start();
        }
        ShowSolution();
    }

    private void ShowSolution() {
        switch (currentQuestion.getAsnwerNb()){
            case 1 :
                Toast.makeText(QuizActivity.this ,""+currentQuestion.getOption1()+" is correct" ,Toast.LENGTH_SHORT).show();
                break;
            case 2 :
                Toast.makeText(QuizActivity.this ,""+currentQuestion.getOption2()+" is correct" ,Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(QuizActivity.this ,""+currentQuestion.getOption3()+" is correct" ,Toast.LENGTH_SHORT).show();
                break;
            case 4 :
                Toast.makeText(QuizActivity.this ,""+currentQuestion.getOption4()+" is correct" ,Toast.LENGTH_SHORT).show();
                break;
        }
        if(questionCounter <quetionCountTotal){
            btn.setText("Suivant");
        }else{
            btn.setText("Point Final");
        }
    }

    private void finishQuiz() {
        Intent resutlintent = new Intent();
        resutlintent.putExtra(EXTRA_SCORE,score);
        setResult(RESULT_OK,resutlintent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()){
            finishQuiz();
        }else{
            Toast.makeText(this,"Press Back again to finsih" ,Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}