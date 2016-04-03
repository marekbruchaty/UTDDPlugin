package main.java;

import com.sun.org.apache.bcel.internal.classfile.ExceptionTable;
import com.sun.org.apache.bcel.internal.classfile.Method;
import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;
import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.org.apache.xpath.internal.operations.String.*;

import java.util.IllegalFormatException;

/**
 * Created by Marek Bruchatý on 01/04/16.
 */
public class Parser {

    public String getTestPath(String mainPath) throws IllegalFormatException {

        if (mainPath.contains("Main")) {
            return mainPath.replaceFirst("(Main)","Test");
        } else if (mainPath.contains("main")) {
            return mainPath.replaceFirst("(main)","test");
        } else {
            throw new IllegalArgumentException("Provided path doesn't contain any 'Main' folder.");
        }

    }

    public Boolean methodValidator(String str) {
        try {
            new MethodPrototype(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public MethodPrototype parseMethodPrototype(String str) throws Exception {
        return new MethodPrototype(str);
    }

    private void print(Exception e) {
        System.out.print(e.getCause());
    }

}
