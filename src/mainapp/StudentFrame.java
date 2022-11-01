package mainapp;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.Font;

import entity.Classroom;
import entity.Student;
import generic.Triplet;
import manager.ClassroomManager;
import manager.StudentManager;
import manager.TeacherManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StudentFrame extends JFrame implements ActionListener, MouseListener
{
    private JPanel infoPanel; 
    private JLabel lblAvatar, lblId, lblName, lblGender, lblBirthday;
    private JLabel lblAddress, lblGroup, lblEmail, lblPhoneNumber;
    private JScrollPane scrollPane;
    private JPanel tableOfClassrooms;

    private Student student;

    private TreeMap<String,Classroom> arrLClassroom;

    private JButton btnJoin, btnRefresh;

    GridBagConstraints gbc = new GridBagConstraints();

    private void readData()
    {
        this.arrLClassroom = student.getListClassroom();
    }

    public StudentFrame(Student STUDENT)
    {
        StudentManager.readData();
        ClassroomManager.readData();
        this.student = STUDENT;
        readData();
        
        initFrame();
        initAvatar();
        initInfoPanel();
        initTable();
        initButtons();
        this.setVisible(true);
    }

    private void initFrame()             // create the main frame
    {
        this.setLocationRelativeTo(null);
        this.setBounds(200, 50, 1200,750);
        this.setResizable(false);
        this.setTitle("E-Classroom");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    private void initAvatar()
    {
        String gender = student.getGender(); // create avatar for student
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
        lblId = new JLabel("Ma SV: " + student.getId(), resizeImage(iconId), JLabel.CENTER);                  
        lblId.setVerticalTextPosition(JLabel.BOTTOM);
        lblId.setHorizontalTextPosition(JLabel.CENTER);
        lblId.setBorder(border);
        

        ImageIcon iconName = new ImageIcon("resources\\images\\ProfileIcon\\Name.png");
        lblName = new JLabel("Ho ten: " + student.getName(), resizeImage(iconName), JLabel.CENTER); 
        lblName.setVerticalTextPosition(JLabel.BOTTOM);
        lblName.setHorizontalTextPosition(JLabel.CENTER);
        lblName.setBorder(border);
        
        
        ImageIcon iconGender = new ImageIcon("resources\\images\\ProfileIcon\\Gender.png");
        lblGender = new JLabel("Gioi tinh: " + student.getGender(), resizeImage(iconGender), JLabel.CENTER);
        lblGender.setVerticalTextPosition(JLabel.BOTTOM);
        lblGender.setHorizontalTextPosition(JLabel.CENTER);
        lblGender.setBorder(border);

        
        ImageIcon iconAddress = new ImageIcon("resources\\images\\ProfileIcon\\Address.png");
        lblAddress = new JLabel("Dia chi: " + student.getAddress(), resizeImage(iconAddress), JLabel.CENTER);
        lblAddress.setVerticalTextPosition(JLabel.BOTTOM);
        lblAddress.setHorizontalTextPosition(JLabel.CENTER);
        lblAddress.setBorder(border);


        ImageIcon iconEmail = new ImageIcon("resources\\images\\ProfileIcon\\Email.png");
        lblEmail = new JLabel("Email: " + student.getEmail(), resizeImage(iconEmail), JLabel.CENTER);
        lblEmail.setVerticalTextPosition(JLabel.BOTTOM);
        lblEmail.setHorizontalTextPosition(JLabel.CENTER);
        lblEmail.setBorder(border);


        ImageIcon iconGroup = new ImageIcon("resources\\images\\ProfileIcon\\Group.png");
        lblGroup = new JLabel("Lop: " + student.getGroup(), resizeImage(iconGroup), JLabel.CENTER);
        lblGroup.setVerticalTextPosition(JLabel.BOTTOM);
        lblGroup.setHorizontalTextPosition(JLabel.CENTER);
        lblGroup.setBorder(border);

        
        ImageIcon iconBirthday = new ImageIcon("resources\\images\\ProfileIcon\\Birthday.png");
        lblBirthday = new JLabel("Ngay sinh: " + student.getBirthday(), resizeImage(iconBirthday), JLabel.CENTER);
        lblBirthday.setVerticalTextPosition(JLabel.BOTTOM);
        lblBirthday.setHorizontalTextPosition(JLabel.CENTER);
        lblBirthday.setBorder(border);


        ImageIcon iconPhoneNumber = new ImageIcon("resources\\images\\ProfileIcon\\PhoneNumber.png");        
        lblPhoneNumber = new JLabel("SDT: " + student.getPhoneNumber(), resizeImage(iconPhoneNumber), JLabel.CENTER);
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

    private JButton createBackGroundButton(String url, String name)
    {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        ImageIcon imgCreateClass = new ImageIcon(url);
        JLabel lbCreateClass = new JLabel(name,resizeImage(imgCreateClass), JLabel.CENTER);
        lbCreateClass.setVerticalTextPosition(JLabel.BOTTOM);
        lbCreateClass.setHorizontalTextPosition(JLabel.CENTER);
        button.add(lbCreateClass, BorderLayout.CENTER);
        button.setPreferredSize(new Dimension(130, 130));
        button.addActionListener(this);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void createClassButton(String id, String name, int i, int index)
    {
        gbc.gridx = i % 5;
        gbc.gridy = i / 5;
        JButton btnClass = createBackGroundButton("resources\\images\\Logo\\Classroom.png", id);
        btnClass.addMouseListener(this);
        if(index != -1)
            tableOfClassrooms.add(btnClass,gbc,index);
        else
            tableOfClassrooms.add(btnClass,gbc);
    }

    private void initClassroomButtons()
    {
        int index = 0 ;
        
        ArrayList<String> indexOfClassroomDeleted = new ArrayList<>();

        for(String i: arrLClassroom.keySet())
        {
            if(ClassroomManager.findClassroomById(i) == null)
            {
                indexOfClassroomDeleted.add(i);
                continue;
            }
            else
            {
                createClassButton(i, arrLClassroom.get(i).getName(), index, -1);
                index ++;
            }
        }

        for(String i: indexOfClassroomDeleted)
        {
            arrLClassroom.remove(i);
        }
        if(indexOfClassroomDeleted.size() > 0)
            StudentManager.writeData();
    }

    private void initTable()  // create the table of classroom
    {
        gbc.insets = new Insets(0,5,10, 15);

        tableOfClassrooms = new JPanel();
        tableOfClassrooms.setLayout(new GridBagLayout());
        
        initClassroomButtons();

        scrollPane = new JScrollPane(tableOfClassrooms,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(200, 210, 960, 480);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.add(scrollPane);
    }

    private void initButtons() // create the refresh and join buttons
    {
        btnJoin = new JButton("Tham gia");  // join button
        btnJoin.setFocusable(false);
        btnJoin.setBounds(20, 350, 100, 30);
        btnJoin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnJoin.addActionListener(this);

        this.add(btnJoin);
    }

    @Override
    public void actionPerformed(ActionEvent e)  // when clicked
    {
        if(e.getSource() == btnJoin) // when click button join
        {
            ClassroomManager.readData();

            JTextField tfIDclassroom = new JTextField();

            Object[] input = {
                "Id của lớp học:", tfIDclassroom
            };

            int option = JOptionPane.showConfirmDialog(null, input, "Tìm kiếm lớp học", JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);

            String idClassroom = tfIDclassroom.getText();
            Classroom classroomTemp = ClassroomManager.findClassroomById(idClassroom);
            if(classroomTemp != null)
            {
                if(arrLClassroom.containsKey(idClassroom))
                {
                    JOptionPane.showMessageDialog(null, "Bạn đã than gia lớp học này", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Tham gia lớp học thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE);
                    
                    ClassroomManager.findClassroomById(idClassroom).addAnStudent(student);
                    ClassroomManager.writeData();

                    createClassButton(idClassroom, classroomTemp.getName(), arrLClassroom.size(), -1);
                    arrLClassroom.put(idClassroom, classroomTemp);

                    Student temp = StudentManager.findStudentById(student.getId());
                    temp.addClassroomId(idClassroom, classroomTemp);
                    StudentManager.writeData();

                    updatePanel(tableOfClassrooms);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Không tìm thấy lớp học!", "Thông báo",JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            String classroomID = ( (JLabel) ((JButton) e.getSource()).getComponent(0)).getText();
            this.dispose();
            new ClassroomOfStudent(ClassroomManager.findClassroomById(classroomID) );
        }
    } 

    private void updatePanel(JPanel temp)
    {
        temp.revalidate(); 
        temp.repaint();
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}