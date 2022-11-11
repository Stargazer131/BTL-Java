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
import entity.Teacher;
import inputform.LogInFrame;
import launch.App;
import manager.AccountManager;
import manager.ClassroomManager;
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

    private String getIDofClassroomButton(JButton temp)
    {
        return ((JLabel) temp.getComponent(0)).getText();
    }

    private void deleteClass()
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

    private void createNewClassButton()
    {
        //Tạo nút bấm thêm 1 lớp mới
        gbc.gridx = arrLClassroom.size() % 5;
        gbc.gridy = arrLClassroom.size() / 5;
        btnCreateClass = createBackGroundButton("resources\\images\\Logo\\add.png", "Tạo lớp mới");
        tableOfClassrooms.add(btnCreateClass, gbc);
    }

    private void initClassroomButtons()
    {
        for(int i = 0 ; i < arrLClassroom.size() ; i++)
        {
            createClassButton(arrLClassroom.get(i).getId(), arrLClassroom.get(i).getName(), i,-1);
        }
        createNewClassButton();
    }

    private void initTable()  // create the table of classroom
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

    private void initPopUpMenu()
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

    private void changeInforClass(Classroom temp, String oldID)
    {
        int index = 0;
        for(Component i: tableOfClassrooms.getComponents())
        {
            if(i == btnClassRightClick)
            {
                createClassButton(temp.getId(), temp.getName(), index, index);
                tableOfClassrooms.remove(i);

                ClassroomManager.deleteClassroom(oldID);
                ClassroomManager.addClassroom(temp);

                arrLClassroom.remove(index);
                arrLClassroom.add(index,temp);

                this.teacher.setClassrooms(arrLClassroom);

                TeacherManager.addTeacher(teacher);
                
                break;
            }
            index ++;
        }
    }

    private void buttonClassMassage(String optionTitle)
    {
        JTextField tfIDClass = new JTextField(),
                       tfNameClass = new JTextField(),
                       tfTeacherClass = new JTextField();

        Object[] input = 
        {
            "ID lớp học:", tfIDClass,
            "Tên lớp học:", tfNameClass,
            "Tên giáo viên: ", tfTeacherClass
        };

        while(true)
        {
            int option = JOptionPane.showConfirmDialog(null, input, optionTitle, JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION)
            {
                String idClass = tfIDClass.getText(),
                        nameClass = tfNameClass.getText(),
                        teachClass = tfTeacherClass.getText();

                if(idClass.equals("") || nameClass.equals("") || teachClass.equals(""))
                {
                    JOptionPane.showMessageDialog(null,"Thông tin không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    if(kiemTraIDTonTai(idClass, nameClass) == 1)
                    {
                        if(optionTitle.equals("Thay đổi thông tin lớp học"))
                        {
                            if(getIDofClassroomButton(btnClassRightClick).equals(idClass))
                            {
                                Classroom temp = new Classroom(idClass, nameClass, teachClass);
                                changeInforClass(temp, getIDofClassroomButton(btnClassRightClick));

                                this.updatePanel(tableOfClassrooms);

                                JOptionPane.showMessageDialog(null,"Thay đổi thông tin thành công","Thành công", JOptionPane.INFORMATION_MESSAGE);

                                break;
                            }
                        }

                        JOptionPane.showMessageDialog(null,"ID lớp học này đã tồn tại",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(kiemTraIDTonTai(idClass, nameClass) == 2)
                    {
                        if(optionTitle.equals("Thay đổi thông tin lớp học"))
                        {
                            if(ClassroomManager.findClassroomById(getIDofClassroomButton(btnClassRightClick)).getName().equals(nameClass))
                            {
                                Classroom temp = new Classroom(idClass, nameClass, teachClass);
                                changeInforClass(temp, getIDofClassroomButton(btnClassRightClick));

                                this.updatePanel(tableOfClassrooms);

                                JOptionPane.showMessageDialog(null,"Thay đổi thông tin thành công",
                                "Thành công", JOptionPane.INFORMATION_MESSAGE);

                                break;
                            }
                        }
                        
                        
                        JOptionPane.showMessageDialog(null,"Tên lớp học này đã tồn tại",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(kiemTraIDTonTai(idClass, nameClass) == 0)
                    {
                        if(optionTitle.equals("Tạo lớp học"))
                        {   
                            //Cập nhật lại thông tin cho các file dữ liệu
                            Classroom temp = new Classroom(idClass, nameClass, teachClass);
                            this.teacher.addClassRoom(temp);
                            this.arrLClassroom = teacher.getClassRooms();
                            ClassroomManager.addClassroom(temp);
                            TeacherManager.addTeacher(teacher);

                            //Cập nhật lại danh sách các lớp
                            tableOfClassrooms.remove(btnCreateClass);
                            createClassButton(idClass,nameClass, arrLClassroom.size() - 1, -1);
                            createNewClassButton();
                        }
                        else
                        {
                            Classroom temp = new Classroom(idClass, nameClass, teachClass);
                            changeInforClass(temp, getIDofClassroomButton(btnClassRightClick));
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

    private void changeInforTeacher(String id)
    {
        App.accountUser.setID(id);
        AccountManager.writeData();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btnCreateClass)
        {
            buttonClassMassage("Tạo lớp học");
        }
        else if(e.getSource() == btnChangeInfor)
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
                           textName = Formatter.formatName(tfNameTeacher.getText());

                    if(textID.equals("") || textName.equals(""))
                    {
                        JOptionPane.showMessageDialog(null, "Thông tin không được để trống",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    if(!TeacherManager.checkIDExist(textID))
                    {
                        if(textID.equals(this.teacher.getId()))
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

                            break;
                        }

                        JOptionPane.showMessageDialog(null,"Mã GV này đã tồn tại",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    else
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

                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        else if(e.getSource() == mnChinhSuaLopHoc)
        {
            buttonClassMassage("Thay đổi thông tin lớp học");
        }
        else if(e.getSource() == mnXoaLopHoc)
        {
            int deleteOption = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa lớp học này?","Thông báo",JOptionPane.OK_CANCEL_OPTION);
            if(deleteOption == JOptionPane.OK_OPTION)
            {
                deleteClass();

            }
        }
        else if(e.getSource() == btnLogOut)
        {
            App.teacherUser = null;
            this.dispose();
            new LogInFrame();
        }
        else
        {
            String classroomID = ( (JLabel) ((JButton) e.getSource()).getComponent(0)).getText();
            this.dispose();
            new ClassroomOfTeacher(ClassroomManager.findClassroomById(classroomID) );
        }
    }

    private void updatePanel(JPanel temp)
    {
        temp.revalidate(); 
        temp.repaint();
    }
    
    private int kiemTraIDTonTai(String IDCLASS, String NAMECLASS)
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
        if(e.isPopupTrigger())
        {
            this.btnClassRightClick = (JButton) e.getSource();
            pmClassRightClick.show(e.getComponent(),e.getX(),e.getY());
        }
    }
    //Kết thúc phần sự kiện chuột

    public static void main(String[] args) { // start the frame directly
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {   
                //Dùng để lấy danh sách lớp, chỉ cần dùng 1 lần duy nhất trong cả chương trình, sau này sẽ bỏ đi
                ClassroomManager.readData();

                //Dùng để test, sau này gộp file lại có thể bỏ đi
                TeacherManager.readData();
                Teacher temp = TeacherManager.findTeacherById("PHUC2405");//Do idsau khi tạo tài khoản sẽ tự động viết hoa tất cả
                new TeacherFrame(temp);
            }
        });
    }
}
