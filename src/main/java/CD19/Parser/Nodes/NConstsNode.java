package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NConstsNode implements Node{
    //Special	<consts>	::=	constants <initlist> | ε

    private NInitListNode nInitListNode;

    public NConstsNode(){
        this(new NInitListNode());
    }

    public NConstsNode(NInitListNode nInitListNode){
        this.nInitListNode = nInitListNode;
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

