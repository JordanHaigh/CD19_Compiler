package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NFactNode implements Node{

    //NPOW	<fact>	::=	<exponent><factTail>
    //	<factTail>	::=	ε | ^<fact>


    NExponentNode nExponentNode;

    public NFactNode() {
        this(new NExponentNode());
    }

    public NFactNode(NExponentNode nExponentNode) {
        this.nExponentNode = nExponentNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode exponent = nExponentNode.make(parser);
        TreeNode tail = tail(parser);
        return new TreeNode(tail.getValue(), exponent, tail);
    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCART)){
            TreeNode fact = this.make(parser);
            return new TreeNode(TreeNode.NPOW, fact, null);
        }
        else
            return null;

    }
}

