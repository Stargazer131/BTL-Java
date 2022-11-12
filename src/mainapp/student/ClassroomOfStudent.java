package mainapp.student;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import entity.Classroom;
import entity.Student;
import generic.Pair;
import launch.App;
import mainapp.ClassroomFrame;
import mainapp.StudentInfo;
import manager.ClassroomManager;
import manager.StudentManager;

public class ClassroomOfStudent extends ClassroomFrame
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

    //Xem thông tin của sinh viên khác trong bảng xếp hạng
    protected void initRakingOfStudentTable()
    {
        super.initRakingOfStudentTable();

        rankingOfStudentTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rankingOfStudentTable.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                int indexRow = rankingOfStudentTable.rowAtPoint(e.getPoint());
                new StudentInfo(studentResult.get(indexRow).getFirst());
            }
        });
    }

    @Override
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
            StudentManager.readData();
            this.dispose();
            App.studentUser = StudentManager.findStudentById(App.studentUser.getId());
            new StudentFrame(App.studentUser);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        if(e.getSource().getClass() == JLabel.class)
        {
            int indexOfExerciseToDo = 0;
            for(Component i: pnOfThisClassroom.get(1).getComponents())
            {
                if(e.getSource() == i)
                {
                    break;
                }
                indexOfExerciseToDo++;
            }

            ClassroomManager.readData();
            this.classroom = ClassroomManager.findClassroomById(classroom.getId());
            readDataOfClassroom();

            Boolean checkThisStudentInThisClassroom = true;

            for(Pair<Student,Double> i: studentResult)
            {
                if(i.getFirst().getId().equals(App.studentUser.getId()))
                {
                    checkThisStudentInThisClassroom = false;
                    doExercise(indexOfExerciseToDo);
                    this.dispose();
                    break;
                }
            }

            if(checkThisStudentInThisClassroom)
            {
                JOptionPane.showMessageDialog(null, "Bạn phải thành viên của lớp học này","Thông báo", JOptionPane.ERROR_MESSAGE);

                this.dispose();
                new StudentFrame(App.studentUser);
            }
        }
    }
}