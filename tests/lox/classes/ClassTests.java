package lox.classes;

import lox.ExpressionTestsBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassTests extends ExpressionTestsBase {
    @Test
    public void printClass() {
        String printClass = "class TestClass {" +
                "testMethod() { return \"wahoo\"; }}" +
                "TestClass;";

        assertEquals("TestClass", runString(printClass));
    }

    @Test
    public void basicMethodCalls() {
        String basicMethodCall1 = """
                class TestClass { testMethod() { return "All good";}} var a = TestClass(); a.testMethod();""";

        String basicMethodCall2 = """
                class AAAAAAAAAAAAA {
                    someRandomMethodName() {
                        return 42 * 4;
                    }
                    anotherMethod() {
                        return -121;
                    }
                }
                var a = AAAAAAAAAAAAA(); a.someRandomMethodName() + a.anotherMethod();""";

        assertEquals("All good", runString(basicMethodCall1));
        assertEquals(42 * 4 - 121, runInt(basicMethodCall2));
    }

    @Test
    public void fieldAssignAndAccess() {
        String fieldAccess1 = """
                class TestClass {}
                var a = TestClass();
                a.someVar = 42;
                a.someVar;""";

        assertEquals(42, runInt(fieldAccess1));

        String fieldAccess2 = """
                class NameNotImportant {}
                var np = NameNotImportant();
                np.abcdef = -1;
                np.abcdef = np.abcdef * 1000;
               """;

        assertEquals(-1000, runInt(fieldAccess2));
    }

    @Test
    public void replaceMethod() {
        String replaceMethod = """
                class Abc { sneakyReferenceToPopularMedia() { return "insert reference here"; }}
                fun wow() { return "a better reference actually"; }
                var a = Abc();
                a.sneakyReferenceToPopularMedia = wow;
                a.sneakyReferenceToPopularMedia();""";

        String replaceNonMethod = """
                class Abc { sneakyReferenceToPopularMedia() { return "insert reference here"; }}
                var a = Abc();
                a.sneakyReferenceToPopularMedia = "a better reference actually and not even a method";
                a.sneakyReferenceToPopularMedia;""";

        assertEquals("a better reference actually", runString(replaceMethod));
        assertEquals("a better reference actually and not even a method", runString(replaceNonMethod));
    }
}
