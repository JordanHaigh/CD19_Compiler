package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NConstsNode implements Node{
    //Special	<consts>	::=	constants <initlist> | eps

    private NInitListNode nInitListNode;

    public NConstsNode(){
        this(NInitListNode.INSTANCE());
    }

    public NConstsNode(NInitListNode nInitListNode){
        this.nInitListNode = nInitListNode;
    }


    private static NConstsNode instance;
    public static NConstsNode INSTANCE() {
        if (instance == null) {
            instance = new NConstsNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TCONS)){ //// constants token
            Token token = parser.peek();
            if(token.getTokenID() == Token.TIDEN){
                //constants initlist transition

                return nInitListNode.make(parser);
            }
        }

        return null;
    }

}

