package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;

public class NAsgnStatNode implements Node{

    //	<asgnstat>	::=	 <varTail> <asgnop> <bool>

    NVarTailNode nVarTailNode;
    NAsgnOpNode nAsgnOpNode;
    NBoolNode nBoolNode;

    public NAsgnStatNode() {
        this(new NVarTailNode(), new NAsgnOpNode(), new NBoolNode());
    }

    public NAsgnStatNode(NVarTailNode nVarTailNode, NAsgnOpNode nAsgnOpNode, NBoolNode nBoolNode) {
        this.nVarTailNode = nVarTailNode;
        this.nAsgnOpNode = nAsgnOpNode;
        this.nBoolNode = nBoolNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode vartail = nVarTailNode.make(parser);
        TreeNode asgnop = nAsgnOpNode.make(parser);
        TreeNode bool = nBoolNode.make(parser);

        return new TreeNode(TreeNode.NUNDEF, vartail, asgnop, bool); //todo ask dan about this
    }
}

