package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a locals of the form:
 * <locals>	::=	<arrlocals> <arrlocalsTail>
 * <localsTail>	::=	eps |  , <arrlocals> <arrlocalsTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NLocalsNode implements Node{

//	<locals>	::=	<dlist> | eps
    NDListNode ndListNode;
    private static NLocalsNode instance;

    public NLocalsNode() {
        this(NDListNode.INSTANCE());
    }

    public NLocalsNode(NDListNode ndListNode) {
        this.ndListNode = ndListNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NLocalsNode INSTANCE() {
        if (instance == null) {
            instance = new NLocalsNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the locals node
     * @param parser The parser
     * @return A valid locals TreeNode or null if doesn't exist
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN){
            return ndListNode.make(parser);
        }
        else{
            return null; //eps trans
        }


    }
}

