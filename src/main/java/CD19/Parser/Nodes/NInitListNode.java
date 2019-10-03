package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a initlist of the form:
 * NILIST	<initlist>	::=	<init> <initListTail>
 * <initListTail>	::=	eps | ,<initlist>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NInitListNode implements Node {
    //NILIST	<initlist>	::=	<init> <initListTail>
    //	<initListTail>	::=	eps | ,<initlist>

    private NInitNode nInitNode;
    private static NInitListNode instance;

    public NInitListNode(){
        this(NInitNode.INSTANCE());
    }

    public NInitListNode(NInitNode nInitNode){
        this.nInitNode = nInitNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NInitListNode INSTANCE() {
        if (instance == null) {
            instance = new NInitListNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the initlist node
     * @param parser The parser
     * @return A valid initlist TreeNode
     */
    @Override
    public TreeNode make(Parser parser) {
        //NILIST	<initlist>	::=	<init> <initListTail>
        TreeNode init = nInitNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return init;

        return new TreeNode(TreeNode.NILIST, init, tail);
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent initlist nodes, or a TreeNode containing tailing initlist nodes
     */
    private TreeNode tail(Parser parser){
        //	<initListTail>	::=	eps | ,<init> <initListTail>>
        if(parser.peekAndConsume(Token.TCOMA)){
            TreeNode init = nInitNode.make(parser);
            TreeNode tail = tail(parser);
            if(tail == null)
                return init;
            return new TreeNode(TreeNode.NILIST, init, tail);

        }

        return null; //eps transition

    }

}

