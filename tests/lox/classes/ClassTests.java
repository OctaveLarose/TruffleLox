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

        assertEquals(runString(printClass), "TestClass");
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

        assertEquals(runString(basicMethodCall1), "All good");
        assertEquals(runDouble(basicMethodCall2), 42 * 4 - 121, 0.01);
    }

    @Test
    public void fieldAssignAndAccess() {
        String fieldAccess1 = """
                class TestClass {}
                var a = TestClass();
                a.someVar = 42;
                a.someVar;""";

        assertEquals(runDouble(fieldAccess1), 42, 0.01);

        String fieldAccess2 = """
                class NameNotImportant {}
                var np = NameNotImportant();
                np.abcdef = -1;
                np.abcdef = np.abcdef * 1000;
               """;

        assertEquals(runDouble(fieldAccess2), -1000, 0.01);
    }

   /* @Test
    public void replaceMethod() {
//        String replaceMethod = """
//                class Abc { sneakyReferenceToPopularMedia() { return "insert reference here"; }}
//                var a = Abc();
//                a.sneakyReferenceToPopularMedia = fun wow() { return "a better reference actually"; }
//                a.sneakyReferenceToPopularMedia();""";

        String replaceNonMethod = """
                class Abc { sneakyReferenceToPopularMedia() { return "insert reference here"; }}
                var a = Abc();
                a.sneakyReferenceToPopularMedia = "a better reference actually and not even a method";
                a.sneakyReferenceToPopularMedia;""";

//        assertEquals(runString(replaceMethod), "a better reference actually");
        assertEquals(runString(replaceNonMethod), "a better reference actually");
    }*/
}
