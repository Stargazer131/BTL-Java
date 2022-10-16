package entity;

import java.util.ArrayList;
import java.util.TreeSet;
import java.io.Serializable;

public class Classroom implements Serializable 
{
    private static final long serialVersionUID = 131202L;
    
    private String id, name, teacherId;
    private TreeSet<String> studentIds;
    private ArrayList<Question> questions;

    public Classroom(String id, String name, String teacherId)
    {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        studentIds = new TreeSet<>();
        questions = new ArrayList<>();
    }    

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void addStudentId(String studentId)
    {
        studentIds.add(studentId);
    }
}
