package dev.jbang.source;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import dev.jbang.BaseTest;

public class TestSourcesMutualDependency extends BaseTest {

	String classMain = "//SOURCES A.java\n"
			+ "\n"
			+ "public class Main {    \n"
			+ "    public static void main(String args[]) {\n"
			+ "        new A();\n"
			+ "    }\n"
			+ "}\n";

	String classA = "//SOURCES B.java\n"
			+ "\n"
			+ "public class A {\n"
			+ "    public A() { new B(); }\n"
			+ "}\n";

	String classB = "//SOURCES A.java\n"
			+ "\n"
			+ "public class B {\n"
			+ "	public B() {\n"
			+ "		System.out.println(\"B constructor.\");\n"
			+ "	}\n"
			+ "}\n";

	@Test
	void testFindSourcesInMultipleFilesRecursively() throws IOException {
		Path mainPath = TestScript.createTmpFileWithContent("", "Main.java", classMain);
		TestScript.createTmpFileWithContent("", "A.java", classA);
		TestScript.createTmpFileWithContent("", "B.java", classB);
		String scriptURL = mainPath.toString();
		ScriptSource script = ScriptSource.prepareScript(scriptURL, null);
		assertEquals(script.getAllSources().size(), 2);
	}

}
