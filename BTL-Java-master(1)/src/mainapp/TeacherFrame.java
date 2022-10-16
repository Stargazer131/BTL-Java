package mainapp;

import javax.swing.*;
import javax.xml.transform.Templates;

import entity.Classroom;
import entity.Teacher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.*;
import java.util.*;
import manager.ClassroomManager;
import manager.TeacherManager;

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

    private HashMap<JButton, String> classroomButtonList;

    private Teacher teacher;

    GridBagConstraints gbc;

    private JPopupMenu pmClassRightClick;
    private JMenuItem mnChinhSuaLopHoc;
    private JMenuItem mnXoaLopHoc;

    public TeacherFrame(Teacher TEACHER)
    {
        this.classroomButtonList = new HashMap<>();
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
        this.setBounds(250, 100, 1050,650);
        this.setResizable(false);
        this.setTitle("E-Classroom");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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

    private void deleteClass()
    {
        String idDeleteClass = classroomButtonList.get(btnClassRightClick);
        ClassroomManager.deleteClassroom(idDeleteClass);

        for(Classroom i: arrLClassroom)
        {
            if(i.getId().equals(idDeleteClass))
            {
                arrLClassroom.remove(i);
                break;
            }
        }

        TeacherManager.addTeacher(teacher);
        this.tableOfClassrooms.remove(btnClassRightClick);

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
        classroomButtonList.put(btnClass, id);
    }

    private void createNewClassButton()
    {
        //Tạo nút bấm thêm 1 lớp mới
        gbc.gridx = arrLClassroom.size() % 5;
        gbc.gridy = arrLClassroom.size() / 5;
        btnCreateClass = createBackGroundButton("resources\\images\\Logo\\add.png", "Tao lop moi");
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
        JLabel lbLopHoc = new JLabel("Danh sach lop hoc:");
        lbLopHoc.setFont(new Font("Arial",100,40));
        lbLopHoc.setBounds(250,0,790,75);
        this.add(lbLopHoc);

        tableOfClassrooms = new JPanel();
        tableOfClassrooms.setLayout(new GridBagLayout());
        
        initClassroomButtons();

        scrollPane = new JScrollPane(tableOfClassrooms,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(250, 80, 780, 520);
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

        mnChinhSuaLopHoc = new JMenuItem("Chinh sua thong tin lop hoc");
        mnChinhSuaLopHoc.addActionListener(this);
        this.pmClassRightClick.add(mnChinhSuaLopHoc);

        mnXoaLopHoc = new JMenuItem("Xoa lop hoc");
        mnXoaLopHoc.addActionListener(this);
        this.pmClassRightClick.add(mnXoaLopHoc);

    }

    private void initPanelLeft()          // create the left panel
    {        
        panelLeft = new JPanel();
        panelLeft.setBounds(0, 200, 250, 465);
        panelLeft.setLayout(null);

        ImageIcon iconId = new ImageIcon("resources\\images\\ProfileIcon\\Id.png");
        lblId = new JLabel("Id: " + teacher.getId(), resizeImage(iconId), JLabel.LEFT);
        lblId.setBounds(0, 0, 250, 60);
        lblId.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        ImageIcon iconName = new ImageIcon("resources\\images\\ProfileIcon\\Name.png");
        lblName = new JLabel("Name: " + teacher.getName(), resizeImage(iconName), JLabel.LEFT);
        lblName.setBounds(0, 60, 250, 60);
        lblName.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        ImageIcon iconChangeInfor = new ImageIcon("resources\\images\\Logo\\information.png");
        lblChangeInfor = new JLabel("Thay doi thong tin", resizeImage(iconChangeInfor), JLabel.LEFT);
        btnChangeInfor = new JButton();
        btnChangeInfor.add(lblChangeInfor);
        btnChangeInfor.setBounds(0, 120, 250, 60);
        btnChangeInfor.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        btnChangeInfor.addActionListener(this);

        ImageIcon iconLogOut = new ImageIcon("resources\\images\\Logo\\logout.png");
        lbLogOut = new JLabel("Dang xuat", resizeImage(iconLogOut), JLabel.LEFT);
        btnLogOut = new JButton();
        btnLogOut.add(lbLogOut);
        btnLogOut.setBounds(0, 180, 250, 60);
        btnLogOut.setBorder(BorderFactory.createRaisedSoftBevelBorder());
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
            "ID lop hoc:", tfIDClass,
            "Ten lop hoc:", tfNameClass,
            "Ten giao vien: ", tfTeacherClass
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
                    JOptionPane.showMessageDialog(null,"Thong tin khong duoc de trong!","Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    if(kiemTraIDTonTai(idClass, nameClass) == 1)
                    {
                        JOptionPane.showMessageDialog(null,"ID lop hoc nay da ton tai!","Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(kiemTraIDTonTai(idClass, nameClass) == 2)
                    {
                        if(optionTitle.equals("Tao lop hoc"))
                            JOptionPane.showMessageDialog(null,"Ten lop hoc nay da ton tai!","Error", JOptionPane.ERROR_MESSAGE);
                        else
                        {
                            
                        }
                    }
                    else if(kiemTraIDTonTai(idClass, nameClass) == 0)
                    {
                        if(optionTitle.equals("Tao lop hoc"))
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
                            changeInforClass(temp, classroomButtonList.get(btnClassRightClick));
                        }

                        this.updatePanel(tableOfClassrooms);

                        JOptionPane.showMessageDialog(null,"Thanh cong","Error", JOptionPane.ERROR_MESSAGE);
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

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btnCreateClass)
        {
            buttonClassMassage("Tao lop hoc");
        }
        else if(e.getSource() == btnChangeInfor)
        {
            JTextField tfIDTeacher = new JTextField(),
                       tfNameTeacher = new JTextField();

            Object input[] = 
            {
                "ID giao vien:", tfIDTeacher,
                "Ten giao vien:", tfNameTeacher
            };
            
            while(true)
            {
                int option = JOptionPane.showConfirmDialog(null, input, "Thay doi thong tin", JOptionPane.OK_CANCEL_OPTION);

                if(option == JOptionPane.OK_OPTION)
                {
                    String textID = tfIDTeacher.getText().toUpperCase(),
                           textName = tfNameTeacher.getText();
                    
                    if(!TeacherManager.checkIDExist(textID))
                    {
                        JOptionPane.showMessageDialog(null,"ID nay da ton tai!","Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        Teacher temp = new Teacher(textID, textName);
                        temp.setClassrooms(this.teacher.getClassRooms());
                        TeacherManager.replaceTeacger(this.teacher.getId(), this.teacher, temp);
                        this.teacher = temp;
                        JOptionPane.showMessageDialog(null, "Thay doi thong tin thanh cong!", "Thong bao", JOptionPane.INFORMATION_MESSAGE);
                        this.lblId.setText(textID);
                        this.lblName.setText(textName);
                        this.updatePanel(panelLeft);

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
            buttonClassMassage("Thay doi thong tin lop hoc");
        }
        else if(e.getSource() == mnXoaLopHoc)
        {
            int deleteOption = JOptionPane.showConfirmDialog(null, "Ban co muon xoa lop hoc nay?","Thong bao",JOptionPane.OK_CANCEL_OPTION);
            if(deleteOption == JOptionPane.OK_OPTION)
            {
                deleteClass();
            }
        }
        else
        {
            
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
        hienThiPopUpMenu(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        hienThiPopUpMenu(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    private void hienThiPopUpMenu(MouseEvent e)
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
                Teacher temp = TeacherManager.findTeacherById("PHUC2405");//Do id sau khi tạo tài khoản sẽ tự động viết hoa tất cả
                new TeacherFrame(temp);
            }
        });
    }
}
