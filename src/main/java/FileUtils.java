package main.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Author: Marek
 * Date: 11/04/16.
 */

public class FileUtils {

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

    public static Path swapDirectory(Path projectDirectory, Path path, String from, String to) {
        String shortPath = path.toString().replace(projectDirectory.toString(),"");
        StringBuilder sb = new StringBuilder();
        Iterator<Path> pathIterator = Paths.get("/" + shortPath).iterator();
        sb.append(projectDirectory).append("/");
        while (pathIterator.hasNext()) {
            Path p = pathIterator.next();
            if (p.toString().toLowerCase().compareTo(from.toLowerCase())==0) sb.append(to);
            else sb.append(p.toString());
            sb.append("/");
        }
        return Paths.get(sb.toString());
    }

}
