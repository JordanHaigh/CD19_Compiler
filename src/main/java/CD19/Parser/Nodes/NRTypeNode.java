package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NRTypeNode implements Node{

	//<rtype>	::=	<stype> | void

    NSTypeNode nsTypeNode;

    public NRTypeNode() {
        this(NSTypeNode.INSTANCE());
    }

    public NRTypeNode(NSTypeNode nsTypeNode) {
        this.nsTypeNode = nsTypeNode;
    }

    private static NRTypeNode instance;
    public static NRTypeNode INSTANCE() {
        if (instance == null) {
            instance = new NRTypeNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TVOID)){ //void
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType(NodeDataTypes.Void);
            return dummy;
        }

        return nsTypeNode.make(parser); //stype trans
    }
}
