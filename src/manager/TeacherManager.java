package manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import entity.Teacher;

public class TeacherManager 
{
    private static HashMap<String, Teacher> teachers;

    // add a new Teacher
    public static void addTeacher(Teacher teacher)
    {
        teachers.put(teacher.getId(), teacher);
        writeData();
    }

    //Replace a teacher
    public static void replaceTeacher(String id, Teacher oldTeacher, Teacher newTeacher)
    {
        teachers.remove(oldTeacher.getId());
        teachers.put(newTeacher.getId(), newTeacher);
        writeData();
    }

    public static Teacher findTeacherById(String id) // find and return the teacher with the given id
    {
        return teachers.get(id);
    }

    // read from teacher.dat
    @SuppressWarnings("unchecked")
    public static void readData()
    {
        String filename = "resources\\data\\teacher.dat";
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename)))
        {
            teachers = (HashMap<String, Teacher>)input.readObject();
        }
        catch(Exception e)
        {
            teachers = new HashMap<>();
        }
    }

    // Update data back in the teacher.dat 
    public static void writeData()
    {
        String filename = "resources\\data\\teacher.dat";
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            output.writeObject(teachers);
        }
        catch(Exception e)
        {
            System.out.println("Loi");
        }
    }

    public static boolean checkIDExist(String ID)
    {
        for(String i: teachers.keySet())
        {
            if(i.equals(ID))
                return false;
        }
        return true;
    }

    public static boolean checkNameExist(String NAME)
    {
        for(String i: teachers.keySet())
        {
            if(NAME.equals(teachers.get(i).getName()))
                return false;
        }
        return true;
    }
}
