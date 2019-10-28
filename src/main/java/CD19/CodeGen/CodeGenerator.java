package CD19.CodeGen;

import CD19.CodeGen.Instructions.Declaration;
import CD19.CodeGen.Instructions.Statement;
import CD19.Parser.SymbolTable;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    TreeNode tree;
    InstructionMatrix program;
    public static final int REGISTERSIZE = 8;
    public static int offset = 0;

    public CodeGenerator(TreeNode tree){
        this.tree = tree;
        program = new InstructionMatrix();

    }

    public InstructionMatrix getProgram() { return program; }

    public void run(){
        run(tree);
    }

    public void populateStringConstants(SymbolTable constants){
        List<SymbolTableRecord> constantsList = constants.getAllRecords();
        for(SymbolTableRecord record : constantsList){
            program.addString(record.getLexeme());
        }

    }

    public void run(TreeNode root){
        //post order traversal
        if (root == null)
            return;

        run(root.getLeft());
        // todo middle???
        run(root.getRight());

        // now deal with the node
        if(root.getValue() == TreeNode.NSDECL){
            Declaration.generate(this, root);
        }
        if(root.getValue() == TreeNode.NPRLN){
            Statement.generate(this,root);
        }
        System.out.println(root + " ");
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

    public void generateString(String string){
        program.addString(string);
    }

    public void generate1Byte(OpCodes opCode) {
        program.addByte(opCode.getValue());
    }

    public void generate2Bytes(OpCodes opCode, int operand) {
        generateXBytes(opCode, operand, 2);
    }

    public void generate3Bytes(OpCodes opCode, int operand) {
        generateXBytes(opCode, operand, 3);
    }

    public void generate5Bytes(OpCodes opCode, int operand) {
        generateXBytes(opCode, operand, 5);
    }

    public void generateXBytes(OpCodes opCode, int operand, int x){
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
            program.addByte(operands[i]);
        }
    }

}



