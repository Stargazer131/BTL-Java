package mainapp.student;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import generic.Pair;
import inputform.LogInFrame;
import launch.App;
import manager.ClassroomManager;
import manager.StudentManager;
import entity.*;

public class StudentFrame extends JFrame implements ActionListener, MouseListener
{
    private JPanel infoPanel; 
    private JLabel lblAvatar, lblId, lblName, lblGender, lblBirthday;
    private JLabel lblAddress, lblGroup, lblEmail, lblPhoneNumber;
    private JScrollPane scrollPane;
    private JPanel tableOfClassrooms;

    private Student student;

    private TreeMap<String,Classroom> arrLClassroom;

    private JButton btnJoin, btnLogOut;

    GridBagConstraints gbc = new GridBagConstraints();

    private void readData(String id)
    {
        StudentManager.readData();
        ClassroomManager.readData();
        this.student = StudentManager.findStudentById(id);
        this.arrLClassroom = student.getListClassroom();
    }

    public StudentFrame(Student STUDENT)
    {
        readData(STUDENT.getId());
        
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
        lblId = new JLabel("Mã SV: " + student.getId(), resizeImage(iconId), JLabel.CENTER);                  
        lblId.setVerticalTextPosition(JLabel.BOTTOM);
        lblId.setHorizontalTextPosition(JLabel.CENTER);
        lblId.setBorder(border);
        

        ImageIcon iconName = new ImageIcon("resources\\images\\ProfileIcon\\Name.png");
        lblName = new JLabel("Họ tên: " + student.getName(), resizeImage(iconName), JLabel.CENTER); 
        lblName.setVerticalTextPosition(JLabel.BOTTOM);
        lblName.setHorizontalTextPosition(JLabel.CENTER);
        lblName.setBorder(border);
        
        
        ImageIcon iconGender = new ImageIcon("resources\\images\\ProfileIcon\\Gender.png");
        lblGender = new JLabel("Giới tính: " + student.getGender(), resizeImage(iconGender), JLabel.CENTER);
        lblGender.setVerticalTextPosition(JLabel.BOTTOM);
        lblGender.setHorizontalTextPosition(JLabel.CENTER);
        lblGender.setBorder(border);

        
        ImageIcon iconAddress = new ImageIcon("resources\\images\\ProfileIcon\\Address.png");
        lblAddress = new JLabel("Địa chỉ: " + student.getAddress(), resizeImage(iconAddress), JLabel.CENTER);
        lblAddress.setVerticalTextPosition(JLabel.BOTTOM);
        lblAddress.setHorizontalTextPosition(JLabel.CENTER);
        lblAddress.setBorder(border);


        ImageIcon iconEmail = new ImageIcon("resources\\images\\ProfileIcon\\Email.png");
        lblEmail = new JLabel("Email: " + student.getEmail(), resizeImage(iconEmail), JLabel.CENTER);
        lblEmail.setVerticalTextPosition(JLabel.BOTTOM);
        lblEmail.setHorizontalTextPosition(JLabel.CENTER);
        lblEmail.setBorder(border);


        ImageIcon iconGroup = new ImageIcon("resources\\images\\ProfileIcon\\Group.png");
        lblGroup = new JLabel("Lớp: " + student.getGroup(), resizeImage(iconGroup), JLabel.CENTER);
        lblGroup.setVerticalTextPosition(JLabel.BOTTOM);
        lblGroup.setHorizontalTextPosition(JLabel.CENTER);
        lblGroup.setBorder(border);

        
        ImageIcon iconBirthday = new ImageIcon("resources\\images\\ProfileIcon\\Birthday.png");
        lblBirthday = new JLabel("Ngày sinh: " + student.getBirthday(), resizeImage(iconBirthday), JLabel.CENTER);
        lblBirthday.setVerticalTextPosition(JLabel.BOTTOM);
        lblBirthday.setHorizontalTextPosition(JLabel.CENTER);
        lblBirthday.setBorder(border);


        ImageIcon iconPhoneNumber = new ImageIcon("resources\\images\\ProfileIcon\\PhoneNumber.png");        
        lblPhoneNumber = new JLabel("SĐT: " + student.getPhoneNumber(), resizeImage(iconPhoneNumber), JLabel.CENTER);
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

        //Kiểm tra xem trong danh sách lớp học của học sinh xem có lớp học nào không hợp lệ
        for(String i: arrLClassroom.keySet())
        {
            Classroom checkClassroom = ClassroomManager.findClassroomById(i);

            if(checkClassroom == null || !checkClassroom.getTimeCreate().equals(arrLClassroom.get(i).getTimeCreate()))
            {
                indexOfClassroomDeleted.add(i);
                continue;
            }
            else
            {
                // Classroom temp = arrLClassroom.get(i);
                // temp = checkClassroom;
                createClassButton(i, arrLClassroom.get(i).getName(), index, -1);
                index ++;
                //Cập nhật thông tin của lớp học
                //arrLClassroom.put(i, checkClassroom);
            }
        }

        //Xoá các lớp học không hợp lệ
        for(String i: indexOfClassroomDeleted)
        {
            arrLClassroom.remove(i);
        }
        
        //Cập nhật lại dữ liệu
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
        btnJoin.setBounds(45, 350, 100, 30);
        btnJoin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnJoin.addActionListener(this);

        btnLogOut = new JButton("Đăng xuất");  // join button
        btnLogOut.setFocusable(false);
        btnLogOut.setBounds(45, 450, 100, 30);
        btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogOut.addActionListener(this);

        this.add(btnJoin);
        this.add(btnLogOut);
    }

    private String getIDofClassroomButton(JButton temp)
    {
        return ((JLabel) temp.getComponent(0)).getText();
    }

    private void deleteClass(JButton temp, boolean check)
    {
        String idDeleteClass = getIDofClassroomButton(temp);

        int index = 0;

        for(String i: arrLClassroom.keySet())
        {
            if(i.equals(idDeleteClass))
            {
                arrLClassroom.remove(i);
                break;
            }
            index ++;
        }
        
        this.tableOfClassrooms.remove(temp);

        Component[] arrComponents = tableOfClassrooms.getComponents();
        
        for(int i = index; i < arrComponents.length - 1; i++)
        {
            gbc.gridx = i % 5;
            gbc.gridy = i / 5;
            JButton btnNewClass = (JButton) arrComponents[i];
            tableOfClassrooms.add(btnNewClass, gbc, i);
        }
        
        StudentManager.writeData();  

        updatePanel(tableOfClassrooms);
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

            JOptionPane.showConfirmDialog(null, input, "Tìm kiếm lớp học", JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);

            String idClassroom = tfIDclassroom.getText();
            Classroom classroomTemp = ClassroomManager.findClassroomById(idClassroom);
            if(classroomTemp != null)
            {
                if(arrLClassroom.containsKey(idClassroom))
                {
                    JOptionPane.showMessageDialog(null, "Bạn đã tham gia lớp học này", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Tham gia lớp học thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE);
                    
                    ClassroomManager.findClassroomById(idClassroom).addAnStudent(student);
                    ClassroomManager.writeData();

                    createClassButton(idClassroom, classroomTemp.getName(), arrLClassroom.size() + 1, -1);
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
        
        else if(e.getSource() == btnLogOut)
        {
            App.studentUser = null;
            this.dispose();
            new LogInFrame();
        }

        else
        {
            ClassroomManager.readData();

            String classroomID = ( (JLabel) ((JButton) e.getSource()).getComponent(0)).getText();

            Boolean checkExitsClassroom = false;
            
            Classroom openClassroom = ClassroomManager.findClassroomById(classroomID);

            if(openClassroom == null)
            {
                JOptionPane.showMessageDialog(null, "Lớp học này không tồn tại!"," Thông báo",JOptionPane.ERROR_MESSAGE);

                readData(student.getId());

                this.remove(scrollPane);
                initTable();

                updatePanel(tableOfClassrooms);

                return;
            }

            ArrayList<Pair<Student, Double>> studentList = openClassroom.getStudentResult();

            for(Pair<Student, Double> i: studentList)
            {
                if(i.getFirst().getId().equals(App.studentUser.getId()))
                {
                    this.dispose();
                    new ClassroomOfStudent(openClassroom);
                    checkExitsClassroom = true;
                    break;
                }
            }
            if(!checkExitsClassroom)
            {
                JOptionPane.showMessageDialog(null, "Bạn không phải là thành viên của lớp học này","Thông báo",JOptionPane.ERROR_MESSAGE);
                
                this.arrLClassroom.remove(classroomID);

                deleteClass((JButton) e.getSource() , true);
            }
        }
    } 

    protected void updatePanel(Object temp)
    {
        ((Component) temp).revalidate(); 
        ((Component) temp).repaint();
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        
    }

    @Override
    public void mousePressed(MouseEvent e) {

        
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}