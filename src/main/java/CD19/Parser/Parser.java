package CD19.Parser;

import CD19.ErrorHandler;
import CD19.Observer.*;
import CD19.Parser.Nodes.NGlobNode;
import CD19.Parser.Nodes.NProgNode;
import CD19.Scanner.Token;

import java.util.ArrayList;
import java.util.List;

public class Parser implements Subject {
    private List<Token> tokens;
    private int tokenIndex = 0;

    private List<Observer> observers = new ArrayList<>();

//    private SymbolTable constants;
//    private SymbolTable identifiers;
//
//    private boolean syntacticallyValid = true;
//    private boolean semanticallyValid = true;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
//        observers.add(errorHandler);
    }

    public Token peek() {return tokens.get(tokenIndex);}
    public Token peek(int index){return tokens.get(index);}
    public void consume(){tokenIndex++;}
    public void consume(int amount) {tokenIndex += amount;}

    public TreeNode parse(){

        //try making an nprog node
        try{
            TreeNode tree = new NProgNode().make(this);

            if(tree == null)
                throw new Exception();

            return tree;
        }
        catch(Exception e){
            notifyObservers(new ObservableImmediateErrorMessage("Error occurred whilst parsing the program"));

//            syntacticallyValid = false;
//            semanticallyValid = false;

            return new TreeNode(TreeNode.NUNDEF);
        }
    }

    public boolean peekAndConsume(int tokenId){
        int peekedTokenId = peek().getTokenID();
        if(peekedTokenId == tokenId){
            consume();
            return true;
        }
        return false;
    }


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
        for(Observer o : observers){
            o.handleMessage(message);

        }
    }
}
