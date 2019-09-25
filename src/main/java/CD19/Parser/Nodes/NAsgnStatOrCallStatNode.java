package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NAsgnStatOrCallStatNode implements Node{

//	<asgnStatOrCallStat>	::= 	<id><tail>
//	<tail> ::= (<callStat>) | <asgnStat>

    NCallStatNode nCallStatNode;
    NAsgnStatNode nAsgnStatNode;

    public NAsgnStatOrCallStatNode() {
        this(NCallStatNode.INSTANCE(), NAsgnStatNode.INSTANCE());
    }

    public NAsgnStatOrCallStatNode(NCallStatNode nCallStatNode, NAsgnStatNode nAsgnStatNode) {
        this.nCallStatNode = nCallStatNode;
        this.nAsgnStatNode = nAsgnStatNode;
    }



    private static NAsgnStatOrCallStatNode instance;
    public static NAsgnStatOrCallStatNode INSTANCE() {
        if (instance == null) {
            instance = new NAsgnStatOrCallStatNode();
        }
        return instance;
    }



    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        parser.peekAndConsume(Token.TIDEN);

        TreeNode tail = tail(parser);

        SymbolTableRecord record = new SymbolTableRecord(token.getStr(), tail.getType(), token.getStr()+"_"+parser.getScope()); //todo fix scope
        tail.getLeft().setSymbol(record);

        return tail;
    }



    private TreeNode tail(Parser parser){
        Token token = parser.peek();
        //todo fix type for semantics
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

