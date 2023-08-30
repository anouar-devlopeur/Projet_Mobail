package com.example.miniproject101;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.miniproject101.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    this.db = db;

    final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
            QuestionsTable.TABLE_NAME + " ( " +
            QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuestionsTable.COLUMN_QUESTION + " TEXT, " +
            QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
            QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
            QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
            QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER_NB + " INTEGER" +
            ")";
    db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
    fillTable();

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
    onCreate(db);
    }

    private void fillTable() {
        Question q1 = new Question("Le premier joueur à remporté un ballon d'or?","Stanley Matthews","Pele","Mardona","Messi",1);
        addQuestion(q1);
        Question q2 = new Question("Qui meilleur buteur de la coupe du monde?","Stephen Fry","Bill Gates"," Miroslav Klose "," Stephen Hawking",3);
        addQuestion(q2);
        Question q3 = new Question("Lequel de ceux-ci n'est pas un périphérique, en termes electronique?","Kybord","capture","doide","Carte mère",4);
        addQuestion(q3);
        Question q4 = new Question("Quelle pièce est absolument à protéger dans un jeu d’échec?","President"," Le roi"," Le dallai lama"," Victor Hugo",2);
        addQuestion(q4);
        Question q5 = new Question("De qui est amoureux Juliette?"," Roméo","zverev","maicle","reolando",1);
        addQuestion(q5);
        Question q6 = new Question("En quelle année est mort JFK?","1970","1990","2000","1963",4);
        addQuestion(q6);
        Question q7 = new Question("Qui anime Secret Story?","anwar","ouda","Benjamin Castaldi","Zenith",3);
        addQuestion(q7);

    }
    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION , question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4,question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NB,question.getAsnwerNb());
        db.insert(QuestionsTable.TABLE_NAME , null,cv);
    }

    public List<Question> getQuestions(){
        List<Question> questionList =new ArrayList<>();
        db =getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME , null);
        if(c.moveToFirst()){
            do{
                Question question =  new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAsnwerNb(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NB)));
                questionList.add(question);
            }while(c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
