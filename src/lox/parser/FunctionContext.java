package lox.parser;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;

import java.util.HashMap;
import java.util.List;

public class FunctionContext {
    private final String name;

    private final String className;
    private final HashMap<String, Integer> paramsIdMap;

    private final HashMap<String, Integer> localsIdMap;

    private final FrameDescriptor.Builder frameDescriptorBuilder;

    private int localVarIdx = 0;

    public FunctionContext(String funName) {
        this(funName, null);
    }

    public FunctionContext(String funName, String className) {
        this.name = funName;
        this.paramsIdMap = new HashMap<>();
        this.localsIdMap = new HashMap<>();
        this.frameDescriptorBuilder = FrameDescriptor.newBuilder();
        this.className = className;
    }

    public String getName() {
        return this.name;
    }

    public String getClassName() {
        return this.className;
    }

    public Integer getParam(String name) {
        return this.paramsIdMap.get(name);
    }

    public Integer getLocal(String name) {
        return this.localsIdMap.get(name);
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
        return this.frameDescriptorBuilder.build();
    }

    public boolean isVarDefined(String varName) {
        return this.localsIdMap.containsKey(varName) || this.paramsIdMap.containsKey(varName);
    }
}
