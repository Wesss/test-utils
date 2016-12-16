import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.truth0.Truth.ASSERT;

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
