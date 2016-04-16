package main.java;

import java.util.IllegalFormatException;

/**
 * Created by Marek Bruchatý on 01/04/16.
 */
public class Parser {

    public static String getTestPath(String mainPath) throws IllegalFormatException {

        if (mainPath.contains("Main")) {
            return mainPath.replaceFirst("(Main)","Test");
        } else if (mainPath.contains("main")) {
            return mainPath.replaceFirst("(main)","test");
        } else {
            throw new IllegalArgumentException("Provided path doesn't contain any 'Main' folder.");
        }
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
