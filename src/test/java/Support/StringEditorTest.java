package Support;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.util.ArrayList;

public class StringEditorTest extends TestCase {
    public StringEditorTest (String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(StringEditorTest.class);
    }

    //tests if a simple Json Document can be turned into a single line string without whitespaces ad quotes
    public void testTurnJsonListIntoString(){
        //setup
        String expected = "[{piece:pawn,colour:white,position:a2}]";

        File testFile = new File("save/test/testStringEditor.json");
        FileEditor fileEditor = new FileEditor();
        ArrayList<String> fileContent = fileEditor.read(testFile);

        //test if expected outcome is returned
        assertEquals(expected, StringEditor.turnJsonListIntoString(fileContent));
    }

    //turning the first letter of a string upper case: hello -> Hello
    public void testUpperFirst(){
        //test in case of null
        assertNull(StringEditor.upperFirst(null));

        //test in case of no letter
        assertEquals("", StringEditor.upperFirst(""));

        //test in case of one letter
        assertEquals("H", StringEditor.upperFirst("h"));

        //test normal functionality
        assertEquals("Hallo", StringEditor.upperFirst("hallo"));

    }

    //testing how many lines are in a string
    public void testGetLineCounter(){
        //without line feed
        String test = "hallo";
        assertEquals(1, StringEditor.getLineCounter(test));

        //with one line feed
        test = "hallo \n this";
        assertEquals(2, StringEditor.getLineCounter(test));

        //with multiple line feeds
        test = "hallo \n this \n is \n a \n huge \n test";
        assertEquals(6, StringEditor.getLineCounter(test));

        //line feed at the end of line
        test = "hallo \n";
        assertEquals(1, StringEditor.getLineCounter(test));

        //just line feeds
        test = "\n\n\n";
        assertEquals(3, StringEditor.getLineCounter(test));
    }
}
