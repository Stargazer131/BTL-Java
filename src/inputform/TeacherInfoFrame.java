package inputform;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entity.Account;
import entity.Teacher;
import manager.AccountManager;
import manager.TeacherManager;
import utility.Formatter;

public class TeacherInfoFrame extends JFrame implements ActionListener
{
    private JLabel lblId, lblName;
    private JTextField txtId, txtName;
    private JButton btnFinish;

    private String username, password;

    public TeacherInfoFrame(String username, String password)
    {
        this.username = username;
        this.password = password;
        initFrame();
        initComponents();
        this.setVisible(true);
    }

    private void initFrame()    // create the main window
    {
        this.setBounds(600, 200, 350, 300);
        this.setTitle("Thong tin ca nhan");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    private void initComponents()
    {
        ImageIcon iconId = new ImageIcon("resources\\images\\ProfileIcon\\Id.png");   // Id label    
        lblId = new JLabel("Ma GV", resizeImage(iconId), JLabel.LEFT);
        lblId.setHorizontalTextPosition(JLabel.RIGHT);                 
        lblId.setBounds(50, 60, 80, 30);
        
        ImageIcon iconName = new ImageIcon("resources\\images\\ProfileIcon\\Name.png");   // Name label      
        lblName = new JLabel("Ho ten", resizeImage(iconName), JLabel.LEFT);
        lblName.setHorizontalTextPosition(JLabel.RIGHT);
        lblName.setBounds(50, 110, 80, 30);

        txtId = new JTextField();    // create text field for Id     
        txtId.setBorder(BorderFactory.createLoweredBevelBorder());  
        txtId.setFont(new Font("Afical Neue", Font.PLAIN, 13));      
        txtId.setBounds(140, 60, 150, 30);
        
        txtName = new JTextField();   // create text field for Name
        txtName.setBounds(140, 110, 150, 30);
        txtName.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtName.setBorder(BorderFactory.createLoweredBevelBorder());

        btnFinish = new JButton("Hoan thanh");     // create finish button
        btnFinish.setBounds(110, 190, 120, 30);
        btnFinish.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // change the cursor
        btnFinish.setFocusable(false);
        btnFinish.addActionListener(this);

        this.add(lblId); // add components to the frame
        this.add(lblName);
        this.add(txtId);
        this.add(txtName);
        this.add(btnFinish);
    }

    @Override
    public void actionPerformed(ActionEvent e) // when button is clicked
    {
        if(e.getSource() == btnFinish)
        {
            createAccount();
        }        
    } 

    private void createAccount()
    {
        TeacherManager.readData();
        AccountManager.readData();
        
        String id = txtId.getText(), name = txtName.getText();   // get data from keyboard

        if(id.equals("") || name.equals("")) // if don't type anything
        {
            JOptionPane.showMessageDialog(this, "Khong duoc de trong bat ky o nao!",
            "Error", JOptionPane.WARNING_MESSAGE);
        }

        else
        {
            id = id.toUpperCase();  // format the data
            name = Formatter.formatName(name);
            
            if(TeacherManager.findTeacherById(id) != null) // if the id has already been registered
            {
                JOptionPane.showMessageDialog(this, "Ma GV nay da duoc dang ky",
                "Error", JOptionPane.ERROR_MESSAGE);
            }
    
            else // create new admin account
            {   
                AccountManager.addAccount(new Account(username, password, id)); // add account to database
                TeacherManager.addTeacher(new Teacher(id, name)); // add teacher to database
    
                JOptionPane.showMessageDialog(this, "Dang ky thanh cong!", // show message
                "Successful", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        }
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }

    public static void main(String[] args) {        // run the frame directly
        java.awt.EventQueue.invokeLater(new Runnable() {
              public void run() {
                   new TeacherInfoFrame("", "");
              }
        });
    }
}
