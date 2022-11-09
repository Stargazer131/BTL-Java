package manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import entity.Account;

public class AccountManager
{
    private static ArrayList<Account> accounts;

    // add a new account
    public static void addAccount(Account account)
    {
        accounts.add(account);
        writeData();
    }

    // check if the username is already taken
    public static boolean hasUser(String username)
    {
        for(Account account : accounts)
        {
            if(account.getUsername().equals(username))
            {
                return true;
            }
        }
        return false;
    }

    // check if the account is existed in the database
    public static Account findAccount(String username, String password)
    {
        for(Account x : accounts)
        {
            if(x.getUsername().equals(username) && x.getPassword().equals(password)) return x;
        }
        return null;
    }

    // read from account.dat
    @SuppressWarnings("unchecked")
    public static void readData()
    {
        String filename = "resources\\data\\account.dat";
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename)))
        {
            accounts = (ArrayList<Account>)input.readObject();
        }

        catch(Exception e)
        {
            accounts = new ArrayList<>();
        }
    }

    // update data back in the account.dat 
    public static void writeData()
    {
        String filename = "resources\\data\\account.dat";
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            output.writeObject(accounts);
        }

        catch(Exception e)
        {

        }
    }
}