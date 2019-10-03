package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a vlist of the form:
 * NVLIST	<vlist>	::=	<var> <vlistTail>
 * <vlistTail>	::=	ε | ,<var> <vlistTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NVListNode implements Node{
    //NVLIST	<vlist>	::=	<var> <vlistTail>
    //	<vlistTail>	::=	ε | ,<var> <vlistTail>

    NVarNode nVarNode;
    private static NVListNode instance;


    public NVListNode() {
        this(NVarNode.INSTANCE());
    }

    public NVListNode(NVarNode nVarNode) {
        this.nVarNode = nVarNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NVListNode INSTANCE() {
        if (instance == null) {
            instance = new NVListNode();
        }
        return instance;
    }


    /**
     * Attempts to generate the vlist node
     * @param parser The parser
     * @return A valid vlist TreeNode
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode var = nVarNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return var;

        return new TreeNode(TreeNode.NVLIST, var, tail);
    }


    /**
     * Tail method that can parse more of the same node vlist or not
     * @param parser The parser
     * @return - Null if there are no subsequent vlist nodes, or a TreeNode containing tailing vlist nodes
     */
    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            TreeNode var = nVarNode.make(parser);
            TreeNode tail = tail(parser);
            if(tail == null)
                return var;

            return new TreeNode(TreeNode.NVLIST, var, tail); //recurse
        }
        return null; //eps

    }
}
