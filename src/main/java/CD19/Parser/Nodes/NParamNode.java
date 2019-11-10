package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a param of the form:
 * <param>	::=	<id> : <paramTypeTail> | const <arrdecl>
 * <paramTypeTail>	::=	<stype> | <typeid>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NParamNode implements Node{
    //NSIMP | NARRP | NARRC	<param>	::=	<id> : <paramTypeTail> | const <arrdecl>
    //	<paramTypeTail>	::=	<stype> | <typeid>

    NParamTypeTailNode nParamTypeTailNode;
    private static NParamNode instance;


    public NParamNode() {
        this(NParamTypeTailNode.INSTANCE());
    }

    public NParamNode(NParamTypeTailNode nParamTypeTailNode) {
        this.nParamTypeTailNode = nParamTypeTailNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NParamNode INSTANCE() {
        if (instance == null) {
            instance = new NParamNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the param node
     * @param parser The parser
     * @return A valid param TreeNode or NUNDEF if syntactic error
     */

    @Override
    public TreeNode make(Parser parser) {
        TreeNode param = new TreeNode();

        Token token = parser.peek();
        if(parser.peekAndConsume(Token.TCNST)){
            return constPath(parser);
        }
        else if(token.getTokenID() == Token.TIDEN){ //don't consume it yet
            return sTypeOrTypeIdPath(parser);
        }
        else{
            parser.syntacticError("Expected Const Keyword or Identifier for param", parser.peek());
            return param;
        }
    }

    /**
     * Generates a param node following the constant path
     * @param parser The parser
     * @return A valid const param TreeNode
     */
    private TreeNode constPath(Parser parser){
        //const <arrdecl>
        //already consumed const

        NArrDeclNode nArrDeclNode = new NArrDeclNode();
        Token peek = parser.peek();
        TreeNode arrdecl =  nArrDeclNode.make(parser); //get narrd

        TreeNode returnTreeNode = new TreeNode(TreeNode.NARRC, arrdecl, null);

        SymbolTableRecord constRecord = arrdecl.getSymbol();
        boolean insertSuccessful =  parser.insertConstantRecord(constRecord);
        if(!insertSuccessful){
            parser.semanticError("Array variable already exists", peek);
        }

        return returnTreeNode; //map to narrc

    }

    /**
     * Generates a param node following the stype or typeid path
     * Adds new record to symbol table if it has valid information
     * @param parser The parser
     * @return A valid stype or typeid param TreeNode
     */
    private TreeNode sTypeOrTypeIdPath(Parser parser){
        //<id> : <paramTypeTail>
        Token id  = parser.peek();
        parser.consume(); //already know its an ident, so its cool

        if(!parser.peekAndConsume(Token.TCOLN)){
            parser.syntacticError("Expected a Colon", parser.peek());
            return new TreeNode();
        }

        TreeNode tail = nParamTypeTailNode.make(parser);

        //we need valid tail information going forth, if its null abandon ship
        if(tail.getValue() == TreeNode.NUNDEF){
            return new TreeNode();
        }

        //since we LL(1), we already have the id of the variable, but we need to call param type tail to get the data type
        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), tail.getType(),parser.getScope());

        boolean insertSuccessful =  parser.insertIdentifierRecord(record);
        if(!insertSuccessful){
            parser.semanticError("Variable already exists", id);
        }

        if(tail.getValue() == TreeNode.NARRD){
            TreeNode returnTreeNode = new TreeNode(TreeNode.NARRP, tail, null);
            returnTreeNode.setSymbol(record);
            return returnTreeNode;
        }
        else{
            TreeNode returnTreeNode = new TreeNode(TreeNode.NSIMP, tail, null);
            returnTreeNode.setSymbol(record);
            return returnTreeNode;
        }
    }





}
