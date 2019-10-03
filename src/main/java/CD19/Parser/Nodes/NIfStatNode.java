package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a ifstat of the form:
 *  <ifstat>	::=	if ( <bool> ) <stats> <ifStatTail>
 * 	<ifStatTail>	::=	eps |  else <stats>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NIfStatNode implements Node {
    //NIFTH | NIFTE	<ifstat>	::=	if ( <bool> ) <stats> <ifStatTail>
    //	<ifStatTail>	::=	eps |  else <stats>

    NBoolNode nBoolNode;
    NStatsNode nStatsNode;
    private static NIfStatNode instance;


    public NIfStatNode() {
        this(null, null);
    }

    public NIfStatNode(NBoolNode nBoolNode, NStatsNode nStatsNode) {
        this.nBoolNode = nBoolNode;
        this.nStatsNode = nStatsNode;
    }


    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NIfStatNode INSTANCE() {
        if (instance == null) {
            instance = new NIfStatNode();
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
     * Sets the nStatsNode in the class so cyclic constructors are prevented
     * @param nStatsNode - Node to set
     */
    public void setnStatsNode(NStatsNode nStatsNode) {
        this.nStatsNode = nStatsNode;
    }

    /**
     * Attempts to generate the ifstat node
     * @param parser The parser
     * @return A valid ifstat TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode ifstat = new TreeNode();

        if (!parser.peekAndConsume(Token.TIFTH)) {
            parser.syntacticError("Expected If Keyword", parser.peek());
            return ifstat;
        }

        if (!parser.peekAndConsume(Token.TLPAR)) {
            parser.syntacticError("Expected Left Parenthesis", parser.peek());
            return ifstat;
        }

        TreeNode bool = nBoolNode.make(parser);

        if (!parser.peekAndConsume(Token.TRPAR)) {
            parser.syntacticError("Expected Right Parenthesis", parser.peek());
            return ifstat;
        }

        TreeNode stats = nStatsNode.make(parser);

        TreeNode tail = tail(parser);

        if (tail == null) { //if just an if statement, then we dont need tail node's stats
            ifstat = new TreeNode(TreeNode.NIFTH, bool, stats);
            return ifstat;
        }
        else {
            ifstat = new TreeNode(TreeNode.NIFTE, bool, stats, tail); //contains an else statement so we need tail's stats
            return ifstat;
        }

    }

    /**
     * Tail method used for determining an else condition or not
     * @param parser The parser
     * @return - More stats if theres an else statement or null if not
     */
    private TreeNode tail(Parser parser) {
        if (parser.peekAndConsume(Token.TELSE)) {
            TreeNode stats = nStatsNode.make(parser);
            return stats;
        } else
            return null;

    }
}
