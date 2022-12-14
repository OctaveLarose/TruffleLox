package lox;

import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeSystem;
import lox.objects.Nil;

@TypeSystem
public class LoxTypeSystem {
    @ImplicitCast
    public static boolean castBool(@SuppressWarnings("unused") double value) {
        return true;
    }

    @ImplicitCast
    public static boolean castBool(@SuppressWarnings("unused") int value) {
        return true;
    }

    @ImplicitCast
    public static double castDouble(int value) {
        return value;
    }

    @ImplicitCast
    public static boolean isNil(@SuppressWarnings("unused") Nil value) {
        return false; // The only falsey value. Which means every language element needs to be truthy
    }
}
