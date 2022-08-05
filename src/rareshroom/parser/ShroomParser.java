package rareshroom.parser;

import rareshroom.nodes.ExpressionNode;
import rareshroom.nodes.IntLiteralNode;
import rareshroom.nodes.arithmetic.AddNode;
import rareshroom.nodes.arithmetic.DivNode;
import rareshroom.nodes.arithmetic.MultNode;
import rareshroom.nodes.arithmetic.SubstractNode;

import java.util.List;

import static rareshroom.parser.Token.TokenType;

public class ShroomParser {
    private final String sourceStr;

    private List<Token> tokens;

    private int currentIdx = 0;

    public ShroomParser(String inputStr) {
        this.sourceStr = inputStr;
    }

    public ExpressionNode parse() {
        ShroomLexer lexer = new ShroomLexer(this.sourceStr);
        try {
            this.tokens = lexer.getTokens();
        } catch (ParseError exception) {
            System.err.println(exception.getMessage());
        }

        try {
            return expression(); // TODO make root node
        } catch (ParseError parseError) {
            System.err.println(parseError.getMessage());
            return null;
        }
    }

    private ExpressionNode expression() throws ParseError {
        return term();
    }

    private ExpressionNode term() throws ParseError {
        ExpressionNode expr = factor();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            TokenType operatorType = previous().type;
            ExpressionNode right = factor();

            if (operatorType == TokenType.PLUS)
                expr = new AddNode(expr, right);
            else if (operatorType == TokenType.MINUS) {
                expr = new SubstractNode(expr, right);
            }
        }

        return expr;
    }

    private ExpressionNode factor() throws ParseError {
        ExpressionNode expr = primary();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            TokenType operatorType = previous().type;
            ExpressionNode right = primary();

            if (operatorType == TokenType.STAR)
                expr = new MultNode(expr, right);
            else if (operatorType == TokenType.SLASH) {
                expr = new DivNode(expr, right);
            }
        }

        return expr;
    }

    private ExpressionNode primary() throws ParseError {
        if (match(TokenType.NUMBER)) {
            return new IntLiteralNode((Long) previous().literal);
        } else if (match(TokenType.PAREN_OPEN)) {
            ExpressionNode expr = expression();
            if (!match(TokenType.PAREN_CLOSE))
                throw new ParseError("Unclosed parentheses");
            return expr;
        } else {
            throw new ParseError("Invalid expression");
        }
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(currentIdx);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) currentIdx++;
        return previous();
    }
    private Token previous() {
        return tokens.get(currentIdx - 1);
    }

    private boolean match(Token.TokenType... types) {
        for (Token.TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }
}
