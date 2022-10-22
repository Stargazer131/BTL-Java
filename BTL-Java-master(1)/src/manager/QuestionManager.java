package manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

import entity.Question;

public class QuestionManager 
{
    private static TreeMap<String, Question> questions = new TreeMap<>();
    
    public static void addQuestion(Question q)
    {
        questions.put(q.getID(),q);
        writeData();
    }

    public static void removeQuestion(String id)
    {
        questions.remove(id);
        writeData();
    }

    public static Question findQuestionByID(String id)
    {
        return questions.get(id);
    }

    public static void writeData()
    {
        ObjectOutputStream oos;
        try 
        {
            oos = new ObjectOutputStream(new FileOutputStream("resources\\data\\question.dat"));
            oos.writeObject(questions);
            oos.flush();
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        
    }

    public static void readData()
    {
        try 
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resources\\data\\question.dat"));
            try 
            {
                questions = (TreeMap<String, Question>) ois.readObject();
            } catch (ClassNotFoundException e) 
            {

            }

            for(String i: questions.keySet())
            {
                System.out.println(i + " " + questions.get(i).toString()) ;
            }

            ois.close();
        }catch (IOException e) 
        {
        }
    }

    public static void setMap(TreeMap<String, Question> map)
    {
        questions = map;
    }
}