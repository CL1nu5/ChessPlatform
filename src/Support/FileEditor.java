package Support;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class FileEditor {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    //reads all the content of a file and returns it as a ArrayList of file rows
    public ArrayList<String> read (File filePath){
        ArrayList<String> content = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                content.add(str);
            }
            in.close();

        } catch (FileNotFoundException e) {
            logger.warning("file editor:read - cant access file / file does not exist");
        } catch (IOException e) {
            logger.warning("file editor:read - cant read file content");
        }

        return content;
    }

    //writes the given content in a file
    public void write(ArrayList<String> content, File file, boolean append){
        try {
            //overwrites the current file if append is false -> all content is deleted, else appends it to the file
            //if there isn't a file, it creates it
            FileWriter fileWriter = new FileWriter(file, append);
            for(String s:content) {
                fileWriter.write(s+"\n");
            }
            fileWriter.close();
        }
        catch (IOException e){
            logger.warning("file editor:write - cant write in file");
        }
    }
}
