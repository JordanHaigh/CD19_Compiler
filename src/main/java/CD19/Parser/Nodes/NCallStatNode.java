package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a callStat of the form:
 * NCALL	<callStat>	::=	ε | <elist>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NCallStatNode implements Node{
    //NCALL	<callStat>	::=	eps | <elist>
    NEListNode neListNode;
    private static NCallStatNode instance;


    public NCallStatNode() {
        this(NEListNode.INSTANCE());
    }

    public NCallStatNode(NEListNode neListNode) {
        this.neListNode = neListNode;
    }


    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NCallStatNode INSTANCE() {
        if (instance == null) {
            instance = new NCallStatNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the callStat node
     * @param parser The parser
     * @return A valid callStat TreeNode or null
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN || //elist ->bool->rel->expr->term->fact->exponent
                token.getTokenID() == Token.TILIT ||
                token.getTokenID() == Token.TFLIT ||
                token.getTokenID() == Token.TTRUE ||
                token.getTokenID() == Token.TFALS ||
                token.getTokenID() == Token.TLPAR){

            TreeNode elist = neListNode.make(parser);
            return new TreeNode(TreeNode.NCALL, elist,null);
        }
        return null;
    }

}
