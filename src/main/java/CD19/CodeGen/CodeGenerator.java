package CD19.CodeGen;

import CD19.CodeGen.Instructions.Declaration;
import CD19.CodeGen.Instructions.Statement;
import CD19.Observer.InstructionOverrideMessage;
import CD19.Observer.ObservableMessage;
import CD19.Observer.Observer;
import CD19.Parser.SymbolTable;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import com.sun.org.apache.bcel.internal.classfile.Code;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    TreeNode tree;
    SymbolTable constants;
    SymbolTable identifiers;
    InstructionMatrix program;
    boolean stopProcessing = false;
    public static final int REGISTERSIZE = 8;
    public static int offset = 0;

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
        Declaration.generate(this, identifiers);
        run(tree); //first run for building most of matrix. need to do post code gen sweep for string constant locations
        if(!stopProcessing) //if user hadn't stopped code-genning manually (didn't use a return statement)
            generate1Byte(OpCodes.RETN); //use this when finished program
        program.populateConstants(constants, intConstants, realConstants);
        secondRun();
    }

    public InstructionMatrix getProgram() { return program; }

    public void run(TreeNode root){
        if(stopProcessing){
            return;
        }

        //post order traversal
        if (root == null)
            return;

        run(root.getLeft());
        // todo middle???
        run(root.getRight());

        // now deal with the node
        int rootValue = root.getValue();
        switch(rootValue) {
            case TreeNode.NPRLN: case TreeNode.NPRINT: case TreeNode.NINPUT: //iostat
            case TreeNode.NASGN: case TreeNode.NPLEQ : case TreeNode.NMNEQ : case TreeNode.NSTEQ: case TreeNode.NDVEQ: //asgnstat
            case TreeNode.NRETN : //return stat
                Statement.generate(this, root);
                break;
            // reptstat

            //callstat
        }
        System.out.println(root + " ");
    }



    public void secondRun(){
        //fix up any addressing that was borked in first run
        for(InstructionOverrideMessage message : overrideMessages){
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

    public void printMatrix(boolean printByteAsChar, PrintWriter printWriter){
        program.printMatrix(printByteAsChar, printWriter);
    }

    public void realLiteral(TreeNode node){
        realConstants.add(node.getSymbol());

        generateInstructionOverrideMessage(OpCodes.LV0, 5, node);

        generate5Bytes(OpCodes.LV0,-99); //placeholder - updated in second run

    }

    public void integerLiteral(TreeNode node){
        int value = Integer.parseInt(node.getSymbol().getLexeme());

        if(value < 256){
            //LB Operation
            generate2Bytes(OpCodes.LB, value);
        }
        else if(value < 65536){
            generate3Bytes(OpCodes.LH,value);
        }
        else{
            //add to constants
            intConstants.add(node.getSymbol());

            generateInstructionOverrideMessage(OpCodes.LV0,5,node);

            generate5Bytes(OpCodes.LV0,-99); //placeholder - updated in second run
        }
    }

    public void generateInstructionOverrideMessage(OpCodes opCode, int numberOfBytes, TreeNode node){
        InstructionOverrideMessage message = new InstructionOverrideMessage(
                program.getProgramCounter().getRow(),
                program.getProgramCounter().getByte(),
                numberOfBytes,
                opCode,
                node
        );

        overrideMessages.add(message);
    }

    private List<TreeNode> leaves = new ArrayList<>();
    public List<TreeNode> detreeify(TreeNode root){
        leaves = new ArrayList<>();
        detreeify_recurse(root);
        return leaves;
    }

    private void detreeify_recurse(TreeNode root){
        if(root == null){
            return;
        }

        if(root.getLeft() == null && root.getMiddle() == null && root.getRight() == null){
            leaves.add(root);
        }

        if(root.getLeft() != null) detreeify_recurse(root.getLeft());
        if(root.getMiddle() != null) detreeify_recurse(root.getMiddle());
        if(root.getRight() != null) detreeify_recurse(root.getRight());
    }

    public void stopProcessing(){
        stopProcessing = true;
    }
}



