package Support;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class StringEditor {

    //turns an arraylist containing a .json into a single line string without spaces, quotes
    public static String turnJsonListIntoString(ArrayList<String> json) {
        StringBuilder returnValue = new StringBuilder();

        for (String jsonLine : json) {
            String chars = " \n\t\"";
            StringBuilder line = new StringBuilder();

            //removes whitespaces and newline-char
            for (String c : jsonLine.split("")) {
                if (!chars.contains(c)) {
                    line.append(c);
                }
            }

            returnValue.append(line);
        }

        return returnValue.toString();
    }

    //collects all characters of a string from a position inside the string to a given end character / end of string
    public static String collectFromTill(int startPos, char endCharacter, String string) {
        StringBuilder builder = new StringBuilder();

        for (int i = startPos; i < string.length(); i++) {
            if (string.charAt(i) == endCharacter) {
                break;
            }
            builder.append(string.charAt(i));
        }

        return builder.toString();
    }

    //turns the first letter of a sting upper case
    public static String upperFirst(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    //getting the display size of a sting in combination with a font
    public static Dimension getStringSize(String string, Font font) {
        AffineTransform af = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(af, true, true);
        Rectangle2D bounds = font.getStringBounds(string, frc);

        return new Dimension((int) bounds.getWidth(), (int) bounds.getHeight() / 2);
    }

    //gets the number of lines in a String if the '\n' is at the end of the string it doesn't count as a new line
    public static int getLineCounter(String string){
        return (int) string.lines().count();
    }
}
