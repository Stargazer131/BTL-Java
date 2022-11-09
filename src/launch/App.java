package launch;

import entity.Student;
import entity.Teacher;
import inputform.LogInFrame;
import manager.AccountManager;
import manager.ClassroomManager;
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
        QuestionManager.readData();
        StudentManager.readData();
        TeacherManager.readData();

        new LogInFrame();
    }
}
