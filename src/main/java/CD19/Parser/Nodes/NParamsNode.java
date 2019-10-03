package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a params of the form:
 * NPLIST	<params>	::=	<param> <paramTail>
 *  <paramsTail>	::=	ε | , <param> <paramTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NParamsNode implements Node{
    //NPLIST	<params>	::=	<param> <paramTail>
    //	<paramsTail>	::=	ε | , <param> <paramTail>
    NParamNode nParamNode;
    private static NParamsNode instance;


    public NParamsNode() {
        this(NParamNode.INSTANCE());
    }

    public NParamsNode(NParamNode nParamNode) {
        this.nParamNode = nParamNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NParamsNode INSTANCE() {
        if (instance == null) {
            instance = new NParamsNode();
        }
        return instance;
    }


    /**
     * Attempts to generate the params node
     * @param parser The parser
     * @return A valid params TreeNode or param node
     */

    @Override
    public TreeNode make(Parser parser) {
        TreeNode param = nParamNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return param;

        return new TreeNode(TreeNode.NPLIST, param, tail);

    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent params nodes, or a TreeNode containing tailing params nodes
     */
    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            TreeNode param = nParamNode.make(parser);
            TreeNode tail = tail(parser);
            if(tail == null)
                return param;
            return new TreeNode(TreeNode.NPLIST, param, tail);
        }
        return null; //eps transition

    }

}

