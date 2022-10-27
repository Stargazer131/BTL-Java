package mainapp;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import entity.Student;

public class PendingStudent extends JFrame implements ActionListener
{
    private TreeMap<String, Student> pendingStudents;

    private JPanel mainPanel;
    private JScrollPane spForPanel;
    private JLabel lblPendingStudents;

    private JButton btnAcceptStudent, 
                    btnRejectStudent;

    private GridBagConstraints gbc;

    public PendingStudent(TreeMap<String, Student> pendingStudents)
    {
        this.pendingStudents = pendingStudents;
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0,5, 0);

        initFrame();

        initButtons();
        initMainPanel();
        this.setVisible(true);
    }


    private void initFrame()
    {
        this.setBounds(200, 50, 1200, 750);
        this.setResizable(false);
        this.setTitle("E-Classroom");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    
    private void initButtons()
    {
        ImageIcon acceptIcon = new ImageIcon("resources\\images\\Logo\\accept.png");
        btnAcceptStudent = new JButton("Chap nhan vao lop");
        btnAcceptStudent.setIcon(resizeImage(acceptIcon));
        btnAcceptStudent.addActionListener(this);
        btnAcceptStudent.setFocusable(false);
        btnAcceptStudent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAcceptStudent.setBounds(600, 20, 200, 40);
        this.add(btnAcceptStudent);

        ImageIcon rejectIcon = new ImageIcon("resources\\images\\Logo\\reject.png");
        btnRejectStudent = new JButton("Tu choi vao lop");
        btnRejectStudent.setIcon(resizeImage(rejectIcon));
        btnRejectStudent.addActionListener(this);
        btnRejectStudent.setFocusable(false);
        btnRejectStudent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRejectStudent.setBounds(900, 20, 200, 40);
        this.add(btnRejectStudent);

    }

    private void initMainPanel() // tao main panel
    {
        lblPendingStudents = new JLabel("Danh sach hoc sinh cho gia nhap");
        lblPendingStudents.setFont(new Font("Arial",100,30));
        lblPendingStudents.setBounds(0, 20, 600, 40);
        this.add(lblPendingStudents);


        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        initListOfStudentPanel();

        spForPanel = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        spForPanel.setBounds(0, 75, 1185, 625);
        spForPanel.getVerticalScrollBar().setUnitIncrement(15);
        spForPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(spForPanel);
    }

    
    private void initListOfStudentPanel() // khoi tao tat ca cac panel chua thong tin sinh vien
    {
        for(int i = 0; i < 15; i++)
        {
            initStudentInfoPanel(i, null);
        }
    }
    
    private void initStudentInfoPanel(int index, Student student) // tao 1 panel chua thong tin cua sinh vien
    {
        gbc.gridx = 0;
        gbc.gridy = index;
        
        JPanel temp = new JPanel();
        temp.setPreferredSize(new Dimension(1185, 50));
        temp.setLayout(null);
        
        JRadioButton radioButton = new JRadioButton();
        radioButton.setBounds(15, 15, 20, 20);

        JLabel lblId = createLabel("resources\\images\\ProfileIcon\\Id.png", String.format("B20DCCN%03d", index)); // ma sinh vien
        lblId.setBounds(50, 0, 200, 50);

        JLabel lblName = createLabel("resources\\images\\ProfileIcon\\Name.png", String.format("Nguyen Van %02d", index)); // ho ten
        lblName.setBounds(250, 0, 275, 50);

        JLabel lblGroup = createLabel("resources\\images\\ProfileIcon\\Group.png", String.format("D20CQCN-%02db", index)); // lop
        lblGroup.setBounds(525, 0, 200, 50);

        JLabel lblPhone = createLabel("resources\\images\\ProfileIcon\\PhoneNumber.png", String.format("%09d", index));  // so dien thoai
        lblPhone.setBounds(725, 0, 150, 50);

        JLabel lblEmail = createLabel("resources\\images\\ProfileIcon\\Email.png", String.format("Nguyen Van %02d@gmail.com", index));  // email
        lblEmail.setBounds(875, 0, 325, 50);


        temp.add(radioButton);
        temp.add(lblId);
        temp.add(lblName);
        temp.add(lblGroup);
        temp.add(lblPhone);
        temp.add(lblEmail);

        mainPanel.add(temp, gbc);
    }

    private static JLabel createLabel(String url, String name)
    {
        ImageIcon iconId = new ImageIcon(url);         
        JLabel lbl = new JLabel(name, resizeImage(iconId), JLabel.LEFT);                  
        lbl.setVerticalTextPosition(JLabel.CENTER);
        lbl.setHorizontalTextPosition(JLabel.RIGHT);
        lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return lbl;
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(35, 35, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAcceptStudent)
        {

        }
        
        else if(e.getSource() == btnRejectStudent)
        {

        }
    }    
    public static void main(String[] args) {
        new PendingStudent(null);    
    }
}
