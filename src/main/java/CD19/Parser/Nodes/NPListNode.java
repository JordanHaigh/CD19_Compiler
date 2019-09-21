package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NPListNode implements Node{

    //<plist>	::=	<params> | eps
    NParamsNode nParamsNode;

    public NPListNode() {
        this(NParamsNode.INSTANCE());
    }

    public NPListNode(NParamsNode nParamsNode) {
        this.nParamsNode = nParamsNode;
    }

    private static NPListNode instance;
    public static NPListNode INSTANCE() {
        if (instance == null) {
            instance = new NPListNode();
        }
        return instance;
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

