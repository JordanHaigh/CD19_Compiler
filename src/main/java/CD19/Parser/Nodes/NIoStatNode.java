package CD19.Parser.Nodes;

import CD19.Parser.*;
import CD19.Scanner.Token;

/**
 * Generates a iostat of the form:
 * <iostat>	::=	input <vlist> | print <prlist> | printline <prlist>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NIoStatNode implements Node{

    //NINPUT | NPRINT| NPRLN	<iostat>	::=	input <vlist> | print <prlist> | printline <prlist>

    NVListNode nvListNode;
    NPrListNode nPrListNode;
    private static NIoStatNode instance;

    public NIoStatNode() {
        this(NVListNode.INSTANCE(), NPrListNode.INSTANCE());
    }

    public NIoStatNode(NVListNode nvListNode, NPrListNode nPrListNode) {
        this.nvListNode = nvListNode;
        this.nPrListNode = nPrListNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */

    public static NIoStatNode INSTANCE() {
        if (instance == null) {
            instance = new NIoStatNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the iostat node
     * @param parser The parser
     * @return A valid iostat TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TINPT)){
            TreeNode vlist = nvListNode.make(parser);
            return new TreeNode(TreeNode.NINPUT, vlist,null);
        }
        else if(parser.peekAndConsume(Token.TPRIN)){
            TreeNode prlist = nPrListNode.make(parser);
            return new TreeNode(TreeNode.NPRINT, prlist, null);
        }
        else if(parser.peekAndConsume(Token.TPRLN)){
            TreeNode prlist = nPrListNode.make(parser);
            return new TreeNode(TreeNode.NPRLN, prlist, null);
        }
        else{
            parser.syntacticError("Expected Valid IoStat keyword (input, print, printline)", parser.peek());
            return new TreeNode();
        }
    }
}

