package mainapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import entity.Question;
import manager.QuestionManager;

public class QuestionBank extends JFrame implements ActionListener
{
    private JPanel panelMain;

    private GridBagConstraints gbc;

    private JButton btnTurnBack,
                    btnUpdateQuestion,
                    btnCreateAnExcercise;

    ArrayList<Question> questions;

    public QuestionBank()
    {
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

    private void createQuestionPanel(int index)
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

        JTextField tfQuestionTitle = new JTextField(),
                   tfAnswer [] = new JTextField [4];

        lbQuestionID.setBounds(10,10,70,40);
        panelTemp.add(lbQuestionID);

        ButtonGroup bg = new ButtonGroup();

        panelTemp.add(tfQuestionTitle);
        tfQuestionTitle.setBounds(80,20,300,20);

        for(int i = 0 ; i < 4; i++)
        {
            lbAnswer[i] = new JLabel( (char)('A' + i)  + ":");
            lbAnswer[i].setBounds(30, 50 * (i + 1),30,20);

            rbtn[i] = new JRadioButton();
            rbtn[i].setBounds(10, 50 * (i + 1),20,20);

            tfAnswer[i] = new JTextField();
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
        panelTemp.add(btnDeleteQuestion);

        lbContainer.add(panelTemp);
        panelMain.add(lbContainer, gbc);
    }

    private void initmainPanel()
    {
        this.gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,0,5,0);
        
        panelMain = new JPanel();
        panelMain.setOpaque(true);
        panelMain.setBackground(Color.white);
        panelMain.setLayout(new GridBagLayout());

        for(int i = 0 ; i < 10; i++)
            createQuestionPanel(i);

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

    private void collectData()
    {
        Component listComponent[] = panelMain.getComponents();
        for(int i = 0 ; i < listComponent.length; i++)
        {
            Component panelComponent = ((JLabel) listComponent[i]).getComponent(0),
                      elementList[] = ((JPanel) panelComponent).getComponents();
            
            String questionID = ( (JLabel) elementList[0] ).getText(),
                   questionTitle = ( (JTextField) elementList[1] ).getText(),
                   questionAnswer[] = new String[4],
                   questionAnswerKey = "";

            HashMap<String,Boolean>answerKeys = new HashMap<>();
            int index = 2;
            
            for(int x = 0 ; x < 4 ; x++)
            {
                boolean isChoose = ( (JRadioButton) elementList[index] ).isSelected();
                index += 2;
                questionAnswer[x] = ( (JTextField) elementList[index++] ) .getText();

                if(isChoose)
                {
                    answerKeys.put(questionAnswer[x], true);
                    questionAnswerKey = questionAnswer[x];
                }    
                else
                    answerKeys.put(questionAnswer[x],false);
            }
            QuestionManager.addQuestion(new Question(questionID, questionTitle, answerKeys, questionAnswerKey));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btnUpdateQuestion)
        {
            collectData();
        }
    }

    public static void main(String[] args) 
    {
        QuestionManager.readData();

        new QuestionBank();
    }
}
