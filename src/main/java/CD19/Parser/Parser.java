package CD19.Parser;

import CD19.ErrorHandler;
import CD19.Observer.*;
import CD19.Parser.Nodes.*;
import CD19.Scanner.Token;

import java.util.ArrayList;
import java.util.List;

public class Parser implements Subject {
    private List<Token> tokens;
    private int tokenIndex = 0;

    private List<Observer> observers = new ArrayList<>();

//    private SymbolTable symbolTable;
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
            TreeNode tree = getProgNode().make(this);

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

    public NProgNode getProgNode() {
        // Create exponent node separately or else we get a recursive loop
        NExponentNode nExponentNode = NExponentNode.INSTANCE();
        NReptStatNode nReptStatNode = NReptStatNode.INSTANCE();
        NAsgnStatNode nAsgnStatNode= NAsgnStatNode.INSTANCE();
        NEListNode neListNode = NEListNode.INSTANCE();
        NForStatNode nForStatNode = NForStatNode.INSTANCE();
        NIfStatNode nIfStatNode = NIfStatNode.INSTANCE();

        NInitNode nInitNode = NInitNode.INSTANCE();
        NTypeNode nTypeNode = NTypeNode.INSTANCE();
        NRelNode nRelNode = NRelNode.INSTANCE();
        NPrintItemNode nPrintItemNode = NPrintItemNode.INSTANCE();
        NReturnStatNode nReturnStatNode = NReturnStatNode.INSTANCE();

        NFuncBodyNode nFuncBodyNode = NFuncBodyNode.INSTANCE();
        //rept stat done
        //for stat done
        //if stat done
        NMainBodyNode nMainBodyNode = NMainBodyNode.INSTANCE();


        NProgNode nProgNode = NProgNode.INSTANCE();

        // Fix broken recursive loop
        nExponentNode.setnBoolNode(NBoolNode.INSTANCE());
        nReptStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());
        neListNode.setnBoolNode(NBoolNode.INSTANCE());
        nForStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nIfStatNode.setnBoolNode(NBoolNode.INSTANCE());

        nInitNode.setnExprNode(NExprNode.INSTANCE());
        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        nRelNode.setnExprNode(NExprNode.INSTANCE());
        nPrintItemNode.setnExprNode(NExprNode.INSTANCE());
        nReturnStatNode.setnExprNode(NExprNode.INSTANCE());

        nFuncBodyNode.setnStatsNode(NStatsNode.INSTANCE());
        nReptStatNode.setnStatsNode(NStatsNode.INSTANCE());
        nForStatNode.setnStatsNode(NStatsNode.INSTANCE());
        nIfStatNode.setnStatsNode(NStatsNode.INSTANCE());
        nMainBodyNode.setnStatsNode(NStatsNode.INSTANCE());

        return nProgNode;
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

    @Override
    public String toString(){

        try{
            return "Current Token: " + tokens.get(tokenIndex);
        }catch(Exception e){
            return "Yo my dude I'm out of tokens";
        }
    }
}
