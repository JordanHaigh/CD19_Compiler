package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a plist of the form:
 * <plist>	::=	<params> | eps
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NPListNode implements Node{

    //<plist>	::=	<params> | eps
    NParamsNode nParamsNode;
    private static NPListNode instance;

    public NPListNode() {
        this(NParamsNode.INSTANCE());
    }

    public NPListNode(NParamsNode nParamsNode) {
        this.nParamsNode = nParamsNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NPListNode INSTANCE() {
        if (instance == null) {
            instance = new NPListNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the plist node
     * @param parser The parser
     * @return A valid plist TreeNode or null if epsilon transition
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN || token.getTokenID() == Token.TCNST){
            return nParamsNode.make(parser);
        }

        return null; //eps
    }

}

