package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher implements Serializable
{
    private static final long serialVersionUID = 1301202L;
    
    private String id, name;

    private ArrayList<Classroom> classrooms;
    
    public Teacher(String id, String name)
    {
        this.id = id;
        this.name = name;
        classrooms = new ArrayList<>();
    }

    public String getId()
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }

    public void addClassRoom(Classroom temp)
    {
        this.classrooms.add(temp);
    }

    public void deleteClassroom(String id)
    {
        for(Classroom i: classrooms)
        {
            if(i.getId().equals(id))
            {
                classrooms.remove(i);
            }
        }
    }

    public ArrayList<Classroom> getClassRooms()
    {
        return classrooms;
    }

    public void setClassrooms(ArrayList<Classroom> temp)
    {
        this.classrooms = temp;
    }
}
