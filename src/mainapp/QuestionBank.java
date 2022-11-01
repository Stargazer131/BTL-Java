package mainapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import entity.Classroom;
import entity.Exercise;
import entity.Question;
import generic.Pair;
import manager.ClassroomManager;
import manager.ExerciseManager;
import manager.QuestionManager;

public class QuestionBank extends JFrame implements ActionListener
{
    private Classroom classroom;

    private JPanel panelMain;

    private GridBagConstraints gbc;

    private JButton btnTurnBack,
                    btnUpdateQuestion,
                    btnCreateAnExcercise,
                    btnCreateQuestion;

    ArrayList<Question> questions;

    ArrayList<JButton> btnListDelete = new ArrayList<>();

    public QuestionBank(Classroom classroom)
    {
        this.classroom = classroom;

        initFrame();

        initmainPanel();

        this.setVisible(true);
    }

    private void initFrame()
    {
        JLabel lbTitle = new JLabel("Danh sach cau hoi");
        lbTitle.setFont(new Font("Arial",100,40));
        lbTitle.setBounds(350, 0, 1050, 40);
        this.add(lbTitle);

        btnTurnBack = new JButton("Quay lai");
        btnTurnBack.setBounds(10,10,120,30);
        btnTurnBack.addActionListener(this);
        this.add(btnTurnBack);

        btnUpdateQuestion = new JButton("Cap nhat du lieu");
        btnUpdateQuestion.setBounds(900,10,130,30);
        btnUpdateQuestion.addActionListener(this);
        this.add(btnUpdateQuestion);

        btnCreateAnExcercise = new JButton("Tao bai tap");
        btnCreateAnExcercise.setBounds(900,50,130,30);
        btnCreateAnExcercise.addActionListener(this);
        this.add(btnCreateAnExcercise);

        this.setBackground(Color.CYAN);
        this.setBounds(250, 100, 1050,650);
        this.setResizable(false);
        this.setTitle("Question bank");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\questionBank.png");
        this.setIconImage(icon.getImage());
    }

    private void deleteQuestion(int index)
    {
        Component lisComponent[] = panelMain.getComponents();
        for(int i = index ; i < lisComponent.length - 1 ; i ++)
        {
            Component temp = lisComponent[i];
            panelMain.remove(i);
            gbc.gridy = i;
            ((JLabel) ((JPanel) ((JLabel) temp).getComponent(0)).getComponent(0)).setText(String.format("Cau hoi %d:", i + 1));
            panelMain.add(temp,gbc,i);
        }
    }   

    private void createQuestionPanel(int index, Question temp, int indexAdd)
    {
        gbc.gridx = 0;
        gbc.gridy = index;

        JLabel lbContainer = new JLabel("1");
        lbContainer.setPreferredSize(new Dimension(620,300));
        lbContainer.setLayout(new GridLayout(0,1));

        JPanel panelTemp = new JPanel();
        panelTemp.setLayout(null);
        panelTemp.setBounds(40, (index) * 300 + 10 * (index + 1), 620, 300);

        JLabel lbQuestionID = new JLabel(String.format("Cau hoi %d:", index + 1)),
               lbAnswer[] = new JLabel [4];

        JRadioButton rbtn[] = new JRadioButton [4];

        JTextField tfQuestionTitle = new JTextField(temp.getQuestionTitle()),
                   tfAnswer [] = new JTextField [4];

        lbQuestionID.setBounds(10,10,70,40);
        panelTemp.add(lbQuestionID);

        ButtonGroup bg = new ButtonGroup();

        panelTemp.add(tfQuestionTitle);
        tfQuestionTitle.setBounds(80,20,300,20);

        for(int i = 0 ; i < 4; i++)
        {
            rbtn[i] = new JRadioButton();

            if(temp.getAnswerList().size() != 0)
            {
                if(temp.getAnswerList().get(i).getSecond())
                    rbtn[i].setSelected(true);

                tfAnswer[i] = new JTextField(temp.getAnswerList().get(i).getFirst());
            }
            else
            {
                tfAnswer[i] = new JTextField();
            }

            lbAnswer[i] = new JLabel( (char)('A' + i)  + ":");
            lbAnswer[i].setBounds(30, 50 * (i + 1),30,20);
            
            rbtn[i].setBounds(10, 50 * (i + 1),20,20);
            
            tfAnswer[i].setBounds(50, 50 * (i + 1), 200,20);

            panelTemp.add(rbtn[i]);
            panelTemp.add(lbAnswer[i]);
            panelTemp.add(tfAnswer[i]);

            bg.add(rbtn[i]);
        }

        JLabel lbSelectThisQuestion = new JLabel("Chon cau hoi nay:");
        lbSelectThisQuestion.setBounds(450,10,100,40);
        panelTemp.add(lbSelectThisQuestion);

        JRadioButton rSelectThisQuestion = new JRadioButton();
        rSelectThisQuestion.setBounds(560,12,20,40);
        panelTemp.add(rSelectThisQuestion);

        JButton btnDeleteQuestion = new JButton("Xoa cau hoi");
        btnDeleteQuestion.setBounds(500,250,100,30);
        btnDeleteQuestion.addActionListener(this);
        btnListDelete.add(btnDeleteQuestion);
        panelTemp.add(btnDeleteQuestion);

        lbContainer.add(panelTemp);
        if(indexAdd == -1)
            panelMain.add(lbContainer, gbc);
        else
            panelMain.add(lbContainer,gbc,indexAdd);
    }

    private void initCreateQuestion()
    {
        gbc.gridx = 0;
        gbc.gridy = panelMain.getComponents().length;

        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\add.png");

        JLabel lbCreateQuestion = new JLabel("Them cau hoi", resizeImage(icon), JLabel.CENTER);

        btnCreateQuestion = new JButton();
        btnCreateQuestion.add(lbCreateQuestion);
        btnCreateQuestion.setPreferredSize(new Dimension(620,100));
        btnCreateQuestion.addActionListener(this);

        this.panelMain.add(btnCreateQuestion,gbc);
    }

    private void initmainPanel()
    {
        this.gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,0,5,0);
        
        panelMain = new JPanel();
        panelMain.setOpaque(true);
        panelMain.setBackground(Color.white);
        panelMain.setLayout(new GridBagLayout());

        for(int i = 0 ; i < QuestionManager.questions.size(); i++)
        {
            createQuestionPanel(i , QuestionManager.questions.get(i), -1);
        }

        initCreateQuestion();

        JScrollPane scrollPane = new JScrollPane(panelMain, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(175, 40, 700, 570);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.add(scrollPane);
    }

    private void updatePanel(JPanel temp)
    {
        temp.revalidate(); 
        temp.repaint();
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }

    private void collectData(String option)
    {
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<Question> questionsOfAnExercise = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>>answerKeyOfExercise = new ArrayList<>();

        int indexOfQuestion = 0;

        Component listComponent[] = panelMain.getComponents();
        for(int i = 0 ; i < listComponent.length - 1; i++)
        {
            Component panelComponent = ((JLabel) listComponent[i]).getComponent(0),
                      elementList[] = ((JPanel) panelComponent).getComponents();
            
            String questionID = ( (JLabel) elementList[0] ).getText(),
                   questionTitle = ( (JTextField) elementList[1] ).getText(),
                   questionAnswer[] = new String[4],
                   questionAnswerKey = "";

            ArrayList< Pair<String, Boolean>>answerKeys = new ArrayList<>();

            int index = 2,
                trueAnswer = 0;
            
            for(int x = 0 ; x < 4 ; x++)
            {
                boolean isChoose = ( (JRadioButton) elementList[index] ).isSelected();
                index += 2;
                questionAnswer[x] = ( (JTextField) elementList[index++] ) .getText();

                if(isChoose)
                {
                    trueAnswer = indexOfQuestion;
                    answerKeys.add(new Pair<String,Boolean>(questionAnswer[x], true));
                    questionAnswerKey = questionAnswer[x];
                    answerKeyOfExercise.add(new Pair<Integer,Integer>(indexOfQuestion++, x));
                }    
                else
                    answerKeys.add(new Pair<String,Boolean>(questionAnswer[x], false));
            }
            Question temp = new Question(questionID, questionTitle, answerKeys, questionAnswerKey, trueAnswer);
            if( ((JRadioButton) elementList[elementList.length - 2]).isSelected() && option.equals("Tao bai tap"))
                questionsOfAnExercise.add(temp);

            questions.add(temp);
        }

        if(option.equals("Tao bai tap"))
        {
            JTextField tfExerciseTitle = new JTextField(),
                       tfExerciseTime = new JTextField();

            Object[] inputObject = {
                "Tên bài tập:", tfExerciseTitle,
                "Thời gian làm bài: ", tfExerciseTime
            };

            int optionCreateExercise = JOptionPane.showConfirmDialog(null,inputObject,"Thông báo", JOptionPane.OK_CANCEL_OPTION);
        
            if(optionCreateExercise == JOptionPane.OK_OPTION)
            {
                Exercise temp = new Exercise(tfExerciseTitle.getText(), Integer.parseInt(tfExerciseTime.getText()), questionsOfAnExercise,answerKeyOfExercise);
                ExerciseManager.addExerCise(temp);
                this.classroom.addAnExercise(temp);
                ClassroomManager.writeData();
                turnBackToClassroom();
            }
        }
        else
        {
            QuestionManager.questions = questions;
            QuestionManager.writeData();
        }

        updatePanel(panelMain);
    }

    private void turnBackToClassroom()
    {
        this.dispose();
        new ClassroomOfTeacher(ClassroomManager.findClassroomById("triet01"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btnUpdateQuestion)
        {
            collectData("");
        }
        else if(e.getSource() == btnCreateQuestion)
        {
            createQuestionPanel(panelMain.getComponents().length - 1, new Question(), panelMain.getComponents().length - 1);
            panelMain.remove(btnCreateQuestion);
            initCreateQuestion();
            updatePanel(panelMain);
        }
        else if(e.getActionCommand().equals("Xoa cau hoi"))
        {
            for(int i = 0 ; i < btnListDelete.size(); i++)
            {
                if( e.getSource() == btnListDelete.get(i))
                {
                    panelMain.remove(i);
                    deleteQuestion(i);
                    btnListDelete.remove(i);
                    updatePanel(panelMain);
                    break;
                }
            }
        }
        else if(e.getSource() == btnCreateAnExcercise)
        {
            collectData("Tao bai tap");
        }
        else if(e.getSource() == btnTurnBack)
        {
            turnBackToClassroom();
        }
    }

    public static void main(String[] args) 
    {
        QuestionManager.readData();

        new QuestionBank(null);
    }
}
