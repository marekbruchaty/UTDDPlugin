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
    private String sign;
    private PrimitiveType returnType;
    private ArrayList<PrimitiveType> parameterTypes;
    private ArrayList<String> parameterValues;


    public MethodPrototype(String str) throws Exception {
        raw = str;
        name = "";
        sign = "==";
        returnType = VOID;
        parameterTypes = new ArrayList<>();
        processString(str);
    }

    private void processString(String str) throws Exception {
        str = str.replaceAll("\\s+", " ");
        if (str.length() == 0 || str.matches(" ")) throw new Exception("No method prototype.");
        if (str.startsWith(" ")) str = str.substring(1);
        if (str.endsWith(" ")) str = str.substring(0,str.length()-1);
        validate(str);

        String[] str_split = str.split("\\(|\\)");
        if (str_split.length == 1) {
            this.name = parseName(str_split[0]);
        } else if (str_split.length == 2) {
            this.name = parseName(str_split[0]);
            this.parameterTypes = makeParametersList(str_split[1]);
        } else if (str_split.length == 3) {
            this.name = parseName(str_split[0]);
            this.parameterTypes = makeParametersList(str_split[1]);
            this.sign = parseSign(str_split[2]);
            this.returnType = returnType(str_split[2]);
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

    private ArrayList<PrimitiveType> makeParametersList(String str) throws Exception {
        ArrayList<PrimitiveType> parameters = new ArrayList<>();

        str = str.replaceAll("\\s+", "");
        if (str.length() == 0) return parameters;

        String[] par_array = str.split(",");
        for (String s: par_array) {
            if (s.isEmpty()) throw new Exception("Empty parameter.");
            parameters.add(parseType(s));
        }
        return parameters;
    }

    private String parseName(String str) throws Exception {
        if (str.endsWith(" ")) str = str.substring(0,str.length()-1);
        if (str.matches("\\w+")) return str;
        else throw new Exception("Wrong name format ["+str+"].");
    }

    private String parseSign(String str) throws Exception {
        if (str.length() < 3)
            throw new Exception("Wrong comparative sign ["+str.replaceAll("\\s+", "")+"].");
        str = str.replaceAll("\\s+", "");
        String sign = str.substring(0, 2);
        if (sign.matches("!=") || sign.matches("==")) return sign;
        throw new Exception("Wrong comparative sign [" + sign + "].");
    }

    private PrimitiveType returnType(String str) throws Exception {
        if (str.startsWith(" ")) str = str.substring(1);
        if (str.length() < 3) throw new Exception("Wrong return value format. ["+str+"].");
        str = str.substring(2);
        if (str.startsWith(" ")) str = str.substring(1);
        if (str.length() == 0) throw new Exception("Wrong return value format. ["+str+"].");
        PrimitiveType type = parseType(str);
        if (type == VOID) throw new Exception("Wrong return value. ["+str+"].");
        return type;
    }

    private PrimitiveType parseType(String str) {
        if (str.matches("\".*\"")) return STRING;
        else if (str.matches("[0-9]+")) return INT;
        else if (str.matches("[0-9]+\\.[0-9]*")) return FLOAT;
        else if (str.matches("true") || str.matches("false")) return BOOLEAN;
        else return VOID;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nPrototype:\t\t\t").append(this.raw);
        sb.append("\nMethod name:\t\t").append(this.name);
        sb.append("\nMethod parameters:\t");

        if (this.parameterTypes != null && this.parameterTypes.size() != 0) {
            for (PrimitiveType p : this.parameterTypes) {
                sb.append(p.getName()).append(", ");
            }

            sb.delete(sb.length()-2,sb.length()-1);
        }

        sb.append("\nMethod sign:\t\t").append(this.sign);
        sb.append("\nMethod returnType:\t\t").append(this.returnType);

        return sb.toString();
    }

    public String constructMethod() {

        StringBuilder sb = new StringBuilder();

        sb.append("public ").append(this.getReturnType()).append(" ").append(this.getName()).append("(");

        int[] index = new int[]{1,1,1,1,1};
        if (this.getParameterTypes().size() != 0) {
            for (PrimitiveType p : this.getParameterTypes()) {
                sb.append(p.getName()).append(" ");
                switch (p) {
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
                    case VOID:
                        sb.append(VOID.getName()).append(index[4]++);
                        break;
                }
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append(") {\n");
        sb.append("  //TODO - Add method implementation here\n\n");
        switch (this.returnType) {
            case STRING: sb.append("return \"\";");
                break;
            case INT: sb.append("return 0;");
                break;
            case FLOAT: sb.append("return 0.0;");
                break;
            case BOOLEAN: sb.append("return false;");
                break;
            case VOID:
                break;
        }
        sb.append("\n}");

        return sb.toString();
    }

    public String constructTestMethod2() {

        StringBuilder sb = new StringBuilder();
        sb.append("@Test\n");
        sb.append("public void test").append(this.name).append("() {\n");
        sb.append("  //TODO - Add test implementation here\n\n");
        sb.append("  ");

        switch (this.sign) {
            case "==": {
                if (this.returnType == BOOLEAN)
                    sb.append("assertTrue();");
                else sb.append("assertEquals(, );");
            }
                break;
            case "!=": {
                if (this.returnType == BOOLEAN)
                    sb.append("assertFalse();");
                else sb.append("assertNotEquals(, );");
            }
                break;
        }

        sb.append("\n}");
        return sb.toString();
    }

    public String constructTestMethod() {
        StringBuilder sb = new StringBuilder();
        sb.append("@Test\n");
        sb.append("public void test").append(this.getName()).append("() throws Exception {\n");
        sb.append("\t//TODO - Automatically generated test\n");
        sb.append("\texpectedType expected = expectedValue;\n");
        sb.append("\tactualType = actualValue;\n");
        sb.append("\tassertEquals(expected, actual);\n");
        sb.append("}");
        return sb.toString();
    }

    public String getRaw() {
        return raw;
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType.getName();
    }

    public String getSign() {
        return sign;
    }

    public ArrayList<PrimitiveType> getParameterTypes() {
        return parameterTypes;
    }

}

enum PrimitiveType {
    STRING("String"),
    INT("int"),
    FLOAT("float"),
    BOOLEAN("boolean"),
    CHAR("char"),
    VOID("void");

    private String name;

    PrimitiveType(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}

class TypeValuePair {
    private PrimitiveType type;
    private String value;

    public TypeValuePair(PrimitiveType type, String value) {
        this.type = type;
        this.value = value;
    }

    public PrimitiveType getType() {
        return type;
    }

    public void setType(PrimitiveType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}