package entity;

import java.io.Serializable;

public class Account implements Serializable
{
    private static final long serialVersionUID = 1312002L;

    private String username;
    private String password;
    private String id;
    private boolean isStudent;

    public Account(String username, String password, String id, boolean isStudent)
    {
        this.username = username;
        this.password = password;
        this.id = id;
        this.isStudent = isStudent;
    }

    public void setID(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getId()
    {
        return id;
    }

    public boolean isStudent()
    {
        return isStudent;
    }
}
