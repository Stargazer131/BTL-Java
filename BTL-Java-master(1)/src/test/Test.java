package test;

// import java.io.*;
// import java.util.*;
// import entity.*;
import manager.*;

// test file, nothing special

public class Test
{
    public static void main(String[] args) throws Exception
    {
        StudentManager.readData();
        for(int i = 2; i <= 30; i++) StudentManager.addNewClassroom("B20DCCN228", String.format("Class%02d", i));
    }
}

