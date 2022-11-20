package mainapp.teacher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import entity.Classroom;
import entity.Student;
import entity.Teacher;
import generic.Pair;
import inputform.LogInFrame;
import launch.App;
import manager.AccountManager;
import manager.ClassroomManager;
import manager.StudentManager;
import manager.TeacherManager;
import utility.Formatter;

public class TeacherFrame extends JFrame implements ActionListener, MouseListener
{
    private JLabel lblAvatar, 
                   lblId, 
                   lblName,
                   lblChangeInfor,
                   lbLogOut;
    private JButton btnCreateClass,
                    btnChangeInfor,
                    btnLogOut,
                    btnClassRightClick;

    private JPanel panelLeft;
    
    private JScrollPane scrollPane;

    private JPanel tableOfClassrooms;

    private ArrayList<Classroom> arrLClassroom;

    private Teacher teacher;

    private GridBagConstraints gbc;

    private JPopupMenu pmClassRightClick;
    private JMenuItem mnChinhSuaLopHoc;
    private JMenuItem mnXoaLopHoc;

    public TeacherFrame(Teacher TEACHER)
    {
        ClassroomManager.readData();
        TeacherManager.readData();
        
        this.btnClassRightClick = new JButton();
        this.teacher = TEACHER;
        this.arrLClassroom = teacher.getClassRooms();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,5,10, 15); //Tạo khoảng cách giữa các phần tử

        initPopUpMenu();
        initFrame();
        initAvatar();
        initPanelLeft();
        initTable();
        this.setVisible(true);
    }

    private void initFrame()             // create the main frame
    {
        this.setBounds(250, 100, 1200,750);
        this.setResizable(false);
        this.setTitle("E-Classroom");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    private JButton createBackGroundButton(String url, String name) //Tạo các thuộc tính của 1 lớp học
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

    private String getIDofClassroomButton(JButton temp) //Lấy id của nút bấm
    {
        return ((JLabel) temp.getComponent(0)).getText();
    }

    private void deleteClass() //Xoá 1 lớp học
    {
        String idDeleteClass = getIDofClassroomButton(btnClassRightClick);
        ClassroomManager.deleteClassroom(idDeleteClass);

        int index = 0;

        for(Classroom i: arrLClassroom)
        {
            if(i.getId().equals(idDeleteClass))
            {
                arrLClassroom.remove(i);
                break;
            }
            index ++;
        }
        
        this.tableOfClassrooms.remove(btnClassRightClick);

        Component[] arrComponents = tableOfClassrooms.getComponents();
        
        for(int i = index; i < arrComponents.length - 1; i++)
        {
            gbc.gridx = i % 5;
            gbc.gridy = i / 5;
            JButton btnNewClass = (JButton) arrComponents[i];
            tableOfClassrooms.add(btnNewClass, gbc, i);
        }

        //Khởi tạo lại vị trí của nút tạo lớp mới
        tableOfClassrooms.remove(btnCreateClass);
        createNewClassButton();

        TeacherManager.writeData();

        this.teacher.setClassrooms(arrLClassroom);
        TeacherManager.addTeacher(teacher);

        updatePanel(tableOfClassrooms);
    }

    private void createClassButton(String id, String name, int i, int index) //Tạo ra 1 nút bấm lớp học mới
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

    private void createNewClassButton() //nút bấm tạo ra 1 lớp học mới
    {
        //Tạo nút bấm thêm 1 lớp mới
        gbc.gridx = arrLClassroom.size() % 5;
        gbc.gridy = arrLClassroom.size() / 5;
        btnCreateClass = createBackGroundButton("resources\\images\\Logo\\add.png", "Tạo lớp mới");
        tableOfClassrooms.add(btnCreateClass, gbc);
    }

    private void initClassroomButtons() //Tạo ra 1 nút bấm lớp học
    {
        for(int i = 0 ; i < arrLClassroom.size() ; i++)
        {
            createClassButton(arrLClassroom.get(i).getId(), arrLClassroom.get(i).getName(), i,-1);
        }
        createNewClassButton();
    }

    private void initTable()  //Tại phần chưa lớp học
    {
        JLabel lbLopHoc = new JLabel("Danh sách lớp học:");
        lbLopHoc.setFont(new Font("Arial",100,40));
        lbLopHoc.setBounds(250,0,790,75);
        this.add(lbLopHoc);

        tableOfClassrooms = new JPanel();
        tableOfClassrooms.setLayout(new GridBagLayout());
        
        initClassroomButtons();

        scrollPane = new JScrollPane(tableOfClassrooms,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(255, 80, 920, 620);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.add(scrollPane);
    }

    private void initAvatar()
    {
        ImageIcon icon = new ImageIcon("resources\\images\\Avatar\\Admin.png");  // create avatar for teacher (admin)
        lblAvatar = new JLabel(icon, JLabel.CENTER);
        lblAvatar.setBounds(0, 0, 250, 200);
        lblAvatar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(lblAvatar);
    }

    private void initPopUpMenu() //Menu chuột phải
    {
        this.pmClassRightClick = new JPopupMenu();

        mnChinhSuaLopHoc = new JMenuItem("Chỉnh sửa thông tin lớp học");
        mnChinhSuaLopHoc.addActionListener(this);
        this.pmClassRightClick.add(mnChinhSuaLopHoc);

        mnXoaLopHoc = new JMenuItem("Xóa lớp học");
        mnXoaLopHoc.addActionListener(this);
        this.pmClassRightClick.add(mnXoaLopHoc);

    }

    private void initPanelLeft()          // create the left panel
    {        
        panelLeft = new JPanel();
        panelLeft.setBounds(0, 200, 250, 465);
        panelLeft.setLayout(null);

        ImageIcon iconId = new ImageIcon("resources\\images\\ProfileIcon\\Id.png");
        lblId = new JLabel("Mã GV: " + teacher.getId(), resizeImage(iconId), JLabel.LEFT);
        lblId.setBounds(0, 0, 250, 60);
        lblId.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        ImageIcon iconName = new ImageIcon("resources\\images\\ProfileIcon\\Name.png");
        lblName = new JLabel("Họ tên: " + teacher.getName(), resizeImage(iconName), JLabel.LEFT);
        lblName.setBounds(0, 60, 250, 60);
        lblName.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        ImageIcon iconChangeInfor = new ImageIcon("resources\\images\\Logo\\information.png");
        lblChangeInfor = new JLabel("Thay đổi thông tin", resizeImage(iconChangeInfor), JLabel.LEFT);
        btnChangeInfor = new JButton();
        btnChangeInfor.add(lblChangeInfor);
        btnChangeInfor.setBounds(0, 120, 250, 60);
        btnChangeInfor.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        btnChangeInfor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnChangeInfor.addActionListener(this);

        ImageIcon iconLogOut = new ImageIcon("resources\\images\\Logo\\logout.png");
        lbLogOut = new JLabel("Đăng xuất", resizeImage(iconLogOut), JLabel.LEFT);
        btnLogOut = new JButton();
        btnLogOut.add(lbLogOut);
        btnLogOut.setBounds(0, 180, 250, 60);
        btnLogOut.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogOut.addActionListener(this);

        panelLeft.add(lblId);
        panelLeft.add(lblName);
        panelLeft.add(btnChangeInfor);
        panelLeft.add(btnLogOut);
        this.add(panelLeft);
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }

    private void changeInforClass(String newID, String newName, String oldID) //Thay đổi thông tin của lớp học
    {
        int index = 0;
        for(Component i: tableOfClassrooms.getComponents())
        {
            if(i == btnClassRightClick)
            {
                ((JLabel) ((JButton) i).getComponent(0)).setText(newID);

                //Cập nhật lại dữ liệu cho danh sách lớp
                ClassroomManager.readData();
                Classroom oldClassroom = ClassroomManager.findClassroomById(oldID);
                oldClassroom.changeInforOfClassroom(newID, newName);
                ClassroomManager.deleteClassroom(oldID);
                ClassroomManager.addClassroom(oldClassroom);

                //Cập nhật lại dữ liệu cho giáo viên
                arrLClassroom.remove(index);
                arrLClassroom.add(index,oldClassroom);
                this.teacher.setClassrooms(arrLClassroom);
                TeacherManager.addTeacher(teacher);
                
                //Cập nhật lại dữ liệu cho từng sinh viên của lớp đó
                StudentManager.readData();
                ArrayList<Pair<Student,Double>> listStudentOfThisClassroom = oldClassroom.getStudentResult();
                for(Pair<Student, Double> j: listStudentOfThisClassroom)
                {
                    Student studentChangInforClass = StudentManager.findStudentById(j.getFirst().getId());

                    System.out.println(studentChangInforClass.getId());

                    TreeMap<String, Classroom> classroomsOfStudentInThisClassroom = studentChangInforClass.getListClassroom();
                    classroomsOfStudentInThisClassroom.remove(oldID);
                    classroomsOfStudentInThisClassroom.put(newID, oldClassroom);
                }
                ClassroomManager.writeData();
                StudentManager.writeData();

                break;
            }
            index ++;
        }
    }

    //Tạo 1 lớp học mới hoặc thay đổi thông tin lớp học tuỳ vào option
    private void buttonClassMassage(String optionTitle)
    {
        JTextField tfIDClass = new JTextField(),
                       tfNameClass = new JTextField();

        Object[] input = 
        {
            "ID lớp học:", tfIDClass,
            "Tên lớp học:", tfNameClass
        };

        while(true)
        {
            int option = JOptionPane.showConfirmDialog(null, input, optionTitle, JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION)
            {
                String idClass = tfIDClass.getText(),
                        nameClass = tfNameClass.getText();

                if(idClass.equals("") || nameClass.equals("")) //1 textfield chưa được nhập
                {
                    JOptionPane.showMessageDialog(null,"Thông tin không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    if(kiemTraIDTonTai(idClass, nameClass) == 1) //Nếu ID đã tồn tại
                    {
                        if(optionTitle.equals("Thay đổi thông tin lớp học"))
                        {
                            if(getIDofClassroomButton(btnClassRightClick).equals(idClass))
                            {
                                changeInforClass(idClass, nameClass, getIDofClassroomButton(btnClassRightClick));

                                this.updatePanel(tableOfClassrooms);

                                JOptionPane.showMessageDialog(null,"Thay đổi thông tin thành công","Thành công", JOptionPane.INFORMATION_MESSAGE);

                                break;
                            }
                        }

                        JOptionPane.showMessageDialog(null,"ID lớp học này đã tồn tại",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(kiemTraIDTonTai(idClass, nameClass) == 2) //Nếu tên lớp học đã tồn tại
                    {
                        if(optionTitle.equals("Thay đổi thông tin lớp học"))
                        {
                            if(ClassroomManager.findClassroomById(getIDofClassroomButton(btnClassRightClick)).getName().equals(nameClass))
                            {
                                changeInforClass(idClass, nameClass, getIDofClassroomButton(btnClassRightClick));

                                this.updatePanel(tableOfClassrooms);

                                JOptionPane.showMessageDialog(null,"Thay đổi thông tin thành công",
                                "Thành công", JOptionPane.INFORMATION_MESSAGE);

                                break;
                            }
                        }
                        
                        JOptionPane.showMessageDialog(null,"Tên lớp học này đã tồn tại",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(kiemTraIDTonTai(idClass, nameClass) == 0) //Các điều kiện đều thoả mãn 
                    {
                        if(optionTitle.equals("Tạo lớp học"))
                        {   
                            //Cập nhật lại thông tin cho các file dữ liệu
                            Classroom temp = new Classroom(idClass, nameClass, App.teacherUser.getName());
                            this.teacher.addClassRoom(temp);
                            this.arrLClassroom = teacher.getClassRooms();
                            ClassroomManager.addClassroom(temp);
                            TeacherManager.addTeacher(teacher);

                            //Cập nhật lại danh sách các lớp
                            tableOfClassrooms.remove(btnCreateClass);
                            createClassButton(idClass,nameClass, arrLClassroom.size() - 1, -1);
                            createNewClassButton();
                        }
                        else //Thay đổi thông tin cho lớp học thành công
                        {
                            changeInforClass(idClass, nameClass, getIDofClassroomButton(btnClassRightClick));
                        }

                        this.updatePanel(tableOfClassrooms);

                        JOptionPane.showMessageDialog(null,"Thành công","Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }
                }
            else
            {
                break;
            }
        }
    }

    private void changeInforTeacher(String id) //Thay đổi account sử dụng
    {
        App.accountUser.setID(id);
        App.teacherUser = TeacherManager.findTeacherById(id);
        AccountManager.writeData();
    }

    private void changeTeacherNameOfClassroom() //Thay đổi tên giáo viên của các lớp học thuộc giáo viên này
    {
        ClassroomManager.readData();
        for(Classroom i: arrLClassroom)
        {
            Classroom temp = ClassroomManager.findClassroomById(i.getId());
            temp.changeTeacherName(App.teacherUser.getName());
            i = temp;
        }
        ClassroomManager.writeData();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btnCreateClass)
        {
            buttonClassMassage("Tạo lớp học");
        }
        else if(e.getSource() == btnChangeInfor) //Thay đổi thông tin giáo viên
        {
            JTextField tfIDTeacher = new JTextField(),
                       tfNameTeacher = new JTextField();

            Object input[] = 
            {
                "Mã GV:", tfIDTeacher,
                "Tên giáo viên:", tfNameTeacher
            };
            
            while(true)
            {
                int option = JOptionPane.showConfirmDialog(null, input, "Thay đổi thông tin", JOptionPane.OK_CANCEL_OPTION);

                if(option == JOptionPane.OK_OPTION)
                {
                    String textID = tfIDTeacher.getText().toUpperCase(),
                           textName = Formatter.toTitle(tfNameTeacher.getText());

                    if(textID.equals("") || textName.equals("")) //Lỗi còn phần chưa được ghi
                    {
                        JOptionPane.showMessageDialog(null, "Thông tin không được để trống",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    if(!TeacherManager.checkIDExist(textID)) //Nếu ID này đã tồn tại
                    {
                        if(textID.equals(this.teacher.getId())) //Nếu ID mới trùng với id của giáo viên
                        {
                            Teacher temp = new Teacher(textID, textName);
                            temp.setClassrooms(this.teacher.getClassRooms());
                            TeacherManager.replaceTeacher(this.teacher.getId(), this.teacher, temp);
                            this.teacher = temp;
                            JOptionPane.showMessageDialog(null, "Thay đổi thông tin thành công", 
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            
                            this.lblId.setText(textID);
                            this.lblName.setText(textName);
                            this.updatePanel(panelLeft);

                            //Thay đổi thông tin giáo viên
                            changeInforTeacher(textID);

                            //Đổi tên giáo viên của các lớp
                            changeTeacherNameOfClassroom();

                            break;
                        }

                        JOptionPane.showMessageDialog(null,"Mã GV này đã tồn tại",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    else //Thoả mãn điều kiện
                    {
                        Teacher temp = new Teacher(textID, textName);
                        temp.setClassrooms(this.teacher.getClassRooms());
                        TeacherManager.replaceTeacher(this.teacher.getId(), this.teacher, temp);
                        this.teacher = temp;
                        JOptionPane.showMessageDialog(null, "Thay đổi thông tin thành công", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        
                        this.lblId.setText(textID);
                        this.lblName.setText(textName);
                        this.updatePanel(panelLeft);

                        //Thay đổi thông tin giáo viên
                        changeInforTeacher(textID);

                        //Đổi tên giáo viên của các lớp
                        changeTeacherNameOfClassroom();

                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        else if(e.getSource() == mnChinhSuaLopHoc) //Chỉnh sửa thông tin lớp học
        {
            buttonClassMassage("Thay đổi thông tin lớp học");
        }
        else if(e.getSource() == mnXoaLopHoc) //Xoá lớp học
        {
            int deleteOption = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa lớp học này?","Thông báo",JOptionPane.OK_CANCEL_OPTION);
            if(deleteOption == JOptionPane.OK_OPTION)
            {
                deleteClass();

            }
        }
        else if(e.getSource() == btnLogOut) //Đăng xuất
        {
            App.teacherUser = null;
            this.dispose();
            new LogInFrame();
        }
        else //Mở 1 lớp học
        {
            String classroomID = ( (JLabel) ((JButton) e.getSource()).getComponent(0)).getText();
            this.dispose();
            new ClassroomOfTeacher(ClassroomManager.findClassroomById(classroomID) );
        }
    }

    private void updatePanel(JPanel temp)//Cập nhật lại panel
    {
        temp.revalidate(); 
        temp.repaint();
    }
    
    private int kiemTraIDTonTai(String IDCLASS, String NAMECLASS)//Kiểm tra lớp học tồn tại
    {
        if(!ClassroomManager.checkIDExist(IDCLASS))
            return 1;
        else if(!ClassroomManager.checkNAMEExist(NAMECLASS))
            return 2;
        return 0;
    }

    //Lắng nghe sự kiện của chuột
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        showPopUpMenu(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        showPopUpMenu(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
   
    }

    private void showPopUpMenu(MouseEvent e)
    {
        if(e.isPopupTrigger())//Nếu click chuột là chuột phải
        {
            this.btnClassRightClick = (JButton) e.getSource();
            pmClassRightClick.show(e.getComponent(),e.getX(),e.getY()); //Hiển thị menu chuột phải
        }
    }
    //Kết thúc phần sự kiện chuột
}
