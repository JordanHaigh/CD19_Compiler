package CD19.Observer;

import CD19.CodeGen.OpCodes;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

public class InstructionOverrideMessage extends ObservableMessage {

    int pcRowStart;
    int pcByteStart;
    int generateXBytes;
    OpCodes opCode;
    SymbolTableRecord record;
    public int getPcRowStart() { return pcRowStart; }

    public InstructionOverrideMessage(int pcRowStart, int pcByteStart, int generateXBytes, OpCodes opCode, SymbolTableRecord record) {
        this.pcRowStart = pcRowStart;
        this.pcByteStart = pcByteStart;
        this.generateXBytes = generateXBytes;
        this.opCode = opCode;
        this.record = record;
    }
    public int getPcByteStart() { return pcByteStart; }
    public int getGenerateXBytes() { return generateXBytes; }
    public OpCodes getOpCode() { return opCode; }
    public SymbolTableRecord getRecord() { return record; }
}
