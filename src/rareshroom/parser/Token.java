package rareshroom.parser;

public class Token {
    enum TokenType {
        MINUS, PLUS, SLASH, STAR,
        EQUAL,
        NUMBER,
        SEMICOLON,
        EOF
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