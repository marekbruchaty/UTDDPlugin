package main.java;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.IllegalFormatException;
import java.util.Iterator;


/**
 * Created by Marek Bruchatý on 01/04/16.
 */
public class Parser {

    public static String getTestPath(String projectPath, String mainPath) {
        final String lTest = "test";
        final String uTest = "Test";
        Path mPath = Paths.get(mainPath);
        Path pPath = Paths.get(projectPath);

        Path mPath_r = pPath.relativize(mPath);
        String testPath_lower = mPath_r.toString().replaceFirst("[mM]ain", lTest);
        String testPath_upper = mPath_r.toString().replaceFirst("[mM]ain", uTest);


        if (testPath_lower.indexOf(lTest) != -1) {
            if (new File(testPath_lower).exists()){
                System.out.println("This path exists = " + testPath_lower);
            }
        } else if (testPath_lower.indexOf(lTest) != -1) {

            if (new File(testPath_upper).exists()){
                System.out.println("This path exists = " + testPath_upper);
            }
        } else {

        }


        System.out.println("mPath.toString() = " + mPath.toString());
        System.out.println("mPath.getFileName() = " + mPath.getFileName());
        System.out.println("mPath.getParent() = " + mPath.getParent().toString());
        System.out.println("pPath.relativize(mPath) = " + pPath.relativize(mPath).toString());


        return null;
    }

    public static Boolean classNameValidator(String str) {

        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException e) {
            return true;
        }

    }

    public static Boolean methodValidator(String str) {

        try {
            new MethodPrototype(str);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static MethodPrototype parseMethodPrototype(String str) throws Exception {
        return new MethodPrototype(str);
    }

    private void print(Exception e) {
        System.out.print(e.getCause());
    }

}
