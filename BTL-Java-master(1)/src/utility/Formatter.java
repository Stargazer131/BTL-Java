package utility;

/** Format data
* @author hao
*/

import java.text.*;

public class Formatter 
{
    private Formatter() {}

    public static String formatName(String name)    // format the name: nGyEN  VAn  a -> Nguyen Van A
    {
        String result = "";
        String[] arr = name.trim().split("\\s+");
        for(String x : arr) result += toTitle(x) + " ";
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

    private static String toTitle(String s)              // nGyEN -> Nguyen
    {
        String result = ""+Character.toUpperCase(s.charAt(0));
        if(s.length() > 1) result += s.substring(1).toLowerCase();
        return result;
    }
}