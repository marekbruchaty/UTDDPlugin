package main.java;

/**
 * Author: Marek Bruchatý
 * Date: 01/04/16.
 */

public class Parser {

    /**
     * Used to identify test method with Test affix
     * @param name Method name to be tested
     * */
    public static boolean isTestMethod(String name) {
        return name.toLowerCase().matches(".+test");
    }

}
