package CD19.CodeGen.Instructions;

import CD19.CodeGen.CodeGenerator;
import CD19.CodeGen.OpCodes;
import CD19.Observer.ObservableMessage;
import CD19.Observer.Observer;
import CD19.Observer.Subject;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Declaration implements Subject {

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
        //todo differen timplementations for int, real bool
        allocateVariable(generator, node);
        initialiseVariableToDefault(generator, node);
    }


    private static void allocateVariable(CodeGenerator generator, TreeNode node) {
        //LB = Load Byte
        //LH = Load Hex
        final int numberOfVariablesToAlloc = 1;
        generator.generate3Bytes(OpCodes.LH, numberOfVariablesToAlloc); //todo lb or lh - ask dan
        //Gen 1 thing
        //ALLOC
        //Allocate stuff to "memory" - give node symboltablerecord a base register and mem addr
        SymbolTableRecord record = node.getSymbol();
        generator.allocateVariable(record);
    }

    private static void initialiseVariableToDefault(CodeGenerator generator, TreeNode node) {
        SymbolTableRecord record = node.getSymbol();
        int baseAddr = record.getBaseRegister();
        String LA ="LA" + baseAddr;

        // 91 = LA (Load Address)
        //      Loads Address from Base Reg 1 (Main Stack)
        //      Pushes an address pointer to the stack which points to the address
        //      of where x is defined
        generator.generate5Bytes(OpCodes.valueOf(LA),record.getOffset());

        // 42 = LB (Load Byte)
        //      Push 1 byte of data to the stack with a value of x's initial value
        generator.generate3Bytes(OpCodes.LH,record.getOffset());

        // 43 = ST (Store)
        //      Store the top value on the stack, to the Address pointer ref that
        //      is second on the stack
        generator.generate1Byte(OpCodes.ST);
    }

    List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(ObservableMessage message) {
        for(Observer observer : observers){
            observer.handleMessage(message);
        }
    }
}
