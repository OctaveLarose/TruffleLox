package rareshroom;

import rareshroom.parser.ShroomParser;

public class Main {
    static public void main(String[] args) {
        arithmeticAsserts();
    }

    static private Object run(String sourceStr) {
        return new ShroomParser(sourceStr).parse().executeGeneric();
    }

    static private void arithmeticAsserts() {
        // Addition, substraction
        arithmeticAssert("13 + 42", 55);
        arithmeticAssert("1 + 55 + 3812", 3868);
        arithmeticAssert("4617 - 134", 4483);
        arithmeticAssert("4617 - 134 + 18291", 22774);

        // Multiplication
        arithmeticAssert("3 * 4", 12);
        arithmeticAssert("3 * 18 + 13", 67);

        // Division
        arithmeticAssert("18 / 3", 6);
        arithmeticAssert("1783 / 3", 594); // You should not be valid though
        arithmeticAssert("163 * 5 + 100 / 4 - 12 * 2 + 145", 961);
        arithmeticAssert("163 * (5 + 100 / 4) - 12 * (2 + 145)", 3126);
    }

    static private void arithmeticAssert(String input, long expectedOutput) {
        System.out.print(input + " == " + expectedOutput);
        var output = ((long) run(input));
        if (output == expectedOutput)
            System.out.print(" - OK\n");
        else
            System.out.print(" - ERROR - Output was " + output + " (expected " + expectedOutput + ")\n");
    }
}