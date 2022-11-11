package mainapp.teacher;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.Student;

public class StudentInfo extends JFrame
{
    private Student student;

    public StudentInfo(Student student)
    {
        this.student = student;
        initFrame();
        initComponents();
        this.setVisible(true);
    }

    private void initFrame()
    {
        this.setSize(750, 750);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Thông tin sinh viên");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    private void initComponents()
    {
        JPanel mainPanel = new JPanel(new GridLayout(3, 3));

        String id = "Mã SV: " + student.getId();
        mainPanel.add(createLabel(id, new ImageIcon("resources\\images\\ProfileIcon\\Id.png")));

        String name = "Họ tên: " + student.getName();
        mainPanel.add(createLabel(name, new ImageIcon("resources\\images\\ProfileIcon\\Name.png")));

        String gender = "Giới tính: " + student.getGender();
        mainPanel.add(createLabel(gender, new ImageIcon("resources\\images\\ProfileIcon\\Gender.png")));

        String birthday = "Ngày sinh: " + student.getBirthday();
        mainPanel.add(createLabel(birthday, new ImageIcon("resources\\images\\ProfileIcon\\Birthday.png")));

        String avatar = student.getGender();
        avatar = (avatar.equals("Nam")) ? "Male" : "Female";
        mainPanel.add(createLabel("", new ImageIcon(String.format("resources\\images\\Avatar\\%s.png", avatar)))); 

        String address = "Địa chỉ: " + student.getAddress();
        mainPanel.add(createLabel(address, new ImageIcon("resources\\images\\ProfileIcon\\Address.png")));

        String group = "Lớp: " + student.getGroup();
        mainPanel.add(createLabel(group, new ImageIcon("resources\\images\\ProfileIcon\\Group.png")));

        String email = "Email: " + student.getEmail();
        mainPanel.add(createLabel(email, new ImageIcon("resources\\images\\ProfileIcon\\Email.png")));

        String phone = "SĐT: " + student.getPhoneNumber();
        mainPanel.add(createLabel(phone, new ImageIcon("resources\\images\\ProfileIcon\\PhoneNumber.png")));

        this.add(mainPanel);
    }

    private static JLabel createLabel(String text, ImageIcon icon)
    {
        icon = resizeImage(icon, 100, 100);
        JLabel label = new JLabel(icon, JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);   
        label.setText(text);
        return label;
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon, int width, int height)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }
}
