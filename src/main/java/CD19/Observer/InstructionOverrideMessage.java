package CD19.Observer;

import CD19.CodeGen.OpCodes;
import CD19.Parser.TreeNode;

public class InstructionOverrideMessage extends ObservableMessage {

    int pcRowStart;
    int pcByteStart;
    int generateXBytes;
    OpCodes opCodes;
    TreeNode node;

    public InstructionOverrideMessage(int pcRowStart, int pcByteStart, int generateXBytes, OpCodes opCodes, TreeNode node) {
        this.pcRowStart = pcRowStart;
        this.pcByteStart = pcByteStart;
        this.generateXBytes = generateXBytes;
        this.opCodes = opCodes;
        this.node = node;
    }

    public int getPcRowStart() { return pcRowStart; }
    public int getPcByteStart() { return pcByteStart; }
    public int getGenerateXBytes() { return generateXBytes; }
    public OpCodes getOpCodes() { return opCodes; }
    public TreeNode getNode() { return node; }
}
