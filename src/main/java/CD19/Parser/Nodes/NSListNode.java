package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NSListNode implements Node{

    //NSDLST	<slist>	::=	<sdecl> <slistTail>
	//<slistTail>	::=	ε | ,<sdecl> <slistTail>

    NSDeclNode nsDeclNode;

    public NSListNode() {
        this(NSDeclNode.INSTANCE());
    }

    public NSListNode(NSDeclNode nsDeclNode) {
        this.nsDeclNode = nsDeclNode;
    }

    private static NSListNode instance;
    public static NSListNode INSTANCE() {
        if (instance == null) {
            instance = new NSListNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode sdecl = nsDeclNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return sdecl;

        return new TreeNode(TreeNode.NSDLST, sdecl, tail);

    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            TreeNode sdecl = nsDeclNode.make(parser);
            TreeNode tail = tail(parser);

            if(tail == null)
                return sdecl;

            return new TreeNode(TreeNode.NSDLST, sdecl, tail);
        }
        return null; //eps trans

    }
}
