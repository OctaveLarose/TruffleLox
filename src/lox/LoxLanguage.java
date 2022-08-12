package lox;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.TruffleLanguage;
import lox.nodes.ExpressionNode;
import lox.nodes.LoxRootNode;
import lox.parser.error.ParseError;
import lox.parser.error.ParseErrorPrinter;
import lox.parser.LoxParser;

@TruffleLanguage.Registration(id = "tlox", name = "TruffleLox")
public class LoxLanguage extends TruffleLanguage<LoxContext> {

    public static final String LANG_ID = "tlox";

    @CompilationFinal
    private static LoxLanguage current;

    public static LoxLanguage getCurrent() {
        return current;
    }

    @Override
    protected LoxContext createContext(Env env) {
        return new LoxContext(new GlobalScope());
    }

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        try {
            ExpressionNode exprNode = new LoxParser(request.getSource().getReader()).parse();
            LoxRootNode rootNode = new LoxRootNode(exprNode);
            return rootNode.getCallTarget();
        } catch (ParseError e) {
            ParseErrorPrinter.print(e, request.getSource());
            throw e;
        }
    }
}