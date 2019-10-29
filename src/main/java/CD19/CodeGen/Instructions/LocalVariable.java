package CD19.CodeGen.Instructions;

import CD19.CodeGen.CodeGenerator;
import CD19.Observer.ObservableMessage;
import CD19.Observer.Observer;
import CD19.Observer.Subject;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LocalVariable implements Subject {
    private static LocalVariable instance;

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static LocalVariable INSTANCE() {
        if (instance == null) {
            instance = new LocalVariable();
        }
        return instance;
    }


    public static void generate(CodeGenerator generator, TreeNode node) {
        int nodeValue = node.getValue();

        switch(nodeValue){
            case TreeNode.NSIMV:
                generateNSIMV(generator, node);
                break;
        }
    }

    private static void generateNSIMV(CodeGenerator generator, TreeNode node){
        SymbolTableRecord record = node.getSymbol();


    }






    static List<Observer> observers = new ArrayList<>();

        @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers(ObservableMessage message) {

    }
}
