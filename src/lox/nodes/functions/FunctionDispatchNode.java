package lox.nodes.functions;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import lox.nodes.LoxNode;
import lox.objects.LoxFunction;

public abstract class FunctionDispatchNode extends LoxNode {

    public abstract Object executeDispatch(Object function, Object[] arguments);

    @Specialization(guards = "function.callTarget == directCallNode.getCallTarget()", limit = "2")
    protected static Object dispatchDirectly(
            @SuppressWarnings("unused") LoxFunction function,
            Object[] arguments,
            @Cached("create(function.callTarget)") DirectCallNode directCallNode) {
        return directCallNode.call(arguments);
    }

    @Specialization(replaces = "dispatchDirectly")
    protected static Object dispatchIndirectly(
            LoxFunction function,
            Object[] arguments,
            @Cached IndirectCallNode indirectCallNode) {
        return indirectCallNode.call(function.callTarget, arguments);
    }

    @Fallback
    protected Object invalidTarget(Object callTarget, @SuppressWarnings("unused") Object[] evaluatedArguments) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Invalid call target: " + callTarget + " is not a function"); // TODO runtime exception instead?
    }
}
