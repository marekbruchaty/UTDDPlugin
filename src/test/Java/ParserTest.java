package test.java;

import main.java.Parser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Marek Bruchatý on 01/04/16.
 */
public class ParserTest {

    Parser p;

    @Before
    public void setUp() throws Exception {
        p = new Parser();
    }

    @After
    public void tearDown() throws Exception {}

    //Main path -> Test path

    @Test
    public void testPath1() throws Exception {
        String path1 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Main/Java/Sample.java";
        String test1 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Test/Java/Sample.java";
        assertEquals(test1, p.getTestPath(path1));
    }

    @Test
    public void testPath2() throws Exception {
        String path2 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Main/Java/AnotherMain/Sample.java";
        String test2 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Test/Java/AnotherMain/Sample.java";
        assertEquals(test2, p.getTestPath(path2));
    }

    @Test
    public void testPath3() throws Exception {
        String path3 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Main/Sample.java";
        String test3 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Test/Sample.java";
        assertEquals(test3, p.getTestPath(path3));
    }

    @Test
    public void testPath4() throws Exception {
        String path4 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/main/Sample.java";
        String test4 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/test/Sample.java";
        assertEquals(test4, p.getTestPath(path4));
    }

    @Test
    public void testMethodValidator1() throws Exception {
        String input = "a() == 1";
        assertTrue(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator2() throws Exception {
        String input = "abc_def(1,2,3,4,5) == 1";
        assertTrue(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator3() throws Exception {
        String input = "abc_def_123(1,2,3,4,5) == 1";
        assertTrue(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator4() throws Exception {
        String input = "abc(1,\"sample\",2,3) == 1";
        assertTrue(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator5() throws Exception {
        String input = "abc_def(\"first\",\"second\",\"third\") == \"first\"";
        assertTrue(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator6() throws Exception {
        String input = "abc()";
        assertTrue(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator7() throws Exception {
        String input = "abc())";
        assertFalse(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator8() throws Exception {
        String input = "abc_def(1,2,3,4,5) == ";
        assertFalse(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator9() throws Exception {
        String input = "() == 1";
        assertFalse(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator10() throws Exception {
        String input = "some_sample(1,2,3,\"four\",\"five\",6) != 0";
        assertTrue(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator11() throws Exception {
        String input = "some_sample(1.234,2.3,3.456789,\"four\",\"five\",6.7) != 0";
        assertTrue(p.methodValidator(input));
    }

//
//    @Test
//    public void testMethodName1() throws Exception {
//        String input = "sampleFunction(123,\"Sample string\") = 456";
//        String name = "sampleFunction";
//        assertEquals(name, p.getMethodName(input));
//    }
//
//    @Test
//    public void testMethodName2() throws Exception {
//        String input = "_sample_Function(123,\"Sample string\") = 456";
//        String name = "_sample_Function";
//        assertEquals(name, p.getMethodName(input));
//    }
//
//    @Test
//    public void testMethodName3() throws Exception {
//        String input = "sample-Function(123,\"Sample string\") = 456";
//        String name = "sample-Function";
//        assertEquals(name, p.getMethodName(input));
//    }
//
//    @Test
//    public void testMethodName4() throws Exception {
//        String input = "sample123Function456(123,\"Sample string\") = 456";
//        String name = "sample123Function456";
//        assertEquals(name, p.getMethodName(input));
//    }


}