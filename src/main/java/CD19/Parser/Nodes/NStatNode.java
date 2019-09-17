package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NStatNode implements Node{

    NReptStatNode nReptStatNode;
    NIoStatNode nIoStatNode;
    NReturnStatNode nReturnStatNode;
    NAsgnStatOrCallStatNode nAsgnStatOrCallStatNode;

    public NStatNode() {
        this(new NReptStatNode(), new NIoStatNode(), new NReturnStatNode(),new NAsgnStatOrCallStatNode());
    }

    public NStatNode(NReptStatNode nReptStatNode, NIoStatNode nIoStatNode,
                     NReturnStatNode nReturnStatNode, NAsgnStatOrCallStatNode nAsgnStatOrCallStatNode) {
        this.nReptStatNode = nReptStatNode;
        this.nIoStatNode = nIoStatNode;
        this.nReturnStatNode = nReturnStatNode;
        this.nAsgnStatOrCallStatNode = nAsgnStatOrCallStatNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if (token.getTokenID() == Token.TREPT) {
            return nReptStatNode.make(parser);
        }
        else if(token.getTokenID() == Token.TINPT){
            return nIoStatNode.make(parser);
        }
        else if(token.getTokenID() == Token.TRETN){
            return nReturnStatNode.make(parser);
        }
        else if(token.getTokenID() == Token.TIDEN){
            parser.consume();
            return nAsgnStatOrCallStatNode.make(parser);
        }

        return null; //todo error
    }
}

