package lox.nodes;

import com.oracle.truffle.api.nodes.ControlFlowException;

public class ReturnException extends ControlFlowException {
    private final Object             result;

    public ReturnException(final Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
