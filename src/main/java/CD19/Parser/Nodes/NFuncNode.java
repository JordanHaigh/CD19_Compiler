package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NFuncNode implements Node{
    //NFUND	<func>	::=	function <id> ( <plist> ) : <rtype> <funcbody>

    NPListNode npListNode;
    NRTypeNode nrTypeNode;
    NFuncBodyNode nFuncBodyNode;

    public NFuncNode() {
        this(NPListNode.INSTANCE(), NRTypeNode.INSTANCE(), NFuncBodyNode.INSTANCE());
    }

    public NFuncNode(NPListNode npListNode, NRTypeNode nrTypeNode, NFuncBodyNode nFuncBodyNode) {
        this.npListNode = npListNode;
        this.nrTypeNode = nrTypeNode;
        this.nFuncBodyNode = nFuncBodyNode;
    }



    private static NFuncNode instance;
    public static NFuncNode INSTANCE() {
        if (instance == null) {
            instance = new NFuncNode();
        }
        return instance;
    }


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

        parser.enterScope("function_"+id.getStr());

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
        TreeNode stats = funcBody.getRight(); //from funcbody

        if(stats.getValue() == TreeNode.NUNDEF){
            return func;//todo is this right? returning nundef here because we need rtype from here on
        }

        func = new TreeNode(TreeNode.NFUND, plist, locals, stats);

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), rtype.getType(), "function_"+id.getStr());

        parser.insertIdentifierRecord(record);

        func.setSymbol(record);
        func.setType(rtype.getType());
        return func;
    }
}

