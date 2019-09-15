package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NDListNode implements Node{
    //NDLIST	<dlist>	::=	<decl> <dlistTail>
    //	<dlistTail>	::=	ε | , <dlist>


    NDeclNode nDeclNode;

    public NDListNode() {
        this(new NDeclNode());
    }

    public NDListNode(NDeclNode nDeclNode) {
        this.nDeclNode = nDeclNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode decl = nDeclNode.make(parser);
        TreeNode tail = tail(parser);

        return new TreeNode(TreeNode.NDLIST, decl, tail);
    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            return this.make(parser); //dlist trans
        }
        return null; //eps trans
    }
}

