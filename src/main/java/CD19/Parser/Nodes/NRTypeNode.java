package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NRTypeNode implements Node{

	//<rtype>	::=	<stype> | void

    NSTypeNode nsTypeNode;

    public NRTypeNode() {
        this(new NSTypeNode());
    }

    public NRTypeNode(NSTypeNode nsTypeNode) {
        this.nsTypeNode = nsTypeNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TVOID)){ //void
            return null;
        }

        return nsTypeNode.make(parser); //stype trans
    }
}
