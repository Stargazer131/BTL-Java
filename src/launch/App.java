package launch;

import entity.Student;
import entity.Teacher;
import mainapp.StudentFrame;
import mainapp.TeacherFrame;
import manager.AccountManager;
import manager.ClassroomManager;
import manager.ExerciseManager;
import manager.QuestionManager;
import manager.StudentManager;
import manager.TeacherManager;

public class App 
{
    public static Teacher teacherUser;

    public static Student studentUser;

    public static void main(String[] args)  
    {
        //Nạp dữ liệu vào chương trình
        AccountManager.readData();
        ClassroomManager.readData();
        ExerciseManager.readData();
        QuestionManager.readData();
        StudentManager.readData();
        TeacherManager.readData();

        teacherUser = TeacherManager.findTeacherById("PHUC2405");

        studentUser = StudentManager.findStudentById("B20DCCN503");

        //new TeacherFrame(teacherUser);

        new StudentFrame(studentUser);
    }
}