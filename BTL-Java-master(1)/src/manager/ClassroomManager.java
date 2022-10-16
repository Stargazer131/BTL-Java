package manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import entity.Classroom;

public class ClassroomManager 
{
    private static HashMap<String, Classroom> classrooms;    

    public static void addClassroom(Classroom classroom)
    {
        classrooms.put(classroom.getId(), classroom);
        writeData();
    }

    public static void changeInforClass(String id, Classroom classroom1, Classroom classroom2)
    {
        classrooms.replace(id, classroom1, classroom2);
        writeData();
    }

    public static Classroom findClassroomById(String id) // find and return the classroom with the given id
    {
        return classrooms.get(id);
    }

    public static void deleteClassroom(String id)
    {
        classrooms.remove(id, classrooms.get(id));
        writeData();
    }

    @SuppressWarnings("unchecked")
    // read data from classroom.dat
    public static void readData()
    {
        String filename = "resources\\data\\classroom.dat";
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename)))
        {
            classrooms = (HashMap<String, Classroom>)input.readObject();
        }

        catch(Exception e)
        {
            classrooms = new HashMap<>();
        }

        for(String i: classrooms.keySet())
        {
            System.out.println(i + classrooms.get(i).getName());
        }
    }

    // Update data back in the classroom.dat 
    public static void writeData()
    {
        String filename = "resources\\data\\classroom.dat";
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            output.writeObject(classrooms);
        }

        catch(Exception e)
        {

        }
    }

    public static HashMap<String, Classroom> getClassrooms()
    {
        return classrooms;
    }

    public static boolean checkIDExist(String ID)
    {
        for(String i: classrooms.keySet())
        {
            if(i.equals(ID))
                return false;
        }
        return true;
    }
    
    public static boolean checkNAMEExist(String NAME)
    {
        for(String i: classrooms.keySet())
        {
            if(NAME.equals(classrooms.get(i).getName()))
                return false;
        }
        return true;
    }
}
