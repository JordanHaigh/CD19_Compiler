package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a logop of the form:
 * <logop>	::=	and | or | xor
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NLogopNode implements Node{

    private static NLogopNode instance;

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NLogopNode INSTANCE() {
        if (instance == null) {
            instance = new NLogopNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the logop node
     * @param parser The parser
     * @return A valid logop TreeNode or NUNDEF if syntactic error
     */

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TAND))
            return new TreeNode(TreeNode.NAND, null);
        else if(parser.peekAndConsume(Token.TOR))
            return new TreeNode(TreeNode.NOR, null);
        else if(parser.peekAndConsume(Token.TXOR))
            return new TreeNode(TreeNode.NXOR, null);
        else{
            parser.syntacticError("Expecting a valid Logical Operator", parser.peek());
            return new TreeNode(); //todo error recover
        }
    }
}
