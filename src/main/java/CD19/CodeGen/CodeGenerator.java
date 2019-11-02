package CD19.CodeGen;

import CD19.CodeGen.Instructions.Declaration;
import CD19.CodeGen.Instructions.Statement;
import CD19.Observer.InstructionOverrideMessage;
import CD19.Parser.SymbolTable;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodeGenerator {
    //todo fix return statement - when to stop processing - might need to be something in each statement function..

    TreeNode tree;
    SymbolTable constants;
    SymbolTable identifiers;
    InstructionMatrix program;

    private static boolean stopProcessing = false;
    private static final int REGISTERSIZE = 8;
    private static int offset = 0;

    List<SymbolTableRecord> intConstants = new ArrayList<>();
    List<SymbolTableRecord> realConstants = new ArrayList<>();
    List<InstructionOverrideMessage> overrideMessages = new ArrayList<>();

    public CodeGenerator(TreeNode tree, SymbolTable constants, SymbolTable identifiers){
        this.tree = tree;
        this.constants = constants;
        this.identifiers = identifiers;
        program = new InstructionMatrix();
    }

    public void run(){
        runFromNode(tree); //first run for building most of matrix. need to do post code gen sweep for string constant locations
        if(!stopProcessing) //if user hadn't stopped code-genning manually (didn't use a return statement)
            generate1Byte(OpCodes.RETN); //use this when finished program
        program.populateConstants(constants, intConstants, realConstants);
        secondRun();
    }

    public InstructionMatrix getProgram() { return program; }

    public void runFromNode(TreeNode node){
        if(stopProcessing){
            return;
        }

        //post order traversal
        if (node == null)
            return;


//        // now deal with the node
        int nodeValue = node.getValue();
        switch(nodeValue) {
            case TreeNode.NPROG: {
                generateProgram(node);
                break;
            }
            case TreeNode.NGLOB: {
                generateGlobals(node);
                break;
            }
            case TreeNode.NMAIN: {
                generateMain(node);
                break;
            }
            case TreeNode.NSTATS: {

            }
        }
    }



    public void secondRun(){
        //fix up any addressing that was borked in first run
        for(InstructionOverrideMessage message : overrideMessages){
            program.moveProgramCounter(message.getPcRowStart(),message.getPcByteStart());

            int baseRegister = message.getRecord().getBaseRegister();
            int operand = message.getRecord().getOffset();

            String LA = "LA" + baseRegister; //todo probs need to make this generic and use the opcode found in message
            generateXBytes(OpCodes.valueOf(LA),operand, message.getGenerateXBytes(), true);
        }
    }

    public void incrementOffset(){
        offset += REGISTERSIZE;
    }

    public void allocateVariable(SymbolTableRecord record){
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

    private void generateProgram(TreeNode root){
        //GLOB
        generateGlobals(root.getLeft());
        //MAIN
        generateMain(root.getRight());

    }

    private void generateGlobals(TreeNode root){
        //todo later

    }

    private void generateMain(TreeNode root){
        //NSDLST or NSDECL
        List<TreeNode> deforestatedSDeclNodes = root.getLeft().detreeify();
        Declaration.generate(this, deforestatedSDeclNodes);

        //NSTATS or NSTAT
        List<TreeNode> deforestatedStatNodes = root.getRight().detreeify();
        for(TreeNode stat : deforestatedStatNodes){
            Statement.generate(this, stat);
        }
    }

    public void printMatrix(boolean printByteAsChar, PrintWriter printWriter){
        program.printMatrix(printByteAsChar, printWriter);
    }

    public void realLiteral(SymbolTableRecord record){
        realConstants.add(record);

        generateInstructionOverrideMessage(OpCodes.LV0, 5, record);

        generate5Bytes(OpCodes.LV0,-99); //placeholder - updated in second run

    }

    public void integerLiteral(SymbolTableRecord record){
        int value = Integer.parseInt(record.getLexeme());

        if(value < 256){
            //LB Operation
            generate2Bytes(OpCodes.LB, value);
        }
        else if(value < 65536){
            generate3Bytes(OpCodes.LH,value);
        }
        else{
            //add to constants
            intConstants.add(record);

            generateInstructionOverrideMessage(OpCodes.LV0,5,record);

            generate5Bytes(OpCodes.LV0,-99); //placeholder - updated in second run
        }
    }

    public void generateInstructionOverrideMessage(OpCodes opCode, int numberOfBytes, SymbolTableRecord record){
        InstructionOverrideMessage message = new InstructionOverrideMessage(
                program.getProgramCounter().getRow(),
                program.getProgramCounter().getByte(),
                numberOfBytes,
                opCode,
                record
        );

        overrideMessages.add(message);
    }



    public void stopProcessing(){ stopProcessing = true; }
}



