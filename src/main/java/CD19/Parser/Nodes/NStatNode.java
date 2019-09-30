package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NStatNode implements Node{
//	<stat>	::=	<reptstat> | <iostat> | <returnstat> |  <asgnStatOrCallStat>
    NReptStatNode nReptStatNode;
    NIoStatNode nIoStatNode;
    NReturnStatNode nReturnStatNode;
    NAsgnStatOrCallStatNode nAsgnStatOrCallStatNode;

    public NStatNode() {
        this(NReptStatNode.INSTANCE(), NIoStatNode.INSTANCE(), NReturnStatNode.INSTANCE(), NAsgnStatOrCallStatNode.INSTANCE());
    }

    public NStatNode(NReptStatNode nReptStatNode, NIoStatNode nIoStatNode,
                     NReturnStatNode nReturnStatNode, NAsgnStatOrCallStatNode nAsgnStatOrCallStatNode) {
        this.nReptStatNode = nReptStatNode;
        this.nIoStatNode = nIoStatNode;
        this.nReturnStatNode = nReturnStatNode;
        this.nAsgnStatOrCallStatNode = nAsgnStatOrCallStatNode;
    }

    private static NStatNode instance;
    public static NStatNode INSTANCE() {
        if (instance == null) {
            instance = new NStatNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if (token.getTokenID() == Token.TREPT) {
            return nReptStatNode.make(parser);
        }
        else if(token.getTokenID() == Token.TINPT || token.getTokenID() == Token.TPRIN || token.getTokenID() == Token.TPRLN){
            return nIoStatNode.make(parser);
        }
        else if(token.getTokenID() == Token.TRETN){
            return nReturnStatNode.make(parser);
        }
        else if(token.getTokenID() == Token.TIDEN){
            return nAsgnStatOrCallStatNode.make(parser);
        }else{
            parser.syntacticError("Expected a Stat Keyword (Repeat, IO, Return, Asgn, Call)", parser.peek());
            return new TreeNode();
        }

    }

    
}

