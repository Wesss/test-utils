package org.wesss.test_utils.matchers;

import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import javax.annotation.processing.Processor;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;

import static org.truth0.Truth.ASSERT;

public class CompileMatcher extends BaseMatcher<File> {

    private boolean isCompilationExpected;
    private ArrayList<Processor> preprocessors;

    protected CompileMatcher(boolean isCompilationExpected) {
        this.isCompilationExpected = isCompilationExpected;
        this.preprocessors = new ArrayList<>();
    }

    public CompileMatcher withPreprocessor(Processor preprocessor) {
        CompileMatcher matcher =  new CompileMatcher(isCompilationExpected);
        matcher.preprocessors.addAll(preprocessors);
        matcher.preprocessors.add(preprocessor);
        return matcher;
    }

    public CompileMatcher withPreprocessors(Processor... preprocessors) {
        CompileMatcher matcher =  new CompileMatcher(isCompilationExpected);
        matcher.preprocessors.addAll(this.preprocessors);
        Collections.addAll(matcher.preprocessors, preprocessors);
        return matcher;
    }

    @Override
    public boolean matches(Object o) {
        try {
            CompileTester assertCompilationSetup = ASSERT.about(JavaSourceSubjectFactory.javaSource())
                    .that(JavaFileObjects.forResource(((File) o).toURI().toURL()))
                    .processedWith(preprocessors);
            if (isCompilationExpected) {
                assertCompilationSetup.compilesWithoutError();
            } else {
                assertCompilationSetup.failsToCompile();
            }
        } catch (AssertionError | MalformedURLException e) {
            // TODO better error descriptions
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a .java file that compiled without error");
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("failed to compile");
    }
}
