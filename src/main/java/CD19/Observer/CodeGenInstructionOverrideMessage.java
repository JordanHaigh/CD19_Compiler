package CD19.Observer;

import CD19.CodeGen.OpCodes;

public class CodeGenInstructionOverrideMessage extends ObservableMessage {

    int pcRowStart;
    int pcByteStart;
    int generateXBytes;
    OpCodes opCodes;
    int operand;

    public CodeGenInstructionOverrideMessage(int pcRowStart, int pcByteStart, int generateXBytes, OpCodes opCodes, int operand) {
        this.pcRowStart = pcRowStart;
        this.pcByteStart = pcByteStart;
        this.generateXBytes = generateXBytes;
        this.opCodes = opCodes;
        this.operand = operand;
    }


}
