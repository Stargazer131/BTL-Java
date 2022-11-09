package manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    @SuppressWarnings("unchecked")
    public static void readData()
    {
        String filename = "resources\\data\\exercise.dat";
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename)))
        {
            exercises = (ArrayList<Exercise>)input.readObject();
        } 
        catch(Exception e) 
        {
            exercises = new ArrayList<>();
        }
    }

    public static void writeData()
    {
        String filename = "resources\\data\\exercise.dat";
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            output.writeObject(exercises);
        } 
        catch(Exception e) 
        {

        }
    }
    
    public static void setData(ArrayList<Exercise> temp)
    {
        exercises = temp;
    }
}
