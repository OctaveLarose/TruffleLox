package lox.parser;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import lox.nodes.BlockRootNode;
import org.graalvm.collections.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionContext {
    private final String name;

    private final HashMap<String, Integer> paramsIdMap;

    private final HashMap<String, Integer> localsIdMap;

    private final FrameDescriptor.Builder frameDescriptorBuilder;

    private final FunctionContext enclosingContext;

    public List<BlockRootNode> subBlocks;

    public boolean isFunBlock = true;

    private int localVarIdx = 0;

    public FunctionContext(String funName) {
        this(funName, null);
    }

    public FunctionContext(String funName, FunctionContext enclosingContext) {
        this.name = funName;
        this.paramsIdMap = new HashMap<>();
        this.localsIdMap = new HashMap<>();
        this.frameDescriptorBuilder = FrameDescriptor.newBuilder();
        this.enclosingContext = enclosingContext;
        this.subBlocks = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public Integer getParam(String name) {
        return this.paramsIdMap.get(name);
    }

    public Integer getLocal(String name) {
        return this.localsIdMap.get(name);
    }

    public Pair<Integer, Integer> getNonLocalRec(String name, int scope) {
        Integer local = getLocal(name);
        if (local != null)
            return Pair.create(local, scope);

        if (enclosingContext == null)
            return null;

        return enclosingContext.getNonLocalRec(name, scope + 1);
    }
    public Pair<Integer, Integer> getNonLocal(String name) {
        return enclosingContext != null ? enclosingContext.getNonLocalRec(name, 1): null;
    }

    public int setLocal(String name) {
        this.frameDescriptorBuilder.addSlot(FrameSlotKind.Object, null, null);
        this.localsIdMap.put(name, localVarIdx);
        return localVarIdx++;
    }

    public void setParameters(List<String> parameters) {
        for (int i = 0; i < parameters.size(); i++)
            this.paramsIdMap.put(parameters.get(i), i);
    }

    public FrameDescriptor getFrameDescriptor() {
        var frameDescriptor = this.frameDescriptorBuilder.build();
        frameDescriptor.findOrAddAuxiliarySlot(0);
        return frameDescriptor;
    }

    public boolean isVarDefined(String varName) {
        return this.localsIdMap.containsKey(varName) || this.paramsIdMap.containsKey(varName);
    }
}
