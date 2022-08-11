package lox.nodes.arithmetic;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class AddNode extends BinaryExprNode {
    @Specialization(rewriteOn = ArithmeticException.class)
    public long doLong(long left, long right) {
        return Math.addExact(left, right);
    }

    @Specialization(replaces = "doLong")
    public double doDouble(double left, double right) {
        return left + right;
    }

    @Specialization
    public String doString(String left, String right) {
        return left + right;
    }

}
