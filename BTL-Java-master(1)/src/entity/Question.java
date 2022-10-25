package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import generic.Pair;

public class Question implements Serializable
{
    private static final long serialVersionUID = 13012002L;

    private String questionID,
                   questionTitle,
                   questionAnswerKey;

    private int trueAnswer;

    private ArrayList<Pair< String, Boolean>> answerList;

    public Question()
    {
        questionID = "";
        questionTitle = "";
        questionAnswerKey = "";
        answerList = new ArrayList<>();
    }

    public Question(String QUESTION_ID,String QUESTION_TITLE, ArrayList<Pair< String, Boolean>> answerList, String QUESTION_ANSWER_KEY, int TRUE_ANSWER)
    {
        this.questionID = QUESTION_ID;

        this.questionTitle = QUESTION_TITLE;

        this.answerList = answerList;
        
        this.questionAnswerKey = QUESTION_ANSWER_KEY;

        this.trueAnswer = TRUE_ANSWER;
    }

    public String getID()
    {
        return this.questionID;
    }

    public String getQuestionTitle()
    {
        return this.questionTitle;
    }

    public ArrayList<Pair< String, Boolean>> getAnswerList()
    {
        return this.answerList;
    }

    public String toString()
    {
        String outPut = "";
        for(Pair<String, Boolean> i: answerList)
        {
            outPut += i.getFirst() + "\n";
        }
        return questionID + " " + questionTitle + "\n" +
               outPut;
    }
}
