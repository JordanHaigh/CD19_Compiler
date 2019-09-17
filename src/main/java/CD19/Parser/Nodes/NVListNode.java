package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NVListNode implements Node{
    //NVLIST	<vlist>	::=	<var> <vlistTail>
    //	<vlistTail>	::=	ε | ,<vlist>

    NVarNode nVarNode;

    public NVListNode() {
        this(new NVarNode());
    }

    public NVListNode(NVarNode nVarNode) {
        this.nVarNode = nVarNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode var = nVarNode.make(parser);
        TreeNode tail = tail(parser);
        return new TreeNode(TreeNode.NVLIST, var, tail);
    }


    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            return this.make(parser); //recurse
        }
        return null; //eps

    }
}
