package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import generic.Pair;

public class Classroom implements Serializable 
{
    private static final long serialVersionUID = 131202L;
    
    private String id, 
                   name, 
                   teacherName,
                   timeCreate;
    private ArrayList<Pair<Student, Double>> studentIds;
    private ArrayList<EventMessage> eventMessages;
    private ArrayList<Exercise> eventExercise;

    private static SimpleDateFormat sfdHours = new SimpleDateFormat("HH:mm:ss"),
                                    sdfDay = new SimpleDateFormat("DD/MM/YYYY");

    public Classroom(String id, String name, String teacherName)
    {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
        studentIds = new ArrayList<>();
        eventMessages = new ArrayList<>();
        eventExercise = new ArrayList<>();

        Date d = new Date();
        this.timeCreate = String.format("%s_%s", sfdHours.format(d), sdfDay.format(d));
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

    public void changeInforOfClassroom(String newID, String newName)
    {
        this.id = newID;
        this.name = newName;
    }

    public void changeTeacherName(String newName)
    {
        this.teacherName = newName;
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
        this.studentIds.add(new Pair<Student,Double>(s, 0.0));
    }

    public void studentDoExerciseResult(Student student, double point)
    {
        for(Pair<Student,Double> i: studentIds)
        {
            if(i.getFirst().getId().equals(student.getId()))
            {
                i.setSecond(i.getSecond() + point);
                System.out.println(i.getSecond());
                break;
            }
        }
    }

    public ArrayList<Pair<Student, Double>>getStudentResult()
    {
        return studentIds;
    }

    public String getTimeCreate()
    {
        return this.timeCreate;
    }
}
