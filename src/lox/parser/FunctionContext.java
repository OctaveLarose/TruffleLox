package lox.parser;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;

import java.util.HashMap;
import java.util.List;

public class FunctionContext {
    final private String name;
    private final HashMap<String, Integer> functionParamsIdMap;

    private final HashMap<String, Integer> localsIdMap;

    private final FrameDescriptor.Builder frameDescriptorBuilder;

    private int localVarIdx = 0;

    public FunctionContext(String funName) {
        this.name = funName;
        this.functionParamsIdMap = new HashMap<>();
        this.localsIdMap = new HashMap<>();

        this.frameDescriptorBuilder = FrameDescriptor.newBuilder();
    }

    public String getName() {
        return this.name;
    }

    public Integer getParam(String name) {
        return this.functionParamsIdMap.get(name);
    }

    public Integer getLocal(String name) {
        return this.localsIdMap.get(name);
    }

    public void setLocal(String name) {
        this.frameDescriptorBuilder.addSlot(FrameSlotKind.Object, null, null);
        this.localsIdMap.put(name, localVarIdx++);
    }

    public void setParameters(List<String> parameters) {
        for (int i = 0; i < parameters.size(); i++)
            this.functionParamsIdMap.put(parameters.get(i), i);
    }

    public FrameDescriptor getFrameDescriptor() {
        return this.frameDescriptorBuilder.build();
    }
}
