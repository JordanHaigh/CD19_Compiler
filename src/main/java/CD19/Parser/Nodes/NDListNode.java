package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a decl of the form:
 * NDLIST	<dlist>	::=	<decl> <dlistTail>
 * <dlistTail>	::=	ε | , <decl> <dlistTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NDListNode implements Node{
    //NDLIST	<dlist>	::=	<decl> <dlistTail>
    //	<dlistTail>	::=	ε | , <decl> <dlistTail>


    NDeclNode nDeclNode;
    private static NDListNode instance;

    public NDListNode() {
        this(NDeclNode.INSTANCE());
    }

    public NDListNode(NDeclNode nDeclNode) {
        this.nDeclNode = nDeclNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NDListNode INSTANCE() {
        if (instance == null) {
            instance = new NDListNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the decl node
     * @param parser The parser
     * @return A valid decl TreeNode
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode decl = nDeclNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return decl;
        return new TreeNode(TreeNode.NDLIST, decl, tail);
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent decl nodes, or a TreeNode containing tailing decl nodes
     */
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

