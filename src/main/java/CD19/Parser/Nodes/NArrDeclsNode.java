package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;


/**
 * Generates an arrdecls of the form:
 * <arrdecls>	::=	<arrdecl> <arrDeclTail>
 * <arrdeclsTail>	::=	eps |  , <arrdecl> <arrDeclTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NArrDeclsNode implements Node{


    NArrDeclNode nArrDeclNode;
    private static NArrDeclsNode instance;


    public NArrDeclsNode(){
        this(NArrDeclNode.INSTANCE());
    }

    public NArrDeclsNode(NArrDeclNode node){
        this.nArrDeclNode = node;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NArrDeclsNode INSTANCE() {
        if (instance == null) {
            instance = new NArrDeclsNode();
        }
        return instance;
    }


    /**
     * Attempts to generate the arrdecls node
     * @param parser The parser
     * @return A valid arrdecls TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode arrdecl = nArrDeclNode.make(parser);
        TreeNode tail = tail(parser);

        if(tail == null)
            return arrdecl;

        return new TreeNode(TreeNode.NALIST, arrdecl, tail);
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent arrdecls nodes, or a TreeNode containing tailing arrdecls nodes
     */
    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            TreeNode arrdecl = nArrDeclNode.make(parser);
            TreeNode tail = tail(parser);

            if(tail == null)
                return arrdecl;

            return new TreeNode(TreeNode.NALIST, arrdecl, tail);

        }
        return null; //epsilon transition
    }

}

