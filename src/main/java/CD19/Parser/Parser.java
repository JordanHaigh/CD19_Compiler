package CD19.Parser;

import CD19.Observer.*;
import CD19.Parser.Nodes.*;
import CD19.Scanner.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Used for syntactical and semantic analysis, as well as building a parse tree structure
 * of the program
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class Parser implements Subject {
    private List<Token> tokens;
    private int tokenIndex = 0;

    private List<Observer> observers = new ArrayList<>();

    private SymbolTable constants, identifiers, types;
    private Stack<String> scopeStack = new Stack<>();

    private List<SyntacticErrorMessage> syntacticErrors = new ArrayList<>();
    private List<SemanticErrorMessage> semanticErrors = new ArrayList<>();

    private boolean syntacticallyValid = true;
    private boolean semanticallyValid = true;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        constants = new SymbolTable();
        identifiers = new SymbolTable();
        types = new SymbolTable();
        scopeStack.push("");
    }

    public boolean isSyntacticallyValid() {
        return syntacticallyValid;
    }

    public boolean isSemanticallyValid() {
        return semanticallyValid;
    }

    /**
     * Enter new scope of program
     *
     * @param scope - Scope to enter
     */
    public void enterScope(String scope) {
        scopeStack.push(scope);
    }

    /**
     * Leave scope in program
     */
    public void leaveScope() {
        scopeStack.pop();
    }

    /**
     * Create a new Syntactic Error message that will be added to the program listing file
     *
     * @param message - Error Message to print in listing
     * @param line    - Error Line
     * @param column  - Error Column
     */
    public void syntacticError(String message, int line, int column) {
        String prepend = "Syntactic Error - ";
        prepend += message;

        SyntacticErrorMessage syntacticErrorMessage = new SyntacticErrorMessage(prepend, line, column);
        if (!syntacticErrorAlreadyExists(syntacticErrorMessage))
            syntacticErrors.add(syntacticErrorMessage);
    }

    /**
     * Create a new Syntactic Error message that will be added to the program listing file
     *
     * @param message - Error Message to print in listing
     * @param token   - Token containing line and column information
     */
    public void syntacticError(String message, Token token) {
        syntacticError(message, token.getLine(), token.getCol());
    }

    /**
     * Create a new Semantic Error message that will be added to the program listing file
     *
     * @param message - Error Message to print in listing
     * @param line    - Error Line
     * @param column  - Error Column
     */
    public void semanticError(String message, int line, int column) {
        String prepend = "Semantic Error - ";
        prepend += message;
        SemanticErrorMessage semanticErrorMessage = new SemanticErrorMessage(prepend, line, column);
        if (!semanticErrorAlreadyExists(semanticErrorMessage))
            semanticErrors.add(semanticErrorMessage);
    }

    public boolean syntacticErrorAlreadyExists(SyntacticErrorMessage newM) {
        for (SyntacticErrorMessage m : syntacticErrors) {
            if (m.getErrorMessage().equals(newM.getErrorMessage()) &&
                    m.getLine() == newM.getLine() &&
                    m.getColumn() == newM.getColumn()
            ){
                return true;
            }

        }
        return false;
    }

    public boolean semanticErrorAlreadyExists(SemanticErrorMessage newM) {
        for (SemanticErrorMessage m : semanticErrors) {
            if (m.getErrorMessage().equals(newM.getErrorMessage()) &&
                    m.getLine() == newM.getLine() &&
                    m.getColumn() == newM.getColumn()
            ){
                return true;
            }
            if (m.getErrorMessage().equals(newM.getErrorMessage()) && //#judgementfreezone
                    m.getLine() == newM.getLine() &&
                    m.getColumn() == newM.getColumn()+1
            ){
                return true;
            }
        }
        return false;
    }

    /**
     * Create a new Semantic Error message that will be added to the program listing file
     *
     * @param message - Error Message to print in listing
     * @param token   - Token containing line and column information
     */
    public void semanticError(String message, Token token) {
        semanticError(message, token.getLine(), token.getCol());
    }


    /**
     * Get Current Scope of Program
     *
     * @return - Scope of Program
     */
    public String getScope() {
        return scopeStack.peek();
    }

    /**
     * Peek at what the next token will be
     *
     * @return Next Token in token stream
     */
    public Token peek() {
        if (tokenIndex < tokens.size())
            return tokens.get(tokenIndex);
        else
            return tokens.get(tokens.size() - 1); //keep returning eof token
    }

    /**
     * Peek at a specific index of what the token is
     *
     * @param index - Index to peek
     * @return - Token at index
     */
    public Token peek(int index) {
        return tokens.get(index);
    }

    /**
     * Consume token. Moves token index forwards by one
     */
    public void consume() {
        tokenIndex++;
    }

    /**
     * Consume token. Moves token index forwards by the amount
     *
     * @param amount amount to consume
     */
    public void consume(int amount) {
        tokenIndex += amount;
    }

    /**
     * Parse the token stream into a parse tree
     *
     * @return - Parse tree of the token stream
     */
    public TreeNode parse() {
        //try making an nprog node

        try {
            TreeNode tree = getProgNode().make(this);

            if (tree == null)
                throw new Exception();

            if (syntacticErrors.size() > 0)
                syntacticallyValid = false;

            if (semanticErrors.size() > 0)
                semanticallyValid = false;


            //Send messages for listing file
            for (SyntacticErrorMessage message : syntacticErrors) {
                notifyObservers(message);
            }

            for (SemanticErrorMessage message : semanticErrors) {
                notifyObservers(message);
            }

            return tree;

        } catch (Exception e) {
            e.printStackTrace();
            syntacticallyValid = false;
            semanticallyValid = false;

            //Send messages for listing file
            for (SyntacticErrorMessage message : syntacticErrors) {
                notifyObservers(message);
            }

            for (SemanticErrorMessage message : semanticErrors) {
                notifyObservers(message);
            }

            return new TreeNode();
        }
    }

    /**
     * Print trees - One is pretty and the other is from Dan's code
     *
     * @param tree - Tree to print
     */
    public void printTree(TreeNode tree) {

        System.out.println(tree.prettyPrintTree());

        PrintWriter out = new PrintWriter(System.out);
        tree.danPrintTree(out, tree);
        out.flush();

        System.out.println();

    }

    /**
     * Peek and consume token if the next token in the stream matches the parameter
     *
     * @param tokenId - Token to consume if found next
     * @return - True if consume, false if not
     */
    public boolean peekAndConsume(int tokenId) {
        int peekedTokenId = peek().getTokenID();
        if (peekedTokenId == tokenId) {
            consume();
            return true;
        }
        return false;
    }


    /**
     * Find Symbol Table Record in the Types Table
     *
     * @param record - Record to find
     * @return - record in table or null
     */
    public SymbolTableRecord lookupTypeRecord(SymbolTableRecord record) {
        return lookup(record, types);
    }

    /**
     * Find Symbol Table Record in the Constants Table
     *
     * @param record - Record to find
     * @return - record in table or null
     */
    public SymbolTableRecord lookupConstantRecord(SymbolTableRecord record) {
        return lookup(record, constants);
    }

    /**
     * Find Symbol Table Record in the Identifiers Table
     *
     * @param record - Record to find
     * @return - record in table or null
     */
    public SymbolTableRecord lookupIdentifierRecord(SymbolTableRecord record) {
        return lookup(record, identifiers);
    }

    /**
     * Find Symbol Table Record in the Table argument
     *
     * @param record      - Record to find
     * @param symbolTable - SymbolTable to Search
     * @return - record in table or null
     */
    public SymbolTableRecord lookup(SymbolTableRecord record, SymbolTable symbolTable) {
        if (symbolTable.contains(record.getSymbolTableKey())) {
            return symbolTable.get(record.getSymbolTableKey()); //return the version of the record that already exists in the table
        } else {
            return null;
        }
    }

    /**
     * Insert new record into Type Symbol Symbol Table
     *
     * @param record - Record to add
     */
    public boolean insertTypeRecord(SymbolTableRecord record) {
        return insertRecord(record, types);
    }

    /**
     * Insert new record into Constant Symbol Table
     *
     * @param record - Record to add
     */
    public boolean insertConstantRecord(SymbolTableRecord record) {
        return insertRecord(record, constants);
    }

    /**
     * Insert new record into Identifier Symbol Table
     *
     * @param record - Record to add
     */
    public boolean insertIdentifierRecord(SymbolTableRecord record) {
        return insertRecord(record, identifiers);
    }

    /**
     * Insert new record into Symbol Table
     *
     * @param record      - Record to add
     * @param symbolTable - SymbolTable to insert
     */
    public boolean insertRecord(SymbolTableRecord record, SymbolTable symbolTable) {
        if (!symbolTable.contains(record.getSymbolTableKey())) {
            symbolTable.insert(record);
            return true;
        } else {
            return false;
        }

    }

    public List<SemanticErrorMessage> getSemanticErrors() {
        return semanticErrors;
    }

    public List<SyntacticErrorMessage> getSyntacticErrors() {
        return syntacticErrors;
    }

    public SymbolTable getConstants(){return constants;}
    public SymbolTable getIdentifiers(){return identifiers;}

    public String getProgramScope() {
        return scopeStack.get(0);
    }

    /**
     * Sets up instances that may cause cyclic constructors. Stop stack overflow exceptions
     *
     * @return NProgNode setup with instances
     */
    public NProgNode getProgNode() {
        // Create exponent node separately or else we get a recursive loop
        //bool
        NExponentNode nExponentNode = NExponentNode.INSTANCE();
        NReptStatNode nReptStatNode = NReptStatNode.INSTANCE();
        NAsgnStatNode nAsgnStatNode = NAsgnStatNode.INSTANCE();
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
        for (Observer o : observers) {
            o.handleMessage(message);

        }
    }

    @Override
    public String toString() {
        try {
            return "Current Token: " + tokens.get(tokenIndex);
        } catch (Exception e) {
            return "Yo my dude I'm out of tokens";
        }
    }
}
