package lox.parser;

import com.oracle.truffle.api.source.Source;

import lox.LoxConstants;
import lox.nodes.*; // Bad habit but useful in this context of generated classes. To be changed later though
import lox.nodes.calls.*;
import lox.nodes.arithmetic.*;
import lox.nodes.classes.*;
import lox.nodes.comparison.*;
import lox.nodes.conditionals.*;
import lox.nodes.functions.*;
import lox.nodes.literals.*;
import lox.nodes.loop.*;
import lox.nodes.statements.*;
import lox.nodes.variables.*;
import lox.parser.error.FailedDuringParsing;
import lox.parser.error.LexerError;
import lox.parser.error.ParseError;
import org.graalvm.collections.Pair;

import java.util.*;

import static lox.parser.Token.TokenType;
import static lox.parser.Token.TokenType.SEMICOLON;

// Recursive descent AST parser
public class LoxParser extends Parser {
    private final String ROOT_FUNCTION_NAME = "_main";

    private final String BLOCK_NAME = "_block";

    private final String sourceStr;

    boolean hasThrown = false;

    private ClassContext currentClassContext;

    private FunctionContext currentFunContext;

    private final Stack<FunctionContext> contextStack = new Stack<>();

    private final List<Pair<NodeWithContext, FunctionContext>> variableContextMap = new ArrayList<>();

    @SuppressWarnings("unused") // Used for debugging
    public LoxParser(String inputStr) {
        this.sourceStr = inputStr;
    }

    // Ideally we would fully leverage the Source object to get better debugging info, but this is good enough for a toy language
    public LoxParser(Source source) {
        this.sourceStr = (String) source.getCharacters();
    }

    public LoxRootNode parse() throws FailedDuringParsing {
        Lexer lexer = new Lexer(this.sourceStr);

        try {
            this.tokens = lexer.getTokens();
        } catch (LexerError e) {
            System.err.println(e.getMessage());
            throw new FailedDuringParsing();
        }

        this.currentFunContext = new FunctionContext(ROOT_FUNCTION_NAME);
        contextStack.push(this.currentFunContext);

        ExpressionNode program = program();

        if (hasThrown)
            throw new FailedDuringParsing();

        var frameDescr = this.currentFunContext.getFrameDescriptor();
        BlockNode programBlock = new BlockNode(new SequenceNode(Collections.singletonList(program)), frameDescr);

        for (var varContextPair: this.variableContextMap) {
            if (varContextPair.getRight() == currentFunContext)
                varContextPair.getLeft().setContext(programBlock.getBlockRootNode().getFrame());
        }

        return new LoxRootNode(programBlock, frameDescr);
    }

    private ExpressionNode program() {
        ArrayList<ExpressionNode> expressions = new ArrayList<>();
        while (!isAtEnd()) {
            var declaration = declaration();
            expressions.add(declaration);
        }

        return new SequenceNode(expressions);
    }

    private ExpressionNode declaration() {
        try {
            if (match(TokenType.CLASS)) {
                return classDecl();
            } else if (match(TokenType.FUN)) {
                return funDecl();
            } else if (match(TokenType.VAR)) {
                return varDecl();
            }
            return statement();
        } catch (ParseError e) {
            System.err.println(e.getMessage());
            hasThrown = true;
            synchronize();
            return null;
        }
    }

    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return;

            switch (peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                    return;
            }

            advance();
        }
    }
    private ClassDeclarationNode classDecl() throws ParseError {
//        classDecl ->    "class" IDENTIFIER ( "<" IDENTIFIER )? "{" function* "}" ;
        Token identifierToken = peek();

        if (!match(TokenType.IDENTIFIER))
            error("Expect an identifier after class declaration");

        String className = (String) identifierToken.literal;

        String superclass = null;
        if (match(TokenType.LESSER_THAN)) {
            Token superClassToken = peek();
            if (!match(TokenType.IDENTIFIER))
                error("Expect identifier after superclass declaration");
            superclass = (String) superClassToken.literal;
            if (superclass.equals(className))
                error("A class can't inherit from itself", superClassToken);
        }

        if (!match(TokenType.CURLY_BRACKET_OPEN))
            error("Expect a { to define the class body");


        ClassContext prevClassContext = this.currentClassContext;
        this.currentClassContext = new ClassContext(className, (superclass != null));
        List<FunctionDeclarationNode> methods = new ArrayList<>();
        while (!isAtEnd() && !match(TokenType.CURLY_BRACKET_CLOSE)) {
            FunctionDeclarationNode funDecl = funDecl();
            funDecl.setIsMethod(true);
            methods.add(funDecl);
        }
        this.currentClassContext = prevClassContext;

        if (isAtEnd() && previous().type != TokenType.CURLY_BRACKET_CLOSE)
            error("Unterminated class body");

        return new ClassDeclarationNode(className, superclass, methods);
    }

    private FunctionDeclarationNode funDecl() throws ParseError {
        Token idToken = this.peek();

        if (!match(TokenType.IDENTIFIER))
            error("No identifier specified for function declaration");

        if (!match(TokenType.PAREN_OPEN))
            error("Expected a '(' before function arguments");

        FunctionContext functionContext = new FunctionContext((String)idToken.literal);

        this.currentFunContext = functionContext;

        List<String> parameters = parameters();

        if (!match(TokenType.CURLY_BRACKET_OPEN))
            error("Expected a '{' to specify a function declaration");

        functionContext.setParameters(parameters);
        functionContext.isFunBlock = true;

        this.contextStack.push(functionContext);

        BlockNode block = block();

        var funDeclNode = new FunctionDeclarationNode((String)idToken.literal, block);

        this.contextStack.pop();
        this.currentFunContext = this.contextStack.peek();

        return funDeclNode;
    }

    private LocalVariableNode.VariableWriteNode varDecl() throws ParseError {
        Token identifierToken = peek();

        if (!match(TokenType.IDENTIFIER))
            error("No identifier in variable declaration");

        String varName = (String) identifierToken.literal;

        // Not a fan, I'd rather have this be handled in setLocal() but it's another class that doesn't have access to error()
        if (this.currentFunContext.isVarDefined(varName))
            error("Already a variable with this name in the current scope");

        if (match(SEMICOLON))
            return LocalVariableNodeFactory.VariableWriteNodeGen.create(varName, this.currentFunContext.setLocal(varName), new NilLiteralNode());

        if (!match(TokenType.EQUALS))
            error("Expect an equals or semicolon after an identifier in a variable declaration");

        ExpressionNode assignedExpr = expression();

        if (!match(SEMICOLON))
            error("Unterminated variable declaration");

        return LocalVariableNodeFactory.VariableWriteNodeGen.create(varName, this.currentFunContext.setLocal(varName), assignedExpr);
    }

    private ExpressionNode statement() throws ParseError {
        if (match(TokenType.FOR))
            return forStmt();
        else if (match(TokenType.IF))
            return ifStmt();
        else if (match(TokenType.PRINT))
            return printStmt();
        else if (match(TokenType.RETURN))
            return returnStmt();
        else if (match(TokenType.WHILE))
            return whileStmt();
        else if (match(TokenType.CURLY_BRACKET_OPEN)) {
            this.currentFunContext = new FunctionContext(BLOCK_NAME, this.currentFunContext);
            this.currentFunContext.isFunBlock = false;

            this.contextStack.push(currentFunContext);
            BlockNode block = block();
            contextStack.pop();
            this.currentFunContext = contextStack.peek();

            return block;
        } else
            return exprStatement();
    }

    private ForNode forStmt() throws ParseError {
        if (!match(TokenType.PAREN_OPEN))
            error("Expect parentheses after a for keyword");

        ExpressionNode initialization = null;
        if (!match(SEMICOLON)) {
            if (match(TokenType.VAR))
                initialization = varDecl();
            else
                error("for statement expects an initializer or a ';'");
        }

        ExpressionNode termination = null;
        if (peek().type != SEMICOLON)
            termination = expression();

        if (!match(SEMICOLON))
            error("Expect at least one semicolon in for declaration");

        ExpressionNode increment = null;
        if (peek().type != TokenType.PAREN_CLOSE) {
            increment = expression();
        }

        if (!match(TokenType.PAREN_CLOSE))
            error("Unclosed parentheses after for declaration");

        ExpressionNode body = statement();

        return new ForNode(initialization, termination, increment, body);
    }

    private ExpressionNode ifStmt() throws ParseError {
        if (!match(TokenType.PAREN_OPEN))
            error("Expected a '(' after keyword \"if\"");
        ExpressionNode conditionalExpression = expression();

        if (!match(TokenType.PAREN_CLOSE))
            error("Expected a ')' after \"if\" conditional expression");

        ExpressionNode consequent = statement();

        if (match(TokenType.ELSE)) {
            ExpressionNode alternative = statement();
            return new IfElseNode(conditionalExpression, consequent, alternative);
        }

        return new IfNode(conditionalExpression, consequent);
    }

    private WhileNode whileStmt() throws ParseError {
        // "while" "(" expression ")" statement ;
        if (!match(TokenType.PAREN_OPEN))
            error("No open parenthesis after while");

        ExpressionNode cond = expression();

        if (!match(TokenType.PAREN_CLOSE))
            error("No closed parenthesis after while condition");

        ExpressionNode statement = statement();

        return new WhileNode(cond, statement);
    }

    private PrintStatement printStmt() throws ParseError {
        ExpressionNode expr = exprStatement();
        return PrintStatementFactory.create(expr);
    }

    private BlockNode block() throws ParseError {
        List<ExpressionNode> declarations = new ArrayList<>();

        while (!isAtEnd() && !match(TokenType.CURLY_BRACKET_CLOSE)) {
            var declaration = declaration();
            declarations.add(declaration);
        }

        if (isAtEnd() && previous().type != TokenType.CURLY_BRACKET_CLOSE)
            error("Unterminated block statement");

        var blockNode = new BlockNode(new SequenceNode(declarations), currentFunContext.getFrameDescriptor());

        for (var varContextPair: this.variableContextMap) {
            if (varContextPair.getRight() == currentFunContext)
                varContextPair.getLeft().setContext(blockNode.getBlockRootNode().getFrame());
        }

        return blockNode;
    }

    private ExpressionNode exprStatement() throws ParseError {
        ExpressionNode expression = expression();

        if (!match(SEMICOLON))
            error("Unterminated statement");

        return expression;
    }

    private ExpressionNode expression() throws ParseError {
        return assignment();
    }

    private ExpressionNode assignment() throws ParseError {
        ExpressionNode assignee = orExpr();

        if (match(TokenType.EQUALS)) {
            ExpressionNode assignment = assignment();

            if (assignee instanceof LocalVariableNode localVar) {
                return LocalVariableNodeFactory.VariableWriteNodeGen.create(localVar.getName(), localVar.getSlotId(), assignment);
            } else if (assignee instanceof LocalArgumentNode localArg) {
                return LocalArgumentNodeFactory.LocalArgumentWriteNodeGen.create(localArg.getSlotId(), assignment);
            } else if (assignee instanceof NonLocalVariableNode nonLocalVar) {
                var nonLocalVarWrite = NonLocalVariableNodeFactory.VariableWriteNodeGen.create(nonLocalVar.getName(), nonLocalVar.getSlotId(), assignment);

                for (var varContextPair: this.variableContextMap) {
                    if (varContextPair.getLeft() == nonLocalVar) {
                        variableContextMap.add(Pair.create(nonLocalVarWrite, varContextPair.getRight()));
                        variableContextMap.remove(varContextPair);
                        break;
                    }
                }

                return nonLocalVarWrite;
            } else if (assignee instanceof NonLocalArgumentNode nonLocalArg) {
                var nonLocalArgWrite = NonLocalArgumentNodeFactory.NonLocalArgumentWriteNodeGen.create(nonLocalArg.getSlotId(), assignment);

                for (var varContextPair: this.variableContextMap) {
                    if (varContextPair.getLeft() == nonLocalArgWrite) {
                        variableContextMap.add(Pair.create(nonLocalArgWrite, varContextPair.getRight()));
                        variableContextMap.remove(varContextPair);
                        break;
                    }
                }

                return nonLocalArgWrite;
            } else if (assignee instanceof ObjectPropertyNode objectPropertyNode) {
                return ObjectPropertyNodeFactory.ObjectPropertyWriteNodeGen.create(objectPropertyNode.getClassExpr(), objectPropertyNode.getPropertyName(), assignment);
            } else {
                error("Invalid assignment target");
            }
        }
        return assignee;
    }

    private ExpressionNode orExpr() throws ParseError {
        ExpressionNode expr = andExpr();

        while (match(TokenType.OR)) {
            expr = LogicalOrNodeFactory.create(expr, andExpr());
        }

        return expr;
    }

    private ExpressionNode andExpr() throws ParseError {
        ExpressionNode expr = equality();

        while (match(TokenType.AND)) {
            expr = LogicalAndNodeFactory.create(expr, equality());
        }

        return expr;
    }

    private ExpressionNode equality() throws ParseError {
        ExpressionNode expr = compare();

        if (match(TokenType.DOUBLE_EQUALS, TokenType.NOT_EQUALS)) {
            TokenType operatorType = previous().type;
            ExpressionNode arg = term();

            switch (operatorType) {
                case NOT_EQUALS -> expr = NotEqualsNodeFactory.create(expr, arg);
                case DOUBLE_EQUALS -> expr = EqualsNodeFactory.create(expr, arg);
            }
        }

        return expr;
    }

    private ExpressionNode compare() throws ParseError {
        ExpressionNode expr = term();

        if (match(TokenType.GREATER_THAN, TokenType.GREATER_EQUALS_THAN,
                TokenType.LESSER_THAN, TokenType.LESSER_EQUALS_THAN)) {
            TokenType operatorType = previous().type;
            ExpressionNode arg = term();

            switch (operatorType) {
                case GREATER_THAN -> expr = GreaterThanNodeFactory.create(expr, arg);
                case GREATER_EQUALS_THAN -> expr = GreaterOrEqualsToNodeFactory.create(expr, arg);
                case LESSER_THAN -> expr = LesserThanNodeFactory.create(expr, arg);
                case LESSER_EQUALS_THAN -> expr = LesserOrEqualsToNodeFactory.create(expr, arg);
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
                case MINUS -> expr = SubNodeFactory.create(expr, right);
                case PLUS -> expr = AddNodeFactory.create(expr, right);
            }
        }

        return expr;
    }

    private ExpressionNode factor() throws ParseError {
        ExpressionNode expr = unary();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            TokenType operatorType = previous().type;
            ExpressionNode right = unary();

            switch (operatorType) {
                case SLASH -> expr = DivNodeFactory.create(expr, right);
                case STAR -> expr = MultNodeFactory.create(expr, right);
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
                    if (unary instanceof DoubleLiteralNode) {
                        double value = ((DoubleLiteralNode) unary).executeDouble(null);
                        return new DoubleLiteralNode(-value);
                    } else if  (unary instanceof IntLiteralNode) {
                        int value = ((IntLiteralNode) unary).executeInt(null);
                        return new IntLiteralNode(-value);
                    } else {
                        return NegateNodeFactory.create(unary);
                    }
                }
                default -> error("Should be unreachable");
            }
        }
        return call();
    }

    private ExpressionNode call() throws ParseError {
        ExpressionNode primary = primary();

        while (match(TokenType.PAREN_OPEN) || match(TokenType.DOT)) {
            if (previous().type == TokenType.PAREN_OPEN) {
                List<ExpressionNode> arguments = arguments();
                primary = new CallNode(new LookupNode(primary), arguments); // Shouldn't always have to look it up, but it's a start
            } else if (previous().type == TokenType.DOT) {
                if (!match(TokenType.IDENTIFIER))
                    error("Expected an identifier after a \".\"");

                primary = ObjectPropertyNodeFactory.ObjectPropertyReadNodeGen.create(primary, (String) previous().literal);
            }
        }

        return primary;
    }


    private ExpressionNode primary() throws ParseError {
        Token currentToken = advance();

        switch (currentToken.type) {
            case TRUE -> { return new TrueLiteralNode(); }
            case FALSE -> { return new FalseLiteralNode(); }
            case NIL -> { return new NilLiteralNode(); }
            case INT -> { return new IntLiteralNode((int) currentToken.literal); }
            case DOUBLE -> { return new DoubleLiteralNode((Double) currentToken.literal); }
            case STRING -> { return new StringLiteralNode((String) currentToken.literal); }
            case PAREN_OPEN -> {
                ExpressionNode expr = expression();
                if (!match(TokenType.PAREN_CLOSE))
                    error("Unclosed parentheses");
                return expr;
            }
            case IDENTIFIER -> {
                String identifierName = (String) currentToken.literal;

                if (peek().type == TokenType.PAREN_OPEN) // A function call
                    return new FunctionNameLiteralNode((String) currentToken.literal);
                else
                    return resolveIdentifier(identifierName);
            }
            case SUPER -> {
                if (this.currentClassContext == null)
                    error("super cannot be used outside of a class", currentToken);

                if (!this.currentClassContext.hasSuperClass())
                    error("super cannot be used in a class which has no superclass", currentToken);

                if (!match(TokenType.DOT))
                    error("Expect '.' after 'super'");

                Token token = peek();
                if (!match(TokenType.IDENTIFIER))
                    error("Expect superclass method name");

                return new SuperExprNode(this.currentClassContext.getName(), (String) token.literal);
            }
            default -> error("Expect expression", currentToken);
        }

        return null; // Unreachable
    }

    private ExpressionNode resolveIdentifier(String identifierName) {
        if (this.currentFunContext.getLocal(identifierName) != null)
            return LocalVariableNodeFactory.VariableReadNodeGen.create(identifierName, this.currentFunContext.getLocal(identifierName));

        var nonLocalVar = this.currentFunContext.getNonLocal(identifierName);
        if (nonLocalVar != null) {
            assert nonLocalVar.getRight() != 0 : "Scope of non local variable cannot be 0";
            var nonLocalVariableNode = NonLocalVariableNodeFactory.VariableReadNodeGen.create(identifierName, nonLocalVar.getLeft());
            var correspondingScope = this.contextStack.get(this.contextStack.size() - nonLocalVar.getLeft() - 2);
            this.variableContextMap.add(Pair.create(nonLocalVariableNode, correspondingScope));
            return nonLocalVariableNode;
        }

        if (!this.currentFunContext.getName().equals(ROOT_FUNCTION_NAME)) { // i.e. whether we are currently parsing a function
            Integer argSlotId = this.currentFunContext.getParam(identifierName);
            if (argSlotId != null)
                return LocalArgumentNodeFactory.LocalArgumentReadNodeGen.create(argSlotId);
        }

        for (int i = this.contextStack.size() - 1; i >= 0; i--) {
            var funContext = this.contextStack.get(i);
            Integer argSlotId = funContext.getParam(identifierName);
            if (argSlotId != null) {
                var correspondingScope = this.contextStack.get(this.contextStack.size() - argSlotId - 1);
                var nonLocalVariableNode = NonLocalArgumentNodeFactory.NonLocalArgumentReadNodeGen.create(argSlotId);
                this.variableContextMap.add(Pair.create(nonLocalVariableNode, correspondingScope));
                return nonLocalVariableNode;
            }
        }

        return new IdentifierNode(identifierName);
    }

    private List<ExpressionNode> arguments() throws ParseError {
        ArrayList<ExpressionNode> arguments = new ArrayList<>();
        if (match(TokenType.PAREN_CLOSE))
            return arguments;

        while (!isAtEnd()) {
            ExpressionNode expr = expression();
            arguments.add(expr);
            if (arguments.size() > LoxConstants.MAX_ARG_SIZE)
                error("Can't have more than 255 arguments", previous());
            if (!match(TokenType.COMMA))
                break;
        }

        if (!match(TokenType.PAREN_CLOSE))
            error("Unterminated arguments declaration");

        return arguments;
    }

    private List<String> parameters() throws ParseError {
        ArrayList<String> parameters = new ArrayList<>();
        if (match(TokenType.PAREN_CLOSE))
            return parameters;

        while (!isAtEnd()) {
            Token idToken = advance();
            if (idToken.type != TokenType.IDENTIFIER)
                error("Expected an identifier in parameters list, instead got " + idToken);
            parameters.add((String) idToken.literal);

            if (!match(TokenType.COMMA))
                break;
        }

        if (!match(TokenType.PAREN_CLOSE))
            error("Unterminated parameters declaration");

        return parameters;
    }

    private ExpressionNode returnStmt() throws ParseError {
        if (match(SEMICOLON))
            return new ReturnStmt(null);

        ExpressionNode expr = expression();

        if (!match(SEMICOLON))
            error("Unterminated return statement");

        return new ReturnStmt(expr);
    }
}
