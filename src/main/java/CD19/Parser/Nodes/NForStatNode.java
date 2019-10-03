package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a forstat of the form:
 * NFOR	<forstat>	::=	for ( <asgnlist> ; <bool> ) <stats> end
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NForStatNode implements Node{

    //NFOR	<forstat>	::=	for ( <asgnlist> ; <bool> ) <stats> end
    NAsgnListNode nAsgnListNode;
    NBoolNode nBoolNode;
    NStatsNode nStatsNode;
    private static NForStatNode instance;

    public NForStatNode() {
        this(NAsgnListNode.INSTANCE(), null,null);
    }

    public NForStatNode(NAsgnListNode nAsgnListNode, NBoolNode nBoolNode, NStatsNode nStatsNode) {
        this.nAsgnListNode = nAsgnListNode;
        this.nBoolNode = nBoolNode;
        this.nStatsNode = nStatsNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NForStatNode INSTANCE() {
        if (instance == null) {
            instance = new NForStatNode();
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
     * Attempts to generate the forstat node
     * @param parser The parser
     * @return A valid forstat TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode forstat = new TreeNode();

        if(!parser.peekAndConsume(Token.TFOR)){
            parser.syntacticError("Expected the For keyword", parser.peek());
            return forstat;
        }

        if(!parser.peekAndConsume(Token.TLPAR)){
            parser.syntacticError("Expected a left parenthesis", parser.peek());
            return forstat;
        }

        TreeNode asgnlist = nAsgnListNode.make(parser);

        if(!parser.peekAndConsume(Token.TSEMI)){
            parser.syntacticError("Expected a semicolon", parser.peek());
            return forstat;
        }

        TreeNode bool = nBoolNode.make(parser);

        if(!parser.peekAndConsume(Token.TRPAR)){
            parser.syntacticError("Expected a right parenthesis", parser.peek());
            return forstat;
        }

        TreeNode stats = nStatsNode.make(parser);

        return new TreeNode(TreeNode.NFOR, asgnlist, bool, stats);

    }
}
