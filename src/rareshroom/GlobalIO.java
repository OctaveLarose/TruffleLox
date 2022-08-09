package rareshroom;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class GlobalIO {
    private static final InputStream INPUT_STREAM = System.in;
    private static final PrintStream OUTPUT_STREAM = System.out;
    private static final PrintStream ERROR_STREAM = System.err;

    private static final Scanner in = new Scanner(INPUT_STREAM);

    static public String getInput() {
        if (!in.hasNext())
            return null;
        return in.nextLine();
    }

    static public void print(Object input) {
        OUTPUT_STREAM.print(input);
    }

    static public void printErr(Object input) {
        ERROR_STREAM.print(input);
    }

    static public void printLn(Object input) {
        OUTPUT_STREAM.println(input);
    }

    static public void printErrLn(Object input) {
        ERROR_STREAM.println(input);
    }
}
