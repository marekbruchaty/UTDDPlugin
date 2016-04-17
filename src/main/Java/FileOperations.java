package main.java;

import java.io.*;

/**
 * Created by Marek Bruchatý on 31/03/16.
 */
public class FileOperations {

    public static void createFile2(String file) {
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
        writer.close();
    }

    /**
     * Returns true if file was created successfully, false otherwise
     * */
    public static boolean createFile(File file, String content) throws IOException {
        if (file.getParent() != null)
            file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            return true;
        } catch (IOException e) {
            if (file.exists()) {
                if (file.delete()) {
                    throw new IOException("Delete action error. File was created but can not be written to.");
                }
            }
            return false;
        }
    }
}
