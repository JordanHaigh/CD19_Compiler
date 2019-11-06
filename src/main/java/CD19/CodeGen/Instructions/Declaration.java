package CD19.CodeGen.Instructions;

import CD19.CodeGen.CodeGenerator;
import CD19.CodeGen.OpCodes;
import CD19.Parser.SymbolTable;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates an sdecl node
 *
 * @author Jordan Haigh c3256730
 * @since 6/11/19
 */
public class Declaration {

    /**
     * Generates an sdecl node
     * @param generator - Code generator
     * @param sdecls - TreeNode of all sdecls
     */
    public static void generate(CodeGenerator generator, List<TreeNode> sdecls){
        if(sdecls.size() > 0){ //more than 1 sdecl, so its a list. need to optimise loading bytes
            generator.generate2Bytes(OpCodes.LB, sdecls.size());
            //generator.generate3Bytes(OpCodes.LH, recordsInScope.size());
            generator.generate1Byte(OpCodes.ALLOC);
        }

        for(TreeNode node : sdecls){
            SymbolTableRecord record = node.getSymbol();
            generator.allocateVariable(record);

            initialiseVariableToDefault(generator, record); //k=i+j doesnt initialise, but hello world does. so just initialise regardless
        }
    }

    /**
     * Initialises variable to its respective default value
     * @param generator  - Code Generator
     * @param record - Record to initialise value
     */
    private static void initialiseVariableToDefault(CodeGenerator generator, SymbolTableRecord record) {
        int baseAddr = record.getBaseRegister();
        String LA ="LA" + baseAddr;
        generator.generate5Bytes(OpCodes.valueOf(LA),record.getOffset());

        if(record.getDataType().equals("Boolean")){
            generator.generate1Byte(OpCodes.FALSE);
        }
        else{
            generator.generate2Bytes(OpCodes.LB,0);
            //generator.generate3Bytes(OpCodes.LH,record.getOffset());
        }

        generator.generate1Byte(OpCodes.ST);
    }
}
