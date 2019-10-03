package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a rtype of the form:
 * <rtype>	::=	<stype> | void
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NRTypeNode implements Node{

	//<rtype>	::=	<stype> | void

    NSTypeNode nsTypeNode;
    private static NRTypeNode instance;

    public NRTypeNode() {
        this(NSTypeNode.INSTANCE());
    }

    public NRTypeNode(NSTypeNode nsTypeNode) {
        this.nsTypeNode = nsTypeNode;
    }


    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NRTypeNode INSTANCE() {
        if (instance == null) {
            instance = new NRTypeNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the rtype node
     * @param parser The parser
     * @return A valid rtype TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TVOID)){ //void
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType(NodeDataTypes.Void);
            return dummy;
        }

        return nsTypeNode.make(parser); //stype trans

    }
}
