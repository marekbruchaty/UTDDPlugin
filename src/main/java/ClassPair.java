package main.java;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;


/**
 * Author: Marek
 * Date: 16/04/16.
 */

public class ClassPair {
    private Path testDirectory;
    private Path mainDirectory;
    private Path projectDirectory;
    private File testClass;
    private File mainClass;

    public ClassPair(String testDir, String projectDir) throws Exception {
        validatePath(testDir);
        validatePath(projectDir);
        this.testDirectory = Paths.get(testDir);
        this.projectDirectory = Paths.get(projectDir);
        this.mainDirectory = genMainClassDirectory(this.testDirectory);
    }

    private Path genMainClassDirectory(Path tcDirectory) {
        return swapDirectory(tcDirectory, "test", "main");
    }

    private Path swapDirectory(Path path, String from, String to) {
        String shortPath = path.toString().replace(this.projectDirectory.toString(),"");
        StringBuilder sb = new StringBuilder();
        Iterator<Path> pathIterator = Paths.get("/" + shortPath).iterator();
        sb.append(this.projectDirectory).append("/");
        while (pathIterator.hasNext()) {
            Path p = pathIterator.next();
            if (p.toString().toLowerCase().compareTo(from.toLowerCase())==0) sb.append(to);
            else sb.append(p.toString());
            sb.append("/");
        }
        return Paths.get(sb.toString());
    }


    private void validatePath(String path) throws Exception {
        try {
            Path p = Paths.get(path);
            if (!Files.exists(p)) throw new Exception("Directory \" " + path + " \"don't exist");
        } catch (InvalidPathException ipe) {
            throw new Exception("Path \" " + path + " \"is not valid path.");
        }
    }

    public void setTestClass(String testClassName) throws Exception {
        this.testClass = new File(testDirectory + "/" + testClassName);
        if (testClassName.toLowerCase().contains("test")) {
            String testLess = testClassName.substring(0, testClassName.length() - "test.java".length())+".java";
            if (!Utils.isValidJavaClass(testLess)) throw new Exception("Class \" " + testLess + " \"is not valid Java class.");
            this.mainClass = new File(mainDirectory + "/" + testLess);
        }
    }

    public String getTestClassBody() {
        return buildClassBodyString(testDirectory,testClass);
    }

    public String getMainClassBody() {
        return buildClassBodyString(mainDirectory,mainClass);
    }

    private String buildClassBodyString(Path directory, File file) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(getPackage(directory,projectDirectory)).append(";\n\n");
        sb.append("public class ").append(file.getName().replace(".java","")).append(" {\n");
        sb.append("\t//TODO - Automatically generated class\n").append("}");
        return sb.toString();
    }

    private String getPackage(Path path, Path projectPath) {
        String s = path.toString().replace(projectPath.toString(), "");
        return s.substring(s.indexOf("/",1)+1).replace("/",".");
    }

    public Path getMainDirectory() {
        return mainDirectory;
    }

    public Path getTestDirectory() {
        return testDirectory;
    }

    public Path getProjectDirectory() {
        return projectDirectory;
    }

    public File getMainClass() {
        return mainClass;
    }

    public File getTestClass() {
        return testClass;
    }

}
