package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a prlist of the form:
 * NPRLST	<prlist>	::=	<printitem><prlistTail>
 * <prlistTail>	::=	ε |  , <printitem><prlistTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NPrListNode implements Node{
    //NPRLST	<prlist>	::=	<printitem><prlistTail>
    //	<prlistTail>	::=	ε |  , <printitem><prlistTail>

    NPrintItemNode nPrintItemNode;
    private static NPrListNode instance;


    public NPrListNode() {
        this(NPrintItemNode.INSTANCE());
    }
    public NPrListNode(NPrintItemNode nPrintItemNode) {
        this.nPrintItemNode = nPrintItemNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NPrListNode INSTANCE() {
        if (instance == null) {
            instance = new NPrListNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the prlist node
     * @param parser The parser
     * @return A valid prlist TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {

        TreeNode printitem = nPrintItemNode.make(parser);
        TreeNode tail = tail(parser);

        if(tail == null)
            return printitem;

        return new TreeNode(TreeNode.NPRLST, printitem, tail);
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent prlist nodes, or a TreeNode containing tailing prlist nodes
     */
    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            TreeNode printitem = nPrintItemNode.make(parser);
            TreeNode tail = tail(parser);

            if(tail == null)
                return printitem;

            return new TreeNode(TreeNode.NPRLST, printitem, tail);
        }
        return null; //eps trans

    }

}

