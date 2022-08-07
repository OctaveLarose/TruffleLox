package rareshroom.parser;

public class ParseError extends Exception {
    public ParseError(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Parsing error: " + super.getMessage();
    }
}
