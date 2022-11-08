package mainapp;

import javax.swing.*;
import java.awt.event.*;
import entity.Classroom;
import entity.Student;
import generic.Pair;
import launch.App;
import manager.ClassroomManager;

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
        else if(e.getSource() == btnTurnBack)
        {
            this.dispose();
            new StudentFrame(App.studentUser);
        }
    }

    public void mousePressed(MouseEvent e) 
    {

        if(e.getSource().getClass() == JLabel.class)
        {
            ClassroomManager.readData();
            readDataOfClassroom();

            JLabel temp = (JLabel) e.getSource();
            String exerciseTitle = ((JLabel) temp.getComponent(0)).getText();

            Boolean checkThisStudentInThisClassroom = true;

            for(Pair<Student,Double> i: studentResult)
            {
                if(i.getFirst().getId().equals(App.studentUser.getId()))
                {
                    checkThisStudentInThisClassroom = false;
                    doExercise(exerciseTitle);
                    this.dispose();
                    break;
                }
            }

            if(checkThisStudentInThisClassroom)
            {
                JOptionPane.showMessageDialog(null, "Bạn không thể làm bài tập này!","Thông báo", JOptionPane.ERROR_MESSAGE);

                this.dispose();
                new StudentFrame(App.studentUser);
            }
        }
    }
}