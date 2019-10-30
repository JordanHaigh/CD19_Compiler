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
    private static SymbolTable identifiers;

    public void setIdentifiers(SymbolTable identifiers){this.identifiers = identifiers;}
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


    public static void generate(CodeGenerator generator, TreeNode node){
        if(node == null)
            return;

        // determine type of declaration node
        switch(node.getValue()){
            case TreeNode.NSDECL :{
                genSDecl(generator, node);
                break;
            }
        }
    }

    private static void genSDecl(CodeGenerator generator, TreeNode node){
        //get scope of treenode - that way we get all treenodes to decl at once
        String scope = node.getSymbol().getScope();
        List<SymbolTableRecord> recordsInScope = getIdentifierRecordsInScope(scope);


        if(recordsInScope.size() > 0){
            generator.generate2Bytes(OpCodes.LB, recordsInScope.size());
            //generator.generate3Bytes(OpCodes.LH, recordsInScope.size());
            generator.generate1Byte(OpCodes.ALLOC);

            for(SymbolTableRecord record : recordsInScope){
                generator.allocateVariable(record);
                initialiseVariableToDefault(generator, record); //k=i+j doesnt initialise, but hello world does. so just initialise regardless
            }
        }

    }

    private static List<SymbolTableRecord> getIdentifierRecordsInScope(String scope){
        List<SymbolTableRecord> recordList = identifiers.getAllRecords();
        List<SymbolTableRecord> recordsInScope = new ArrayList<>();
        List<SymbolTableRecord> recordsToRemoveFromIdentifiers = new ArrayList<>();

        for(SymbolTableRecord record : recordList){
            if(record.getScope().equals(scope)){
                recordsInScope.add(record);
                recordsToRemoveFromIdentifiers.add(record);
            }
        }

        //remove so we don't repeat this process again for the same sdecls that we just made
        for(SymbolTableRecord record : recordsToRemoveFromIdentifiers){
            identifiers.remove(record);
        }

        return recordsInScope;
    }

    private static void initialiseVariableToDefault(CodeGenerator generator, SymbolTableRecord record) {
        int baseAddr = record.getBaseRegister();
        String LA ="LA" + baseAddr;

        // 91 = LA (Load Address)
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
