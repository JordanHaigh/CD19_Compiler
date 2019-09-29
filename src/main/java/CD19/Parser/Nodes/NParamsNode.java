package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NParamsNode implements Node{
    //NPLIST	<params>	::=	<param> <paramTail>
    //	<paramsTail>	::=	ε | , <param> <paramTail>
    NParamNode nParamNode;

    public NParamsNode() {
        this(NParamNode.INSTANCE());
    }

    public NParamsNode(NParamNode nParamNode) {
        this.nParamNode = nParamNode;
    }

    private static NParamsNode instance;
    public static NParamsNode INSTANCE() {
        if (instance == null) {
            instance = new NParamsNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode param = nParamNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return param;

        return new TreeNode(TreeNode.NPLIST, param, tail);

    }

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

