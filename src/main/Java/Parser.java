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

    public static String createMethod(MethodPrototype mp) {

        StringBuilder sb = new StringBuilder();

        sb.append("public ").append(mp.getReturnType()).append(" ").append(mp.getName()).append("(");

        int index = 1;
        for (String s: mp.getParameterList()) {
            sb.append(s).append(" par").append(index++).append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);

        sb.append(") {");
        sb.append("\n");
        sb.append("}");

//        LiveTemplateBuilder ltb = new LiveTemplateBuilder();


        return sb.toString();
    }

    public static String createTestMethod(MethodPrototype mp) {

        return "";
    }

    private void print(Exception e) {
        System.out.print(e.getCause());
    }

}
