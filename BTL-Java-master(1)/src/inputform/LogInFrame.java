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

import entity.Account;
import mainapp.StudentFrame;
import manager.AccountManager;

public class LogInFrame extends JFrame implements ActionListener
{
    private JPanel panelUp;
    private JLabel lblUserName, lblPassWord;
    private JTextField txtUserName;
    private JPasswordField txtPassWord;
    private JLabel logo;
    
    private JPanel panelDown;
    private JButton btnLogIn, btnCreateNewAccount;
    
    public LogInFrame()
    {
        initFrame();
        initPanelUp();
        initPanelDown();
        this.setVisible(true);
    }

    private void initFrame()  // create the main window
    {
        this.setBounds(600, 200, 350, 300);
        this.setTitle("E-Classroom");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    private void initPanelUp()  // create the top panel
    {
        panelUp = new JPanel();
        panelUp.setBounds(0, 0, 350, 200);                 
        panelUp.setLayout(null);

        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");  // set logo in the top panel
        logo = new JLabel(resizeImage(icon), JLabel.CENTER);
        logo.setBounds(130, 10, 90, 90);
        panelUp.add(logo);

        lblUserName = new JLabel("Username");      // create label
        lblUserName.setBounds(50, 120, 60, 20);
        
        lblPassWord = new JLabel("Password");    
        lblPassWord.setBounds(50, 160, 60, 20);
               

        txtUserName = new JTextField();        // create text field
        txtUserName.setBorder(BorderFactory.createLoweredBevelBorder()); 
        txtUserName.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtUserName.setBounds(120, 120, 150, 20);
        
        txtPassWord = new JPasswordField();
        txtPassWord.setBorder(BorderFactory.createLoweredBevelBorder());
        txtPassWord.setBounds(120, 160, 150, 20);

        panelUp.add(lblUserName);   // add components
        panelUp.add(lblPassWord);
        panelUp.add(txtUserName);
        panelUp.add(txtPassWord);
        this.add(panelUp);
    }

    private void initPanelDown()   // create the bottom panel
    {
        panelDown = new JPanel();                                                
        panelDown.setBounds(0, 200, 350, 100);
        panelDown.setLayout(null);
        

        btnLogIn = new JButton("Log in");   // create button Log in
        btnLogIn.setFocusable(false);
        btnLogIn.addActionListener(this);
        btnLogIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));      
        btnLogIn.setBounds(20, 15, 120, 30);
       
        
        btnCreateNewAccount = new JButton("New account"); // create button New account
        btnCreateNewAccount.setFocusable(false);
        btnCreateNewAccount.addActionListener(this);
        btnCreateNewAccount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCreateNewAccount.setBounds(200, 15, 120, 30);
 
        panelDown.add(btnLogIn);
        panelDown.add(btnCreateNewAccount);
        this.add(panelDown);
    }


    @Override
    public void actionPerformed(ActionEvent e)        // when click the buttons
    {
        if(e.getSource() == btnLogIn)                 // if button is LOG IN
        {
            AccountManager.readData();
            String username = txtUserName.getText();     // get data from keyboard
            String password = String.valueOf(txtPassWord.getPassword());
            Account account;
            if((account = AccountManager.findAccount(username, password)) != null)  // if the account is valid
            {
                if(username.startsWith("gvptit"))  // teacher account
                {

                }

                else  // student account
                {
                    this.dispose();
                    new StudentFrame(account.getId());
                }
            }

            else   // display the error message                
            {
                JOptionPane.showMessageDialog(this, "Username or Password is incorrect!",
                "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        else if(e.getSource() == btnCreateNewAccount)         // if button is create, open register window
        {
            new RegisterFrame();
        }
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(90, 90, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }
}