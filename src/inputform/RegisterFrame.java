package inputform;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import manager.AccountManager;
import utility.Checker;

public class RegisterFrame extends JFrame implements ActionListener 
{
    private JPanel topPanel;  // for the top side
    private JRadioButton teacherCheckbox, studentCheckbox;
    private ButtonGroup group;
    private JLabel lblTeacher, lblStudent; 
    
    private JPanel middlePanel;              // for the middle side
    private JLabel lblUserName, lblPassWord;
    private JTextField txtUserName;
    private JPasswordField txtPassWord;
    
    private JPanel bottomPanel;   // for the bottom side
    private JButton btnNext;
    
    public RegisterFrame()
    {
        initFrame();
        initTopPanel();
        initMiddlePanel();
        initBottomPanel();
        initKeyBindings();
        this.setVisible(true);
    }

    private void initFrame() // create the main window
    {
        this.setBounds(550, 150, 400, 400);
        this.setTitle("Dang ky");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    private void initKeyBindings() // neu bam enter 
    {
        EnterAction enterAction = new EnterAction();
        
        txtPassWord.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        txtPassWord.getActionMap().put("enterAction", enterAction);

        txtUserName.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        txtUserName.getActionMap().put("enterAction", enterAction);

        teacherCheckbox.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        teacherCheckbox.getActionMap().put("enterAction", enterAction);

        studentCheckbox.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        studentCheckbox.getActionMap().put("enterAction", enterAction);
    }

    private void initTopPanel()
    {
        topPanel = new JPanel();
        topPanel.setBounds(0, 0, 400, 100);    // create the top panel
        topPanel.setLayout(null);


        ImageIcon iconStudent = new ImageIcon("resources\\images\\Avatar\\Student.png");  // create label and add icon to it
        lblStudent = new JLabel("Sinh vien", resizeImage(iconStudent, 50, 50), JLabel.CENTER);                              
        lblStudent.setHorizontalTextPosition(JLabel.RIGHT);
        lblStudent.setBounds(50, 40, 120, 50);


        studentCheckbox = new JRadioButton();
        studentCheckbox.setBounds(30, 55, 20, 20);
        studentCheckbox.setSelected(true);


        ImageIcon iconTeacher = new ImageIcon("resources\\images\\Avatar\\Admin.png");  // create label and add icon to it
        lblTeacher = new JLabel("Giao vien", resizeImage(iconTeacher, 50, 50), JLabel.CENTER);                              
        lblTeacher.setHorizontalTextPosition(JLabel.RIGHT);
        lblTeacher.setBounds(240, 40, 120, 50);


        teacherCheckbox = new JRadioButton();
        teacherCheckbox.setBounds(220, 55, 20, 20);


        group = new ButtonGroup();
        group.add(studentCheckbox);
        group.add(teacherCheckbox);


        topPanel.add(lblStudent);
        topPanel.add(studentCheckbox);
        topPanel.add(lblTeacher);
        topPanel.add(teacherCheckbox);
        
        this.add(topPanel);
    }

    private void initMiddlePanel() 
    {
        middlePanel = new JPanel();
        middlePanel.setBounds(0, 100, 400, 200);    // create the top panel
        middlePanel.setLayout(null);

        
        ImageIcon iconUserName = new ImageIcon("resources\\images\\ProfileIcon\\UserName.png");  // create label and add icon to it
        lblUserName = new JLabel("Tai khoan", resizeImage(iconUserName, 30, 30), JLabel.CENTER);                              
        lblUserName.setHorizontalTextPosition(JLabel.RIGHT);
        lblUserName.setBounds(50, 80, 100, 30);
        

        ImageIcon iconPassword = new ImageIcon("resources\\images\\ProfileIcon\\Password.png");
        lblPassWord = new JLabel("Mat khau", resizeImage(iconPassword, 30, 30), JLabel.CENTER);    
        lblPassWord.setHorizontalTextPosition(JLabel.RIGHT);
        lblPassWord.setBounds(50, 130, 100, 30);
               

        txtUserName = new JTextField();      // create text field and add tool tip text 
        txtUserName.setToolTipText("Chi co chu so va chu cai, do dai tu 4 den 20");
        txtUserName.setBorder(BorderFactory.createLoweredBevelBorder()); 
        txtUserName.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtUserName.setBounds(160, 80, 150, 30);
        

        txtPassWord = new JPasswordField();
        txtPassWord.setToolTipText("Co it nhat 1 chu cai thuong, 1 chu cai hoa, 1 chu so, do dai lon hon 5 va khong co ky tu dac biet");
        txtPassWord.setBorder(BorderFactory.createLoweredBevelBorder());
        txtPassWord.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtPassWord.setBounds(160, 130, 150, 30);

        
        middlePanel.add(lblUserName);      // add components
        middlePanel.add(lblPassWord);
        middlePanel.add(txtUserName);
        middlePanel.add(txtPassWord);
        this.add(middlePanel);
    }

    private void initBottomPanel() 
    {
        bottomPanel = new JPanel();       // create the bottom panel                
        bottomPanel.setBounds(0, 300, 400, 100);
        bottomPanel.setLayout(null);
        
        btnNext = new JButton("Tiep");                                     // create the button
        btnNext.setFocusable(false);
        btnNext.setBounds(135, 15, 100, 30);
        btnNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));   // change the cursor
        btnNext.addActionListener(this);

        bottomPanel.add(btnNext);
        this.add(bottomPanel);
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
            if(teacherCheckbox.isSelected())  // create teacher (admin) account 
            {
                new TeacherInfoFrame(username, password);
            }

            else      // create student account
            {
                new StudentInfoFrame(username, password);
            }
        }
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon, int width, int height)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }

    public class EnterAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            createAccount();
        }
    }
}