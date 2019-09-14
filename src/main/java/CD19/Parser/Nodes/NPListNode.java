package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NPListNode implements Node{

    //<plist>	::=	<params> | eps
    NParamsNode nParamsNode;

    public NPListNode() {
        this(new NParamsNode());
    }

    public NPListNode(NParamsNode nParamsNode) {
        this.nParamsNode = nParamsNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN){
            return nParamsNode.make(parser);
        }

        return null; //eps
    }
}

