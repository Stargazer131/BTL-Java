package mainapp;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.w3c.dom.css.RGBColor;

public class QuestionBank extends JFrame
{
    public QuestionBank()
    {
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

    private void initmainPanel()
    {
        JPanel panelMain = new JPanel();

        JScrollPane scrollPane = new JScrollPane(panelMain,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(175, 0, 700, 650);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane.setBackground(Color.red);

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
