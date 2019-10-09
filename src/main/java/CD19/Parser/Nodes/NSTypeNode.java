package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a stype of the form:
 * <stype>	::=	<arrstype> <arrstypeTail>
 * <stypeTail>	::=	eps |  , <arrstype> <arrstypeTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NSTypeNode implements Node{

    private static NSTypeNode instance;

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NSTypeNode INSTANCE() {
        if (instance == null) {
            instance = new NSTypeNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the stype node
     * @param parser The parser
     * @return A valid stype TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        TreeNode dummy = new TreeNode(TreeNode.NSDECL);

        if(token.getTokenID() == Token.TINTG){
            parser.consume();
            dummy.setType("Integer");
        }
        else if(token.getTokenID() == Token.TREAL){
            parser.consume();
            dummy.setType("Real");
        }
        else if(token.getTokenID() == Token.TBOOL){
            parser.consume();
            dummy.setType("Boolean");
        }
        else{
            parser.syntacticError("Expected a Primitive Type", parser.peek());
            dummy = new TreeNode();
            dummy.setType("Undefined");
        }

        return dummy;
    }
}
