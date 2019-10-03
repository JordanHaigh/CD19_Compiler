package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a strstat of the form:
 * <strstat> ::=	<forstat> | <ifstat>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NStrStatNode implements Node{
    //<strstat>	::=	<forstat> | <ifstat>

    NForStatNode nForStatNode;
    NIfStatNode nIfStatNode;
    private static NStrStatNode instance;

    public NStrStatNode() {
        this(NForStatNode.INSTANCE(), NIfStatNode.INSTANCE());
    }

    public NStrStatNode(NForStatNode nForStatNode, NIfStatNode nIfStatNode) {
        this.nForStatNode = nForStatNode;
        this.nIfStatNode = nIfStatNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NStrStatNode INSTANCE() {
        if (instance == null) {
            instance = new NStrStatNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the strstat node
     * @param parser The parser
     * @return A valid strstat TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();

        if(token.getTokenID() == Token.TFOR){
            return nForStatNode.make(parser);
        }
        else if(token.getTokenID() == Token.TIFTH){
            return nIfStatNode.make(parser);
        }
        else{
            parser.syntacticError("Expected a For or If keyword", parser.peek());
            return new TreeNode();
        }
    }
}


