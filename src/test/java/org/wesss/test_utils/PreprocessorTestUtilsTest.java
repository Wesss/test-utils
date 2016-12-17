package org.wesss.test_utils;

import org.junit.Test;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.File;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.wesss.test_utils.PreprocessorTestUtils.compilesWithoutError;
import static org.wesss.test_utils.PreprocessorTestUtils.failsToCompile;

public class PreprocessorTestUtilsTest {

    private File compilableFile;
    private File notCompilableFile;
    private EmptyPreprocessor emptyPreprocessor;
    private EmptyPreprocessor emptyPreprocessor2;
    private FailingPreprocessor failingPreprocessor;

    public PreprocessorTestUtilsTest() {
        compilableFile = new File("src/test/resources/Compilable.java");
        notCompilableFile = new File("src/test/resources/NotCompilable.java");
        emptyPreprocessor = new EmptyPreprocessor();
        emptyPreprocessor2 = new EmptyPreprocessor();
        failingPreprocessor = new FailingPreprocessor();
    }

    @Test
    public void compilableFileCompiles() throws Exception {
        File compilableFile = new File("src/test/resources/Compilable.java");
        assertThat(compilableFile, compilesWithoutError());
    }

    @Test
    public void notCompilableFileFailsToCompile() throws Exception {
        File notCompilableFile = new File("src/test/resources/NotCompilable.java");
        assertThat(notCompilableFile, failsToCompile());
    }

    @Test
    public void compilableFileCompilesWithProcessor() throws Exception {
        assertThat(compilableFile, compilesWithoutError().withPreprocessor(emptyPreprocessor));
    }

    @Test
    public void notCompilableFileFailsToCompileWithProcessor() throws Exception {
        assertThat(notCompilableFile, failsToCompile().withPreprocessor(emptyPreprocessor));
    }

    @Test
    public void compilableFileFailsToCompileWithFailingProcessor() throws Exception {
        assertThat(compilableFile, failsToCompile().withPreprocessor(failingPreprocessor));
    }

    @Test
    public void compilableFileCompilesWithMultipleProcessors() throws Exception {
        assertThat(compilableFile, compilesWithoutError()
                .withPreprocessors(emptyPreprocessor, emptyPreprocessor2));
    }

    @Test
    public void notCompilableFileFailsToCompileWithMultipleProcessors() throws Exception {
        assertThat(notCompilableFile, failsToCompile()
                .withPreprocessors(emptyPreprocessor, emptyPreprocessor2));
    }

    @Test
    public void compilableFileFailsToCompileWithAPassingAndFailingProcessor() throws Exception {
        assertThat(compilableFile, failsToCompile()
                .withPreprocessors(failingPreprocessor, emptyPreprocessor));
    }

    @SupportedAnnotationTypes("*")
    @SupportedSourceVersion(SourceVersion.RELEASE_8)
    private static class EmptyPreprocessor extends AbstractProcessor {

        @Override
        public boolean process(Set<? extends TypeElement> typeElements, RoundEnvironment roundEnv) {
            return false;
        }
    }

    @SupportedAnnotationTypes("*")
    @SupportedSourceVersion(SourceVersion.RELEASE_8)
    private static class FailingPreprocessor extends AbstractProcessor {

        @Override
        public boolean process(Set<? extends TypeElement> typeElements, RoundEnvironment roundEnv) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "DIE COMPILER");
            return false;
        }
    }
}
