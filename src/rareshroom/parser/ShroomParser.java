package rareshroom.parser;

import rareshroom.nodes.ExpressionNode;
import rareshroom.nodes.GlobalVariableNode;
import rareshroom.nodes.SequenceNode;
import rareshroom.nodes.arithmetic.AddNodeFactory;
import rareshroom.nodes.arithmetic.DivNodeFactory;
import rareshroom.nodes.arithmetic.MultNodeFactory;
import rareshroom.nodes.arithmetic.SubNodeFactory;
import rareshroom.nodes.comparison.*;
import rareshroom.nodes.literals.FalseLiteralNode;
import rareshroom.nodes.literals.NilLiteralNode;
import rareshroom.nodes.literals.NumberLiteralNode;
import rareshroom.nodes.literals.TrueLiteralNode;

import java.util.ArrayList;
import java.util.List;

import static rareshroom.parser.Token.TokenType;

/*
    Grammar:

    program ->      statement* EOF
    statement ->    expr ";"
    expr ->         assignment
    assignment ->   IDENTIFIER "=" assignment
                    | orExpr
    orExpr ->       andExpr ( "or" andExpr )* ;
    andExpr ->      compare ( "and" compare )* ;
    compare ->      term (("==" | "!=" | "<" | "<=" | ">" | ">=") term)? // I think this guy should be split
    term ->         factor (( "+" | "-" ) factor)*
    factor ->       unary (( "*" | "/" ) unary)*
    unary ->        ("!" | "-") unary
                    | primary
    primary ->      NUMBER
                    | "(" expr ")"
 */
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
            return program(); // TODO make root node
        } catch (ParseError parseError) {
            System.err.println(parseError.getMessage());
            return null;
        }
    }

    private ExpressionNode program() throws ParseError {
        ArrayList<ExpressionNode> expressions = new ArrayList<>();
        while (!isAtEnd()) {
            expressions.add(statement());
        }
        return new SequenceNode(expressions);
    }

    private ExpressionNode statement() throws ParseError {
        ExpressionNode expression = expression();

        if (!match(TokenType.SEMICOLON))
            throw new ParseError("Unterminated statement");

        return expression;
    }

    private ExpressionNode expression() throws ParseError {
        return assignment();
    }

    private ExpressionNode assignment() throws ParseError {
        if (match(TokenType.IDENTIFIER)) {
            if (match(TokenType.EQUALS)) {
                return new GlobalVariableNode(assignment());
            }
        }
        return orExpr();
    }

    private ExpressionNode orExpr() throws ParseError {
        ExpressionNode expr = andExpr();

        while (match(TokenType.OR)) {
            expr = LogicalOrNodeFactory.create(expr, andExpr());
        }

        return expr;
    }

    private ExpressionNode andExpr() throws ParseError {
        ExpressionNode expr = compare();

        while (match(TokenType.AND)) {
            expr = LogicalAndNodeFactory.create(expr, compare());
        }

        return expr;
    }


    private ExpressionNode compare() throws ParseError {
        ExpressionNode expr = term();

        if (match(TokenType.DOUBLE_EQUALS, TokenType.NOT_EQUALS,
                TokenType.GREATER_THAN, TokenType.GREATER_EQUALS_THAN,
                TokenType.LESSER_THAN, TokenType.LESSER_EQUALS_THAN)) {
            TokenType operatorType = previous().type;
            ExpressionNode arg = term();

            switch (operatorType) {
                case DOUBLE_EQUALS -> expr = EqualsNodeFactory.create(expr, arg);
                case GREATER_THAN -> expr = GreaterThanNodeFactory.create(expr, arg);
                case GREATER_EQUALS_THAN -> expr = GreaterOrEqualsToNodeFactory.create(expr, arg);
                case LESSER_THAN -> expr = LesserThanNodeFactory.create(expr, arg);
                case LESSER_EQUALS_THAN -> expr = LesserOrEqualsToNodeFactory.create(expr, arg);
                case NOT_EQUALS -> expr = NotEqualsNodeFactory.create(expr, arg);
            }
        }

        return expr;
    }

    private ExpressionNode term() throws ParseError {
        ExpressionNode expr = factor();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            TokenType operatorType = previous().type;
            ExpressionNode right = factor();

            switch (operatorType) {
                case PLUS -> expr = AddNodeFactory.create(expr, right);
                case MINUS -> expr = SubNodeFactory.create(expr, right);
            }
        }

        return expr;
    }

    private ExpressionNode factor() throws ParseError {
        ExpressionNode expr = unary();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            TokenType operatorType = previous().type;
            ExpressionNode right = unary();

            if (operatorType == TokenType.STAR)
                expr = MultNodeFactory.create(expr, right);
            else if (operatorType == TokenType.SLASH) {
                expr = DivNodeFactory.create(expr, right);
            }
        }

        return expr;
    }

    private ExpressionNode unary() throws ParseError {
        if (match(TokenType.BANG, TokenType.MINUS)) {
            TokenType operatorType = previous().type;
            switch (operatorType) {
                case BANG -> { return LogicalNotNodeFactory.create(unary()); }
                case MINUS -> {
                    ExpressionNode unary = unary();
                    if (unary instanceof NumberLiteralNode) {
                        double value = ((NumberLiteralNode) unary).executeDouble();
                        return NegateNodeFactory.create(new NumberLiteralNode(-value));
                    } else {
                        return NegateNodeFactory.create(unary);
                    }
                }
                default -> throw new ParseError("Should be unreachable");
            }
        } else {
            return primary();
        }
    }

    private ExpressionNode primary() throws ParseError {
        Token currentToken = advance();

        switch (currentToken.type) {
            case NUMBER -> { return new NumberLiteralNode((Double) previous().literal); }
            case TRUE -> { return new TrueLiteralNode(); }
            case FALSE -> { return new FalseLiteralNode(); }
            case NIL -> { return NilLiteralNode.NIL_VALUE; }
            case PAREN_OPEN -> {
                ExpressionNode expr = expression();
                if (!match(TokenType.PAREN_CLOSE))
                    throw new ParseError("Unclosed parentheses");
                return expr;
            }
            default -> throw new ParseError("Invalid expression: primary token of type " + currentToken.type);
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
