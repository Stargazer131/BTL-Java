package mainapp.student;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entity.Classroom;
import entity.Exercise;
import entity.Question;
import generic.Pair;
import launch.App;
import manager.ClassroomManager;

public class DoExercise extends JFrame implements ActionListener
{
    private Exercise exercise;

    private Classroom classroom;

    private JLabel lbtimeRemain,
                   lbQuestionFinished;

    private int questionsFinish,
                exerciseTime,
                exerciseTimeRemain,
                indexOfQuestionDisplay = 0;

    private ArrayList<Question> questions;

    private ArrayList<JPanel> questionShow = new ArrayList<>();

    private boolean exerciseFinish,
                    firstTimeDoThisExercise,
                    isSubmit;

    private JPanel pnleftFrame,
                   pnQuestion = new JPanel(),
                   pnQuestionList,
                   currentQuestion;

    private JButton btnSubmit,
                    btnPreQuestion,
                    btnNextQuestion;

    private Component[] questionPanel;

    private void readData()
    {
        this.exerciseFinish = false;

        this.questions = exercise.getQuestions();
        this.exerciseTime = 60 * exercise.getTime();
        this.questionsFinish = 0;
        this.exerciseTimeRemain = exerciseTime;
    }
    
    public DoExercise(Exercise exercise, Classroom classroom, boolean check)
    {   
        this.exercise = exercise;
        this.classroom = classroom;
        this.firstTimeDoThisExercise = check;
        this.isSubmit = false;
        readData();

        initFrame();
        initLeftFrame();

        this.setVisible(true);

        checkTimeRemain();
    }

    private void checkTimeRemain()
    {
        class TimeCounter extends Thread 
        {
            @Override
            public void run()
            {
                while(exerciseTimeRemain > 0 && !exerciseFinish)
                {
                    exerciseTimeRemain --;

                    try 
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) 
                    {
                        e.printStackTrace();
                    }

                    lbtimeRemain.setText("Thời gian còn lại: " + String.format("%02d", exerciseTimeRemain / 60) + ":" + String.format("%02d",exerciseTimeRemain % 60) );
                }

                if(exerciseTimeRemain == 0)
                {
                    JOptionPane.showConfirmDialog(null, "Đã hết thời gian làm bài!", " Thông báo", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
                    collectData();
                }
            }
        }

        new TimeCounter().start();
    }

    private void countQuestionFinished()
    {
        questionsFinish = 0;

        for(int i = 0; i < questions.size(); i++)
        {
            Component arrTemp[] = ((JPanel) questionPanel[i]).getComponents();

            int index = 2;
            for(int j = 0 ; j < 4; j++)
            {
                if( ((JRadioButton) arrTemp[index]).isSelected())
                {
                    questionsFinish++;
                    break;
                }
                index += 3;
            }
            
        }

        lbQuestionFinished.setText("Số câu hỏi đã hoàn thành: " + questionsFinish + "/" + questions.size());
    }

    private void initQuestionListPanel()
    {
        pnQuestion.setBounds(220, 10, 950, 690);
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
            btnTemp.addActionListener(this);
            btnTemp.setFocusable(false);
            pnQuestionList.add(btnTemp, gbc);

            initQuestionFrame(i, questions.get(i));
        }

        this.add(pnQuestionList);
        this.add(pnQuestion);

        questionShow.get(indexOfQuestionDisplay).setVisible(true);
        currentQuestion = questionShow.get(indexOfQuestionDisplay);

        questionPanel = pnQuestion.getComponents();//Lấy các Jpanel chứa câu hỏi
    }

    private void initLeftFrame()
    {
        pnleftFrame = new JPanel();
        pnleftFrame.setBounds(9,10,205,690);
        pnleftFrame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnleftFrame.setLayout(null);

        lbtimeRemain = new JLabel("Thời gian còn lại: " + String.format("%02d", exerciseTimeRemain / 60) + ":" + String.format("%02d",exerciseTimeRemain % 60) );
        lbtimeRemain.setBounds(10,0,180,20);
        
        lbQuestionFinished = new JLabel("Số câu hỏi đã hoàn thành: " + questionsFinish + "/" + questions.size());
        lbQuestionFinished.setBounds(10,50,180,40);

        btnSubmit = new JButton("Nộp bài sớm");
        btnSubmit.setBounds(10,650,180,30);
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
        pntemp.setPreferredSize(new Dimension(870,670));
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
            rbtnAnswer[i].addActionListener(this);
            
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
        btnPreQuestion.setBounds(230,600,100,30);
        btnPreQuestion.addActionListener(this);

        //Nút câu hỏi tiếp theo
        btnNextQuestion = new JButton("Câu sau");
        btnNextQuestion.setFocusable(false);
        btnNextQuestion.setBounds(1050,600,100,30);
        btnNextQuestion.addActionListener(this);

        hideOrShowPreAndNextButton();

        this.setBounds(250, 100, 1200,750);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setTitle("Test");

        this.setTitle(exercise.getTitle());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\questionBank.png");
        this.setIconImage(icon.getImage());

    }

    private void displayNextOrPreQuestion()
    {
        currentQuestion.setVisible(false);
        currentQuestion = questionShow.get(indexOfQuestionDisplay);
        currentQuestion.setVisible(true);
        updatePanel(pnQuestion);
    }

    private void collectData()
    {
        if(isSubmit)
        {
            this.dispose();
            new ClassroomOfStudent(classroom);

            return;
        }

        double diem = 0;
        
        int soCauDung = 0;

        questionPanel = pnQuestion.getComponents();

        for(int i = 0 ; i < questions.size(); i++)
        {
            Component arrTemp[] = ((JPanel) questionPanel[i]).getComponents();

            int index = 2;

            Question temp = questions.get(i);

            for(int j = 0 ; j < 4; j++)
            {
                if( ((JRadioButton) arrTemp[index]).isSelected() && temp.getAnswerList().get(j).getSecond())
                {
                    soCauDung++;
                    break;
                }
                index += 3;
            }
        }

        diem = Math.round( 1.0 * soCauDung / questions.size() * 100.0) / 100.0 * 10 ;

        //Cập nhật lại điểm cho sinh viên trong lơp
        if(firstTimeDoThisExercise)
        {
            classroom.studentDoExerciseResult(App.studentUser, diem);
            exercise.addAnStudentFinish(App.studentUser.getId());
            ClassroomManager.writeData();
        }
        
        int option = JOptionPane.showConfirmDialog(null, "Chúc mừng bạn đã đạt được " + String.format("%.2f",diem), "Thông báo", JOptionPane.OK_CANCEL_OPTION);

        this.dispose();
        new ClassroomOfStudent(classroom);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //Nếu bấm vào 1 JRadioButton thì sẽ đếm số câu hỏi đã click
        if(e.getSource().getClass().equals(javax.swing.JRadioButton.class))
        {
            countQuestionFinished();
        }

        if(e.getSource() == btnSubmit)
        {
            if(exerciseTimeRemain > 0)
            {
                int option = JOptionPane.showConfirmDialog(null, "Chưa hết thời gian làm bài, bạn có muốn nộp bài sớm ?","Thông báo", JOptionPane.OK_CANCEL_OPTION);
                if(option == JOptionPane.OK_OPTION)
                {
                    exerciseFinish = true;
                    collectData();
                }
            }
        }   
        else if(e.getSource() == btnPreQuestion)
        {
            indexOfQuestionDisplay--;
            displayNextOrPreQuestion();
        }
        else if(e.getSource() == btnNextQuestion)
        {
            indexOfQuestionDisplay++;
            displayNextOrPreQuestion();
        }
        else
        {
            Component arTemp[] = pnQuestionList.getComponents();
            for(int i = 0 ; i < arTemp.length; i++)
            {
                if(arTemp[i] == e.getSource())
                {
                    indexOfQuestionDisplay = i;
                    displayNextOrPreQuestion();
                    break;
                }
            }
        }
    }

    private void hideOrShowPreAndNextButton()
    {
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

    private void updatePanel(JPanel temp)
    {
        this.revalidate(); 
        this.repaint();

        hideOrShowPreAndNextButton();
    }
}
