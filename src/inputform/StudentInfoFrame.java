package inputform;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import entity.Account;
import entity.Student;
import manager.AccountManager;
import manager.StudentManager;
import utility.Checker;
import utility.Formatter;

public class StudentInfoFrame extends JFrame implements ActionListener
{
    private JPanel panelLeft;
    private JLabel lblId, lblName, lblGender, lblBirthday;          // for the left side
    private JTextField txtId, txtName, txtGender, txtBirthday;

    private JPanel panelRight;
    private JLabel lblAddress, lblGroup, lblEmail, lblPhoneNumber;  // for the right side
    private JTextField txtAddress, txtGroup, txtEmail, txtPhoneNumber;

    private JPanel panelDown;   // for the bottom side
    private JButton btnFinish;

    private String username, password; // username and password of the account

    public StudentInfoFrame(String username, String password)
    {
        this.username = username;
        this.password = password;
        initFrame();
        initPanelLeft();
        initPanelRight();
        initPanelDown();
        initKeyBindings();
        this.setVisible(true);
    }

    private void initFrame()    // create the main window
    {
        this.setBounds(400, 100, 700, 500);
        this.setTitle("Thông tin cá nhân");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    private void initKeyBindings() // neu bam enter 
    {
        EnterAction enterAction = new EnterAction();
        
        txtId.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        txtId.getActionMap().put("enterAction", enterAction);

        txtName.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        txtName.getActionMap().put("enterAction", enterAction);

        txtGender.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        txtGender.getActionMap().put("enterAction", enterAction);

        txtBirthday.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        txtBirthday.getActionMap().put("enterAction", enterAction);

        txtAddress.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        txtAddress.getActionMap().put("enterAction", enterAction);

        txtGroup.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        txtGroup.getActionMap().put("enterAction", enterAction);

        txtEmail.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        txtEmail.getActionMap().put("enterAction", enterAction);

        txtPhoneNumber.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        txtPhoneNumber.getActionMap().put("enterAction", enterAction);
    }

    private void initPanelLeft()  // create left size panel
    {
        panelLeft = new JPanel();
        panelLeft.setBounds(0, 0, 350, 400);
        panelLeft.setLayout(null);

        initJLabelLeft();
        initJTextLeft();
        this.add(panelLeft);
    }

    private void initJLabelLeft()   // create the left size label and add Icon to that label
    {
        ImageIcon iconId = new ImageIcon("resources\\images\\ProfileIcon\\Id.png");   // Id label    
        lblId = new JLabel("Mã SV", resizeImage(iconId), JLabel.LEFT);
        lblId.setHorizontalTextPosition(JLabel.RIGHT);
        lblId.setBounds(60, 50, 90, 30);
        
        
        ImageIcon iconName = new ImageIcon("resources\\images\\ProfileIcon\\Name.png");   // Name label      
        lblName = new JLabel("Họ tên", resizeImage(iconName), JLabel.LEFT);
        lblName.setHorizontalTextPosition(JLabel.RIGHT);
        lblName.setBounds(60, 130, 90, 30);

        ImageIcon iconGender = new ImageIcon("resources\\images\\ProfileIcon\\Gender.png");  // Gender label       
        lblGender = new JLabel("Giới tính", resizeImage(iconGender), JLabel.LEFT);
        lblGender.setHorizontalTextPosition(JLabel.RIGHT);
        lblGender.setBounds(60, 210, 90, 30);

        ImageIcon iconBirthday = new ImageIcon("resources\\images\\ProfileIcon\\Birthday.png");  // Birthday label
        lblBirthday = new JLabel("Ngày sinh", resizeImage(iconBirthday), JLabel.LEFT);
        lblBirthday.setHorizontalTextPosition(JLabel.RIGHT);
        lblBirthday.setBounds(60, 290, 90, 30);
        
        
        panelLeft.add(lblId);
        panelLeft.add(lblName);
        panelLeft.add(lblGender);
        panelLeft.add(lblBirthday);
    }

    private void initJTextLeft()   // create the left size textfield and add tool tip text
    {
        txtId = new JTextField();  // text field for Id 
        txtId.setBorder(BorderFactory.createLoweredBevelBorder());
        txtId.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtId.setBounds(150, 50, 150, 30);
        
        txtName = new JTextField(); // text field for Name
        txtName.setBorder(BorderFactory.createLoweredBevelBorder());
        txtName.setToolTipText("Họ tên hợp lệ, VD: Nguyễn Văn A");
        txtName.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtName.setBounds(150, 130, 150, 30);
        
        txtGender = new JTextField(); // text field for Gender
        txtGender.setBorder(BorderFactory.createLoweredBevelBorder());
        txtGender.setToolTipText("Nam hoặc Nữ");
        txtGender.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtGender.setBounds(150, 210, 150, 30);
        
        txtBirthday = new JTextField(); // text field for Birthday
        txtBirthday.setBorder(BorderFactory.createLoweredBevelBorder());
        txtBirthday.setToolTipText("Ngày sinh hợp lệ, VD: 10/12/2002");
        txtBirthday.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtBirthday.setBounds(150, 290, 150, 30);
        
        
        panelLeft.add(txtId); // add components
        panelLeft.add(txtName);
        panelLeft.add(txtGender);
        panelLeft.add(txtBirthday);
    }

    private void initPanelRight()   // create the right side panel   
    {
        panelRight = new JPanel();
        panelRight.setBounds(350, 0, 350, 400);
        panelRight.setLayout(null);

        initJLabelRight();
        initJTextRight();
        this.add(panelRight);
    }

    private void initJLabelRight()                   // create the right size label and add Icon to that label
    {
        ImageIcon iconAddress = new ImageIcon("resources\\images\\ProfileIcon\\Address.png");         
        lblAddress = new JLabel("Địa chỉ", resizeImage(iconAddress), JLabel.LEFT);
        lblAddress.setBounds(60, 50, 90, 30);
        lblAddress.setHorizontalTextPosition(JLabel.RIGHT);
        
        ImageIcon iconGroup = new ImageIcon("resources\\images\\ProfileIcon\\Group.png");         
        lblGroup = new JLabel("Lớp", resizeImage(iconGroup), JLabel.LEFT);
        lblGroup.setBounds(60, 130, 90, 30);
        lblGroup.setHorizontalTextPosition(JLabel.RIGHT);
        

        ImageIcon iconEmail = new ImageIcon("resources\\images\\ProfileIcon\\Email.png");         
        lblEmail = new JLabel("Email", resizeImage(iconEmail), JLabel.LEFT);
        lblEmail.setBounds(60, 210, 90, 30);
        lblEmail.setHorizontalTextPosition(JLabel.RIGHT);


        ImageIcon iconPhoneNumber = new ImageIcon("resources\\images\\ProfileIcon\\PhoneNumber.png");         
        lblPhoneNumber = new JLabel("SĐT", resizeImage(iconPhoneNumber), JLabel.LEFT);
        lblPhoneNumber.setBounds(60, 290, 90, 30);
        lblPhoneNumber.setHorizontalTextPosition(JLabel.RIGHT);
        

        panelRight.add(lblAddress);
        panelRight.add(lblGroup);
        panelRight.add(lblEmail);
        panelRight.add(lblPhoneNumber);
    }

    private void initJTextRight()                 // create the right size textfield and add tool tip text
    {
        txtAddress = new JTextField(); // text field for Address
        txtAddress.setBorder(BorderFactory.createLoweredBevelBorder());
        txtAddress.setToolTipText("Địa chỉ hợp lệ, VD: Hà Nội"); 
        txtAddress.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtAddress.setBounds(150, 50, 150, 30);
        
        txtGroup = new JTextField();   // text field for Group
        txtGroup.setBorder(BorderFactory.createLoweredBevelBorder());
        txtGroup.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtGroup.setBounds(150, 130, 150, 30);

        txtEmail = new JTextField();  // text field for Email
        txtEmail.setBorder(BorderFactory.createLoweredBevelBorder());
        txtEmail.setToolTipText("Email hợp lệ, VD: AnNV@stu.ptit.edu.vn, nguyenvanan@gmail.com, ...");
        txtEmail.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtEmail.setBounds(150, 210, 150, 30);
        
        txtPhoneNumber = new JTextField();  // text field for Phone Number
        txtPhoneNumber.setBorder(BorderFactory.createLoweredBevelBorder());
        txtPhoneNumber.setToolTipText("Số điện thoại 10 số");
        txtPhoneNumber.setFont(new Font("Afical Neue", Font.PLAIN, 13));
        txtPhoneNumber.setBounds(150, 290, 150, 30);
        
        panelRight.add(txtAddress);
        panelRight.add(txtGroup);
        panelRight.add(txtEmail);
        panelRight.add(txtPhoneNumber);
    }

    private void initPanelDown()                      // create the bottom side
    {
        panelDown = new JPanel();
        panelDown.setBounds(0, 400, 700, 100);
        panelDown.setLayout(null);

        btnFinish = new JButton("Kết thúc"); // create the button
        btnFinish.setBounds(290, 15, 120, 30);
        btnFinish.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));   // change the cursor
        btnFinish.setFocusable(false);
        btnFinish.addActionListener(this);
        panelDown.add(btnFinish);

        this.add(panelDown);
    }

    @Override
    public void actionPerformed(ActionEvent e)  // when click
    {
        if(e.getSource() == btnFinish)
        {
            createAccount();
        }    
        
    }

    private void createAccount()
    {
        StudentManager.readData();
        AccountManager.readData();

        String id = txtId.getText(), name = txtName.getText(), gender = txtGender.getText(); // get data from the keyboard
        String birthday = txtBirthday.getText(), address = txtAddress.getText(), group = txtGroup.getText();
        String email = txtEmail.getText(), phoneNumber = txtPhoneNumber.getText();


        // checking for empty field
        if(id.equals("") || name.equals("") || gender.equals("") || birthday.equals("")
          || address.equals("") || group.equals("") || email.equals("") || phoneNumber.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống bất kỳ ô nào",
            "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
        
        else
        {
            id = id.toUpperCase();              // format data
            name = Formatter.toTitle(name);
            gender = Formatter.formatGender(gender);
            birthday = Formatter.formatBirthday(birthday);
            address = Formatter.toTitle(address);
            email = email.toLowerCase();
            group = group.toUpperCase();
    
            if(!Checker.isValidGender(gender))                                // if gender is invalid
            {
                JOptionPane.showMessageDialog(this, "Giới tính không hợp lệ", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
    
            else if(!Checker.isValidBirthday(birthday))                             // if birthday is invalid
            {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
    
            else if(!Checker.isValidEmail(email))                               // if email is invalid
            {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
    
            else if(!Checker.isValidPhoneNumber(phoneNumber))                          // if phone number is invalid
            {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            }                                                                               // end of checking data
    
            else if(StudentManager.findStudentById(id) != null) // if id has already been registered
            {
                JOptionPane.showMessageDialog(this, "Mã SV này đã được đăng ký",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
    
            else // create account
            { 
                AccountManager.addAccount(new Account(username, password, id, true));  // add the account to database
                StudentManager.addStudent(new Student(id, name, gender, birthday, address, group, email, phoneNumber)); // add the student to database
                
                JOptionPane.showMessageDialog(this, "Đăng ký thành công",
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
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
    
    public class EnterAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            createAccount();
        }
    }
} 