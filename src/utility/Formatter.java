package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Formatter 
{
    private Formatter() {}

    public static String toTitle(String str)    // format the name: nGyEN  VAn  a -> Nguyen Van A
    {
        String result = "";
        String[] arr = str.trim().split("\\s+");
        for(String x : arr) result += format(x) + " ";
        return result.trim();
    }

    public static String formatGender(String gender)
    {
        return toTitle(gender);
    }

    public static String formatBirthday(String birthday) 
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(sdf.parse(birthday));
        }

        catch(ParseException e) { return ""; }
    }

    private static String format(String s)              // nGyEN -> Nguyen
    {
        if(s.equals("")) return "";
        String result = ""+Character.toUpperCase(s.charAt(0));
        if(s.length() > 1) result += s.substring(1).toLowerCase();
        return result;
    }
}
