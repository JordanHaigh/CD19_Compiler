package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
/**
 * Generates a consts of the form:
 * <consts>	::=	constants <initlist> | eps
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NConstsNode implements Node{
    //Special	<consts>	::=	constants <initlist> | eps

    private NInitListNode nInitListNode;
    private static NConstsNode instance;

    public NConstsNode(){
        this(NInitListNode.INSTANCE());
    }

    public NConstsNode(NInitListNode nInitListNode){
        this.nInitListNode = nInitListNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */

    public static NConstsNode INSTANCE() {
        if (instance == null) {
            instance = new NConstsNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the consts node
     * @param parser The parser
     * @return A valid consts TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TCONS)){ //// constants token
            Token token = parser.peek();
            if(token.getTokenID() == Token.TIDEN){
                //constants initlist transition
                return nInitListNode.make(parser);
            }
            else{
                parser.syntacticError("Expected an Identifer after Constants Keyword", parser.peek());
                return new TreeNode();
            }

        }

        return null;
    }

}

