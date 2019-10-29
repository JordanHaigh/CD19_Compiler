package CD19.CodeGen.Instructions;

import CD19.CodeGen.CodeGenerator;
import CD19.CodeGen.OpCodes;
import CD19.Observer.ObservableMessage;
import CD19.Observer.Observer;
import CD19.Observer.Subject;
import CD19.Parser.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Statement implements Subject {

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
        //todo add message
        generator.generate5Bytes(OpCodes.LA0,operand);

        generator.generate1Byte(OpCodes.STRPR);
    }

    private static void generatePrintLineStatement(CodeGenerator generator, TreeNode node){
        generatePrintStatement(generator, node);
        generator.generate1Byte(OpCodes.NEWLN);
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
