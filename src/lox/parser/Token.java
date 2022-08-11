package lox.parser;

public class Token {
    enum TokenType {
        MINUS, PLUS, SLASH, STAR,
        PAREN_OPEN, PAREN_CLOSE, CURLY_BRACKET_OPEN, CURLY_BRACKET_CLOSE,
        EQUALS,
        DOUBLE_EQUALS, NOT_EQUALS, GREATER_THAN, GREATER_EQUALS_THAN, LESSER_THAN, LESSER_EQUALS_THAN,
        NUMBER, STRING, IDENTIFIER,
        TRUE, FALSE, NIL,
        SEMICOLON, COMMA, BANG, AND, OR,
        FUN, RETURN, EOF
    }

    final TokenType type;
    final String lexeme;
    final Object literal;

    Token(TokenType type, String lexeme, Object literal) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
    }

    public String toString() {
        return type + " "
                + (literal != null ? literal : "")
                + " (\"" + lexeme + "\")";
    }
}