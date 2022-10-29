package entity;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import java.io.Serializable;

public class Classroom implements Serializable 
{
    private static final long serialVersionUID = 131202L;
    
    private String id, name, teacherName;
    private TreeMap<Student, Boolean> studentIds;
    private ArrayList<EventMessage> eventMessages;
    private ArrayList<Exercise> eventExercise;

    public Classroom(String id, String name, String teacherName)
    {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
        studentIds = new TreeMap<>();
        eventMessages = new ArrayList<>();
        eventExercise = new ArrayList<>();
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

    public void addStudentId(Student student)
    {
        studentIds.put(student, false);
    }

    public void addAnEventMessage(EventMessage e)
    {
        this.eventMessages.add(e);
    }

    public ArrayList<EventMessage> getEventMessage()
    {
        return eventMessages;
    }

    public void addAnExercise(Exercise e)
    {
        this.eventExercise.add(e);
    }

    public ArrayList<Exercise> getExercise()
    {
        return eventExercise;
    }

    public void addAnStudent(Student s)
    {
        this.studentIds.put(s, false);
    }
}
