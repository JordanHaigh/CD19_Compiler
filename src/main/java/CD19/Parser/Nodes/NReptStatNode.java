package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a repstat of the form:
 * /NREPT	<repstat>	::=	repeat ( <asgnlist> ) <stats> until <bool>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NReptStatNode implements Node {
    //NREPT	<repstat>	::=	repeat ( <asgnlist> ) <stats> until <bool>
    NAsgnListNode nAsgnListNode;
    NStatsNode nstatsNode;
    NBoolNode nBoolNode;
    private static NReptStatNode instance;


    public NReptStatNode() {
        this(NAsgnListNode.INSTANCE(), null, null);
    }

    public NReptStatNode(NAsgnListNode nAsgnListNode, NStatsNode nstatsNode, NBoolNode nBoolNode) {
        this.nAsgnListNode = nAsgnListNode;
        this.nstatsNode = nstatsNode;
        this.nBoolNode = nBoolNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NReptStatNode INSTANCE() {
        if (instance == null) {
            instance = new NReptStatNode();
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
        this.nstatsNode = nStatsNode;
    }


    /**
     * Attempts to generate the repstat node
     * @param parser The parser
     * @return A valid repstat TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode repstat = new TreeNode();

        if (!parser.peekAndConsume(Token.TREPT)) {
            parser.syntacticError("Expected a Repeat Keyword", parser.peek());
            return repstat;
        }

        if (!parser.peekAndConsume(Token.TLPAR)){
             parser.syntacticError("Expected a Left Paraethesis", parser.peek());
             return repstat;
        }

        TreeNode asgnList = nAsgnListNode.make(parser);


        if(!parser.peekAndConsume(Token.TRPAR)){
            parser.syntacticError("Expected a Right Parenthesis", parser.peek());
            return repstat;
        }

        TreeNode stats = nstatsNode.make(parser);
        if(stats.getValue() == TreeNode.NUNDEF){
            return repstat;
        }

        if(!parser.peekAndConsume(Token.TUNTL)){
            parser.syntacticError("Expected an Until keyword", parser.peek());
            return repstat;
        }

        TreeNode bool = nBoolNode.make(parser);

        repstat = new TreeNode(TreeNode.NREPT, asgnList, stats, bool);
        return repstat;

    }
}


