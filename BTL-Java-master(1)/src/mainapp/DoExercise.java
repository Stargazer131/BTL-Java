package mainapp;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.GridBagConstraints;
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
                exerciseTimeRemain,
                indexOfQuestionDisplay = 0;

    private ArrayList<Question> questions;

    private ArrayList<JPanel> questionShow = new ArrayList<>();

    private JPanel pnleftFrame,
                   pnQuestion = new JPanel(),
                   pnQuestionList,
                   currentQuestion;

    private JButton btnSubmit,
                    btnPreQuestion,
                    btnNextQuestion;

    private void readData()
    {
        this.questions = exercise.getQuestions();
        this.exerciseTime = 5 * 60;
        this.questionsFinish = 0;
        this.exerciseTimeRemain = exerciseTime;
        //this.questions = exercise.getQuestions();
        //this.questionSum = questions.size();
        this.questionSum = 0;
    }
    
    public DoExercise(Exercise temp)
    {   
        this.exercise = temp;
        readData();

        initFrame();
        initLeftFrame();

        this.setVisible(true);
    }

    private void initQuestionListPanel()
    {
        pnQuestion.setBounds(220, 10, 800, 600);
        pnQuestion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnQuestion.setLayout(new GridBagLayout());

        pnQuestionList = new JPanel();
        pnQuestionList.setBounds(10,150,200,(questions.size() / 5 + 1 ) * 50);
        pnQuestionList.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        for(int i = 0 ; i < questions.size(); i++)
        {
            gbc.gridx = i % 5;
            gbc.gridy = i / 5;

            JButton btnTemp = new JButton(i + 1 + "");
            btnTemp.setFont(new Font("Arial",100,10));
            btnTemp.setPreferredSize(new Dimension(40,40));
            btnTemp.setMargin(new Insets(5,5,5,5));
            pnQuestionList.add(btnTemp, gbc);

            initQuestionFrame(i, questions.get(i));
        }

        System.out.println(pnQuestionList);

        this.add(pnQuestionList);
        this.add(pnQuestion);

        questionShow.get(indexOfQuestionDisplay).setVisible(true);
        currentQuestion = questionShow.get(indexOfQuestionDisplay);

    }

    private void initLeftFrame()
    {
        pnleftFrame = new JPanel();
        pnleftFrame.setBounds(9,10,205,600);
        pnleftFrame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnleftFrame.setLayout(null);

        lbtimeRemain = new JLabel("Thời gian còn lại: ");
        lbtimeRemain.setBounds(10,0,180,100);
        
        lbQuestionFinished = new JLabel("Số câu hỏi đã hoàn thành: " + questionsFinish + "/" + questionSum);
        lbQuestionFinished.setBounds(10,50,180,100);

        btnSubmit = new JButton("Nộp bài sớm");
        btnSubmit.setBounds(10,550,180,30);
        btnSubmit.setFocusable(false);
        btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(this);

        initQuestionListPanel();

        pnleftFrame.add(lbtimeRemain);
        pnleftFrame.add(lbQuestionFinished);
        pnleftFrame.add(btnSubmit);
        
        this.add(pnleftFrame);
    }

    private void initQuestionFrame(int index, Question q)
    {
        JPanel pntemp = new JPanel();
        pntemp.setPreferredSize(new Dimension(770,590));
        pntemp.setLayout(null);

        JLabel lbQuestionID = new JLabel(String.format("Câu %d:", index + 1)),
               llbQuestionTitle = new JLabel(q.getQuestionTitle()),
               lbAnswer[] = new JLabel[4];
    
        JRadioButton rbtnAnswer[] = new JRadioButton[4];

        ButtonGroup bg = new ButtonGroup();

        ArrayList<Pair<String, Boolean>> answerList = q.getAnswerList();

        //Set bounds
        lbQuestionID.setBounds(10,10,50,20);
        llbQuestionTitle.setBounds(50,10,400,20);

        pntemp.add(lbQuestionID);
        pntemp.add(llbQuestionTitle);

        for(int i = 0; i < 4 ; i++)
        {
            rbtnAnswer[i] = new JRadioButton();
            rbtnAnswer[i].setBounds(10, 50 * (i + 1),20,15);
            
            JLabel temp = new JLabel( (char) ('A' + i) + ":");
            temp.setBounds(30,50 * ( i + 1), 40,15);

            lbAnswer[i] = new JLabel(answerList.get(i).getFirst());
            lbAnswer[i].setBounds(50, 50 * (i + 1), 300, 15);

            pntemp.add(rbtnAnswer[i]);
            pntemp.add(temp);
            pntemp.add(lbAnswer[i]);
            bg.add(rbtnAnswer[i]);
        }

        pntemp.setVisible(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        pnQuestion.add(pntemp,gbc);
        questionShow.add(pntemp);

        this.add(btnPreQuestion);
        this.add(btnNextQuestion);
    }

    private void initFrame()
    {
        //Nút câu hỏi trước
        btnPreQuestion = new JButton("Câu trước");
        btnPreQuestion.setFocusable(false);
        btnPreQuestion.setBounds(230,500,100,30);
        btnPreQuestion.addActionListener(this);

        //Nút câu hỏi tiếp theo
        btnNextQuestion = new JButton("Câu sau");
        btnNextQuestion.setFocusable(false);
        btnNextQuestion.setBounds(900,500,100,30);
        btnNextQuestion.addActionListener(this);

        if(indexOfQuestionDisplay == 0)
        {
            btnPreQuestion.setVisible(false);
        }
        else
            btnPreQuestion.setVisible(true);

        if(indexOfQuestionDisplay == questions.size() - 1)
        {
            btnNextQuestion.setVisible(false);
        }
        else
            btnNextQuestion.setVisible(true);

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
        else if(e.getSource() == btnPreQuestion)
        {
            indexOfQuestionDisplay--;
            currentQuestion.setVisible(false);
            currentQuestion = questionShow.get(indexOfQuestionDisplay);
            currentQuestion.setVisible(true);
            updatePanel(pnQuestion);
        }
        else if(e.getSource() == btnNextQuestion)
        {
            indexOfQuestionDisplay++;
            currentQuestion.setVisible(false);
            currentQuestion = questionShow.get(indexOfQuestionDisplay);
            currentQuestion.setVisible(true);
            updatePanel(pnQuestion);
        }
    }

    private void updatePanel(JPanel temp)
    {
        this.revalidate(); 
        this.repaint();

        if(indexOfQuestionDisplay == 0)
        {
            btnPreQuestion.setVisible(false);
        }
        else
            btnPreQuestion.setVisible(true);

        if(indexOfQuestionDisplay == questions.size() - 1)
        {
            btnNextQuestion.setVisible(false);
        }
        else
            btnNextQuestion.setVisible(true);
    }
    
    public static void main(String[] args) 
    {
        ExerciseManager.readData();

        new DoExercise(ExerciseManager.getExerciseByTitle("123"));
    }
}
