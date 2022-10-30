package entity;

import java.util.ArrayList;
import java.util.TreeMap;
import java.io.Serializable;

public class Student implements Serializable, Comparable<Student>
{
    private static final long serialVersionUID = 13012002L;
    
    private String id, name, gender, birthday;
    private String address, group, email, phoneNumber;

    private TreeMap<String, Classroom> classroomIds;
    
    public Student(String id, String name, String gender, String birthday, String address, String group, String email, String phoneNumber) 
    {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.group = group;
        this.email = email;
        this.phoneNumber = phoneNumber;
        classroomIds = new TreeMap<>();
    }

    public String getId()   // start of getters
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }

    public String getGender() 
    {
        return gender;
    }

    public String getBirthday() 
    {
        return birthday;
    }

    public String getAddress() 
    {
        return address;
    }

    public String getGroup() 
    {
        return group;
    }

    public String getEmail() 
    {
        return email;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }                             // end of getters

    public TreeMap<String, Classroom> getListClassroom()
    {
        return classroomIds;
    }

    public void addClassroomId(String classroomId, Classroom temp) // add a new classroom 
    {
        classroomIds.put(classroomId, temp);
    }

    public void deleteClassroomId(String classroomId) // delete a classroom 
    {
        classroomIds.remove(classroomId);
    }

    @Override
    public int compareTo(Student o) 
    {
        return -this.id.compareTo(o.id);
    }
}
