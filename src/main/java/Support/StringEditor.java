package Support;

import java.util.ArrayList;
import java.util.Arrays;

public class StringEditor {
    //turns an arraylist containing a .json into a single line string without spaces, quotes
    public static String turnJsonListIntoString(ArrayList<String> json){
        StringBuilder returnValue = new StringBuilder();

        for (String jsonLine: json){
            String chars = " \n\t\"";
            StringBuilder line = new StringBuilder();

            //removes whitespaces and newline-char
            for (String c: jsonLine.split("")){
                if (!chars.contains(c)){
                    line.append(c);
                }
            }

            returnValue.append(line);
        }

        return returnValue.toString();
    }
}
