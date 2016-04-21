package main.java;

import java.util.ArrayList;

import static main.java.PrimitiveType.*;

/**
 * Author: Marek Bruchatý
 * Date: 02/04/16.
 */

public class MethodPrototype {
    private String raw;
    private String name;
    private String comparativeSign;
    private TypeValuePair returnType;
    private ArrayList<TypeValuePair> parameters;


    public MethodPrototype(String str) throws Exception {
        raw = str;
        setDefaults();
        processString(str);
    }

    private void setDefaults() {
        this.name = "";
        this.comparativeSign = "==";
        this.returnType = new TypeValuePair(VOID,VOID.getName());
        this.parameters = new ArrayList<>();
    }

    private void processString(String str) throws Exception {
        str = str.replaceAll("\\s+", " ").trim();
        if (str.isEmpty()) throw new Exception("No method prototype.");
        validate(str);

        String[] str_split = str.split("\\(|\\)");
        if (str_split.length == 1) {
            this.name = parseName(str_split[0]);
        } else if (str_split.length == 2) {
            this.name = parseName(str_split[0]);
            this.parameters = makeParametersList(str_split[1]);
        } else if (str_split.length == 3) {
            this.name = parseName(str_split[0]);
            this.parameters = makeParametersList(str_split[1]);
            this.comparativeSign = extractComparativeSign(str_split[2]);
            this.returnType = extractReturnTypeValuePair(str_split[2]);
        } else throw new Exception("Wrong use of parentheses.");
    }

    private void validate(String str) throws Exception {
        if (!str.substring(0,1).matches("[a-zA-Z_]+")) throw new Exception("Wrong name format.");
        int lCount = numOccurrences(str,'(');
        if (lCount == 0) throw new Exception("No opening parenthesis.");
        else if (lCount > 1) throw new Exception("Too many opening parentheses.");
        int rCount = numOccurrences(str,')');
        if (rCount == 0) throw new Exception("No closing parentheses");
        else if (rCount > 1) throw new Exception("Too many closing parentheses");
    }

    private int numOccurrences(String str, char c) {
        return str.length() - str.replace(String.valueOf(c), "").length();
    }

    private ArrayList<TypeValuePair> makeParametersList(String str) throws Exception {
        ArrayList<TypeValuePair> parameters = new ArrayList<>();

        str = str.replaceAll("\\s+", "");
        if (str.length() == 0) return parameters;
        if (str.contains(",,")) throw new Exception("Empty parameter.");
        for (String s: str.split(",")) {
            if (s.isEmpty()) throw new Exception("Empty parameter.");
            parameters.add(extractTypeValuePair(s));
        }
        return parameters;
    }

    private String parseName(String str) throws Exception {
        if (str.endsWith(" ")) str = str.substring(0,str.length()-1);
        if (str.matches("\\w+")) return str;
        else throw new Exception("Wrong name format ["+str+"].");
    }

    private String extractComparativeSign(String str) throws Exception {
        if (str.length() < 3)
            throw new Exception("Wrong comparative comparativeSign ["+str.replaceAll("\\s+", "")+"].");
        str = str.replaceAll("\\s+", "");
        String sign = str.substring(0, 2);
        if (sign.matches("!=") || sign.matches("==")) return sign;
        throw new Exception("Wrong comparative comparativeSign [" + sign + "].");
    }

    private TypeValuePair extractReturnTypeValuePair(String str) throws Exception {
        str = str.trim();
        if (str.length() < 3) throw new Exception("Wrong return value format. ["+str+"].");
        str = str.substring(2);
        if (str.startsWith(" ")) str = str.substring(1);
        if (str.length() == 0) throw new Exception("Wrong return value format. ["+str+"].");
        return extractTypeValuePair(str);
    }

    private TypeValuePair extractTypeValuePair(String str) throws Exception {
        PrimitiveType pt;
        if (str.matches("\".*\"")) pt = STRING;
        else if (str.matches("\'.\'")) pt = CHAR;
        else if (str.matches("[0-9]+")) pt = INT;
        else if (str.matches("[0-9]+\\.[0-9]*")) pt = FLOAT;
        else if (str.matches("true") || str.matches("false")) pt = BOOLEAN;
        else if (str.matches("void")) pt = VOID;
        else if (str.matches("[_a-zA-Z]+[_a-zA-Z0-9]*")) pt = OBJECT;
        else throw new Exception("Wrong return value format. ["+str+"].");
        return new TypeValuePair(pt,str);
    }

    public String constructMethod() {

        StringBuilder sb = new StringBuilder();
        sb.append("public ").append(this.getReturnType().getType().getName()).append(" ").append(this.getName()).append("(");

        int[] index = new int[]{1,1,1,1,1};
        if (this.parameters.size() != 0) {
            for (TypeValuePair p : this.parameters) {
                sb.append(p.getType().getName()).append(" ");
                switch (p.getType()) {
                    case STRING:
                        sb.append(STRING.getName()).append(index[0]++);
                        break;
                    case INT:
                        sb.append(INT.getName()).append(index[1]++);
                        break;
                    case FLOAT:
                        sb.append(FLOAT.getName()).append(index[2]++);
                        break;
                    case BOOLEAN:
                        sb.append(BOOLEAN.getName()).append(index[3]++);
                        break;
                    case OBJECT:
                        sb.append(OBJECT.getName()).append(index[4]++);
                        break;
                }
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append(") {\n");
        sb.append("\t//TODO - Automatically generated method\n\n");
        switch (this.returnType.getType()) {
            case STRING: sb.append("return \"\";");
                break;
            case INT: sb.append("return 0;");
                break;
            case FLOAT: sb.append("return 0.0;");
                break;
            case BOOLEAN: sb.append("return false;");
                break;
            case OBJECT: sb.append("return void;");
                break;
            case VOID:
                break;
        }
        sb.append("\n}");

        return sb.toString();
    }

    public String constructTestMethod() {
        StringBuilder sb = new StringBuilder();
        sb.append("@Test\n");
        sb.append("public void test").append(getName()).append("() throws Exception {\n");
        sb.append("\t//TODO - Automatically generated test\n");
        sb.append("\t").append(getReturnType().getType().getName()).append(" expected = ").append(getReturnType().getValue()).append(";\n");
        sb.append("\t").append(getReturnType().getType().getName()).append(" actual = ").append(getName()).append("(");

        for (TypeValuePair tvp:getParameters()) {
            sb.append(tvp.getValue()).append(", ");
        }
        if (getParameters().size() != 0) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append(");\n");


        switch (this.comparativeSign) {
            case "==": {
                if (getReturnType().getType() == BOOLEAN) {
                    sb.append("assertTrue();");
                }
                else {
                    sb.append("assertEquals(expected, actual);");
                }
            }
            break;
            case "!=": {
                if (getReturnType().getType() == BOOLEAN) {
                    sb.append("assertFalse();");
                }
                else {
                    sb.append("assertNotEquals(expected, actual);");
                }
            }
            break;
        }

        sb.append("\n}");
        return sb.toString();
    }

    public String getRaw() {
        return raw;
    }

    public String getName() {
        return name;
    }

    public String getComparativeSign() {
        return comparativeSign;
    }

    public TypeValuePair getReturnType() {
        return returnType;
    }

    public ArrayList<TypeValuePair> getParameters() {
        return parameters;
    }

}

