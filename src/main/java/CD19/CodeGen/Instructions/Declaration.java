package CD19.CodeGen.Instructions;

import CD19.CodeGen.CodeGenerator;
import CD19.CodeGen.OpCodes;
import CD19.Observer.ObservableMessage;
import CD19.Observer.Observer;
import CD19.Observer.Subject;
import CD19.Parser.SymbolTable;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Declaration {

    private static Declaration instance;

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static Declaration INSTANCE() {
        if (instance == null) {
            instance = new Declaration();
        }
        return instance;
    }


    public static void generate(CodeGenerator generator, SymbolTable identifiers){
        List<SymbolTableRecord> identifierList = identifiers.getAllRecords();

        if(identifierList.size() > 0){
            generator.generate2Bytes(OpCodes.LB, identifierList.size());
            //generator.generate3Bytes(OpCodes.LH, recordsInScope.size());
            generator.generate1Byte(OpCodes.ALLOC);
        }

        for(SymbolTableRecord record : identifierList){
            generator.allocateVariable(record);
            initialiseVariableToDefault(generator, record); //k=i+j doesnt initialise, but hello world does. so just initialise regardless
        }
    }

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
