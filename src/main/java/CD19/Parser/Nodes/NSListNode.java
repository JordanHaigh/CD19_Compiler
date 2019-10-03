package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a slist of the form:
 * <slist>	::=	<arrslist> <arrslistTail>
 * <slistTail>	::=	eps |  , <arrslist> <arrslistTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NSListNode implements Node{

    //NSDLST	<slist>	::=	<sdecl> <slistTail>
	//<slistTail>	::=	ε | ,<sdecl> <slistTail>

    NSDeclNode nsDeclNode;
    private static NSListNode instance;


    public NSListNode() {
        this(NSDeclNode.INSTANCE());
    }

    public NSListNode(NSDeclNode nsDeclNode) {
        this.nsDeclNode = nsDeclNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NSListNode INSTANCE() {
        if (instance == null) {
            instance = new NSListNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the slist node
     * @param parser The parser
     * @return A valid slist TreeNode
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode sdecl = nsDeclNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return sdecl;

        return new TreeNode(TreeNode.NSDLST, sdecl, tail);

    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent slist nodes, or a TreeNode containing tailing slist nodes
     */
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
