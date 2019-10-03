package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
/**
 * Generates a returnstat of the form:
 * NRETN	<returnstat>	::=	return <returnStatTail>
 *   <returnStatTail>	::=	eps | <expr>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */


public class NReturnStatNode implements Node {

    //NRETN	<returnstat>	::=	return <returnStatTail>
    //	<returnStatTail>	::=	eps | <expr>

    NExprNode nExprNode;
    private static NReturnStatNode instance;


    public NReturnStatNode() {
        this(null);
    }

    public NReturnStatNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NReturnStatNode INSTANCE() {
        if (instance == null) {
            instance = new NReturnStatNode();
        }
        return instance;
    }

    /**
     * Sets the nExprNode in the class so cyclic constructors are prevented
     * @param nExprNode - Node to set
     */
    public void setnExprNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    /**
     * Attempts to generate the returnstat node
     * @param parser The parser
     * @return A valid returnstat TreeNode
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode returnstat = new TreeNode();

        if (!parser.peekAndConsume(Token.TRETN)) {
            parser.syntacticError("Expected Return Keyword", parser.peek());
            return returnstat;
        }

        TreeNode tail = tail(parser);

        returnstat = new TreeNode(TreeNode.NRETN, tail, null);
        return returnstat;
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent returnstat nodes, or a TreeNode containing tailing returnstat nodes
     */
    private TreeNode tail(Parser parser) {
        Token token = parser.peek();
        if (token.getTokenID() == Token.TIDEN || //expr->term->fact->exponent
                token.getTokenID() == Token.TILIT ||
                token.getTokenID() == Token.TFLIT ||
                token.getTokenID() == Token.TTRUE ||
                token.getTokenID() == Token.TFALS ||
                token.getTokenID() == Token.TLPAR) {

            return nExprNode.make(parser);
        }
        return null; //eps trans

    }
}
