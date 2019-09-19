package CD19.Parser.Nodes;

import CD19.Parser.*;
import CD19.Scanner.Token;

public class NGlobNode implements Node {
    //NGLOB	<globals>	::=	<consts> <types> <arrays>

    private NConstsNode nConstsNode;
    private NTypesNode nTypesNode;
    private NArraysNode nArraysNode;

    public NGlobNode(){
        this(new NConstsNode(), new NTypesNode(), new NArraysNode());
    }

    public NGlobNode(NConstsNode nConstsNode, NTypesNode nTypesNode, NArraysNode nArraysNode){
        this.nConstsNode = nConstsNode;
        this.nTypesNode = nTypesNode;
        this.nArraysNode = nArraysNode;

    }

    public TreeNode make(Parser parser){
        TreeNode nConstsTreeNode = nConstsNode.make(parser);
        TreeNode nTypesTreeNode = nTypesNode.make(parser);
        TreeNode nArraysTreeNode = nArraysNode.make(parser);

        TreeNode nGlobTreeNode = new TreeNode(TreeNode.NGLOB, nConstsTreeNode, nTypesTreeNode,nArraysTreeNode);
        return nGlobTreeNode;
    }


}

