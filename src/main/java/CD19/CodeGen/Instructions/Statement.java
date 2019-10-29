package CD19.CodeGen.Instructions;

import CD19.CodeGen.CodeGenerator;
import CD19.CodeGen.OpCodes;
import CD19.Observer.InstructionOverrideMessage;
import CD19.Observer.ObservableMessage;
import CD19.Observer.Observer;
import CD19.Observer.Subject;
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
        }
    }

    private static void generatePrintStatement(CodeGenerator generator, TreeNode node){
        //LA0 - get constant
        int operand = -99; //todo need to second sweep

        InstructionOverrideMessage message = new InstructionOverrideMessage(     //todo only applies to string print
                generator.getProgram().getProgramCounter().getRow(),
                generator.getProgram().getProgramCounter().getByte(),
                5,
                OpCodes.LA0,
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
