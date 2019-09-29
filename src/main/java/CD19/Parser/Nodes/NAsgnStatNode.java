package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NAsgnStatNode implements Node{

    //	<asgnstat>	::=	 <varTail> <asgnop> <bool>

    NVarTailNode nVarTailNode;
    NAsgnOpNode nAsgnOpNode;
    NBoolNode nBoolNode;

    public NAsgnStatNode() {
        this(NVarTailNode.INSTANCE(), NAsgnOpNode.INSTANCE(), null);
    }

    public NAsgnStatNode(NVarTailNode nVarTailNode, NAsgnOpNode nAsgnOpNode, NBoolNode nBoolNode) {
        this.nVarTailNode = nVarTailNode;
        this.nAsgnOpNode = nAsgnOpNode;
        this.nBoolNode = nBoolNode;
    }

    private static NAsgnStatNode instance;
    public static NAsgnStatNode INSTANCE() {
        if (instance == null) {
            instance = new NAsgnStatNode();
        }
        return instance;
    }

    public void setnBoolNode(NBoolNode boolNode) {
        this.nBoolNode = boolNode;
    }


    @Override
    public TreeNode make(Parser parser) {
        TreeNode vartail = nVarTailNode.make(parser);
        TreeNode asgnop = nAsgnOpNode.make(parser);
        TreeNode bool = nBoolNode.make(parser);
        //nodetype will be what is returned from the nasgnop node (nasgn, npleq...)
        
        //return new TreeNode(asgnop.getValue(), vartail, bool);
        //asgnop.setType(bool.getType()); //todo data types come later
        asgnop.setLeft(vartail);
        asgnop.setRight(bool);
        return asgnop;
    }

}

