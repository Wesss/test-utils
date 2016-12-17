package org.wesss.test_utils;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;

public class PreprocessorTestUtilsTest {

    private TestPreprocessor preprocessor;

    public PreprocessorTestUtilsTest() {
        preprocessor = new TestPreprocessor();
    }

    @Test
    public void CompilableFileCompiles() throws Exception {
        File compilableFile = new File("src/test/resources/Compilable.java");
        assertThat(compilableFile, PreprocessorTestUtils.compilesWithoutError());
    }

    @Test
    public void NotCompilableFileFailsToCompile() throws Exception {
        File notCompilableFile = new File("src/test/resources/NotCompilable.java");
        assertThat(notCompilableFile, PreprocessorTestUtils.failsToCompile());
    }
}
