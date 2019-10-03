package CD19.Parser.Nodes;

import CD19.Parser.*;
import CD19.Scanner.Token;

/**
 * Generates a globals of the form:
 * NGLOB	<globals>	::=	<consts> <types> <arrays>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NGlobNode implements Node {
    //NGLOB	<globals>	::=	<consts> <types> <arrays>

    private NConstsNode nConstsNode;
    private NTypesNode nTypesNode;
    private NArraysNode nArraysNode;
    private static NGlobNode instance;


    public NGlobNode(){
        this(NConstsNode.INSTANCE(), NTypesNode.INSTANCE(), NArraysNode.INSTANCE());
    }

    public NGlobNode(NConstsNode nConstsNode, NTypesNode nTypesNode, NArraysNode nArraysNode){
        this.nConstsNode = nConstsNode;
        this.nTypesNode = nTypesNode;
        this.nArraysNode = nArraysNode;

    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NGlobNode INSTANCE() {
        if (instance == null) {
            instance = new NGlobNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the globals node
     * @param parser The parser
     * @return A valid globals TreeNode (may contain NUNDEF children)
     */
    public TreeNode make(Parser parser){
        TreeNode nConstsTreeNode = nConstsNode.make(parser);
        TreeNode nTypesTreeNode = nTypesNode.make(parser);
        TreeNode nArraysTreeNode = nArraysNode.make(parser);

        TreeNode nGlobTreeNode = new TreeNode(TreeNode.NGLOB, nConstsTreeNode, nTypesTreeNode,nArraysTreeNode);
        return nGlobTreeNode;
    }

}

