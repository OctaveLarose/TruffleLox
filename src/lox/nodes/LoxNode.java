package lox.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.nodes.Node;
import lox.LoxTypeSystem;

@TypeSystemReference(LoxTypeSystem.class)
public abstract class LoxNode extends Node {
}
