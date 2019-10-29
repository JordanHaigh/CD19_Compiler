package CD19.CodeGen.Instructions;

import CD19.CodeGen.CodeGenerator;
import CD19.CodeGen.OpCodes;
import CD19.Observer.InstructionOverrideMessage;
import CD19.Observer.ObservableMessage;
import CD19.Observer.Observer;
import CD19.Observer.Subject;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Statement implements Subject {

    private static Statement instance;

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static Statement INSTANCE() {
        if (instance == null) {
            instance = new Statement();
        }
        return instance;
    }

    public static void generate(CodeGenerator generator, TreeNode node){
        int nodeValue = node.getValue();
        switch(nodeValue){
            case TreeNode.NPRINT: {
                generatePrintStatement(generator,node);
            }
            case TreeNode.NPRLN : {
                generatePrintLineStatement(generator,node);
            }
            case TreeNode.NINPUT: {
                generateInputStatement(generator,node);
            }
        }
    }

    private static void generatePrintStatement(CodeGenerator generator, TreeNode node){
        //LA0 - get constant
        int operand = -99;

        //todo maybe check that this is a string before sending off a message, you might not need to do this for ints and variables
        InstructionOverrideMessage message = new InstructionOverrideMessage(
                generator.getProgram().getProgramCounter().getRow(),
                generator.getProgram().getProgramCounter().getByte(),
                5,
                OpCodes.LA0, //todo try printing out single number, variable, TRUE, etc.
                node.getLeft()
        );

        generator.generate5Bytes(OpCodes.LA0,operand);

        instance.notifyObservers(message);

        generator.generate1Byte(OpCodes.STRPR);
    }



    private static void generatePrintLineStatement(CodeGenerator generator, TreeNode node){
        generatePrintStatement(generator, node);
        generator.generate1Byte(OpCodes.NEWLN);
    }

    private static void generateInputStatement(CodeGenerator generator, TreeNode node){
        SymbolTableRecord record = node.getLeft().getSymbol();
        //that will tell us the variable to print

        int baseRegister = record.getBaseRegister();
        int offset = record.getOffset();

        String LA = "LA" + baseRegister;
        generator.generate5Bytes(OpCodes.valueOf(LA), offset);
        generator.generate1Byte(OpCodes.READI);
        generator.generate1Byte(OpCodes.ST);
    }


    static List<Observer> observers = new ArrayList<>();
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
