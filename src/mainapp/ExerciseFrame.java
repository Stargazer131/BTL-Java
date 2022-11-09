package mainapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import entity.Classroom;
import entity.Exercise;
import entity.Question;
import manager.ClassroomManager;

public class ExerciseFrame extends JFrame implements ActionListener
{
    private Exercise exercise;

    private ArrayList<Question> questions;

    private String classroomID;

    private JPanel pnMainFrame;

    private JButton btnTurnBackToClassroom;

    private GridBagConstraints gbc;

    public ExerciseFrame(Exercise exercise, String classroomId)
    {
        this.exercise = exercise;
        this.classroomID = classroomId;
        this.questions = exercise.getQuestions();

        initThisFrame();
        initMainFrame();
    }

    private void initThisFrame()
    {
        this.setTitle("Bài tập: " + exercise.getTitle());
        this.setSize(1200, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        //Nút quay lại lớp học
        btnTurnBackToClassroom = new JButton("Quay lại");
        btnTurnBackToClassroom.setBounds(20,10,100,30);
        btnTurnBackToClassroom.setFocusable(false);
        btnTurnBackToClassroom.addActionListener(this);
        this.add(btnTurnBackToClassroom);

        this.setVisible(true);
    }

    private void initQuestionPanel(int index)
    {
        gbc.gridx = 0;
        gbc.gridy = index;

        JPanel pnTemp = new JPanel();
        pnTemp.setPreferredSize(new Dimension(780, 300));
        pnTemp.setBorder(BorderFactory.createLineBorder(Color.black));

        pnMainFrame.add(pnTemp,gbc);
    }

    private void initMainFrame()
    {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,10,0,0);

        pnMainFrame = new JPanel();
        pnMainFrame.setBounds(200,0,800,720);
        pnMainFrame.setBorder(BorderFactory.createLineBorder(Color.black));
        pnMainFrame.setLayout(new GridBagLayout());

        //Tạo các panel câu hỏi
        for(int i = 0 ; i < questions.size(); i++)
        {
            initQuestionPanel(i);
        }
        
        this.add(pnMainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btnTurnBackToClassroom)
        {
            ClassroomManager.readData();
            Classroom classroomTemp = ClassroomManager.findClassroomById(this.classroomID);
            new ClassroomOfTeacher(classroomTemp);
            this.dispose();
        }
    }
}
