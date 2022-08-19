package lox.parser;

public class Token {
    enum TokenType {
        MINUS, PLUS, SLASH, STAR,
        PAREN_OPEN, PAREN_CLOSE, CURLY_BRACKET_OPEN, CURLY_BRACKET_CLOSE,
        EQUALS,
        DOUBLE_EQUALS, NOT_EQUALS, GREATER_THAN, GREATER_EQUALS_THAN, LESSER_THAN, LESSER_EQUALS_THAN,
        NUMBER, STRING, IDENTIFIER,
        TRUE, FALSE, NIL,
        IF, ELSE, WHILE, FOR,
        SEMICOLON, COMMA, BANG, AND, OR,
        VAR, FUN, PRINT, RETURN, EOF
    }

    public final TokenType type;
    public final String lexeme;
    public final Object literal;

    public final int lineIdx;
    public final int startCharIdx;

    Token(TokenType type, String lexeme, Object literal, int line, int startCharIdx) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.lineIdx = line;
        this.startCharIdx = startCharIdx;
    }

    public String toString() {
        return type + " "
                + (literal != null ? literal : "")
                + " (\"" + lexeme + "\")";
    }
}