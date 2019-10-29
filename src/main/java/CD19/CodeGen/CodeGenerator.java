package CD19.CodeGen;

import CD19.CodeGen.Instructions.Declaration;
import CD19.CodeGen.Instructions.Statement;
import CD19.Observer.InstructionOverrideMessage;
import CD19.Observer.ObservableMessage;
import CD19.Observer.Observer;
import CD19.Parser.SymbolTable;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator implements Observer {

    TreeNode tree;
    SymbolTable constants;
    InstructionMatrix program;
    public static final int REGISTERSIZE = 8;
    public static int offset = 0;

    List<SymbolTableRecord> intConstants = new ArrayList<>();
    List<SymbolTableRecord> realConstants = new ArrayList<>();
    List<InstructionOverrideMessage> overrideMessages = new ArrayList<>();

    public CodeGenerator(TreeNode tree, SymbolTable constants){
        this.tree = tree;
        this.constants = constants;
        program = new InstructionMatrix();

        Statement.INSTANCE().addObserver(this);
        Declaration.INSTANCE().addObserver(this);
    }

    public void run(){
        run(tree); //first run for building most of matrix. need to do post code gen sweep for string constant locations
        generate1Byte(OpCodes.RETN); //use this when finished program
        program.populateConstants(constants, intConstants, realConstants);
        secondRun();
    }

    public InstructionMatrix getProgram() { return program; }

    public void run(TreeNode root){
        //post order traversal
        if (root == null)
            return;

        run(root.getLeft());
        // todo middle???
        run(root.getRight());

        // now deal with the node
        int rootValue = root.getValue();
        switch(rootValue) {
            case TreeNode.NSDECL:
                Declaration.generate(this, root);
                break;
            case TreeNode.NPRLN:
                Statement.generate(this, root);
                break;
            case TreeNode.NILIT: //todo more logic. lb < 256, lh < 65536, only add to int constants when too big
                intConstants.add(root.getSymbol());
                break;
            case TreeNode.NFLIT:
                realConstants.add(root.getSymbol());
                break;


        }
        System.out.println(root + " ");
    }

    public void secondRun(){
        //fix up any addressing that was borked in first run
        for(InstructionOverrideMessage message : overrideMessages){
            //do the thing
            program.moveProgramCounter(message.getPcRowStart(),message.getPcByteStart());

            int baseRegister = message.getNode().getSymbol().getBaseRegister();
            int operand = message.getNode().getSymbol().getOffset();

            String LA = "LA" + baseRegister; //todo probs need to make this generic and use the opcode found in message
            generateXBytes(OpCodes.valueOf(LA),operand, message.getGenerateXBytes(), true);

        }
    }

    public void incrementOffset(){
        offset += REGISTERSIZE;
    }

    public void allocateVariable(SymbolTableRecord record){
        generate1Byte(OpCodes.ALLOC);

        String scope = record.getScope();
        if(scope.equals("program")){
            record.setBaseRegister(0);
        }
        else if(scope.equals("main")){
            record.setBaseRegister(1);
        }
        else{
            record.setBaseRegister(2);
        }

        record.setOffset(offset);
        incrementOffset();
    }

    public void generate1Byte(OpCodes opCode) {
        program.addByte(opCode.getValue(),false);
    }

    public void generate2Bytes(OpCodes opCode, int operand) {
        generateXBytes(opCode, operand, 2,false);
    }

    public void generate3Bytes(OpCodes opCode, int operand) {
        generateXBytes(opCode, operand, 3,false);
    }

    public void generate5Bytes(OpCodes opCode, int operand) {
        generateXBytes(opCode, operand, 5,false);
    }

    public void generateXBytes(OpCodes opCode, int operand, int x, boolean overridingAddresses){
        if(x < 0)
            return;

        int[] operands = new int[x];
        operands[0] = opCode.getValue();
        for (int i = 1; i < x; i++) {
            int power = 8 * (x - i - 1);
            int divisor = (int)Math.pow(2, power);
            int higherOrder = operand / divisor;
            operands[i] = higherOrder;
            operand -= higherOrder * divisor;
        }

        for(int i = 0; i < x; i++){
            program.addByte(operands[i], overridingAddresses);
        }
    }

    @Override
    public void handleMessage(ObservableMessage message) {
        if(message instanceof InstructionOverrideMessage){
            InstructionOverrideMessage typedMessage = (InstructionOverrideMessage)message;
            overrideMessages.add(typedMessage);
        }
    }
}



