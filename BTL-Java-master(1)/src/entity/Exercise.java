package entity;

import java.util.ArrayList;
import java.util.Date;

public class Exercise 
{
    private String exerciseTitle;

    private Date timeStart,
                 timeEnd;

    private int exerciseTime;

    private ArrayList<Question> questions;

    private boolean exerciseFinish;

    public Exercise(String EXERCISE_TITLE, int EXERCISE_TIME, ArrayList<Question> QUESTIONS)
    {
        this.exerciseTitle = EXERCISE_TITLE;
        this.exerciseTime =EXERCISE_TIME;
        this.questions = QUESTIONS;
        this.exerciseFinish = false;
    }

    public String getTitle()
    {
        return exerciseTitle;
    }

    public int getTime()
    {
        return exerciseTime;
    }

    public ArrayList<Question> getQuestions()
    {
        return questions;
    }

    public boolean isFinish()
    {
        return exerciseFinish;
    }
}
