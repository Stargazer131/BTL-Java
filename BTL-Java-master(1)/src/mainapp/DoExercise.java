package mainapp;

import javax.swing.BorderFactory;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entity.Exercise;
import entity.Question;

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
        initQuestionFrame();

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

    private void initQuestionFrame()
    {
        JPanel pnQuestion = new JPanel();
        pnQuestion.setBounds(215, 10, 800, 600);
        pnQuestion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnQuestion.setLayout(null);

        this.add(pnQuestion);
    }

    private void initFrame()
    {
        this.setBounds(250, 100, 1050,650);
        this.setResizable(false);

        this.setTitle("Test");

        //this.setTitle(exercise.getTitle());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\questionBank.png");
        this.setIconImage(icon.getImage());
    }

    public static void main(String[] args) {
        new DoExercise(null);
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
}
