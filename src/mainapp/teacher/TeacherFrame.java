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
        gbc.insets = new Insets(0,5,10, 15); //T???o kho???ng c??ch gi???a c??c ph???n t???

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

    private JButton createBackGroundButton(String url, String name) //T???o c??c thu???c t??nh c???a 1 l???p h???c
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

    private String getIDofClassroomButton(JButton temp) //L???y id c???a n??t b???m
    {
        return ((JLabel) temp.getComponent(0)).getText();
    }

    private void deleteClass() //Xo?? 1 l???p h???c
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

        //Kh???i t???o l???i v??? tr?? c???a n??t t???o l???p m???i
        tableOfClassrooms.remove(btnCreateClass);
        createNewClassButton();

        TeacherManager.writeData();

        this.teacher.setClassrooms(arrLClassroom);
        TeacherManager.addTeacher(teacher);

        updatePanel(tableOfClassrooms);
    }

    private void createClassButton(String id, String name, int i, int index) //T???o ra 1 n??t b???m l???p h???c m???i
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

    private void createNewClassButton() //n??t b???m t???o ra 1 l???p h???c m???i
    {
        //T???o n??t b???m th??m 1 l???p m???i
        gbc.gridx = arrLClassroom.size() % 5;
        gbc.gridy = arrLClassroom.size() / 5;
        btnCreateClass = createBackGroundButton("resources\\images\\Logo\\add.png", "T???o l???p m???i");
        tableOfClassrooms.add(btnCreateClass, gbc);
    }

    private void initClassroomButtons() //T???o ra 1 n??t b???m l???p h???c
    {
        for(int i = 0 ; i < arrLClassroom.size() ; i++)
        {
            createClassButton(arrLClassroom.get(i).getId(), arrLClassroom.get(i).getName(), i,-1);
        }
        createNewClassButton();
    }

    private void initTable()  //T???i ph???n ch??a l???p h???c
    {
        JLabel lbLopHoc = new JLabel("Danh s??ch l???p h???c:");
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

    private void initPopUpMenu() //Menu chu???t ph???i
    {
        this.pmClassRightClick = new JPopupMenu();

        mnChinhSuaLopHoc = new JMenuItem("Ch???nh s???a th??ng tin l???p h???c");
        mnChinhSuaLopHoc.addActionListener(this);
        this.pmClassRightClick.add(mnChinhSuaLopHoc);

        mnXoaLopHoc = new JMenuItem("X??a l???p h???c");
        mnXoaLopHoc.addActionListener(this);
        this.pmClassRightClick.add(mnXoaLopHoc);

    }

    private void initPanelLeft()          // create the left panel
    {        
        panelLeft = new JPanel();
        panelLeft.setBounds(0, 200, 250, 465);
        panelLeft.setLayout(null);

        ImageIcon iconId = new ImageIcon("resources\\images\\ProfileIcon\\Id.png");
        lblId = new JLabel("M?? GV: " + teacher.getId(), resizeImage(iconId), JLabel.LEFT);
        lblId.setBounds(0, 0, 250, 60);
        lblId.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        ImageIcon iconName = new ImageIcon("resources\\images\\ProfileIcon\\Name.png");
        lblName = new JLabel("H??? t??n: " + teacher.getName(), resizeImage(iconName), JLabel.LEFT);
        lblName.setBounds(0, 60, 250, 60);
        lblName.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        ImageIcon iconChangeInfor = new ImageIcon("resources\\images\\Logo\\information.png");
        lblChangeInfor = new JLabel("Thay ?????i th??ng tin", resizeImage(iconChangeInfor), JLabel.LEFT);
        btnChangeInfor = new JButton();
        btnChangeInfor.add(lblChangeInfor);
        btnChangeInfor.setBounds(0, 120, 250, 60);
        btnChangeInfor.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        btnChangeInfor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnChangeInfor.addActionListener(this);

        ImageIcon iconLogOut = new ImageIcon("resources\\images\\Logo\\logout.png");
        lbLogOut = new JLabel("????ng xu???t", resizeImage(iconLogOut), JLabel.LEFT);
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

    private void changeInforClass(String newID, String newName, String oldID) //Thay ?????i th??ng tin c???a l???p h???c
    {
        int index = 0;
        for(Component i: tableOfClassrooms.getComponents())
        {
            if(i == btnClassRightClick)
            {
                ((JLabel) ((JButton) i).getComponent(0)).setText(newID);

                //C???p nh???t l???i d??? li???u cho danh s??ch l???p
                ClassroomManager.readData();
                Classroom oldClassroom = ClassroomManager.findClassroomById(oldID);
                oldClassroom.changeInforOfClassroom(newID, newName);
                ClassroomManager.deleteClassroom(oldID);
                ClassroomManager.addClassroom(oldClassroom);

                //C???p nh???t l???i d??? li???u cho gi??o vi??n
                arrLClassroom.remove(index);
                arrLClassroom.add(index,oldClassroom);
                this.teacher.setClassrooms(arrLClassroom);
                TeacherManager.addTeacher(teacher);
                
                //C???p nh???t l???i d??? li???u cho t???ng sinh vi??n c???a l???p ????
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

    //T???o 1 l???p h???c m???i ho???c thay ?????i th??ng tin l???p h???c tu??? v??o option
    private void buttonClassMassage(String optionTitle)
    {
        JTextField tfIDClass = new JTextField(),
                       tfNameClass = new JTextField();

        Object[] input = 
        {
            "ID l???p h???c:", tfIDClass,
            "T??n l???p h???c:", tfNameClass
        };

        while(true)
        {
            int option = JOptionPane.showConfirmDialog(null, input, optionTitle, JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION)
            {
                String idClass = tfIDClass.getText(),
                        nameClass = tfNameClass.getText();

                if(idClass.equals("") || nameClass.equals("")) //1 textfield ch??a ???????c nh???p
                {
                    JOptionPane.showMessageDialog(null,"Th??ng tin kh??ng ???????c ????? tr???ng",
                    "L???i", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    if(kiemTraIDTonTai(idClass, nameClass) == 1) //N???u ID ???? t???n t???i
                    {
                        if(optionTitle.equals("Thay ?????i th??ng tin l???p h???c"))
                        {
                            if(getIDofClassroomButton(btnClassRightClick).equals(idClass))
                            {
                                changeInforClass(idClass, nameClass, getIDofClassroomButton(btnClassRightClick));

                                this.updatePanel(tableOfClassrooms);

                                JOptionPane.showMessageDialog(null,"Thay ?????i th??ng tin th??nh c??ng","Th??nh c??ng", JOptionPane.INFORMATION_MESSAGE);

                                break;
                            }
                        }

                        JOptionPane.showMessageDialog(null,"ID l???p h???c n??y ???? t???n t???i",
                        "L???i", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(kiemTraIDTonTai(idClass, nameClass) == 2) //N???u t??n l???p h???c ???? t???n t???i
                    {
                        if(optionTitle.equals("Thay ?????i th??ng tin l???p h???c"))
                        {
                            if(ClassroomManager.findClassroomById(getIDofClassroomButton(btnClassRightClick)).getName().equals(nameClass))
                            {
                                changeInforClass(idClass, nameClass, getIDofClassroomButton(btnClassRightClick));

                                this.updatePanel(tableOfClassrooms);

                                JOptionPane.showMessageDialog(null,"Thay ?????i th??ng tin th??nh c??ng",
                                "Th??nh c??ng", JOptionPane.INFORMATION_MESSAGE);

                                break;
                            }
                        }
                        
                        JOptionPane.showMessageDialog(null,"T??n l???p h???c n??y ???? t???n t???i",
                        "L???i", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(kiemTraIDTonTai(idClass, nameClass) == 0) //C??c ??i???u ki???n ?????u tho??? m??n 
                    {
                        if(optionTitle.equals("T???o l???p h???c"))
                        {   
                            //C???p nh???t l???i th??ng tin cho c??c file d??? li???u
                            Classroom temp = new Classroom(idClass, nameClass, App.teacherUser.getName());
                            this.teacher.addClassRoom(temp);
                            this.arrLClassroom = teacher.getClassRooms();
                            ClassroomManager.addClassroom(temp);
                            TeacherManager.addTeacher(teacher);

                            //C???p nh???t l???i danh s??ch c??c l???p
                            tableOfClassrooms.remove(btnCreateClass);
                            createClassButton(idClass,nameClass, arrLClassroom.size() - 1, -1);
                            createNewClassButton();
                        }
                        else //Thay ?????i th??ng tin cho l???p h???c th??nh c??ng
                        {
                            changeInforClass(idClass, nameClass, getIDofClassroomButton(btnClassRightClick));
                        }

                        this.updatePanel(tableOfClassrooms);

                        JOptionPane.showMessageDialog(null,"Th??nh c??ng","Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
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

    private void changeInforTeacher(String id) //Thay ?????i account s??? d???ng
    {
        App.accountUser.setID(id);
        App.teacherUser = TeacherManager.findTeacherById(id);
        AccountManager.writeData();
    }

    private void changeTeacherNameOfClassroom() //Thay ?????i t??n gi??o vi??n c???a c??c l???p h???c thu???c gi??o vi??n n??y
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
            buttonClassMassage("T???o l???p h???c");
        }
        else if(e.getSource() == btnChangeInfor) //Thay ?????i th??ng tin gi??o vi??n
        {
            JTextField tfIDTeacher = new JTextField(),
                       tfNameTeacher = new JTextField();

            Object input[] = 
            {
                "M?? GV:", tfIDTeacher,
                "T??n gi??o vi??n:", tfNameTeacher
            };
            
            while(true)
            {
                int option = JOptionPane.showConfirmDialog(null, input, "Thay ?????i th??ng tin", JOptionPane.OK_CANCEL_OPTION);

                if(option == JOptionPane.OK_OPTION)
                {
                    String textID = tfIDTeacher.getText().toUpperCase(),
                           textName = Formatter.toTitle(tfNameTeacher.getText());

                    if(textID.equals("") || textName.equals("")) //L???i c??n ph???n ch??a ???????c ghi
                    {
                        JOptionPane.showMessageDialog(null, "Th??ng tin kh??ng ???????c ????? tr???ng",
                        "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    if(!TeacherManager.checkIDExist(textID)) //N???u ID n??y ???? t???n t???i
                    {
                        if(textID.equals(this.teacher.getId())) //N???u ID m???i tr??ng v???i id c???a gi??o vi??n
                        {
                            Teacher temp = new Teacher(textID, textName);
                            temp.setClassrooms(this.teacher.getClassRooms());
                            TeacherManager.replaceTeacher(this.teacher.getId(), this.teacher, temp);
                            this.teacher = temp;
                            JOptionPane.showMessageDialog(null, "Thay ?????i th??ng tin th??nh c??ng", 
                            "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
                            
                            this.lblId.setText(textID);
                            this.lblName.setText(textName);
                            this.updatePanel(panelLeft);

                            //Thay ?????i th??ng tin gi??o vi??n
                            changeInforTeacher(textID);

                            //?????i t??n gi??o vi??n c???a c??c l???p
                            changeTeacherNameOfClassroom();

                            break;
                        }

                        JOptionPane.showMessageDialog(null,"M?? GV n??y ???? t???n t???i",
                        "L???i", JOptionPane.ERROR_MESSAGE);
                    }
                    else //Tho??? m??n ??i???u ki???n
                    {
                        Teacher temp = new Teacher(textID, textName);
                        temp.setClassrooms(this.teacher.getClassRooms());
                        TeacherManager.replaceTeacher(this.teacher.getId(), this.teacher, temp);
                        this.teacher = temp;
                        JOptionPane.showMessageDialog(null, "Thay ?????i th??ng tin th??nh c??ng", 
                        "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
                        
                        this.lblId.setText(textID);
                        this.lblName.setText(textName);
                        this.updatePanel(panelLeft);

                        //Thay ?????i th??ng tin gi??o vi??n
                        changeInforTeacher(textID);

                        //?????i t??n gi??o vi??n c???a c??c l???p
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
        else if(e.getSource() == mnChinhSuaLopHoc) //Ch???nh s???a th??ng tin l???p h???c
        {
            buttonClassMassage("Thay ?????i th??ng tin l???p h???c");
        }
        else if(e.getSource() == mnXoaLopHoc) //Xo?? l???p h???c
        {
            int deleteOption = JOptionPane.showConfirmDialog(null, "B???n c?? mu???n x??a l???p h???c n??y?","Th??ng b??o",JOptionPane.OK_CANCEL_OPTION);
            if(deleteOption == JOptionPane.OK_OPTION)
            {
                deleteClass();

            }
        }
        else if(e.getSource() == btnLogOut) //????ng xu???t
        {
            App.teacherUser = null;
            this.dispose();
            new LogInFrame();
        }
        else //M??? 1 l???p h???c
        {
            String classroomID = ( (JLabel) ((JButton) e.getSource()).getComponent(0)).getText();
            this.dispose();
            new ClassroomOfTeacher(ClassroomManager.findClassroomById(classroomID) );
        }
    }

    private void updatePanel(JPanel temp)//C???p nh???t l???i panel
    {
        temp.revalidate(); 
        temp.repaint();
    }
    
    private int kiemTraIDTonTai(String IDCLASS, String NAMECLASS)//Ki???m tra l???p h???c t???n t???i
    {
        if(!ClassroomManager.checkIDExist(IDCLASS))
            return 1;
        else if(!ClassroomManager.checkNAMEExist(NAMECLASS))
            return 2;
        return 0;
    }

    //L???ng nghe s??? ki???n c???a chu???t
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
        if(e.isPopupTrigger())//N???u click chu???t l?? chu???t ph???i
        {
            this.btnClassRightClick = (JButton) e.getSource();
            pmClassRightClick.show(e.getComponent(),e.getX(),e.getY()); //Hi???n th??? menu chu???t ph???i
        }
    }
    //K???t th??c ph???n s??? ki???n chu???t
}
