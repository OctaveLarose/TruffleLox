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
}
