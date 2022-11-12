package mainapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;

import generic.Pair;
import launch.App;
import mainapp.student.DoExercise;
import manager.ClassroomManager;
import manager.StudentManager;
import entity.*;

public class ClassroomFrame extends JFrame implements ActionListener, MouseListener
{
    protected Classroom classroom;

    protected ArrayList<Exercise> listOfExercises; // danh sach bai tap 

    protected ArrayList<Student> listOfStudent; // danh sach sinh vien cua lop hoc

    protected ArrayList<Pair<String, Double>> rankingOfStudent; // bang xep hang

    protected ArrayList<JPanel> pnOfThisClassroom = new ArrayList<>();

    protected JPanel pnMain, //Panel chứa các panel chính
                     pnLeft,
                     pnCurrentDisplay; 

    protected JScrollPane spForPanel;

    protected JTable rankingOfStudentTable; // bang xep hang
    protected JScrollPane spForTable; 
    protected JLabel lblRanking;

    protected GridBagConstraints gbc,
                                 gbc2;//Bố cục các thành phần của GridBagLayout

    protected JButton btnListOfStudent, 
                      btnPendingStudents;

    protected JButton btnEventOfClass = new JButton(),    
                      btnExercise = new JButton(),
                      btnScoreBoard = new JButton(),
                      btnTurnBack = new JButton();

    protected ArrayList<EventMessage> event_Messages;

    protected int indexOfPanelDisplay;

    protected ArrayList<JScrollPane> scroll;

    protected JScrollPane scrollCurrent;

    protected ArrayList<Pair<Student, Double>> studentResult;

    protected void readDataOfClassroom()
    {
        ClassroomManager.readData();
        this.classroom = ClassroomManager.findClassroomById(classroom.getId());

        this.event_Messages = classroom.getEventMessage();
        this.listOfExercises = classroom.getExercise();
        this.studentResult = classroom.getStudentResult();
    }

    public ClassroomFrame(Classroom classroom)
    {
        //Lấy dữ liệu từ classroom
        this.classroom = classroom;
        readDataOfClassroom();

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,5,10, 15);
        
        //Khởi tạo các thông số cơ bản của Frame
        this.initFrame();

        //Khởi tạo Jpanel chính
        initMainFrame();

        //Khởi tạo Jpanel góc trái
        initLeftFrame();

        //Khởi tạo các dòng tin nhắn
        initMessageOfFrame();

        //Tạo bảng xếp hạng
        initRakingOfStudentTable();

        this.setVisible(true);
    }

    private void initFrame()
    {

        this.setBounds(200, 50, 1200,750);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle(classroom.getName());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    protected void initButtonOfLeftPanel(JButton button,String title, int y)
    {
        button.setText(title);
        button.setBounds(10,y,160,30);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        button.addActionListener(this);
        
        pnLeft.add(button);
    }

    protected void initLeftFrame() 
    {
        pnLeft = new JPanel();
        pnLeft.setLayout(null);
        pnLeft.setBounds(10,10,180,690);
        pnLeft.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Khởi tạo khu vực giới thiệu lớp học
        JPanel pnClassinfor = new JPanel();
        pnClassinfor.setLayout(null);
        pnClassinfor.setBounds(1,1,178,100);

        JLabel lbClassName = new JLabel(classroom.getName());
        lbClassName.setBounds(0,0,178,40);
        lbClassName.setFont(new Font("Arial",900,20));
        pnClassinfor.add(lbClassName);

        JLabel lbClassId = new JLabel("ID lớp: " + classroom.getId());
        lbClassId.setBounds(0,41,178,20);
        pnClassinfor.add(lbClassId);

        JLabel lbTeacherName = new JLabel("Giáo viên: " + classroom.getTeacherName());
        lbTeacherName.setBounds(0,61,178,20);
        pnClassinfor.add(lbTeacherName);

        JLabel lbLine = new JLabel();
        lbLine.setBounds(0,86,178,1);
        lbLine.setBorder(BorderFactory.createLineBorder(Color.black));
        pnClassinfor.add(lbLine);

        pnLeft.add(pnClassinfor);

        //Khởi tạo các nút bấm 
        initButtonOfLeftPanel(btnEventOfClass, "Hoạt động gần đây", 150);
        initButtonOfLeftPanel(btnExercise, "Bài tập", 190);
        initButtonOfLeftPanel(btnScoreBoard, "Bảng xếp hạng", 230);

        //Khởi tạo nút quay lại
        btnTurnBack = new JButton("Quay lại");
        btnTurnBack.setFocusable(false);
        btnTurnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnTurnBack.setBounds(30,630,120,30);
        btnTurnBack.addActionListener(this);
        pnLeft.add(btnTurnBack);

        this.add(pnLeft);

    }

    protected void initFrameOfClass(int i)
    {
        JPanel pnTemp = new JPanel();
        pnTemp.setLayout(new GridBagLayout());
        //pnTemp.setBorder(BorderFactory.createLineBorder(Color.black));

        //Test
        // JLabel test = new JLabel(i + "");
        // test.setBounds(100,100,100,100);
        // test.setBorder(BorderFactory.createLineBorder(Color.green));
        // pnTemp.add(test);

        JScrollPane scrollTemp = new JScrollPane(pnTemp);
        scrollTemp.setPreferredSize(new Dimension(940,670));
        scrollTemp.getVerticalScrollBar().setUnitIncrement(15);
        scrollTemp.setBorder(null);

        scrollTemp.setVisible(false);

        pnMain.add(scrollTemp, gbc);

        pnOfThisClassroom.add(pnTemp);
        scroll.add(scrollTemp);
    }

    protected void initMainFrame()
    {
        scroll = new ArrayList<>();

        pnMain = new JPanel();
        pnMain.setBounds(200,10,970,690);
        pnMain.setLayout(new GridBagLayout());
        pnMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        gbc.gridx = 0;
        gbc.gridy = 0;

        for(int i = 0 ; i < 3; i++)
        {
            initFrameOfClass(i);
        }

        this.add(pnMain);

        //Mặc định hiện thị panel hoạt động
        indexOfPanelDisplay = 0;
        scroll.get(indexOfPanelDisplay).setVisible(true);
        scrollCurrent = scroll.get(indexOfPanelDisplay);
        pnCurrentDisplay = pnOfThisClassroom.get(indexOfPanelDisplay);
    }

    protected void initMessageFrame(JPanel temp, int index,String content, String date,String option)
    {
        gbc2.gridx = 0;
        gbc2.gridy = index;

        JLabel lbContent = new JLabel(content);
        lbContent.setBounds(10,5,1000,40);
        

        JLabel lbTime = new JLabel(date);
        lbTime.setFont(new Font("Arial",100,10));
        lbTime.setBounds(770,75,200,20);
 
        JLabel lbTemp = new JLabel();
        lbTemp.setPreferredSize( new Dimension(900,100));
        lbTemp.setBorder(BorderFactory.createLineBorder(Color.black));
        lbTemp.add(lbContent);
        lbTemp.add(lbTime);
        if(!option.equals("Message"))
        {
            lbTemp.addMouseListener(this);
            lbTemp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            //Nếu người dùng là sinh viên thì sẽ thêm dòng trạng thái: đã hoàn thành/ chưa hoàn thành
            if(App.teacherUser == null)
            {
                JLabel isFinish = new JLabel("Chưa hoàn thành");
                if(listOfExercises.get(listOfExercises.size() - index).getListStudentFinish().contains(App.studentUser.getId()))
                {
                    isFinish.setText("Đã hoàn thành");
                    isFinish.setForeground(Color.red);
                }    
                isFinish.setBounds(800,10,100,20);
                lbTemp.add(isFinish);
            }
        }    
        temp.add(lbTemp,gbc2);
    }

    protected void initEventFrame(String option)
    {
        if(option.equals("Message"))
        {
            for(int i = 0; i < event_Messages.size(); i++)
            {
                initMessageFrame(pnOfThisClassroom.get(0), event_Messages.size() - i, event_Messages.get(i).getContent(), event_Messages.get(i).getTime(),option);
            }
        }
        else 
        {
            for(int i = 0 ; i < listOfExercises.size(); i++)
            {
                initMessageFrame(pnOfThisClassroom.get(1), listOfExercises.size() - i, listOfExercises.get(i).getTitle(), listOfExercises.get(i).getMessageTime(), option);
            }
        }
    }

    protected void initMessageOfFrame()
    {
        gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(0,0,50,0);
        initEventFrame("Message");
        initEventFrame("Exercise");
    }

    private void sortStudentByPoint()
    {
        Collections.sort(studentResult, new Comparator<Pair<Student,Double>>() {
            @Override
            public int compare(Pair<Student,Double> a, Pair<Student,Double> b)
            {
                if((Double)a.getSecond() < (Double)b.getSecond())
                    return 1;
                return -1;
            }
        });
    }

    protected void initRakingOfStudentTable()  // tao bang bang xep hang
    {
        this.readDataOfClassroom();

        sortStudentByPoint();

        pnOfThisClassroom.get(2).setLayout(null);
        
        // ten cua cac cot
        Object[] columnNames = {"STT","Mã SV", "Họ tên", "Điểm"};
        
        // du lieu trong tung hang
        Object[][] rowData = new Object[studentResult.size()][4];

        int index = 0;
        for(Pair<Student, Double> i: studentResult)
        {
            rowData[index][0] = index + 1;
            rowData[index][1] = i.getFirst().getId();
            rowData[index][2] = i.getFirst().getName();
            rowData[index][3] =  i.getSecond();
            index++;
        }

        lblRanking = new JLabel("BẢNG XẾP HẠNG");
        lblRanking.setFont(new Font("Arial",100,30));
        lblRanking.setBounds(300, 10, 350, 40);
        pnOfThisClassroom.get(2).add(lblRanking);
        
        /////////
        rankingOfStudentTable = new JTable();
        
        // tao table voi ten cua cac cot va du lieu tung hang
        rankingOfStudentTable.setModel(new DefaultTableModel(rowData, columnNames)
        {
            // Chi ro ra tung cot se chua kieu du lieu gi -> Quan trong cho viec sap xep dung
            Class<?>[] types = {Integer.class,String.class, String.class, Double.class };
        
            @Override
            public Class<?> getColumnClass(int columnIndex) 
            {
                return this.types[columnIndex];
            }
        });

        rankingOfStudentTable.addMouseListener(this);
        
        //Set chiều rộng cho các cột 
        TableColumn temp1 = rankingOfStudentTable.getColumnModel().getColumn(0),
                    temp2 = rankingOfStudentTable.getColumnModel().getColumn(1),
                    temp3 = rankingOfStudentTable.getColumnModel().getColumn(2),
                    temp4 = rankingOfStudentTable.getColumnModel().getColumn(3);

        temp1.setMaxWidth(50);

        temp2.setMaxWidth(200);

        temp3.setMaxWidth(200);

        temp4.setMaxWidth(100);

        rankingOfStudentTable.setEnabled(false);  

        spForTable = new JScrollPane(rankingOfStudentTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        spForTable.setBounds(200, 70, 500, 600);
        spForTable.getVerticalScrollBar().setUnitIncrement(15);
        spForTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnOfThisClassroom.get(2).add(spForTable);

        sortRankingByColumn(3);
    }

    // private void insertNewRow(Object[] data, int index) // chen them 1 dong vao bang xep hang
    // {
    //     DefaultTableModel model = (DefaultTableModel)rankingOfStudentTable.getModel();
    //     model.insertRow(index, data);
    // }

    protected void deleteRow(int index) // xoa 1 dong khoi bang xep hang
    {
        DefaultTableModel model = (DefaultTableModel)rankingOfStudentTable.getModel();
        model.removeRow(index);
    }

    private void sortRankingByColumn(int columnIndexToSort) // sap xep bang xep bang xep hang theo cot can sap xep
    {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel)rankingOfStudentTable.getModel());
        rankingOfStudentTable.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING)); // thu tu sap xep
         
        sorter.setSortKeys(sortKeys);
        sorter.sort();
        rankingOfStudentTable.getRowSorter();
    }

    // private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    // {
    //     Image image = imageIcon.getImage(); // transform it 
    //     Image newImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // scale it the smooth way  
    //     return new ImageIcon(newImage);
    // }

    protected void hideAndShowAnPanel()
    {
        pnCurrentDisplay = pnOfThisClassroom.get(indexOfPanelDisplay);
        scrollCurrent.setVisible(false);
        scroll.get(indexOfPanelDisplay).setVisible(true);
        scrollCurrent = scroll.get(indexOfPanelDisplay);

        updatePanel(pnMain);
    }

    protected void updatePanel(Object temp)
    {
        ((Component) temp).revalidate(); 
        ((Component) temp).repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btnEventOfClass)
        {
            indexOfPanelDisplay = 0;
            hideAndShowAnPanel();
        }
        else if(e.getSource() == btnExercise)
        {
            indexOfPanelDisplay = 1;
            hideAndShowAnPanel();
        }
        else if(e.getSource() == btnScoreBoard)
        {
            indexOfPanelDisplay = 2;
            hideAndShowAnPanel();
        }
    }

    //Làm bài tập
    protected void doExercise(int index)
    {
        this.dispose();
        //Kiểm tra xem sinh viên này đã hoàn thành bài tập này chưa, nếu chưa thì sẽ tính điểm, nếu đã làm rồi thì sẽ coi như là luyện tập
        if(listOfExercises.get(index).getListStudentFinish().contains(App.studentUser.getId()))
            new DoExercise(listOfExercises.get(index), classroom, false);
        else
        new DoExercise(listOfExercises.get(index), classroom, true);
    }

    //Lắng nghe sự kiện chuột
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        
    }

    // khi di chuot vao JTable
    @Override
    public void mouseEntered(MouseEvent e) 
    {
        if(e.getSource() instanceof JTable)
        {
            spForTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    // khi di chuot ra khoi JTable
    @Override
    public void mouseExited(MouseEvent e) 
    {
        if(e.getSource() instanceof JTable)
        {
            spForTable.setCursor(Cursor.getDefaultCursor());
        }
    }

    public static void main(String[] args) 
    {
        ClassroomManager.readData();
        StudentManager.readData();

        new ClassroomFrame(ClassroomManager.findClassroomById("triet01"));
    }
}
