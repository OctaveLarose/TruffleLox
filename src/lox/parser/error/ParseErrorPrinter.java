package lox.parser.error;

import lox.GlobalIO;
import com.oracle.truffle.api.source.Source;
import lox.parser.Token;

import java.io.BufferedReader;
import java.io.IOException;

// To print errors a bit better. I haven't put much effort into it since this toy language has no users
public class ParseErrorPrinter {
    public static void print(ParseError parseError, Source originalSource) throws IOException {
        Token failingToken = parseError.getFailingToken();

        if (failingToken == null) {
            GlobalIO.printErrLn("Parse error: " + parseError.getMessage() + ".");
            return;
        }

        String failingLine = getFailingLine(originalSource, failingToken.lineIdx);

        GlobalIO.printErrLn("Parse error at line " + (failingToken.lineIdx + 1) + ": " + parseError.getMessage() + ".");
        GlobalIO.printErrLn(failingLine);

        String tokenHighlightLine = " ".repeat(failingToken.startCharIdx) + "^";
        GlobalIO.printErrLn(tokenHighlightLine);
    }

    private static String getFailingLine(Source originalSource, int lineIdx) throws IOException {
        var lineReader = new BufferedReader(originalSource.getReader());

        for (int i = 0; i < lineIdx - 1; i++)
            lineReader.readLine();

        return lineReader.readLine();
    }
}
