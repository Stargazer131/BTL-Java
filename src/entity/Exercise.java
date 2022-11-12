package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import generic.Pair;

public class Exercise implements Serializable
{
    private static final long serialVersionUID = 1312002L;

    private String exerciseTitle,
                   messageTime;

    private int exerciseTime;

    private ArrayList<Question> questions;

    private boolean exerciseFinish;

    private ArrayList<Pair<Integer, Integer>> answerKey;

    private TreeSet<String>studentFinishThisExercise;

    private static SimpleDateFormat sdfDay = new SimpleDateFormat("dd/MM/YYYY"),
                                    sdfHour = new SimpleDateFormat("HH:mm");

    public Exercise(String EXERCISE_TITLE, int EXERCISE_TIME, ArrayList<Question> QUESTIONS, ArrayList<Pair<Integer,Integer>> ANSWER_KEY)
    {
        this.exerciseTitle = EXERCISE_TITLE;
        this.exerciseTime =EXERCISE_TIME;
        this.questions = QUESTIONS;
        this.exerciseFinish = false;
        this.answerKey = ANSWER_KEY;
        this.studentFinishThisExercise = new TreeSet<>();

        Date d = new Date();
        this.messageTime = String.format("%s ngày %s", sdfHour.format(d), sdfDay.format(d));
    }

    public void addAnStudentFinish(String id)
    {
        studentFinishThisExercise.add(id);
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

    public ArrayList<Pair<Integer, Integer>> getAnswerOfExercise()
    {
        return this.answerKey;
    }

    public String getMessageTime()
    {
        return this.messageTime;
    }

    public TreeSet<String> getListStudentFinish()
    {
        return studentFinishThisExercise;
    }
}
