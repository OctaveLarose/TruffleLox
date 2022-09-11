package lox.nodes.arithmetic;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class AddNode extends BinaryExprNode {
    @Specialization(rewriteOn = ArithmeticException.class)
    public int doInt(int left, int right) {
        return Math.addExact(left, right);
    }

    @Specialization(replaces = "doInt")
    public double doDouble(double left, double right) {
        return left + right;
    }

    @Specialization
    public String doString(String left, String right) {
        return left + right;
    }

}
