package entity;

import java.io.Serializable;
import java.util.HashMap;

public class Question implements Serializable
{
    private static final long serialVersionUID = 13012002L;

    private String questionID,
                   questionTitle,
                   questionAnswerKey;

    private HashMap<String, Boolean> answerList;

    public Question(String QUESTION_ID,String QUESTION_TITLE, HashMap<String, Boolean> answerList, String QUESTION_ANSWER_KEY)
    {
        this.questionID = QUESTION_ID;

        this.questionTitle = QUESTION_TITLE;

        this.answerList = answerList;
        
        this.questionAnswerKey = QUESTION_ANSWER_KEY;
    }

    public boolean checkAnswer(String answerKey)
    {
        if(answerList.get(answerKey))
        {
            return true;
        }
        return false;
    }

    public String getID()
    {
        return this.questionID;
    }

    public String getQuestionTitle()
    {
        return this.questionTitle;
    }

    public HashMap<String, Boolean> getAnswerList()
    {
        return this.answerList;
    }

    public String toString()
    {
        String outPut = "";
        for(String i: answerList.keySet())
        {
            outPut += i + "\n";
        }
        return questionID + " " + questionTitle + "\n" +
               outPut;
    }
}
