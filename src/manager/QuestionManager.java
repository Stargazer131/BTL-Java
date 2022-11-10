package manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import entity.Question;

public class QuestionManager 
{
    public static ArrayList<Question> questions = new ArrayList<>();
    
    public static void addQuestion(Question q)
    {
        questions.add(q);
        writeData();
    }

    public static void writeData()
    {
        String filename = "resources\\data\\question.dat";
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            output.writeObject(questions);
        } 
        catch(Exception e) 
        {

        }
    }

    @SuppressWarnings("unchecked")
    public static void readData()
    {
        String filename = "resources\\data\\question.dat";
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename)))
        {
            questions = (ArrayList<Question>)input.readObject();   
        } 
        catch(Exception e) 
        {
            questions = new ArrayList<>();
        }
    }
}