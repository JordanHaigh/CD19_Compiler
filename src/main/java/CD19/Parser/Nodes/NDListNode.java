package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NDListNode implements Node{
    //NDLIST	<dlist>	::=	<decl> <dlistTail>
    //	<dlistTail>	::=	ε | , <decl> <dlistTail>


    NDeclNode nDeclNode;

    public NDListNode() {
        this(NDeclNode.INSTANCE());
    }

    public NDListNode(NDeclNode nDeclNode) {
        this.nDeclNode = nDeclNode;
    }


    private static NDListNode instance;
    public static NDListNode INSTANCE() {
        if (instance == null) {
            instance = new NDListNode();
        }
        return instance;
    }



    @Override
    public TreeNode make(Parser parser) {
        TreeNode decl = nDeclNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return decl;
        return new TreeNode(TreeNode.NDLIST, decl, tail);
    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            TreeNode decl = nDeclNode.make(parser);
            TreeNode tail = tail(parser);

            if(tail == null)
                return decl;
            return new TreeNode(TreeNode.NDLIST, decl, tail);
        }
        return null; //eps trans
    }
}

