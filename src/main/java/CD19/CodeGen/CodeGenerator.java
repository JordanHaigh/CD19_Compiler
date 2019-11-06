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

/**
 *
 * Main Code Generation Logic
 *
 * @author Jordan Haigh c3256730
 * @since 6/11/19
 * */
public class CodeGenerator {
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

    public InstructionMatrix getProgram() { return program; }

    /**
     * Two sweeps - first sweep does main logic.
     * Second sweep fixes up any constants addressing
     * Adds return opcode at end of first sweep if necessary and generates the constants section
     */
    public void run(){
        runFromNode(tree); //first run for building most of matrix. need to do post code gen sweep for string constant locations
        if(!stopProcessing) //if user hadn't stopped code-genning manually (didn't use a return statement)
            generate1Byte(OpCodes.RETN); //use this when finished program
        program.populateConstants(constants, intConstants, realConstants);
        secondRun();
    }

    /**
     * Entry point of running the tree structure.
     * It'll go through all node types and generate opcodes for relevant node types.
     * @param node - TreeNode entry point
     */
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


    /**
     * Fixes up any addressing that we may've borked in the first run
     */
    public void secondRun(){
        //fix up any addressing that was borked in first run
        for(InstructionOverrideMessage message : overrideMessages){
            program.moveProgramCounter(message.getPcRowStart(),message.getPcByteStart());

            int baseRegister = message.getRecord().getBaseRegister();
            int operand = message.getRecord().getOffset();
            //use LA for string variables
            //use LV for real lites or big ints
            OpCodes opcode = message.getOpCode();
            //String LA = "LA" + baseRegister;
            generateXBytes(opcode,operand, message.getGenerateXBytes(), true);
        }
    }

    /**
     * Add offset when initialising variables
     */
    public void incrementOffset(){
        offset += REGISTERSIZE;
    }


    /**
     * Set the base register and offset for a symbol table record
     * Base Register depends on the scope of the record (program, main,etc.)
     * @param record - Record to update
     */
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

    /**
     * Generate a 1 byte opcode
     * @param opCode - Opcode to generate
     */
    public void generate1Byte(OpCodes opCode) {
        program.addByte(opCode.getValue(),false);
    }

    /**
     * Generate a 2 byte opcode
     * @param opCode - Opcode to generate
     * @param operand - Address operand for operation
     */
    public void generate2Bytes(OpCodes opCode, int operand) {
        generateXBytes(opCode, operand, 2,false);
    }

    /**
     * Generate a 3 byte opcode
     * @param opCode - Opcode to generate
     * @param operand - Address operand for operation
     */
    public void generate3Bytes(OpCodes opCode, int operand) {
        generateXBytes(opCode, operand, 3,false);
    }

    /**
     * Generate a 5 byte opcode
     * @param opCode - Opcode to generate
     * @param operand - Address operand for operation
     */
    public void generate5Bytes(OpCodes opCode, int operand) {
        generateXBytes(opCode, operand, 5,false);
    }

    /**
     * Generates X bytes including opcode
     * @param opCode - Opcode to generate
     * @param operand - Address operand for generation
     * @param x - Number of bytes
     * @param overridingAddresses - Boolean if we are overriding addresses
     */
    public void generateXBytes(OpCodes opCode, int operand, int x, boolean overridingAddresses){
        //https://bitbucket.org/thaigh/x6jdHG74mg2s/
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

    /**
     * Generate sections of mod file from program node
     * @param root - Program node
     */
    private void generateProgram(TreeNode root){
        //GLOB
        generateGlobals(root.getLeft());
        //MAIN
        generateMain(root.getRight());

    }

    /**
     * Generate globals sections of parse tree as mod file operations
     * @param root - Globals node
     */
    private void generateGlobals(TreeNode root){
        //todo later

    }

    /**
     * Main node consists of sdecls and stats
     * Generate all sdecls and then generate all stats.
     * @param root - Main node
     */
    private void generateMain(TreeNode root){
        //NSDLST or NSDECL
        List<TreeNode> deforestatedSDeclNodes = root.getLeft().detreeify();
        Declaration.generate(this, deforestatedSDeclNodes);

        //NSTATS or NSTAT
        if(root.getRight().getValue() == TreeNode.NSTATS){
            List<TreeNode> deforestatedStatNodes = root.getRight().detreeify();
            for(TreeNode stat : deforestatedStatNodes){
                if(stopProcessing)
                    break;
                Statement.generate(this, stat);
            }
        }
        else{
            Statement.generate(this,root.getRight());
        }

    }

    /**
     * Prints out matrix to printwriter
     * @param printByteAsChar  - boolean if you want to print the chars in str constant section
     * @param printWriter - Print writer to write out to
     */
    public void printMatrix(boolean printByteAsChar, PrintWriter printWriter){
        program.printMatrix(printByteAsChar, printWriter);
    }

    /**
     * Adds real literal to the real constants list. adds a message to go and overwrite that location later in second sweep
     * @param record - Symboltablerecord containing the real literal
     */
    public void realLiteral(SymbolTableRecord record){
        realConstants.add(record);

        generateInstructionOverrideMessage(OpCodes.LV0, 5, record);

        generate5Bytes(OpCodes.LV0,-99); //placeholder - updated in second run

    }

    /**
     * Adds integer literal to the int constants list.
     * adds a message to go and overwrite that location later in second sweep
     * @param record - Symboltablerecord containing the int literal
     */
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

    /**
     * Instruction message containing all relevant information to overwrite location during second sweep
     * @param opCode - Opcode that will overwrite old location
     * @param numberOfBytes - new number of bytes that will overwrite old location
     * @param record - record containing information to overwrite old location
     */
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

    /**
     * if return statement hit, stop future processing
     */
    public void stopProcessing(){
        stopProcessing = true;
    }

    /**
     * Checks if program has stopped processing
     * @return - true or false depending if processing stopped
     */
    public boolean hasStoppedProcessing(){return stopProcessing;}
}



