package rareshroom;

import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeSystem;
import rareshroom.nodes.literals.NilLiteralNode;

@TypeSystem
public class ShroomTypeSystem {
    @ImplicitCast
    public static boolean castBool(@SuppressWarnings("unused") double value) {
        return true;
    }

    @ImplicitCast
    public static boolean isNil(@SuppressWarnings("unused") NilLiteralNode value) {
        return false; // The only falsey value. TODO Which means every language element needs to be truthy
    }
}
