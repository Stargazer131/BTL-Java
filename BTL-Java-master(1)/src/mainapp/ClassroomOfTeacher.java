package mainapp;

import javax.swing.JButton;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import entity.Classroom;
import manager.ClassroomManager;

public class ClassroomOfTeacher extends ClassroomFrame implements ActionListener
{
    private JButton btnListStudent ;

    public ClassroomOfTeacher(Classroom classroom) 
    {
        super(classroom);
        
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

    
    public void actionPerformed(ActionEvent e) 
    {
        //Lấy những sự kiện đã được kế thừa ở lớp cha
        super.actionPerformed(e);
        
        if(e.getSource() == btnListStudent)
        {
            indexOfPanelDisplay = 3;
            hideAndShowAnPanel();
        }
    }

    public static void main(String[] args) 
    {
        ClassroomManager.readData();
        new ClassroomOfTeacher(ClassroomManager.findClassroomById("triet01"));
    }

}
