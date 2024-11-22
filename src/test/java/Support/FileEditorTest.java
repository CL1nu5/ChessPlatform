package Support;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileEditorTest extends TestCase {

    public FileEditorTest (String testName){
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(FileEditorTest.class);
    }

    public void testFileGetsCreatedWriter(){
        //setup
        String[] text = {"Hallo das ist der einzige deutsche Satz in diesem Code", "Verarscht, das ist auch einer haha"};
        File file = new File("save/test.txt");
        FileEditor editor = new FileEditor();

        //file shouldn't exist before the test
        assertFalse(file.exists());

        //test if file gets created by writing the text
        editor.write(new ArrayList<>(List.of(text)), file, false);
        assertTrue(file.exists());

        //test if file can be removed properly
        assertTrue(file.delete());
    }

    public void testWriteAndRead(){
        //setup
        String[] text = {"Hallo, das ist der einzige deutsche Satz in diesem Code", "Verarscht, das ist auch einer haha"};
        File file = new File("save/test.txt");
        FileEditor editor = new FileEditor();

        //write text in file
        editor.write(new ArrayList<>(List.of(text)), file, false);

        //read text from file
        String[] readContent = editor.read(file).toArray(new String[0]);

        //compare the read value, with the pre-defined value
        for (int i = 0; i < text.length; i++){
            assertEquals(text[i], readContent[i]);
        }

        //remove file
        assertTrue(file.delete());
    }
}
