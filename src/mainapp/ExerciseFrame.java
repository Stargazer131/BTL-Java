package mainapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import entity.Classroom;
import entity.Exercise;
import entity.Question;
import generic.Pair;
import manager.ClassroomManager;

public class ExerciseFrame extends JFrame implements ActionListener
{
    private Exercise exercise;

    private ArrayList<Question> questions;

    private String classroomID;

    private JPanel pnMainFrame;

    private JButton btnTurnBackToClassroom;

    private GridBagConstraints gbc;

    private JScrollPane scrollPane;

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
        pnTemp.setLayout(null);

        JLabel lbQuestionIndex = new JLabel(String.format("Câu hỏi %d: ", index + 1));
        lbQuestionIndex.setBounds(20,10,80,20);
        pnTemp.add(lbQuestionIndex);

        JLabel lbQuestionTitle = new JLabel(questions.get(index).getQuestionTitle());
        lbQuestionTitle.setBounds(80,10,200,20);
        pnTemp.add(lbQuestionTitle);

        JLabel lbChoices[] = new JLabel[4],
               lbAnswers[] = new JLabel[4];

        ArrayList<Pair<String, Boolean>>answerTitle = questions.get(index).getAnswerList();

        for(int i = 0 ; i < 4; i++)
        {
            lbChoices[i] = new JLabel((char)('A' + i) + ":" );
            lbChoices[i].setBounds(20,5 + 20 * ( i + 1),20,20);
            pnTemp.add(lbChoices[i]);

            lbAnswers[i] = new JLabel(answerTitle.get(i).getFirst());
            if(answerTitle.get(i).getSecond())
            {
                lbAnswers[i].setForeground(Color.red);
                lbChoices[i].setForeground(Color.red);
            }    
            lbAnswers[i].setBounds(40,5 + 20 * (i + 1),300,20);
            pnTemp.add(lbAnswers[i]);
        }

        pnMainFrame.add(pnTemp,gbc);
    }

    private void initMainFrame()
    {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,0,10,0);

        pnMainFrame = new JPanel();
        pnMainFrame.setBounds(200,0,800,720);
        pnMainFrame.setBorder(BorderFactory.createLineBorder(Color.black));
        pnMainFrame.setLayout(new GridBagLayout());

        //Tạo các panel câu hỏi
        for(int i = 0 ; i < questions.size(); i++)
        {
            initQuestionPanel(i);
        }

        scrollPane = new JScrollPane(pnMainFrame,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(200, 0, 800, 720);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.add(scrollPane);
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
