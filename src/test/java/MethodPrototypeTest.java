package test.java;

import main.java.MethodPrototype;
import main.java.PrimitiveType;
import main.java.TypeValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Author: Marek
 * Date: 20/04/16.
 */
public class MethodPrototypeTest {

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    //Names

    @Test
    public void testMethodNameSimple() throws Exception {
        MethodPrototype mp = new MethodPrototype("method()");
        assertEquals("method", mp.getName());
    }

    @Test
    public void testMethodNameSpacesSimple() throws Exception {
        MethodPrototype mp = new MethodPrototype("   method   ()   ");
        assertEquals("method", mp.getName());
    }

    @Test
    public void testMethodNameNumbers() throws Exception {
        MethodPrototype mp = new MethodPrototype("method123numbers456()");
        assertEquals("method123numbers456", mp.getName());
    }

    @Test
    public void testMethodNameUnderscores() throws Exception {
        MethodPrototype mp = new MethodPrototype("__m__e__t__h__o__d__1__2__3__()");
        assertEquals("__m__e__t__h__o__d__1__2__3__", mp.getName());
    }

    //Parameters

    @Test
    public void testMethodParametersInt() throws Exception {
        MethodPrototype mp = new MethodPrototype("method(1, 22, 333)");
        ArrayList<TypeValuePair> parameters = mp.getParameters();
        assertEquals(PrimitiveType.INT, parameters.get(0).getType());
        assertEquals("1", parameters.get(0).getValue());
        assertEquals(PrimitiveType.INT, parameters.get(1).getType());
        assertEquals("22", parameters.get(1).getValue());
        assertEquals(PrimitiveType.INT, parameters.get(2).getType());
        assertEquals("333", parameters.get(2).getValue());
    }

    @Test
    public void testMethodParametersStrings() throws Exception {
        MethodPrototype mp = new MethodPrototype("method(\"a\", \"bb\", \"ccc\")");
        ArrayList<TypeValuePair> parameters = mp.getParameters();
        assertEquals(PrimitiveType.STRING, parameters.get(0).getType());
        assertEquals("\"a\"", parameters.get(0).getValue());
        assertEquals(PrimitiveType.STRING, parameters.get(1).getType());
        assertEquals("\"bb\"", parameters.get(1).getValue());
        assertEquals(PrimitiveType.STRING, parameters.get(2).getType());
        assertEquals("\"ccc\"", parameters.get(2).getValue());
    }

    @Test
    public void testMethodParametersFloat() throws Exception {
        MethodPrototype mp = new MethodPrototype("method(1.2345, 2., 0.0)");
        ArrayList<TypeValuePair> parameters = mp.getParameters();
        assertEquals(PrimitiveType.FLOAT, parameters.get(0).getType());
        assertEquals("1.2345", parameters.get(0).getValue());
        assertEquals(PrimitiveType.FLOAT, parameters.get(1).getType());
        assertEquals("2.", parameters.get(1).getValue());
        assertEquals(PrimitiveType.FLOAT, parameters.get(2).getType());
        assertEquals("0.0", parameters.get(2).getValue());
    }

    @Test
    public void testMethodParametersBool() throws Exception {
        MethodPrototype mp = new MethodPrototype("method(true, false, true)");
        ArrayList<TypeValuePair> parameters = mp.getParameters();
        assertEquals(PrimitiveType.BOOLEAN, parameters.get(0).getType());
        assertEquals("true", parameters.get(0).getValue());
        assertEquals(PrimitiveType.BOOLEAN, parameters.get(1).getType());
        assertEquals("false", parameters.get(1).getValue());
        assertEquals(PrimitiveType.BOOLEAN, parameters.get(2).getType());
        assertEquals("true", parameters.get(2).getValue());
    }

    @Test
    public void testMethodParametersCustomObjects() throws Exception {
        MethodPrototype mp = new MethodPrototype("method(Foo, Bar)");
        ArrayList<TypeValuePair> parameters = mp.getParameters();
        assertEquals(PrimitiveType.OBJECT, parameters.get(0).getType());
        assertEquals("Foo", parameters.get(0).getValue());
        assertEquals(PrimitiveType.OBJECT, parameters.get(1).getType());
        assertEquals("Bar", parameters.get(1).getValue());
    }

    //Comparative sign

    @Test
    public void testMethodSignEquals() throws Exception {
        MethodPrototype mp = new MethodPrototype("method() == 0");
        assertEquals("==", mp.getComparativeSign());
    }

    @Test
    public void testMethodSignNotEquals() throws Exception {
        MethodPrototype mp = new MethodPrototype("method() != 0");
        assertEquals("!=", mp.getComparativeSign());
    }

    //Return value

    @Test
    public void testMethodReturnTypeInt() throws Exception {
        MethodPrototype mp = new MethodPrototype("method() == 0");
        assertEquals(PrimitiveType.INT, mp.getReturnType().getType());
    }

    @Test
    public void testMethodReturnTypeFloat() throws Exception {
        MethodPrototype mp = new MethodPrototype("method() == 0.0");
        assertEquals(PrimitiveType.FLOAT, mp.getReturnType().getType());
    }

    @Test
    public void testMethodReturnTypeString() throws Exception {
        MethodPrototype mp = new MethodPrototype("method() == \"abc123\"");
        assertEquals(PrimitiveType.STRING, mp.getReturnType().getType());
    }

    @Test
    public void testMethodReturnTypeChar() throws Exception {
        MethodPrototype mp = new MethodPrototype("method() == 'A'");
        assertEquals(PrimitiveType.CHAR, mp.getReturnType().getType());
    }

    @Test
    public void testMethodReturnTypeObject() throws Exception {
        MethodPrototype mp = new MethodPrototype("method() == Foo");
        assertEquals(PrimitiveType.OBJECT, mp.getReturnType().getType());
    }

    @Test
    public void testMethodReturnTypeVoid() throws Exception {
        MethodPrototype mp = new MethodPrototype("method() == void");
        assertEquals(PrimitiveType.VOID, mp.getReturnType().getType());
    }

    //Not valid name

    @Test
    public void testInvalidNameNumbers() throws Exception {
        assertFalse(isValid("123method()"));
    }

    @Test
    public void testInvalidNameParentheses() throws Exception {
        assertFalse(isValid("method(()"));
    }

    @Test
    public void testInvalidNameSpace() throws Exception {
        assertFalse(isValid("met hod()"));
    }

    //Not valid comparative sign

    @Test
    public void testInvalidSignSingle() throws Exception {
        assertFalse(isValid("method() = 0"));
    }

    @Test
    public void testInvalidSignDouble() throws Exception {
        assertFalse(isValid("method() === 0"));
    }

    @Test
    public void testInvalidSignSpace() throws Exception {
        assertFalse(isValid("method() == == 0"));
    }

    @Test
    public void testInvalidSignUnknown() throws Exception {
        assertFalse(isValid("method() >= 0"));
    }

    @Test
    public void testInvalidSignMissing() throws Exception {
        assertFalse(isValid("method() 0"));
    }

    //Not valid parameters

    @Test
    public void testInvalidParametersSpace() throws Exception {
        assertFalse(isValid("method(1, ,2)"));
    }

    @Test
    public void testInvalidParametersEmpty() throws Exception {
        assertFalse(isValid("method(1,,2)"));
    }

    @Test
    public void testInvalidParametersAllEmpty() throws Exception {
        assertFalse(isValid("method(,,)"));
    }

    @Test
    public void testInvalidParametersNoComma() throws Exception {
        assertFalse(isValid("method(1\"abc\")"));
    }

    @Test
    public void testInvalidParametersDoubleParentheses() throws Exception {
        assertFalse(isValid("method(())"));
    }

    //Valid name

    @Test
    public void testValidNameUnderscore() throws Exception {
        assertTrue(isValid("_()"));
    }

    @Test
    public void testValidNameUnderscoresNumbers() throws Exception {
        assertTrue(isValid("_1_2_3()"));
    }

    private boolean isValid(String str) {
        try {
            new MethodPrototype(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
