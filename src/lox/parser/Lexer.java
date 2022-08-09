package lox.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lox.parser.Token.TokenType;

public class Lexer {
    String sourceStr;
    int currentIdx;
    int symStartIdx;

    private static final Map<String, TokenType> keywords = new HashMap<>()
    {{
        put("and",    TokenType.AND);
        put("or",     TokenType.OR);
        put("true",   TokenType.TRUE);
        put("false",  TokenType.FALSE);
        put("nil",    TokenType.NIL);
    }};

    List<Token> tokens;

    Lexer(String sourceInput) {
        this.sourceStr = sourceInput;
        this.tokens = new ArrayList<>();
    }

    private char peek() {
        if (this.currentIdx >= this.sourceStr.length()) return 0;
        return this.sourceStr.charAt(this.currentIdx);
    }

    private char peekNext() {
        if (this.currentIdx + 1 >= this.sourceStr.length())
            return 0;
        return this.sourceStr.charAt(this.currentIdx + 1);
    }

    private char advance() {
        return this.sourceStr.charAt(this.currentIdx++);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void addToken(Token.TokenType tokenType) {
        this.addToken(tokenType, null);
    }

    private void addToken(TokenType tokenType, Object literal) {
        String lexeme = this.sourceStr.substring(this.symStartIdx, this.currentIdx);
        this.tokens.add(new Token(tokenType, lexeme, literal));
    }

    public List<Token> getTokens() throws ParseError {
        while (this.currentIdx < sourceStr.length()) {
            this.symStartIdx = this.currentIdx;
            char c = advance();
            switch (c) {
                case '+' -> addToken(TokenType.PLUS);
                case '*' -> addToken(TokenType.STAR);
                case '/' -> addToken(TokenType.SLASH);
                case '(' -> addToken(TokenType.PAREN_OPEN);
                case ')' -> addToken(TokenType.PAREN_CLOSE);
                case '-' -> addToken(TokenType.MINUS);
                case ';' -> addToken(TokenType.SEMICOLON);
                case '=' -> {
                    if (this.peek() == '=') {
                        addToken(TokenType.DOUBLE_EQUALS);
                        advance();
                    } else {
                        addToken(TokenType.EQUALS);
                    }
                }
                case '<' -> {
                    if (this.peek() == '=') {
                        addToken(TokenType.LESSER_EQUALS_THAN);
                        advance();
                    } else {
                        addToken(TokenType.LESSER_THAN);
                    }
                }
                case '>' -> {
                    if (this.peek() == '=') {
                        addToken(TokenType.GREATER_EQUALS_THAN);
                        advance();
                    } else {
                        addToken(TokenType.GREATER_THAN);
                    }
                }
                case '!' -> {
                    if (this.peek() == '=') {
                        addToken(TokenType.NOT_EQUALS);
                        advance();
                    } else {
                        addToken(TokenType.BANG);
                    }
                }
                case ' ', '\r', '\t' -> {}
                default -> {
                    if (isAlpha(c)) {
                        identifier();
                    } else if (isDigit(c)) {
                        number();
                    } else {
                        throw new ParseError("Unknown character: " + c);
                    }
                }
            }
        }
        this.symStartIdx = this.currentIdx;
        addToken(TokenType.EOF);
        return tokens;
    }

    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) advance();
        }

        addToken(TokenType.NUMBER, Double.parseDouble(sourceStr.substring(this.symStartIdx, this.currentIdx)));
    }

    private void identifier() {
        int startIdx = this.symStartIdx;

        while (isAlphaNumeric(peek())) {
            advance();
        }

        String identifierStr = sourceStr.substring(startIdx, this.currentIdx);

        TokenType tokenType = keywords.get(identifierStr);
        if (tokenType == null)
            addToken(TokenType.IDENTIFIER, identifierStr);
        else
            addToken(tokenType);
    }
}
