package manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import entity.Exercise;

public class ExerciseManager 
{
    private static ArrayList<Exercise> exercises = new ArrayList<>();

    public static void addExerCise(Exercise e)
    {
        exercises.add(e);
        writeData();
    }

    public static Exercise getExerciseByTitle(String title)
    {
        for(Exercise i: exercises)
        {
            if(i.getTitle().equals(title))
                return i;
        }
        return null;
    }

    public static void readData()
    {
        try 
        {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream("resources\\data\\exercise.dat"));
            try 
            {
                exercises = (ArrayList<Exercise>) oos.readObject();

                for(Exercise i: exercises)
                {
                    System.out.println(i.getTitle());
                }
            } catch (ClassNotFoundException e) 
            {
                e.printStackTrace();
            }
            oos.close();
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void writeData()
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resources\\data\\exercise.dat"));

            oos.writeObject(exercises);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

    }
    
    public static void setData(ArrayList<Exercise> temp)
    {
        exercises = temp;
    }
}
