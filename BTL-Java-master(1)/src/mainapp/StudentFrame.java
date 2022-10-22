package mainapp;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import generic.Triplet;
import manager.StudentManager;

public class StudentFrame extends JFrame implements ActionListener
{
    private JPanel infoPanel; 
    private JLabel lblAvatar, lblId, lblName, lblGender, lblBirthday;
    private JLabel lblAddress, lblGroup, lblEmail, lblPhoneNumber;


    private JScrollPane scrollPane;
    private JPanel tableOfClassrooms;
    private ArrayList<Triplet<JButton, String, String>> classroomButtonList;

    private JButton btnJoin, btnRefresh;
    private String studentId;


    public StudentFrame(String studentId)
    {
        this.studentId = studentId;
        StudentManager.readData();
        classroomButtonList = new ArrayList<>();
        initFrame();
        initAvatar();
        initInfoPanel();
        initTable();
        initButtons();
        this.setVisible(true);
    }

    private void initFrame()             // create the main frame
    {
        this.setBounds(200, 50, 1200,700);
        this.setResizable(false);
        this.setTitle("E-Classroom");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    private void initAvatar()
    {
        String gender = StudentManager.findStudentById(studentId).getGender(); // create avatar for student
        gender = (gender.equals("Nam")) ? "Male" : "Female";
        ImageIcon icon = new ImageIcon(String.format("resources\\images\\Avatar\\%s.png", gender));    
        lblAvatar = new JLabel(icon, JLabel.CENTER);
        lblAvatar.setBounds(0, 0, 200, 200);
        lblAvatar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(lblAvatar);
    }

    private void initInfoPanel()      // create the up panel which contains student information
    {
        infoPanel = new JPanel();
        infoPanel.setBounds(200, 0, 1000, 200);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoPanel.setLayout(new GridLayout(2, 4));

        Border border = BorderFactory.createRaisedSoftBevelBorder();
        
        ImageIcon iconId = new ImageIcon("resources\\images\\ProfileIcon\\Id.png");         // display student information
        lblId = new JLabel("Ma SV: " + StudentManager.findStudentById(studentId).getId(), resizeImage(iconId), JLabel.CENTER);                  
        lblId.setVerticalTextPosition(JLabel.BOTTOM);
        lblId.setHorizontalTextPosition(JLabel.CENTER);
        lblId.setBorder(border);
        

        ImageIcon iconName = new ImageIcon("resources\\images\\ProfileIcon\\Name.png");
        lblName = new JLabel("Ho ten: " + StudentManager.findStudentById(studentId).getName(), resizeImage(iconName), JLabel.CENTER); 
        lblName.setVerticalTextPosition(JLabel.BOTTOM);
        lblName.setHorizontalTextPosition(JLabel.CENTER);
        lblName.setBorder(border);
        
        
        ImageIcon iconGender = new ImageIcon("resources\\images\\ProfileIcon\\Gender.png");
        lblGender = new JLabel("Gioi tinh: " + StudentManager.findStudentById(studentId).getGender(), resizeImage(iconGender), JLabel.CENTER);
        lblGender.setVerticalTextPosition(JLabel.BOTTOM);
        lblGender.setHorizontalTextPosition(JLabel.CENTER);
        lblGender.setBorder(border);

        
        ImageIcon iconAddress = new ImageIcon("resources\\images\\ProfileIcon\\Address.png");
        lblAddress = new JLabel("Dia chi: " + StudentManager.findStudentById(studentId).getAddress(), resizeImage(iconAddress), JLabel.CENTER);
        lblAddress.setVerticalTextPosition(JLabel.BOTTOM);
        lblAddress.setHorizontalTextPosition(JLabel.CENTER);
        lblAddress.setBorder(border);


        ImageIcon iconEmail = new ImageIcon("resources\\images\\ProfileIcon\\Email.png");
        lblEmail = new JLabel("Email: " + StudentManager.findStudentById(studentId).getEmail(), resizeImage(iconEmail), JLabel.CENTER);
        lblEmail.setVerticalTextPosition(JLabel.BOTTOM);
        lblEmail.setHorizontalTextPosition(JLabel.CENTER);
        lblEmail.setBorder(border);


        ImageIcon iconGroup = new ImageIcon("resources\\images\\ProfileIcon\\Group.png");
        lblGroup = new JLabel("Lop: " + StudentManager.findStudentById(studentId).getGroup(), resizeImage(iconGroup), JLabel.CENTER);
        lblGroup.setVerticalTextPosition(JLabel.BOTTOM);
        lblGroup.setHorizontalTextPosition(JLabel.CENTER);
        lblGroup.setBorder(border);

        
        ImageIcon iconBirthday = new ImageIcon("resources\\images\\ProfileIcon\\Birthday.png");
        lblBirthday = new JLabel("Ngay sinh: " + StudentManager.findStudentById(studentId).getBirthday(), resizeImage(iconBirthday), JLabel.CENTER);
        lblBirthday.setVerticalTextPosition(JLabel.BOTTOM);
        lblBirthday.setHorizontalTextPosition(JLabel.CENTER);
        lblBirthday.setBorder(border);


        ImageIcon iconPhoneNumber = new ImageIcon("resources\\images\\ProfileIcon\\PhoneNumber.png");        
        lblPhoneNumber = new JLabel("SDT: " + StudentManager.findStudentById(studentId).getPhoneNumber(), resizeImage(iconPhoneNumber), JLabel.CENTER);
        lblPhoneNumber.setVerticalTextPosition(JLabel.BOTTOM);
        lblPhoneNumber.setHorizontalTextPosition(JLabel.CENTER);
        lblPhoneNumber.setBorder(border);                             // end of display


        infoPanel.add(lblId);
        infoPanel.add(lblName);
        infoPanel.add(lblGender);
        infoPanel.add(lblAddress);
        infoPanel.add(lblEmail);
        infoPanel.add(lblGroup);
        infoPanel.add(lblBirthday);
        infoPanel.add(lblPhoneNumber);

        this.add(infoPanel);
    }

    private void initTable()  // create the table of classroom
    {
        tableOfClassrooms = new JPanel();
        tableOfClassrooms.setLayout(new GridLayout(0,5, 50, 50));
        
        initClassroomButtons();

        scrollPane = new JScrollPane(tableOfClassrooms, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane.setBounds(150, 200, 1040, 465);

        this.add(scrollPane);
    }

    private void initClassroomButtons() // classroom button setting
    {
        for(String id : StudentManager.findStudentById(studentId).getListClassroom())
        {
            newButton(id);
        }

        Collections.sort(classroomButtonList);
        for(Triplet<JButton, String, String> btn : classroomButtonList)
        {
            tableOfClassrooms.add(btn.getFirst());
        }
    }

    private void initButtons() // create the refresh and join buttons
    {
        btnJoin = new JButton("Tham gia");  // join button
        btnJoin.setFocusable(false);
        btnJoin.setBounds(20, 350, 100, 30);
        btnJoin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnJoin.addActionListener(this);

        btnRefresh = new JButton("Refresh");  // refresh button
        btnRefresh.setFocusable(false);
        btnRefresh.setBounds(20, 450, 100, 30);
        btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(this);
        
        this.add(btnJoin);
        this.add(btnRefresh);
    }

    private void newButton(String id) // create a new button
    {
        String name = "test";
        JButton btn = new JButton(id);                                
        btn.setFocusable(false);
        btn.setPreferredSize(new Dimension(0, 150));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this);
        
        btn.setIcon(resizeImage(new ImageIcon("resources\\images\\Logo\\Classroom.png")));
        btn.setHorizontalTextPosition(JButton.CENTER);
        btn.setVerticalTextPosition(JButton.TOP);
        classroomButtonList.add(new Triplet<>(btn, name, id));
    }

    @Override
    public void actionPerformed(ActionEvent e)  // when clicked
    {
        if(e.getSource() == btnJoin) // when click button join
        {
            String input = JOptionPane.showInputDialog(this, "Nhap ma lop hoc", 
            "E-Classroom", JOptionPane.INFORMATION_MESSAGE);
            
            if(input != null && !input.equals(""))
            {
                StudentManager.addNewClassroom(studentId, input);
            }
        }

        else if(e.getSource() == btnRefresh) // when click button refresh
        {
            tableOfClassrooms.removeAll(); // delete all
            classroomButtonList.clear();
            initClassroomButtons(); // add all again
            tableOfClassrooms.revalidate(); 
            tableOfClassrooms.repaint();
        }
        
        else
        {

        }
    } 

    private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }

    public static void main(String[] args) { // start the frame directly
        java.awt.EventQueue.invokeLater(new Runnable() {
              public void run() {   
                new StudentFrame("B20DCCN228");
              }
        });
    }
}