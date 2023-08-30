package com.example.miniproject101;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static  final int REQUEST_CODE_QUIZ = 1;
    public static final String SHARED="shared";
    public static final String HIGHSCORE_RES = "highscore_res";

    int highscore;
Button btn;
EditText ed1;
TextView highs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = (EditText) findViewById(R.id.ed1);
        btn = (Button) findViewById(R.id.btn);
        ed1.addTextChangedListener(NomAvatar) ;
        highs = findViewById(R.id.finalscore);
        final MediaPlayer  mediaPlayer = MediaPlayer.create(this,R.raw.sound);
        loadHighScore();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(MainActivity.this , QuizActivity.class);
                x.putExtra("nom",ed1.getText().toString());
                mediaPlayer.start();
                startActivityForResult(x , REQUEST_CODE_QUIZ);
            }
        });
    }

    private TextWatcher NomAvatar = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nom = ed1.getText().toString().trim();
            btn.setEnabled(!nom.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_QUIZ){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if(score>highscore){
                    updateHIghscore(score);
                }
            }
        }
    }

    private void loadHighScore(){
        SharedPreferences prefs = getSharedPreferences(SHARED ,MODE_PRIVATE);
        highscore = prefs.getInt(HIGHSCORE_RES ,0);
        highs.setText("High_score: " +ed1.getText() +" "+highscore);
    }

    private void updateHIghscore(int score) {
        highscore = score;
        highs.setText("high_Score: " +ed1.getText() +" "+ highscore);
        SharedPreferences prefs =getSharedPreferences(SHARED , MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HIGHSCORE_RES , highscore);
        editor.apply();
    }
}