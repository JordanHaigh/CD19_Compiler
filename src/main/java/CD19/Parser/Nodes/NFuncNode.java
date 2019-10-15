package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a func of the form:
 * NFUND	<func>	::=	function <id> ( <plist> ) : <rtype> <funcbody>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NFuncNode implements Node{
    //NFUND	<func>	::=	function <id> ( <plist> ) : <rtype> <funcbody>

    NPListNode npListNode;
    NRTypeNode nrTypeNode;
    NFuncBodyNode nFuncBodyNode;
    private static NFuncNode instance;

    public NFuncNode() {
        this(NPListNode.INSTANCE(), NRTypeNode.INSTANCE(), NFuncBodyNode.INSTANCE());
    }

    public NFuncNode(NPListNode npListNode, NRTypeNode nrTypeNode, NFuncBodyNode nFuncBodyNode) {
        this.npListNode = npListNode;
        this.nrTypeNode = nrTypeNode;
        this.nFuncBodyNode = nFuncBodyNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NFuncNode INSTANCE() {
        if (instance == null) {
            instance = new NFuncNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the func node
     * @param parser The parser
     * @return A valid func TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode func = new TreeNode();

        if(!parser.peekAndConsume(Token.TFUNC)){
            parser.syntacticError("Expected function keyword", parser.peek());
            return func;
        }

        Token id = parser.peek();

        if(!parser.peekAndConsume(Token.TIDEN)){
            parser.syntacticError("Expection Function Identifier", parser.peek());
            return func;
        }

        parser.enterScope(id.getStr()+"_function");

        if(!parser.peekAndConsume(Token.TLPAR)){
            parser.syntacticError("Expected Left Parenthesis", parser.peek());
            return func;
        }

        TreeNode plist = npListNode.make(parser);

        if(!parser.peekAndConsume(Token.TRPAR)){
            parser.syntacticError("Expected Right Parenthesis", parser.peek());
            return func;
        }

        if(!parser.peekAndConsume(Token.TCOLN)){
            parser.syntacticError("Expected Colon", parser.peek());
            return func;
        }

        TreeNode rtype = nrTypeNode.make(parser);

        TreeNode funcBody = nFuncBodyNode.make(parser);

        parser.leaveScope();

        TreeNode locals = funcBody.getLeft(); //from funcbody

//        if(locals == null || locals.getValue() == TreeNode.NUNDEF){
//            return func;
//        }

        TreeNode stats = funcBody.getRight(); //from funcbody

        //we need stats working from here on, so it cant be null or nundef
        if(stats == null || stats.getValue() == TreeNode.NUNDEF){
            return func; //returns NUNDEF
        }

        func = new TreeNode(TreeNode.NFUND, plist, locals, stats);

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), rtype.getType(), parser.getScope()); //should be global scope

        parser.insertIdentifierRecord(record);

        func.setSymbol(record);
        func.setType(rtype.getType());
        return func;
    }
}

