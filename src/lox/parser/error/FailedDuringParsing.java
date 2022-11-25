package lox.parser.error;

public class FailedDuringParsing extends Exception {
    @Override
    public String getMessage() {
        return "Parsing failed";
    }
}
