package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NAsgnStatOrCallStatNode implements Node{

//	<asgnStatOrCallStat>	::= 	(<callStat>) | <asgnStat>

    NCallStatNode nCallStatNode;
    NAsgnStatNode nAsgnStatNode;

    public NAsgnStatOrCallStatNode() {
        this(new NCallStatNode(), new NAsgnStatNode());
    }

    public NAsgnStatOrCallStatNode(NCallStatNode nCallStatNode, NAsgnStatNode nAsgnStatNode) {
        this.nCallStatNode = nCallStatNode;
        this.nAsgnStatNode = nAsgnStatNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();

        if(token.getTokenID() == Token.TLPAR){
            //(<callStat>)
            parser.consume();
            TreeNode callStat = nCallStatNode.make(parser);
            parser.peekAndConsume(Token.TRPAR);

            return callStat;
        }
        else{
            //<asgnStat>
            return nAsgnStatNode.make(parser);
        }

    }
}

