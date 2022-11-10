package mainapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import manager.ClassroomManager;
import manager.StudentManager;
import entity.*;
import launch.App;

public class ClassroomOfTeacher extends ClassroomFrame
{
    private JButton btnListStudent;

    private JButton btnStudentInfor,
                    btnStudentDelete;

    private JPopupMenu pmRightClickStudent;

    private Student studentRightClick;

    private int indexStudentRightClick=0;

    public ClassroomOfTeacher(Classroom classroom) 
    {
        super(classroom);
        
        initMessageFrame(pnOfThisClassroom.get(0), 0, "Tạo tin nhắn mới", "", "");
        initMessageFrame(pnOfThisClassroom.get(1), 0, "Tạo bài tập mới", "", "");

        initPopupMenuRightClickStudent();
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

    //Tạo memupopup chuột phải
    private void initPopupMenuRightClickStudent()
    {
        this.btnStudentDelete = new JButton("Xoá sinh viên này khỏi lớp");
        btnStudentDelete.setMaximumSize(new Dimension(200,25));
        btnStudentDelete.setFocusable(false);
        btnStudentDelete.addActionListener(this);

        this.btnStudentInfor = new JButton("Xem thông tin sinh viên");
        btnStudentInfor.setMaximumSize(new Dimension(200,25));
        btnStudentInfor.setFocusable(false);
        btnStudentInfor.addActionListener(this);

        this.pmRightClickStudent = new JPopupMenu();
        pmRightClickStudent.add(btnStudentInfor);
        pmRightClickStudent.add(btnStudentDelete);
        pmRightClickStudent.setVisible(false);
        pmRightClickStudent.setMaximumSize(new Dimension(220,40));

        this.add(pmRightClickStudent);
    }

    //Ghi đè tạo bảng xếp hạng
    protected void initRakingOfStudentTable()
    {
        super.initRakingOfStudentTable();

        rankingOfStudentTable.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e) 
            {
                if(e.isPopupTrigger())
                    showPopUpMenuStudent(e);
            }

            public void mouseReleased(MouseEvent e)
            {
                if(e.isPopupTrigger())
                    showPopUpMenuStudent(e);
            }

            public void showPopUpMenuStudent(MouseEvent e)
            {
                pmRightClickStudent.show(e.getComponent(), e.getX(), e.getY());

                int indexRow = rankingOfStudentTable.rowAtPoint(e.getPoint());

                indexStudentRightClick  = indexRow;
                
                studentRightClick = studentResult.get(indexRow).getFirst();
            }
        });
    }
    
    //Bắt sự kiện của chương trình
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
        else if(e.getSource() == btnStudentDelete)
        {
            int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn xoá sinh viên này khỏi lớp không?", "Thông báo",JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION)
            {
                StudentManager.findStudentById(studentRightClick.getId()).deleteClassroomId(classroom.getId());

                StudentManager.writeData();

                studentResult.remove(indexStudentRightClick);

                ClassroomManager.writeData();

                pnOfThisClassroom.get(2).remove(rankingOfStudentTable);

                deleteRow(indexStudentRightClick);

                System.out.println(studentResult.size());
            }
        }
        else if(e.getSource() == btnTurnBack)
        {
            this.dispose();
            new TeacherFrame(App.teacherUser);
        }
    }

    //Bắt sự kiện chuột khi nhấn
    @Override
    public void mousePressed(MouseEvent e) 
    {
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
            new QuestionBank(this.classroom.getId());
            pnOfThisClassroom.get(1).remove(pnOfThisClassroom.get(1).getComponentCount() - 1);

            if(listOfExercises.size() > 0)
            {
                Exercise newExercise = classroom.getExercise().get(listOfExercises.size() - 1);
            
                //Tạo 1 bài tập mới
                createAMessage(newExercise, "Exercise");
            }    
        }
        else if(e.getSource().getClass() == JLabel.class)
        {
            int indexOfExerciseToOpen = 0;
            for(Component i: pnOfThisClassroom.get(1).getComponents())
            {
                if(e.getSource() == i)
                {
                    break;
                }
                indexOfExerciseToOpen++;
            }

            new ExerciseFrame(listOfExercises.get(indexOfExerciseToOpen), this.classroom.getId());
            this.dispose();
        }
    }
}