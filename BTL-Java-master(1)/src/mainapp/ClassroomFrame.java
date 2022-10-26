package mainapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;
import generic.Pair;
import entity.*;

public class ClassroomFrame extends JFrame implements ActionListener
{
    protected ArrayList<Pair<String, String>> currentActivities; // danh sach hoat dong gan day
    
    protected ArrayList<String> pendingStudents; // danh sach hoc sinh dang cho tham gia

    protected int numberOfStudents; // si so lop

    protected ArrayList<Exercise> listOfExercises; // danh sach bai tap 

    protected ArrayList<Student> listOfStudent; // danh sach sinh vien cua lop hoc

    protected ArrayList<Pair<String, Integer>> rankingOfStudent; // bang xep hang

    protected JPanel pnListOfExercises;  // panel chua cac exercise

    protected JTable rankingOfStudentTable; // bang xep hang
    protected JScrollPane spForTable; 
    protected JLabel lblRanking;

    protected GridBagConstraints gbc;

    protected JButton btnListOfStudent, 
                      btnPendingStudents,
                      btnSort;

    public ClassroomFrame()
    {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,5,10, 15);
        
        initFrame();
        initTopLeftButtons();


        initRakingOfStudentTable();
        this.setVisible(true);
    }

    private void initFrame()
    {
        this.setBounds(200, 50, 1200,750);
        this.setResizable(false);
        this.setTitle("E-Classroom");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("resources\\images\\Logo\\logo.png");
        this.setIconImage(icon.getImage());
    }

    private void initRakingOfStudentTable()
    {
        int n = 100; // test data
        
        // ten cua cac cot
        Object[] columnNames = {"Ma SV", "Ho ten", "Diem"};
        
        // du lieu trong tung hang
        Object[][] rowData = new Object[n][3];
        for(int i = 0; i < n; i++)
        {
            rowData[i][0] = String.format("B20DCCN%03d", i);
            rowData[i][1] = String.format("Nguyen Van %02d", i);
            Integer num = new Random().nextInt(0, 1000);
            rowData[i][2] = num;
        }

        lblRanking = new JLabel("BANG XEP HANG");
        lblRanking.setFont(new Font("Arial",100,30));
        lblRanking.setBounds(0, 200, 350, 40);
        this.add(lblRanking);
        
        /////////
        rankingOfStudentTable = new JTable();
        
        // tao table voi ten cua cac cot va du lieu tung hang
        rankingOfStudentTable.setModel(new DefaultTableModel(rowData, columnNames){
            // Chi ro ra tung cot se chua kieu du lieu gi -> Quan trong cho viec sap xep dung
            Class[] types = { String.class, String.class, Integer.class };
        
            @Override
            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }
        });
        
        //////////

        spForTable = new JScrollPane(rankingOfStudentTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        spForTable.setBounds(0, 250, 350, 450);
        spForTable.getVerticalScrollBar().setUnitIncrement(15);
        spForTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(spForTable);
    }

    private void initTopLeftButtons()
    {
        btnListOfStudent = new JButton("Danh sach lop");
        btnListOfStudent.setBounds(20, 20, 200, 30);
        this.add(btnListOfStudent);
        btnListOfStudent.addActionListener(this);

        btnPendingStudents = new JButton("Danh sach cho gia nhap");
        btnPendingStudents.setBounds(20, 80, 200, 30);
        this.add(btnPendingStudents);
        btnPendingStudents.addActionListener(this);

        btnSort = new JButton("Sort");
        btnSort.setBounds(20, 140, 200, 30);
        this.add(btnSort);
        btnSort.addActionListener(this);
    }

    private JButton createBackGroundButton(String url, String name)  // tao button voi background tuy chon
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


    private void insertNewRow(Object[] data, int index) // chen them 1 dong vao bang xep hang
    {
        DefaultTableModel model = (DefaultTableModel)rankingOfStudentTable.getModel();
        model.insertRow(index, data);
    }


    private void deleteRow(int index) // xoa 1 dong khoi bang xep hang
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


    private static ImageIcon resizeImage(ImageIcon imageIcon)  // resize icon to fit in the frame
    {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnListOfStudent)
        {  

        }

        else if(e.getSource() == btnPendingStudents)
        {

        }

        else if(e.getSource() == btnSort)
        {
            sortRankingByColumn(2);
        }
    }
    public static void main(String[] args) 
    {
        new ClassroomFrame();
    }
}
