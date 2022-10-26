package test;

// import java.io.*;
// import java.util.*;
// import entity.*;
import manager.*;
import java.io.*;
import entity.*;
import java.util.*;

// test file, nothing special

public class Test
{
    public static void main(String[] args) throws Exception
    {
        // ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\BTL-Java\\BTL-Java-master(1)\\resources\\data\\account.dat"));
        // ArrayList<Account> accounts = (ArrayList<Account>) ois.readObject();
        // for(Account x : accounts)
        // {
        //     System.out.println(x.getUsername() + " " + x.getPassword() + " " + x.getId());
        // }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\BTL-Java\\BTL-Java-master(1)\\resources\\data\\teacher.dat"));
        HashMap<String, Teacher> vec = (HashMap<String, Teacher>) ois.readObject();
        for(String x : vec.keySet())
        {
            System.out.println(x);
            System.out.println(vec.get(x).getName());
        }
    }
}

