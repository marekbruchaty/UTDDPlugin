package test.java;

import main.java.MethodPrototype;
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

//    @Test
//    public void testPath1() throws Exception {
//        String path1 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Main/Java/Sample.java";
//        String test1 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Test/Java/Sample.java";
//        assertEquals(test1, p.getTestPath(path1));
//    }
//
//    @Test
//    public void testPath2() throws Exception {
//        String path2 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Main/Java/AnotherMain/Sample.java";
//        String test2 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Test/Java/AnotherMain/Sample.java";
//        assertEquals(test2, p.getTestPath(path2));
//    }
//
//    @Test
//    public void testPath3() throws Exception {
//        String path3 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Main/Sample.java";
//        String test3 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/Test/Sample.java";
//        assertEquals(test3, p.getTestPath(path3));
//    }
//
//    @Test
//    public void testPath4() throws Exception {
//        String path4 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/main/Sample.java";
//        String test4 = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/test/Sample.java";
//        assertEquals(test4, p.getTestPath(path4));
//    }

    final String projectPath = "/Users/Marek/IdeaProjects/PluginTestProject";

    @Test
    public void simplePath() throws Exception {
        final String mp = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/main/java";
        final String tp = "/Users/Marek/IdeaProjects/PluginTestProject/src/com/company/test/java";
        assertEquals(mp,p.getTestPath(projectPath,mp));
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

    @Test
    public void testMethodValidator12() throws Exception {
        String input = "some_sample(1,2,3) === 0";
        assertFalse(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator13() throws Exception {
        String input = "some sample(1) == 0";
        assertFalse(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator14() throws Exception {
        String input = "some_sample(1,,2,) == 0";
        assertFalse(p.methodValidator(input));
    }

    @Test
    public void testMethodValidator15() throws Exception {
        String input = "some_sample(1,2) == 1 2 3";
        assertFalse(p.methodValidator(input));
    }

    @Test
    public void testMethodName1() throws Exception {
        String input = "sampleFunction(123,\"Sample string\") == 456";
        String name = "sampleFunction";
        MethodPrototype mp = p.parseMethodPrototype(input);
        assertEquals(name, mp.getName());
    }

    @Test
    public void testMethodName2() throws Exception {
        String input = "_sample_Function_(123,\"Sample string\") == 456";
        String name = "_sample_Function_";
        MethodPrototype mp = p.parseMethodPrototype(input);
        assertEquals(name, mp.getName());
    }


    @Test
    public void testMethodName3() throws Exception {
        String input = "sample123Function456(123,\"Sample string\") == 456";
        String name = "sample123Function456";
        MethodPrototype mp = p.parseMethodPrototype(input);
        assertEquals(name, mp.getName());
    }

    @Test
    public void testReturnType1() throws Exception {
        MethodPrototype mp = p.parseMethodPrototype("method() == 1");
        String expected = "int";
        String actual = mp.getReturnType();
        assertEquals(expected,actual);
    }

    @Test
    public void testReturnType1spaces() throws Exception {
        MethodPrototype mp = p.parseMethodPrototype("     method(     )      ==     1     ");
        assertEquals("int",mp.getReturnType());
        assertEquals("method", mp.getName());
        assertEquals("==", mp.getSign());
        assertEquals(0,mp.getParameterList().size());
    }

    @Test
    public void testReturnType2() throws Exception {
        MethodPrototype mp = p.parseMethodPrototype("method()");
        assertEquals("void",mp.getReturnType());
        assertEquals("method",mp.getName());
        assertEquals("==", mp.getSign());
        assertEquals(0,mp.getParameterList().size());
    }

    @Test
    public void testReturnType3() throws Exception {
        MethodPrototype mp = p.parseMethodPrototype("method()==\"test\"");
        assertEquals("String",mp.getReturnType());

    }

    @Test
    public void testReturnType4() throws Exception {
        MethodPrototype mp = p.parseMethodPrototype("method()==\"test 123 ščž {{ ,./;\'\\ }}\"");
        assertEquals("String",mp.getReturnType());
    }

    @Test
    public void testReturnType5() throws Exception {
        MethodPrototype mp = p.parseMethodPrototype(" _fun_123ction123() == 0");
        assertEquals("_fun_123ction123",mp.getName());
    }

    @Test
    public void testReturnType6() throws Exception {
        MethodPrototype mp = p.parseMethodPrototype("a()");
        assertEquals("a",mp.getName());
        assertEquals(0,mp.getParameterList().size());
        assertEquals("void",mp.getReturnType());
        assertEquals("==",mp.getSign());
    }

    @Test
    public void testReturnType7() throws Exception {
        MethodPrototype mp = p.parseMethodPrototype("  method(  )  ==  true   ");
        assertEquals("boolean",mp.getReturnType());
    }

    @Test
    public void testReturnType8() throws Exception {
        MethodPrototype mp = p.parseMethodPrototype("  method(  )  ==  false   ");
        assertEquals("boolean",mp.getReturnType());
    }
}