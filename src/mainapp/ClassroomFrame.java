package mainapp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import entity.Classroom;
import entity.EventMessage;
import entity.Exercise;
import entity.Student;
import generic.Pair;
import launch.App;
import mainapp.student.DoExercise;
import manager.ClassroomManager;

public class ClassroomFrame extends JFrame implements ActionListener, MouseListener
{
    protected Classroom classroom; //classroom của frame này

    protected ArrayList<Exercise> listOfExercises; // danh sach bai tap 

    protected ArrayList<Student> listOfStudent; // danh sach sinh vien cua lop hoc

    protected ArrayList<Pair<String, Double>> rankingOfStudent; // bang xep hang

    protected ArrayList<JPanel> pnOfThisClassroom = new ArrayList<>(); //Các panel chính

    protected JPanel pnMain, //Panel chứa các panel chính
                     pnLeft, //Panel bên trái
                     pnCurrentDisplay; //Panel đang hiển thị

    protected JScrollPane spForPanel;

    protected JTable rankingOfStudentTable; // bang xep hang
    protected JScrollPane spForTable; 
    protected JLabel lblRanking;

    protected GridBagConstraints gbc,
                                 gbc2;//Bố cục các thành phần của GridBagLayout

    protected JButton btnListOfStudent, 
                      btnPendingStudents;

    protected JButton btnEventOfClass = new JButton(), //Nút bấm hoạt động gần đây
                      btnExercise = new JButton(),     //Nút bấm danh sách bài tập
                      btnScoreBoard = new JButton(),   //Nút bấm bảng xếp hạng
                      btnTurnBack = new JButton();     //Nút bấm quay lại

    protected ArrayList<EventMessage> event_Messages;  //Danh sách tin nhắn

    protected int indexOfPanelDisplay;                 //Chỉ số của panel đang hiển thị

    protected ArrayList<JScrollPane> scroll;           //Danh sách scroll của panel chính

    protected JScrollPane scrollCurrent;               //Scroll hiện tại đang trình chiếu

    protected ArrayList<Pair<Student, Double>> studentResult;  //Danh sách pair student và điểm

    protected void readDataOfClassroom()
    {
        ClassroomManager.readData();
        this.classroom = ClassroomManager.findClassroomById(classroom.getId()); //Cập nhật dữ liệu cho lớp học

        this.event_Messages = classroom.getEventMessage(); //Khởi tạo danh sách tin nhắn
        this.listOfExercises = classroom.getExercise();    //Khởi tạo danh sách bài tập
        this.studentResult = classroom.getStudentResult(); //Lấy dữ liệu sinh viên và điểm
    }

    public ClassroomFrame(Classroom classroom)
    {
        //Lấy dữ liệu từ classroom
        this.classroom = classroom;
        readDataOfClassroom(); //Đọc dữ liệu

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

    private void initFrame()//Khởi tạo frame này
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

    protected void initButtonOfLeftPanel(JButton button,String title, int y) //Khởi tạo các nút bấm của panel bên trái
    {
        button.setText(title);
        button.setBounds(10,y,160,30);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        button.addActionListener(this);
        
        pnLeft.add(button);
    }

    // tao string co xuong dong
    protected void initMultiLineString(String str, int maximum, JTextArea text)
    {
        String[] arr = str.trim().split("\\s+");
        String temp = "", res = "";
        for(int i = 0; i < arr.length; i++)
        {
            String nextTemp = temp + arr[i];            
            AffineTransform affineTransform = new AffineTransform();     
            FontRenderContext frc = new FontRenderContext(affineTransform,true,true);     
            Font font = text.getFont();
            int textWidth = (int)(font.getStringBounds(nextTemp, frc).getWidth());
            if(textWidth > maximum-15)
            {
                res += temp + "\n";
                temp = arr[i] + " ";
            }

            else temp += arr[i] + " ";
        }
        res += temp;
        
        text.setText(res);
    }

    protected void initLeftFrame() 
    {
        //Khởi tạo panel bên trái
        pnLeft = new JPanel();
        pnLeft.setLayout(null);
        pnLeft.setBounds(10,10,180,690);
        pnLeft.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Khởi tạo khu vực giới thiệu lớp học
        JPanel pnClassInfo = new JPanel();
        pnClassInfo.setLayout(null);
        pnClassInfo.setBounds(1,1,178,160);

        //Tên của lớp
        JTextArea lbClassName = new JTextArea();
        lbClassName.setEditable(false);
        lbClassName.setFocusable(false);
        lbClassName.setBackground(this.getContentPane().getBackground());
        lbClassName.setBounds(0,0,178,80);
        lbClassName.setFont(new Font("Arial",900,20));
        initMultiLineString(classroom.getName(), 178, lbClassName);
        pnClassInfo.add(lbClassName);

        //ID của lớp
        JLabel lbClassId = new JLabel("ID lớp: " + classroom.getId());
        lbClassId.setBounds(0,81,178,20);
        pnClassInfo.add(lbClassId);

        //Tên giáo viên
        JTextArea lbTeacherName = new JTextArea();
        lbTeacherName.setEditable(false);
        lbTeacherName.setFocusable(false);
        lbTeacherName.setBackground(this.getContentPane().getBackground());
        lbTeacherName.setBounds(0,101,178,40);
        lbTeacherName.setFont(lbClassId.getFont());
        initMultiLineString("Giáo viên: " + classroom.getTeacherName(), 178, lbTeacherName);
        pnClassInfo.add(lbTeacherName);

        //Vạch ngang ngăn cách phần thông tin và phần nút bấm
        JLabel lbLine = new JLabel();
        lbLine.setBounds(0,159,178,1);
        lbLine.setBorder(BorderFactory.createLineBorder(Color.black));
        pnClassInfo.add(lbLine);

        pnLeft.add(pnClassInfo);

        //Khởi tạo các nút bấm 
        initButtonOfLeftPanel(btnEventOfClass, "Hoạt động gần đây", 230);
        initButtonOfLeftPanel(btnExercise, "Bài tập", 270);
        initButtonOfLeftPanel(btnScoreBoard, "Bảng xếp hạng", 310);

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
        //Khởi tạo phần khung con của khung chính
        JPanel pnTemp = new JPanel();
        pnTemp.setLayout(new GridBagLayout());

        JScrollPane scrollTemp = new JScrollPane(pnTemp);
        scrollTemp.setPreferredSize(new Dimension(940,670));
        scrollTemp.getVerticalScrollBar().setUnitIncrement(15);
        scrollTemp.setBorder(null);

        scrollTemp.setVisible(false);

        pnMain.add(scrollTemp, gbc);

        pnOfThisClassroom.add(pnTemp);
        scroll.add(scrollTemp);
    }

    //Phần panel chính
    protected void initMainFrame()
    {
        //Cách scroll
        scroll = new ArrayList<>();

        //panel main chính
        pnMain = new JPanel();
        pnMain.setBounds(200,10,970,690);
        pnMain.setLayout(new GridBagLayout());
        pnMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        gbc.gridx = 0;
        gbc.gridy = 0;

        //Khởi tạo các panel con
        for(int i = 0 ; i < 3; i++)
        {
            initFrameOfClass(i);
        }

        this.add(pnMain);

        //Mặc định hiện thị panel con 1 hoạt động
        indexOfPanelDisplay = 0;
        scroll.get(indexOfPanelDisplay).setVisible(true);
        scrollCurrent = scroll.get(indexOfPanelDisplay);
        pnCurrentDisplay = pnOfThisClassroom.get(indexOfPanelDisplay);
    }

    //Khởi tạo 1 cái tin nhắn
    protected void initMessageFrame(JPanel temp, int index,String content, String date,String option)
    {
        gbc2.gridx = 0;
        gbc2.gridy = index;

        JLabel lbContent = new JLabel(content);
        lbContent.setBounds(10,5,1000,40);
        
        //Thời gian
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

    //Khởi tạo các tin nhắn hoặc các bài tập
    protected void initEventFrame(String option)
    {
        if(option.equals("Message"))//Khởi tạo các tin nhắn
        {
            for(int i = 0; i < event_Messages.size(); i++)
            {
                initMessageFrame(pnOfThisClassroom.get(0), event_Messages.size() - i, event_Messages.get(i).getContent(), event_Messages.get(i).getTime(),option);
            }
        }
        else //Khởi taọ các bài tập
        {
            for(int i = 0 ; i < listOfExercises.size(); i++)
            {
                initMessageFrame(pnOfThisClassroom.get(1), listOfExercises.size() - i, listOfExercises.get(i).getTitle(), listOfExercises.get(i).getMessageTime(), option);
            }
        }
    }

    //Tạo ra các tin nhắn và bài tập
    protected void initMessageOfFrame()
    {
        gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(0,0,50,0);
        initEventFrame("Message");
        initEventFrame("Exercise");
    }

    //Sắp xếp danh sách sinh viên theo điểm
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

    protected void initRakingOfStudentTable()  // Tạo bảng xếp hạng
    {
        //Đọc lại dữ liệu
        this.readDataOfClassroom();

        //Sắp xếp danh sách sinh viên
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

    protected void hideAndShowAnPanel() //Ẩn hoặc hiện 1 panel con
    {
        pnCurrentDisplay = pnOfThisClassroom.get(indexOfPanelDisplay);
        scrollCurrent.setVisible(false);
        scroll.get(indexOfPanelDisplay).setVisible(true);
        scrollCurrent = scroll.get(indexOfPanelDisplay);

        updatePanel(pnMain);
    }

    protected void updatePanel(Object temp) //Cập nhật lại 1 panel
    {
        ((Component) temp).revalidate(); 
        ((Component) temp).repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btnEventOfClass)//Nếu nút bấm là tin nhắn
        {
            indexOfPanelDisplay = 0;
            hideAndShowAnPanel();
        }
        else if(e.getSource() == btnExercise)//Nếu nút bấm là tin nhắn
        {
            indexOfPanelDisplay = 1;
            hideAndShowAnPanel();
        }
        else if(e.getSource() == btnScoreBoard) //Nếu nút bấm là bảng xếp hạng
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
}
