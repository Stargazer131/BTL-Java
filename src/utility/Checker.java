package utility;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Checker
{
    private Checker() {}
    
    public static boolean isUsernameValid(String username)  // must contain only letters and digits, length is within 4 - 20 letters
    {
        String regex = "^\\w{4,20}$";
        return username.matches(regex);
    }

    public static boolean isPasswordValid(String password)    // a valid password contain at least a uppercase and a lowercase and a digit and length at least 6 and no special character 
    {                                                         // -> an invalid password contain no uppercase or no lowercase or no digit or length from 0 to 5 or at least 1 special character 
        String regex = "^(.{0,5}|[^0-9]+|[^A-Z]+|[^a-z]+|.*\\W+.*)$";      // anything that match this regex is an invalid password      
        return !password.matches(regex);                                                                           
    }

    public static boolean isValidGender(String gender)  // check valid gender
    {
        gender = gender.toLowerCase();
        if(gender.equals("nam") || gender.equals("nữ")) return true;
        return false;
    }

    public static boolean isValidBirthday(String date)            // check valid birthday
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dates = sdf.parse(date);
            if(!date.equals(sdf.format(dates))) return false;             // if before format string is not equal after format string
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(dates);
            int age = 2022-cal.get(Calendar.YEAR);
            return (age >= 10 && age <= 60) ? true : false;
        }

        catch(ParseException e)
        {
            return false;
        }
    }

    public static boolean isValidEmail(String email)   // check valid email address
    {
        email = email.toLowerCase();
        String regex = "^\\w+(?:\\.\\w+)*@(?:\\w+\\.)+[a-z]{2,6}$";  // such as: haovn@stu.ptit.edu.vn
        return email.matches(regex);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) // check valid phone number
    {
        String regex = "^\\d{10}$";                       // only 10-digits numbers are allowed
        return phoneNumber.matches(regex);
    }
}
