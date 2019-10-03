package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
/**
 * Generates a typelist of the form:
 * NTYPEL	<typelist>	::=	<type> <typelistTail>
 *  <typelistTail>	::=	eps |  <type> <typelistTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NTypeListNode implements Node{
    //NTYPEL	<typelist>	::=	<type> <typelistTail>
    //	<typelistTail>	::=	eps |  <type> <typelistTail>

    NTypeNode nTypeNode;
    private static NTypeListNode instance;

    public NTypeListNode() {
        this(NTypeNode.INSTANCE());
    }

    public NTypeListNode(NTypeNode nTypeNode) {
        this.nTypeNode = nTypeNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NTypeListNode INSTANCE() {
        if (instance == null) {
            instance = new NTypeListNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the typelist node
     * @param parser The parser
     * @return A valid typelist TreeNode
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode type = nTypeNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return type;

        return new TreeNode(TreeNode.NTYPEL, type,tail);
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent typelist nodes, or a TreeNode containing tailing typelist nodes
     */
    private TreeNode tail(Parser parser){
        if(parser.peek().getTokenID() == Token.TIDEN){  //<type>
            TreeNode type = nTypeNode.make(parser);
            TreeNode tail = tail(parser);

            if(tail == null)
                return type;

            return new TreeNode(TreeNode.NTYPEL, type,tail);
        }
        return null; //eps transition
    }

}


