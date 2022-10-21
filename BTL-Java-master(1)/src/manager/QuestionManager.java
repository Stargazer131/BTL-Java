package manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import entity.Question;

public class QuestionManager 
{
    private static TreeMap<String, Question> questions;
    
    public static void addQuestion(Question q)
    {
        if(questions.size() < 999)
        {
            questions.put(q.getID(),q);
            writeData(null);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "So luong cau hoi vuot qua cho 1000!", "Thong bao", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void removeQuestion(String id)
    {
        questions.remove(id);
        writeData(null);
    }

    public static Question findQuestionByID(String id)
    {
        return questions.get(id);
    }

    public static void writeData(ArrayList<Question>arr)
    {
        try 
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resources\\data\\question.dat"));
            if(arr == null)
                oos.writeObject(questions);
            else
                oos.writeObject(arr);
            oos.close();
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void readData()
    {
        String filename = "resources\\data\\question.dat";
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename)))
        {
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}