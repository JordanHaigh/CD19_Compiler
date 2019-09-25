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
        parser.peekAndConsume(Token.TFUNC);
        Token id = parser.peek();
        parser.peekAndConsume(Token.TIDEN);
        parser.enterScope("function_"+id.getStr());


        parser.peekAndConsume(Token.TLPAR);
        TreeNode plist = npListNode.make(parser);
        parser.peekAndConsume(Token.TRPAR);
        parser.peekAndConsume(Token.TCOLN);
        TreeNode rtype = nrTypeNode.make(parser);

        TreeNode funcBody = nFuncBodyNode.make(parser);
        parser.leaveScope();

        TreeNode locals = funcBody.getLeft(); //from funcbody
        TreeNode stats = funcBody.getRight(); //from funcbody

        TreeNode treenode = new TreeNode(TreeNode.NFUND, plist, locals, stats);

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), rtype.getType(), "function_"+id.getStr());

        parser.insertIdentifierRecord(record);

        treenode.setSymbol(record);
        treenode.setType(rtype.getType());
        return treenode;
    }
}

