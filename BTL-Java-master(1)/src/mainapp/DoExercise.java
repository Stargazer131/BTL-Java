package mainapp;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;

import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Color;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entity.Exercise;
import entity.Question;
import generic.Pair;
import manager.ExerciseManager;

public class DoExercise extends JFrame implements ActionListener
{
    private Exercise exercise;
    private JLabel lbtimeRemain,
                   lbQuestionFinished;
    private int questionsFinish,
                questionSum,
                exerciseTime,
                exerciseTimeRemain;
    private ArrayList<Question> questions;
    private JButton btnSubmit;
    private JPanel pnleftFrame;
    private JComponent pnQuestion;

    private void readData()
    {
        this.exerciseTime = 5 * 60;
        this.questionsFinish = 0;
        this.exerciseTimeRemain = exerciseTime;
        //this.questions = exercise.getQuestions();
        //this.questionSum = questions.size();
        this.questionSum = 0;
    }
    
    public DoExercise(Exercise temp)
    {
        readData();

        this.exercise = temp;

        initFrame();
        initLeftFrame();
        initQuestions();

        this.setVisible(true);
    }

    private void initLeftFrame()
    {
        pnleftFrame = new JPanel();
        pnleftFrame.setBounds(10,10,200,600);
        pnleftFrame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnleftFrame.setLayout(null);

        lbtimeRemain = new JLabel("Thoi gian con lai: ");
        lbtimeRemain.setBounds(10,0,180,100);
        
        lbQuestionFinished = new JLabel("So cau da hoan thanh: " + questionsFinish + "/" + questionSum);
        lbQuestionFinished.setBounds(10,50,180,100);

        btnSubmit = new JButton("Nop bai som");
        btnSubmit.setBounds(10,550,180,30);
        btnSubmit.setFocusable(false);
        btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(this);

        pnleftFrame.add(lbtimeRemain);
        pnleftFrame.add(lbQuestionFinished);
        pnleftFrame.add(btnSubmit);
        
        this.add(pnleftFrame);
    }

    private void initQuestions()
    {
        ArrayList<Question> questionsList = exercise.getQuestions();

        for(int i = 0 ; i < 1; i++)
        {
            initQuestionFrame(i, questionsList.get(i));
        }
    }

    private void initQuestionFrame(int index, Question q)
    {
        pnQuestion = new JPanel();
        pnQuestion.setBounds(215, 10, 800, 600);
        pnQuestion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnQuestion.setLayout(null);

        JLabel lbQuestionID = new JLabel(String.format("Câu %d:", index + 1)),
               llbQuestionTitle = new JLabel(q.getQuestionTitle()),
               lbAnswer[] = new JLabel[4];
    
        JRadioButton rbtnAnswer[] = new JRadioButton[4];

        ButtonGroup bg = new ButtonGroup();

        ArrayList<Pair<String, Boolean>> answerList = q.getAnswerList();

        //Set bounds
        lbQuestionID.setBounds(10,10,50,20);
        llbQuestionTitle.setBounds(50,10,400,20);

        pnQuestion.add(lbQuestionID);
        pnQuestion.add(llbQuestionTitle);

        for(int i = 0; i < 4 ; i++)
        {
            rbtnAnswer[i] = new JRadioButton();
            rbtnAnswer[i].setBounds(10, 50 * (i + 1),20,15);
            
            JLabel temp = new JLabel( (char) ('A' + i) + ":");
            temp.setBounds(30,50 * ( i + 1), 40,15);

            lbAnswer[i] = new JLabel(answerList.get(i).getFirst());
            lbAnswer[i].setBounds(50, 50 * (i + 1), 300, 15);

            pnQuestion.add(rbtnAnswer[i]);
            pnQuestion.add(temp);
            pnQuestion.add(lbAnswer[i]);
            bg.add(rbtnAnswer[i]);
        }

        this.add(pnQuestion);
    }

    private void initFrame()
    {
        this.setBounds(250, 100, 1050,650);
        this.setResizable(false);

        this.setTitle("Test");

        this.setTitle(exercise.getTitle());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\questionBank.png");
        this.setIconImage(icon.getImage());
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btnSubmit)
        {
            if(exerciseTimeRemain > 0)
            {
                int option = JOptionPane.showConfirmDialog(null, "Chưa hết thời gian làm bài, bạn có muốn nộp bài sớm ?","Thông báo", JOptionPane.OK_CANCEL_OPTION);
            }
        }   
    }
    
    public static void main(String[] args) 
    {
        ExerciseManager.readData();

        new DoExercise(ExerciseManager.getExerciseByTitle("123"));
    }
}
