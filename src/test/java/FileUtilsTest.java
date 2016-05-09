package test.java;

import main.java.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

/**
 * Author: Marek
 * Date: 20/04/16.
 */
public class FileUtilsTest {

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void swapWindows1() throws Exception {
        String project = "C:\\User\\Administrator\\IdeaProjects\\testing";
        String test = "C:\\User\\Administrator\\IdeaProjects\\testing\\src\\com\\company\\test";
        String expected = "C:\\User\\Administrator\\IdeaProjects\\testing\\src\\com\\company\\main";
        assertEquals(expected, FileUtils.swapDirectory(Paths.get(project),Paths.get(test),"test","main").toString());
    }

    @Test
    public void swapWindows2() throws Exception {
        String project = "F:\\User\\Administrator\\IdeaProjects\\testing";
        String test = "F:\\User\\Administrator\\IdeaProjects\\testing\\src\\com\\company\\test\\java\\one\\two\\three";
        String expected = "F:\\User\\Administrator\\IdeaProjects\\testing\\src\\com\\company\\main\\java\\one\\two\\three";
        assertEquals(expected, FileUtils.swapDirectory(Paths.get(project),Paths.get(test),"test","main").toString());
    }


    @Test
    public void swapMac1() throws Exception {
        String project = "/Users/Marek/GitHub/UTDDPlugin";
        String test = "/Users/Marek/GitHub/UTDDPlugin/src/test/java";
        String expected = "/Users/Marek/GitHub/UTDDPlugin/src/main/java";
        assertEquals(expected, FileUtils.swapDirectory(Paths.get(project),Paths.get(test),"test","main").toString());
    }

    @Test
    public void swapMac2() throws Exception {
        String project = "/Users/Marek/GitHub/UTDDPlugin";
        String test = "/Users/Marek/GitHub/UTDDPlugin/src/test/java/one/two/three";
        String expected = "/Users/Marek/GitHub/UTDDPlugin/src/main/java/one/two/three";
        assertEquals(expected, FileUtils.swapDirectory(Paths.get(project),Paths.get(test),"test","main").toString());
    }

}
