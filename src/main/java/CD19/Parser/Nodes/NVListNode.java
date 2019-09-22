package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NVListNode implements Node{
    //NVLIST	<vlist>	::=	<var> <vlistTail>
    //	<vlistTail>	::=	ε | ,<var> <vlistTail>

    NVarNode nVarNode;

    public NVListNode() {
        this(NVarNode.INSTANCE());
    }

    public NVListNode(NVarNode nVarNode) {
        this.nVarNode = nVarNode;
    }

    private static NVListNode instance;
    public static NVListNode INSTANCE() {
        if (instance == null) {
            instance = new NVListNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode var = nVarNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return var;

        return new TreeNode(TreeNode.NVLIST, var, tail);
    }


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
