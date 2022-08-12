package lox.parser.error;

public class LexerError extends ParseError {
    public LexerError(String message) {
        super(message, null);
    }
}
