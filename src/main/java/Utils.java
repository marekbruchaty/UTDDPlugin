package main.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Author: Marek
 * Date: 11/04/16.
 */

public class Utils {

    public static boolean isValidJavaClass(String str) {
        for (String part : str.split("\\.")) {
            if (javaKeywords.contains(part) ||  !JAVA_CLASS_NAME_PART_PATTERN.matcher(part).matches())
                return false;
        }
        return str.length() > 0;
    }

    private static final Pattern JAVA_CLASS_NAME_PART_PATTERN = Pattern.compile("[A-Za-z_$]+[a-zA-Z0-9_$]*");

    private static final Set<String> javaKeywords = new HashSet<String>(Arrays.asList(
            "abstract",      "assert",        "boolean",      "break",           "byte",
            "case",         "catch",         "char",         "class",           "const",
            "continue",     "default",       "do",           "double",          "else",
            "enum",         "extends",       "false",        "final",           "finally",
            "float",        "for",           "goto",         "if",              "implements",
            "import",       "instanceof",    "int",          "interface",       "long",
            "native",       "new",           "null",         "package",         "private",
            "protected",    "public",        "return",       "short",           "static",
            "strictfp",     "super",         "switch",       "synchronized",    "this",
            "throw",        "throws",        "transient",    "true",            "try",
            "void",         "volatile",      "while"
    ));

}
