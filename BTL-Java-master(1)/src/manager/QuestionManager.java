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

    private static void writeData(ArrayList<Question>arr)
    {
        try 
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resources\\data\\question.data"));
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

    public static void readData()
    {
        try 
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resources\\data\\question.data"));
            try 
            {
                questions = (TreeMap<String, Question>) ois.readObject();
            } catch (ClassNotFoundException e) 
            {
                e.printStackTrace();
            }
            ois.close();

            //In ra danh sách câu hỏi để check xem có thừa thiếu cái nào không
            for(String i: questions.keySet())
            {
                System.out.println(i + " " + questions.get(i));
            }

        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
