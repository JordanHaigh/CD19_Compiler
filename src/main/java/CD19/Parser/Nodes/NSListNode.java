package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NSListNode implements Node{

    //NSDLST	<slist>	::=	<sdecl> <slistTail>
	//<slistTail>	::=	ε | , <slist>

    NSDeclNode nsDeclNode;

    public NSListNode() {
        this(new NSDeclNode());
    }

    public NSListNode(NSDeclNode nsDeclNode) {
        this.nsDeclNode = nsDeclNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode sdecl = nsDeclNode.make(parser);
        TreeNode tail = tail(parser);

        return new TreeNode(TreeNode.NSDLST, sdecl, tail);

    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            return this.make(parser); //recurse trans
        }
        return null; //eps trans

    }
}
