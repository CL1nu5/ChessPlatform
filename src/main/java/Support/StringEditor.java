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

    public static String collectFromTill(int startPos, char endCharacter, String string){
        StringBuilder builder = new StringBuilder();

        for (int i = startPos; i < string.length(); i++){
            if (string.charAt(i) == endCharacter){
                break;
            }
            builder.append(string.charAt(i));
        }

        return builder.toString();
    }

    public static String upperFirst(String string){
        if (string == null || string.isEmpty()){
            return string;
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
