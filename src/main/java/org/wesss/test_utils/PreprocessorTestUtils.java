package org.wesss.test_utils;

import org.hamcrest.Matcher;

import java.io.File;

/**
 * TODO document preprocessor test utils
 */
public class PreprocessorTestUtils {

    /**
     *
     * @param fileName the name of the source file to retrieve without the .java extension
     * @requires a matching .java file exists in src/test/resources/
     * @return the file specified
     */
    public static File getJavaSourceFile(String fileName) {
        //TODO file existence checking
        return new File("src/test/resources/" + fileName + ".java");
    }

    /**
     *
     * @return
     */
    public static Matcher<File> compilesWithoutError() {
        return new CompileMatcher(true);
    }

    /**
     *
     * @return
     */
    public static Matcher<File> failsToCompile() {
        return new CompileMatcher(false);
    }

    /**
     * Disable Creation
     */
    private PreprocessorTestUtils() {

    }
}