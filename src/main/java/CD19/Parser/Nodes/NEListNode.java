package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates an elist of the form:
 * NEXPL	<elist>	::=	<bool> <elistTail>
 * <elistTail>	::=	ε | , <bool> <elistTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NEListNode implements Node{
    //NEXPL	<elist>	::=	<bool> <elistTail>
    //	<elistTail>	::=	ε | , <bool> <elistTail>

    private NBoolNode nBoolNode;
    private static NEListNode instance;

    public NEListNode() {
        this(null);
    }

    public NEListNode(NBoolNode nBoolNode) {
        this.nBoolNode = nBoolNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NEListNode INSTANCE() {
        if (instance == null) {
            instance = new NEListNode();
        }
        return instance;
    }

    /**
     * Sets the boolNode in the class so cyclic constructors are prevented
     * @param boolNode - Node to set
     */
    public void setnBoolNode(NBoolNode boolNode) {
        this.nBoolNode = boolNode;
    }

    /**
     * Attempts to generate the elist node
     * @param parser The parser
     * @return A valid elist TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        //NEXPL	<elist>	::=	<bool> <elistTail>
        TreeNode bool = nBoolNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null){
            return bool;
        }
        else{
            return new TreeNode(TreeNode.NEXPL, bool, tail);
        }
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent elist nodes, or a TreeNode containing tailing elist nodes
     */
    private TreeNode tail(Parser parser){
        //	<elistTail>	::=	ε | , <bool> <elistTail>
        if((parser).peekAndConsume(Token.TCOMA)){
            TreeNode bool = nBoolNode.make(parser);
            TreeNode tail = tail(parser);
            if(tail == null)
                return bool;

            return new TreeNode(TreeNode.NEXPL, bool, tail);
        }

        return null;
    }

}
