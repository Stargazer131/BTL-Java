package mainapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import manager.ClassroomManager;
import manager.ExerciseManager;
import manager.QuestionManager;
import entity.*;

public class ClassroomOfTeacher extends ClassroomFrame implements ActionListener, MouseListener
{
    private JButton btnListStudent ;

    public ClassroomOfTeacher(Classroom classroom) 
    {
        super(classroom);
        
        initMessageFrame(pnOfThisClassroom.get(0), 0, "Tạo tin nhắn mới", "", "");
        initMessageFrame(pnOfThisClassroom.get(1), 0, "Tạo bài tập mới", "", "");

    }

    protected void initLeftFrame() 
    {
        btnListStudent = new JButton();

        super.initLeftFrame();

        initButtonOfLeftPanel(btnListStudent, "Danh sách học sinh", 230);
        initButtonOfLeftPanel(btnScoreBoard, "Bảng xếp hạng", 270);
    }

    protected void initMainFrame()
    {
        super.initMainFrame();

        initFrameOfClass(3);
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
                initMessageFrame(pnOfThisClassroom.get(0), 0, temp.getContent(), temp.getTime(), "Message");
                initMessageFrame(pnOfThisClassroom.get(0), 0, "Tạo tin nhắn mới", "", "");
                changePositionOfMessage(pnOfThisClassroom.get(0), 0);
                updatePanel(scrollCurrent);
            }
        }
        else if(e.getSource() == pnOfThisClassroom.get(1).getComponent(pnOfThisClassroom.get(1).getComponentCount() - 1))
        {
            this.dispose();
            new QuestionBank(this.classroom);
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