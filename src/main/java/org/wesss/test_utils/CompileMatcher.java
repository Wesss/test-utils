package org.wesss.test_utils;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.io.File;
import java.net.MalformedURLException;

import static org.truth0.Truth.ASSERT;

public class CompileMatcher extends BaseMatcher<File> {

    private boolean isCompilationExpected;

    protected CompileMatcher(boolean isCompilationExpected) {
        this.isCompilationExpected = isCompilationExpected;
    }

    @Override
    public boolean matches(Object o) {
        try {
            if (isCompilationExpected) {
                ASSERT.about(JavaSourceSubjectFactory.javaSource())
                        .that(JavaFileObjects.forResource(((File)o).toURI().toURL()))
                        .compilesWithoutError();
            } else {
                ASSERT.about(JavaSourceSubjectFactory.javaSource())
                        .that(JavaFileObjects.forResource(((File)o).toURI().toURL()))
                        .failsToCompile();
            }
        } catch (AssertionError e) {
            // TODO better error descriptions
            return false;
        } catch (MalformedURLException e) {
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
