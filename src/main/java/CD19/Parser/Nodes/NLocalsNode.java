package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NLocalsNode implements Node{

//	<locals>	::=	<dlist> | eps
    NDListNode ndListNode;

    public NLocalsNode() {
        this(NDListNode.INSTANCE());
    }

    public NLocalsNode(NDListNode ndListNode) {
        this.ndListNode = ndListNode;
    }

    private static NLocalsNode instance;
    public static NLocalsNode INSTANCE() {
        if (instance == null) {
            instance = new NLocalsNode();
        }
        return instance;
    }


    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN){
            return ndListNode.make(parser);
        }
        else{
            return null; //eps trans
        }

    }
}

