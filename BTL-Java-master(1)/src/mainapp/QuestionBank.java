package mainapp;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public class QuestionBank extends JFrame
{
    private JPanel panelMain;

    GridBagConstraints gbc;

    public QuestionBank()
    {
        JLabel lbTitle = new JLabel("Danh sach cau hoi");
        lbTitle.setFont(new Font("Arial",100,40));
        this.add(lbTitle);

        initFrame();

        initmainPanel();

        this.setVisible(true);
    }

    private void initFrame()
    {
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
        JPanel panelTemp = new JPanel();
        panelTemp.setLayout(null);
        panelTemp.setBounds(100, 40 * (index + 1), 500,300);

        JLabel lbQuestionID = new JLabel(String.format("Cau hoi %d:", index + 1)),
               lbQuestionTitle = new JLabel("Cau hoi"),
               lbAnswer[] = new JLabel [4];

        JRadioButton rbtn[] = new JRadioButton [4];

        JTextField tfQuestionTitle = new JTextField(),
                   tfAnswer [] = new JTextField [4];

        panelTemp.add(lbQuestionID);
        panelTemp.add(tfQuestionTitle);

        for(int i = 0 ; i < 4; i++)
        {
            lbAnswer[i] = new JLabel("A" + i);
            rbtn[i] = new JRadioButton();
            tfAnswer[i] = new JTextField();
            panelTemp.add(lbAnswer[i]);
            panelTemp.add(rbtn[i]);
            panelTemp.add(tfAnswer[i]);
        }

        panelMain.add(panelTemp);
    }

    private void initmainPanel()
    {
        gbc = new GridBagConstraints();
        panelMain = new JPanel();
        panelMain.setOpaque(true);
        panelMain.setBackground(Color.white);
        panelMain.setLayout(new GridBagLayout());

        createQuestionPanel(0);

        JScrollPane scrollPane = new JScrollPane(panelMain,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(175, 0, 700, 650);
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
