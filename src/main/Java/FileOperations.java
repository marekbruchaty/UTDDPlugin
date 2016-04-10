package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Marek Bruchatý on 31/03/16.
 */
public class FileOperations {

    public static void createFile(String file) {
        File targetFile = new File(file);
        File parent = targetFile.getParentFile();

        if(!parent.exists() && !parent.mkdirs()){
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

//        try {
//            targetFile.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(targetFile.getAbsolutePath(), "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println("public class [CLASS-NAME] {");
        writer.println("}");
        writer.close();
    }

}
