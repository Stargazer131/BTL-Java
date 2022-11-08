package manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import entity.Question;

public class QuestionManager 
{
    public static ArrayList< Question> questions = new ArrayList<>();
    
    public static void addQuestion(Question q)
    {
        questions.add(q);
        writeData();
    }

    public static void removeQuestion(String id)
    {
        questions.remove(id);
        writeData();
    }

    public static void writeData()
    {
        ObjectOutputStream oos;
        try 
        {
            oos = new ObjectOutputStream(new FileOutputStream("resources\\data\\question.dat"));
            oos.writeObject(questions);
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
                questions = (ArrayList<Question>) ois.readObject();
            } catch (ClassNotFoundException e) 
            {

            }

            //In ra danh sách câu hỏi
            // for(Question i: questions)
            // {
            //     System.out.println(i);
            // }

            ois.close();
        }catch (IOException e) 
        {
        }
    }
}