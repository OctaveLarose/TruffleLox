package rareshroom.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.nodes.Node;
import rareshroom.ShroomTypeSystem;

@TypeSystemReference(ShroomTypeSystem.class)
public abstract class ShroomNode extends Node {
}
