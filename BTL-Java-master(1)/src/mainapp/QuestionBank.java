package mainapp;

import java.awt.*;

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

public class QuestionBank extends JFrame
{
    private JPanel panelMain;

    private GridBagConstraints gbc;

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
        tfQuestionTitle.setBounds(70,20,300,20);

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

        JButton btnDeleteQuestion = new JButton("Xoa cau hoi");
        btnDeleteQuestion.setBounds(500,250,100,30);
        panelTemp.add(btnDeleteQuestion);

        JLabel lbteJLabel = new JLabel(index + "");
        panelMain.add(lbteJLabel,gbc);

        panelMain.add(panelTemp, gbc);
    }

    private void initmainPanel()
    {
        this.gbc = new GridBagConstraints();
        
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

    public static void main(String[] args) 
    {
        new QuestionBank();
    }
}
