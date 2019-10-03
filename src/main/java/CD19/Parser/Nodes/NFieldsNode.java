package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a fields of the form:
 * NFLIST <fields>	::=	<sdecl> <fieldsTail>
 * <fieldsTail>	::=	eps  | , <sdecl> <fieldsTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NFieldsNode implements Node {
    //NFLIST	<fields>	::=	<sdecl> <fieldsTail>
    //	<fieldsTail>	::=	eps  | , <sdecl> <fieldsTail>

    NSDeclNode sDeclNode;
    private static NFieldsNode instance;

    public NFieldsNode() {
        this(NSDeclNode.INSTANCE());
    }

    public NFieldsNode(NSDeclNode sDeclNode) {
        this.sDeclNode = sDeclNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NFieldsNode INSTANCE() {
        if (instance == null) {
            instance = new NFieldsNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the fields node
     * @param parser The parser
     * @return A valid fields TreeNode (may have NUNDEF children)
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode sDecl = sDeclNode.make(parser);
        TreeNode tail = tail(parser);
        if (tail == null)
            return sDecl;

        return new TreeNode(TreeNode.NFLIST, sDecl, tail);
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent fields nodes, or a TreeNode containing tailing fields nodes
     */
    private TreeNode tail(Parser parser) {
        if (parser.peekAndConsume(Token.TCOMA)) {
            TreeNode sDecl = sDeclNode.make(parser);
            TreeNode tail = tail(parser);
            if (tail == null)
                return sDecl;

            return new TreeNode(TreeNode.NFLIST, sDecl, tail);
        }
        return null; //eps trans
    }

}

