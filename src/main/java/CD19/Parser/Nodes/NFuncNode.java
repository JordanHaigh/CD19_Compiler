package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NFuncNode implements Node{
    //NFUND	<func>	::=	function <id> ( <plist> ) : <rtype> <funcbody>

    NPListNode npListNode;
    NRTypeNode nrTypeNode;
    NFuncBodyNode nFuncBodyNode;

    public NFuncNode() {
        this(new NPListNode(), new NRTypeNode(), new NFuncBodyNode());
    }

    public NFuncNode(NPListNode npListNode, NRTypeNode nrTypeNode, NFuncBodyNode nFuncBodyNode) {
        this.npListNode = npListNode;
        this.nrTypeNode = nrTypeNode;
        this.nFuncBodyNode = nFuncBodyNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        parser.peekAndConsume(Token.TFUNC);
        parser.peekAndConsume(Token.TIDEN);
        parser.peekAndConsume(Token.TLPAR);
        TreeNode plist = npListNode.make(parser);
        parser.peekAndConsume(Token.TRPAR);
        parser.peekAndConsume(Token.TSEMI);
        TreeNode rtype = nrTypeNode.make(parser);
        TreeNode funcBody = nFuncBodyNode.make(parser);

        TreeNode locals = funcBody.getLeft(); //from funcbody
        TreeNode stats = funcBody.getRight(); //from funcbody

        //todo get rtype back into play somehow idk its late and im tired figure it out.
        return new TreeNode(TreeNode.NFUND, plist, locals, stats);
    }
}

