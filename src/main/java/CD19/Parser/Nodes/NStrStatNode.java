package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

//<strstat>	::=	<forstat> | <ifstat>
public class NStrStatNode implements Node{

    NForStatNode nForStatNode;
    NIfStatNode nIfStatNode;

    public NStrStatNode() {
        this(new NForStatNode(), new NIfStatNode());
    }

    public NStrStatNode(NForStatNode nForStatNode, NIfStatNode nIfStatNode) {
        this.nForStatNode = nForStatNode;
        this.nIfStatNode = nIfStatNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();

        if(token.getTokenID() == Token.TFOR){
            return nForStatNode.make(parser);
        }
        else if(token.getTokenID() == Token.TIFTH){
            return nIfStatNode.make(parser);
        }
        return null; //todo error
    }
}

