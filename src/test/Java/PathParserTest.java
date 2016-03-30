package test.Java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static com.yourkit.util.Asserts.assertEqual;


//import static com.yourkit.util.Asserts.assertEqual;

/**
 * Created by Marek Bruchatý on 18/03/16.
 */
public class PathParserTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testParseMainPath() throws Exception {
        String mainPath = "/Users/Marek/GitHub/UTDDPlugin/src/main/Java/XYZ.java";
        String testPath = "/Users/Marek/GitHub/UTDDPlugin/src/test/Java/XYZTest.java";

        assertEqual(testPath,"/Users/Marek/GitHub/UTDDPlugin/src/test/Java/XYZTest.java");
    }
}
