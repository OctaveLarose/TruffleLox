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
                class TestClass {
                    testMethod() {
                        return "All good";
                    }
                }
                var a = TestClass(); a.testMethod();""";

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
}
