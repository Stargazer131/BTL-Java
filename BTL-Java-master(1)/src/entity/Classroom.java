package entity;

import java.util.ArrayList;
import java.util.TreeSet;

import generic.EventMessage;

import java.io.Serializable;

public class Classroom implements Serializable 
{
    private static final long serialVersionUID = 131202L;
    
    private String id, name, teacherName;
    private TreeSet<String> studentIds;
    private ArrayList<Question> questions;
    private ArrayList<EventMessage> eventMessages;

    public Classroom(String id, String name, String teacherName)
    {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
        this.questions = new ArrayList<>();
        studentIds = new TreeSet<>();
        questions = new ArrayList<>();
        eventMessages = new ArrayList<>();
    }    

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getTeacherName()
    {
        return teacherName;
    }

    public void addStudentId(String studentId)
    {
        studentIds.add(studentId);
    }

    public void addAnEventMessage(EventMessage e)
    {
        this.eventMessages.add(e);
    }

    public ArrayList<EventMessage> getEventMessage()
    {
        return eventMessages;
    }
}
