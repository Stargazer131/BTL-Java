package mainapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import manager.ClassroomManager;
import manager.ExerciseManager;
import manager.QuestionManager;
import entity.*;

public class ClassroomOfTeacher extends ClassroomFrame
{
    private JButton btnListStudent ;

    private ArrayList<Student> studentInThisClassroom;

    public ClassroomOfTeacher(Classroom classroom) 
    {
        super(classroom);
        
        initMessageFrame(pnOfThisClassroom.get(0), 0, "Tạo tin nhắn mới", "", "");
        initMessageFrame(pnOfThisClassroom.get(1), 0, "Tạo bài tập mới", "", "");

        readDataOfClassroom();
    }

    //Đẩy các ô message xuống 1 dòng
    private void changePositionOfMessage(JPanel temp, int index)
    {
        Component arrCom[] = temp.getComponents();
        for(int i = 0 ; i < arrCom.length; i++)
        {
            gbc2.gridx = 0;
            gbc2.gridy = arrCom.length + 1 - i;
            temp.remove(temp);
            temp.add(arrCom[i],gbc2);
        }
    }
   
    private void createAMessage(Object temp, String option)
    {
        if(!option.equals("Exercise"))
        {
            initMessageFrame(pnOfThisClassroom.get(0), 0, ((EventMessage) temp).getContent(), ((EventMessage) temp).getTime(), "Message");
            initMessageFrame(pnOfThisClassroom.get(0), 0, "Tạo tin nhắn mới", "", "");
            changePositionOfMessage(pnOfThisClassroom.get(0), 0);
        }
        else
        {
            initMessageFrame(pnOfThisClassroom.get(1), 0, ((Exercise) temp).getTitle(), ((Exercise) temp).getMessageTime(), "");
            initMessageFrame(pnOfThisClassroom.get(1), 0, "Tạo bài tập mới", "", "");
            changePositionOfMessage(pnOfThisClassroom.get(1), 0);
        }
        
        updatePanel(scrollCurrent);
    }

    //Bắt sự kiện của chương trình
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

    //Bắt sự kiện chuột khi nhấn
    public void mousePressed(MouseEvent e) 
    {
        System.out.println(pnCurrentDisplay.getComponentCount()) ;
        if(e.getSource() == pnOfThisClassroom.get(0).getComponent(pnOfThisClassroom.get(0).getComponentCount() - 1))
        {
            JTextField tfMessageContent = new JTextField();

            Object [] input = {
                "Nội dung tin nhắn", tfMessageContent
            };

            int option = JOptionPane.showConfirmDialog(null, input, "Tạo tin nhắn", JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION)
            {
                EventMessage temp = new EventMessage(tfMessageContent.getText());
                super.classroom.addAnEventMessage(temp);
                ClassroomManager.writeData();
                pnOfThisClassroom.get(0).remove(event_Messages.size() - 1);
                //Tạo 1 tin nhắn mới
                createAMessage(temp, "Message");
            }
        }
        else if(e.getSource() == pnOfThisClassroom.get(1).getComponent(pnOfThisClassroom.get(1).getComponentCount() - 1))
        {
            this.dispose();
            new QuestionBank(this.classroom);
            pnOfThisClassroom.get(0).remove(event_Messages.size() - 1);

            Exercise newExercise = classroom.getExercise().get(classroom.getExercise().size() - 1);
            
            //Tạo 1 bài tập mới
            createAMessage(newExercise, "Exercise");
        }
    }

    public static void main(String[] args) 
    {
        QuestionManager.readData();
        ClassroomManager.readData();
        ExerciseManager.readData();

        Classroom temp = ClassroomManager.findClassroomById("triet01");

        new ClassroomOfTeacher(temp);
    }

}