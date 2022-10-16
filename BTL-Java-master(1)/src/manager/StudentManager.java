package manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import entity.Student;

/** Manager for student Database
@author hao
*/

public class StudentManager
{
    private static HashMap<String, Student> students;


    // add a new Student
    public static void addStudent(Student student)
    {
        students.put(student.getId(), student);
        writeData();
    }

    public static Student findStudentById(String id) // find and return the student with the given id
    {
        return students.get(id);
    }

    public static void addNewClassroom(String studentId, String classroomId) // add new classroom id to a student
    {
        students.get(studentId).addClassroomId(classroomId);
        writeData();
    }

    // read from student.dat
    @SuppressWarnings("unchecked")
    public static void readData()
    {
        String filename = "resources\\data\\student.dat";
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename)))
        {
            students = (HashMap<String, Student>)input.readObject();
        }

        catch(Exception e)
        {
            students = new HashMap<>();
        }
    }

    // Update data back in the student.dat 
    public static void writeData()
    {
        String filename = "resources\\data\\student.dat";
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            output.writeObject(students);
        }

        catch(Exception e)
        {

        }
    }
}