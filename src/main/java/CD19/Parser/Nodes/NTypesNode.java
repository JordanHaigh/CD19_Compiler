package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a types of the form:
 * <types>	::=	types <typelist> | eps
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NTypesNode implements Node {
    //<types>	::=	types <typelist> | eps

    private NTypeListNode nTypeListNode;
    private static NTypesNode instance;

    public NTypesNode(){
        this(NTypeListNode.INSTANCE());
    }

    public NTypesNode(NTypeListNode nTypeListNode){
        this.nTypeListNode = nTypeListNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NTypesNode INSTANCE() {
        if (instance == null) {
            instance = new NTypesNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the types node
     * @param parser The parser
     * @return A valid types TreeNode or null
     */
    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TTYPS)){
            Token token = parser.peek();
            if(token.getTokenID() == Token.TIDEN){
                return nTypeListNode.make(parser);
            }
        }
        return null; //eps transition
    }
}

