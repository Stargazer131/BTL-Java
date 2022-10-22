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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import manager.AccountManager;
import utility.Checker;

public class RegisterFrame extends JFrame implements ActionListener 
{
    private JPanel panelUp;              // for the top size
    private JLabel lblUserName, lblPassWord;
    private JTextField txtUserName;
    private JPasswordField txtPassWord;
    
    private JPanel panelDown;   // for the down size
    private JButton btnNext;
    
    public RegisterFrame()
    {
        initFrame();
        initPanelUp();
        initPanelDown();
        this.setVisible(true);
    }

    private void initFrame() // create the main window
    {
        this.setBounds(600, 200, 350, 300);
        this.setTitle("Dang ky");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    private void initPanelUp() 
    {
        panelUp = new JPanel();
        panelUp.setBounds(0, 0, 350, 200);    // create the top panel
        panelUp.setLayout(null);

        
        ImageIcon iconUserName = new ImageIcon("resources\\images\\ProfileIcon\\UserName.png");  // create label and add icon to it
        lblUserName = new JLabel("Tai khoan", resizeImage(iconUserName), JLabel.CENTER);                              
        lblUserName.setHorizontalTextPosition(JLabel.RIGHT);
        lblUserName.setBounds(40, 80, 100, 30);
        

        ImageIcon iconPassword = new ImageIcon("resources\\images\\ProfileIcon\\Password.png");
        lblPassWord = new JLabel("Mat khau", resizeImage(iconPassword), JLabel.CENTER);    
        lblPassWord.setHorizontalTextPosition(JLabel.RIGHT);
        lblPassWord.setBounds(40, 130, 100, 30);
               

        txtUserName = new JTextField();      // create text field and add tool tip text 
        txtUserName.setToolTipText("Chi co chu so va chu cai, do dai tu 4 den 20");
        txtUserName.setBorder(BorderFactory.createLoweredBevelBorder()); 
        txtUserName.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtUserName.setBounds(150, 80, 150, 30);
        
        txtPassWord = new JPasswordField();
        txtPassWord.setToolTipText("Co it nhat 1 chu cai thuong, 1 chu cai hoa, 1 chu so, do dai lon hon 5 va khong co ky tu dac biet");
        txtPassWord.setBorder(BorderFactory.createLoweredBevelBorder());
        txtPassWord.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtPassWord.setBounds(150, 130, 150, 30);

        
        panelUp.add(lblUserName);      // add components
        panelUp.add(lblPassWord);
        panelUp.add(txtUserName);
        panelUp.add(txtPassWord);
        this.add(panelUp);
    }

    private void initPanelDown() 
    {
        panelDown = new JPanel();       // create the bottom panel                
        panelDown.setBounds(0, 200, 350, 100);
        panelDown.setLayout(null);
        
        btnNext = new JButton("Tiep");                                     // create the button
        btnNext.setFocusable(false);
        btnNext.setBounds(115, 15, 100, 30);
        btnNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));   // change the cursor
        btnNext.addActionListener(this);

        panelDown.add(btnNext);
        this.add(panelDown);
    }


    @Override
    public void actionPerformed(ActionEvent e)        // when click the buttons
    {
        if(e.getSource() == btnNext)                 
        {
            createAccount();
        }
    }

    private void createAccount()
    {
        AccountManager.readData();
        String username = txtUserName.getText();  // get data from the keyboard
        String password = String.valueOf(txtPassWord.getPassword());
        if(username.equals("") || password.equals(""))  // when don't type anything
        {
            JOptionPane.showMessageDialog(this, "Tai khoan hoac Mat khau khong duoc de trong!",
            "Error", JOptionPane.WARNING_MESSAGE);
        }

        else if(!Checker.isUsernameValid(username)) // if username is invalid
        {
            JOptionPane.showMessageDialog(this, "Tai khoan khong hop le!",
            "Error", JOptionPane.ERROR_MESSAGE);
        }

        else if(!Checker.isPasswordValid(password)) // if password is invalid
        {
            JOptionPane.showMessageDialog(this, "Mat khau khong hop le!",
            "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        else if(AccountManager.hasUser(username))        // the user is already existed
        {
            JOptionPane.showMessageDialog(this, "Ten tai khoan da ton tai!",
            "Error", JOptionPane.ERROR_MESSAGE);
        }

        else
        {
            this.dispose();
            if(username.startsWith("gvptit"))  // create teacher (admin) account 
            {
                new TeacherInfoFrame(username, password);
            }

            else      // create student account
            {
                new StudentInfoFrame(username, password);
            }
        }
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }

    // public static void main(String[] args) { // start the frame directly
    //     java.awt.EventQueue.invokeLater(new Runnable() {
    //           public void run() {
    //                new RegisterFrame();
    //           }
    //     });
    // } 
}