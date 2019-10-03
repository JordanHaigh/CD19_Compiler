package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a bool of the form:
 * NBOOL	<bool>	::=	<rel><boolTail>
 * <boolTail>	::=	ε | <logop><rel><boolTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NBoolNode implements Node{



    NRelNode nRelNode;
    NLogopNode nLogopNode;
    private static NBoolNode instance;

    public NBoolNode() {
        this(NRelNode.INSTANCE(), NLogopNode.INSTANCE());
    }

    public NBoolNode(NRelNode nRelNode, NLogopNode nLogopNode) {
        this.nRelNode = nRelNode;
        this.nLogopNode = nLogopNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NBoolNode INSTANCE() {
        if (instance == null) {
            instance = new NBoolNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the bool node
     * @param parser The parser
     * @return A valid bool TreeNode or just a rel
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode rel = nRelNode.make(parser);
        TreeNode tail = tail(parser);

        if (tail != null) {
            tail.setLeft(rel); //the tail value will be the relop, reassign the tail left to the first rel
            return tail;
        }
        else
            return rel;
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent bool nodes, or a TreeNode containing tailing bool nodes
     */
    private TreeNode tail(Parser parser){
        Token token = parser.peek();
        if(token.getTokenID() == Token.TAND ||
                token.getTokenID() == Token.TOR ||
                token.getTokenID() == Token.TXOR){

            TreeNode logop = nLogopNode.make(parser);
            TreeNode bool = this.make(parser);
            return new TreeNode (TreeNode.NBOOL, null, logop, bool);
        }
        else
          return null; //eps trans

    }

}


