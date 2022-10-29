package mainapp;

import javax.swing.*;
import java.awt.event.*;
import entity.Classroom;
import manager.ClassroomManager;
import manager.ExerciseManager;

public class ClassroomOfStudent extends ClassroomFrame implements MouseListener
{
    private JButton btnListStudent ;

    public ClassroomOfStudent(Classroom classroom) 
    {
        super(classroom);
        
    }

    protected void initMainFrame()
    {
        super.initMainFrame();

        initFrameOfClass(3);
    }

    
    public void actionPerformed(ActionEvent e) 
    {
        //Lấy những sự kiện đã được kế thừa ở lớp cha
        super.actionPerformed(e);
        
        if(e.getSource() == btnListStudent)
        {
            super.indexOfPanelDisplay = 3;
            super.hideAndShowAnPanel();
        }
    }

    public void mousePressed(MouseEvent e) 
    {
        JLabel temp = (JLabel) e.getSource();
        String exerciseTitle = ((JLabel) temp.getComponent(0)).getText();

        doExercise(exerciseTitle);
    }

    public static void main(String[] args) 
    {
        ClassroomManager.readData();
        ExerciseManager.readData();

        Classroom temp = ClassroomManager.findClassroomById("triet01");

        new ClassroomOfStudent(temp);
    }

}