package CD19.Parser;

import CD19.Observer.*;
import CD19.Parser.Nodes.*;
import CD19.Scanner.Token;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser implements Subject {
    private List<Token> tokens;
    private int tokenIndex = 0;

    private List<Observer> observers = new ArrayList<>();

    private SymbolTable constants, identifiers, types;
    private Stack<String> scopeStack = new Stack<>();

    private List<SyntacticErrorMessage> syntacticErrors = new ArrayList<>();

//
//    private boolean syntacticallyValid = true;
//    private boolean semanticallyValid = true;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
        constants = new SymbolTable();
        identifiers = new SymbolTable();
        types = new SymbolTable();
        scopeStack.push("");
    }

    public void enterScope(String scope){
        scopeStack.push(scope);
    }

    public void leaveScope(){
        scopeStack.pop();
    }

    public void syntacticError(String message, int line, int column)
    {
        String prepend = "Syntactic Error - ";
        prepend += message;
        syntacticErrors.add(new SyntacticErrorMessage(prepend,line, column));
    }

    public void syntacticError(String message, Token token)
    {
        syntacticError(message, token.getLine(), token.getCol());
    }

    public String getScope(){
        return scopeStack.peek();
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

            System.out.println(tree.prettyPrintTree());

            PrintWriter out = new PrintWriter(System.out);
            TreeNode.printTree(out, tree);
            out.flush();

            for(SyntacticErrorMessage message : syntacticErrors){
                notifyObservers(message);
            }

            return tree;
        }
        catch(Exception e){
            //notifyObservers(new ObservableImmediateErrorMessage("Error occurred whilst parsing the program"));
            e.printStackTrace();
            System.out.println();
//            syntacticallyValid = false;
//            semanticallyValid = false;
            for(SyntacticErrorMessage message : syntacticErrors){
                notifyObservers(message);
            }
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


    public SymbolTableRecord lookupTypeRecord(SymbolTableRecord record){
        return lookup(record, types);
    }

    public SymbolTableRecord lookupConstantRecord(SymbolTableRecord record){
        return lookup(record, constants);
    }

    public SymbolTableRecord lookupIdentifierRecord(SymbolTableRecord record){
        return lookup(record, identifiers);
    }

    public SymbolTableRecord lookup(SymbolTableRecord record, SymbolTable symbolTable){
        if(symbolTable.contains(record)){
            return record;
        }
        else{
            //throw new IllegalStateException("Variable doesn't exist in symbol table"); //todo readd in semantic
            return null;
        }
    }

    public void  insertTypeRecord(SymbolTableRecord record){
        insertRecord(record, types);
    }

    public void  insertConstantRecord(SymbolTableRecord record){
        insertRecord(record, constants);
    }

    public void insertIdentifierRecord(SymbolTableRecord record){
        insertRecord(record, identifiers);
    }

    public void insertRecord(SymbolTableRecord record, SymbolTable symbolTable){
        if(!symbolTable.contains(record)){
            symbolTable.insert(record);
        }
        else{
            //throw new IllegalStateException("Variable already exists in symbol table"); //todo readd in semantic
        }

    }

    public NProgNode getProgNode() {
        // Create exponent node separately or else we get a recursive loop
        //bool
        NExponentNode nExponentNode = NExponentNode.INSTANCE();
        NReptStatNode nReptStatNode = NReptStatNode.INSTANCE();
        NAsgnStatNode nAsgnStatNode= NAsgnStatNode.INSTANCE();
        NEListNode neListNode = NEListNode.INSTANCE();
        NForStatNode nForStatNode = NForStatNode.INSTANCE();
        NIfStatNode nIfStatNode = NIfStatNode.INSTANCE();

        //expr loop
        NInitNode nInitNode = NInitNode.INSTANCE();
        NTypeNode nTypeNode = NTypeNode.INSTANCE();
        NRelNode nRelNode = NRelNode.INSTANCE();
        NPrintItemNode nPrintItemNode = NPrintItemNode.INSTANCE();
        NReturnStatNode nReturnStatNode = NReturnStatNode.INSTANCE();
        NVarTailNode nVarTailNode = NVarTailNode.INSTANCE();

        ///stat
        NFuncBodyNode nFuncBodyNode = NFuncBodyNode.INSTANCE();
        //rept stat done
        //for stat done
        //if stat done
        NMainBodyNode nMainBodyNode = NMainBodyNode.INSTANCE();


        NProgNode nProgNode = NProgNode.INSTANCE();

        // Fix broken recursive loop
        //bool
        nExponentNode.setnBoolNode(NBoolNode.INSTANCE());
        nReptStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());
        neListNode.setnBoolNode(NBoolNode.INSTANCE());
        nForStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nIfStatNode.setnBoolNode(NBoolNode.INSTANCE());

        //bool
        nInitNode.setnExprNode(NExprNode.INSTANCE());
        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        nRelNode.setnExprNode(NExprNode.INSTANCE());
        nPrintItemNode.setnExprNode(NExprNode.INSTANCE());
        nReturnStatNode.setnExprNode(NExprNode.INSTANCE());
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());

        //stats
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
