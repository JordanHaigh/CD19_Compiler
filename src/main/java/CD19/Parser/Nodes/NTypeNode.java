package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a type of the form:
 * <type>	::=	<anyFuckingID> is <typeTail>
 * <typeTail>	::=	<fields> end |  array [<expr>] of <structid>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NTypeNode implements Node {

    //NRTYPE|NATYPE	<type>	::=	<anyFuckingID> is <typeTail>
    //	<typeTail>	::=	<fields> end |  array [<expr>] of <structid>


    NFieldsNode nFieldsNode;
    NExprNode nExprNode;
    private static NTypeNode instance;


    public NTypeNode() {
        this(NFieldsNode.INSTANCE(), null);
    }

    public NTypeNode(NFieldsNode nFieldsNode, NExprNode nExprNode) {
        this.nFieldsNode = nFieldsNode;
        this.nExprNode = nExprNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     *
     * @return - Instance of the class
     */
    public static NTypeNode INSTANCE() {
        if (instance == null) {
            instance = new NTypeNode();
        }
        return instance;
    }

    /**
     * Sets the nExprNode in the class so cyclic constructors are prevented
     * @param nExprNode - Node to set
     */
    public void setnExprNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    /**
     * Attempts to generate the type node
     * @param parser The parser@param parser The parser
     * @return A valid type TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode type = new TreeNode();

        Token id = parser.peek();
        if (!parser.peekAndConsume(Token.TIDEN)) {
            parser.syntacticError("Expected an Identifier", parser.peek());
            return type;
        }

        if (!parser.peekAndConsume(Token.TIS)) {
            parser.syntacticError("Expected an \"is\" keyword", parser.peek());
            return type;
        }

        TreeNode tail = tail(parser,id);
        if(tail == null){
            return type;// fail
        }

        String dataType;
        boolean isStruct = false;

        if (tail.getValue() == TreeNode.NATYPE)
            dataType = tail.getType();
        else{
            dataType = id.getStr();
            isStruct = true;
        }


        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), dataType, parser.getScope()); //global (program) scope

        if(isStruct){
            boolean insertSuccessful = parser.insertTypeRecord(record);
            if(!insertSuccessful){
                parser.semanticError("Struct already exists", id);
            }
        }
        else{
            boolean insertSuccessful = parser.insertIdentifierRecord(record);
            if(!insertSuccessful){
                parser.semanticError("Array Type already exists", id);
            }
        }
        tail.setSymbol(record);
        return tail;
    }

    /**
     * Tail method that completes the node (two paths
     * @param parser The parser
     * @return - TreeNode containing tailing information
     */
    private TreeNode tail(Parser parser, Token id) {
        Token token = parser.peek();
        if (token.getTokenID() == Token.TARAY) {
            return arrayTail(parser);
        } else {
            return structTail(parser,id);
        }
    }

    /**
     * Tail method that completes the node as an array
     * @param parser The parser
     * @return - TreeNode containing tailing information
     */
    private TreeNode arrayTail(Parser parser) {
        //array [<expr>] of <structid>
        TreeNode arrayTail = new TreeNode();
        if (!parser.peekAndConsume(Token.TARAY)) {
            parser.syntacticError("Expected Array keyword", parser.peek());
            return arrayTail;
        }

        if (!parser.peekAndConsume(Token.TLBRK)) {
            parser.syntacticError("Expected Left Bracket", parser.peek());
            return arrayTail;
        }

        Token peek = parser.peek();
        TreeNode exprTNode = nExprNode.make(parser);

        if(exprTNode != null && exprTNode.getType() != null){ //this stays here for unit tests
            if(!exprTNode.getType().equals("Integer")){
                parser.semanticError("Must use integer for array index", peek);
            }
        }

        if (!parser.peekAndConsume(Token.TRBRK)) {
            parser.syntacticError("Expected Right Bracket", parser.peek());
            return arrayTail;
        }

        if (!parser.peekAndConsume(Token.TOF)) {
            parser.syntacticError("Expected Of keyword", parser.peek());
            return arrayTail;
        }

        Token structId = parser.peek();

        if (!parser.peekAndConsume(Token.TIDEN)) {
            parser.syntacticError("Expected Identifier", parser.peek());
            return arrayTail;
        }

        //semantic check that struct id exists
        SymbolTableRecord structIdRecord = new SymbolTableRecord(structId.getStr(),null,parser.getScope()); //scope will be global

        if(parser.lookupTypeRecord(structIdRecord) == null){
            parser.semanticError("Struct name "+ structId.getStr()+" doesn't exist in symbol table", structId);
        }

        arrayTail = new TreeNode(TreeNode.NATYPE, exprTNode, null);
        arrayTail.setType("ArrayOf"+structId.getStr());
        return arrayTail;
    }

    /**
     * Tail method that completes the node as a struct
     * @type parser The parser
     * @return - TreeNode containing tailing information
     */
    private TreeNode structTail(Parser parser, Token structId) {
        //<fields> end
        parser.enterScope(structId.getStr() + "_struct");
        TreeNode nFieldsTNode = nFieldsNode.make(parser);
        parser.leaveScope();

        if (!parser.peekAndConsume(Token.TEND)) {
            parser.syntacticError("Expected End keyword", parser.peek());
            return new TreeNode();
        }


        return new TreeNode(TreeNode.NRTYPE, nFieldsTNode, null);
    }
}

