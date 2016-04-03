package main.java;

import java.util.ArrayList;

/**
 * Created by Marek Bruchatý on 02/04/16.
 */
public class MethodPrototype {
    String raw = "";
    String name = "";
    String output = "void";
    String sign = "==";
    ArrayList<String> parameterList = new ArrayList<>();

    public MethodPrototype(String str) throws Exception {
        this.raw = str;
        processString(str);

        System.out.println(this.toString());
    }

    private void processString(String str) throws Exception {
        validate(str);

        String[] str_split = str.replaceAll("\\s+", "").split("\\(|\\)");
        if (str_split.length == 1) {
            this.name = parseName(str_split[0]);
        } else if (str_split.length == 2) {
            this.name = parseName(str_split[0]);
            this.parameterList = makeParametersList(str_split[1]);
        } else if (str_split.length == 3) {
            this.name = parseName(str_split[0]);
            this.parameterList = makeParametersList(str_split[1]);
            this.sign = parseSign(str_split[2]);
            this.output = parseOutput(str_split[2]);
        } else throw new Exception("Incorrect method prototype. Wrong format.");
    }

    private void validate(String str) throws Exception {
        int count = numOccurrences(str,'(') + numOccurrences(str,')');
        if (count > 2) throw new Exception("Incorrect method prototype. Wrong number of parenthases [" + count + "].");
    }

    private int numOccurrences(String str, char c) {
        return str.length() - str.replace(String.valueOf(c), "").length();
    }

    private ArrayList<String> makeParametersList(String str) {
        ArrayList<String> parameters = new ArrayList<>();

        if (str.length() == 0) return parameters;

        String[] par_array = str.split(",");
        for (String s: par_array) {
            if (s.matches("\".*\"")) parameters.add("String");
            else if (s.matches("[0-9]+")) parameters.add("int");
            else if (s.matches("[0-9]+\\.[0-9]*")) parameters.add("float");
            else parameters.add("null");
        }
        return parameters;
    }

    private String parseName(String str) throws Exception {
        if (str.matches("\\w+")) return str;
        else throw new Exception("Incorrect method prototype. Badly formated name ["+str+"].");
    }

    private String parseSign(String str) throws Exception {
        String sign = str.substring(0,2);
        if (sign.matches("!=") || sign.matches("==")) return sign;
        throw new Exception("Incorrect method prototype. Bad sign ["+sign+"].");
    }

    private String parseOutput(String str) throws Exception {
        if (str.length() < 3)
            throw new Exception("Incorrect method prototype. Badly formated return value. ["+str+"].");

        String out = str.substring(2);
        if (out.matches("\".*\"")) return "String";
        else if (out.matches("[0-9]+")) return "int";
        else if (out.matches("[0-9]+\\.[0-9]*")) return "float";
        else return "void";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nPrototype:\t\t\t" + this.raw);
        sb.append("\nMethod name:\t\t" + this.name);
        sb.append("\nMethod parameters:\t");

        if (this.parameterList != null && this.parameterList.size() != 0) {
            for (String s : this.parameterList) {
                sb.append(s + ", ");
            }

            sb.delete(sb.length()-2,sb.length()-1);
        }

        sb.append("\nMethod sign:\t\t" + this.sign);
        sb.append("\nMethod output:\t\t" + this.output);

        return sb.toString();
    }

    public String getRaw() {
        return raw;
    }

    public String getName() {
        return name;
    }

    public String getOutput() {
        return output;
    }

    public String getSign() {
        return sign;
    }

    public ArrayList<String> getParameterList() {
        return parameterList;
    }
}
