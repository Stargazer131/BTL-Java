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
import javax.swing.JTextField;
import javax.swing.KeyStroke;

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
        initKeyBindings();
        this.setVisible(true);
    }

    private void initFrame()    // create the main window
    {
        this.setBounds(600, 200, 350, 300);
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
    }

    private void initComponents()
    {
        ImageIcon iconId = new ImageIcon("resources\\images\\ProfileIcon\\Id.png");   // Id label    
        lblId = new JLabel("Mã GV", resizeImage(iconId), JLabel.LEFT);
        lblId.setHorizontalTextPosition(JLabel.RIGHT);                 
        lblId.setBounds(50, 60, 80, 30);
        
        ImageIcon iconName = new ImageIcon("resources\\images\\ProfileIcon\\Name.png");   // Name label      
        lblName = new JLabel("Họ tên", resizeImage(iconName), JLabel.LEFT);
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

        btnFinish = new JButton("Hoàn thành");     // create finish button
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
            JOptionPane.showMessageDialog(this, "Không được bỏ trống bất kỳ ô nào",
            "Lỗi", JOptionPane.WARNING_MESSAGE);
        }

        else
        {
            id = id.toUpperCase();  // format the data
            name = Formatter.formatName(name);
            
            if(TeacherManager.findTeacherById(id) != null) // if the id has already been registered
            {
                JOptionPane.showMessageDialog(this, "Mã GV này đã được đăng ký",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
    
            else // create new admin account
            {   
                AccountManager.addAccount(new Account(username, password, id, false)); // add account to database
                TeacherManager.addTeacher(new Teacher(id, name)); // add teacher to database
    
                JOptionPane.showMessageDialog(this, "Đăng ký thành công", // show message
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
