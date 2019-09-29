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
        TreeNode asgnStatOrCallStat = new TreeNode();

        Token token = parser.peek();
        if(!parser.peekAndConsume(Token.TIDEN)){
            parser.syntacticError("Expected an identifier", parser.peek());
            return asgnStatOrCallStat;
        }

        TreeNode tail = tail(parser);

        if(tail.getValue() != TreeNode.NUNDEF){
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(), tail.getType(), token.getStr()+"_"+parser.getScope());
            tail.getLeft().setSymbol(record);
        }
        return tail;
    }



    private TreeNode tail(Parser parser){
        //todo fix type for semantics

        if(parser.peekAndConsume(Token.TLPAR)){
            //(<callStat>)
            TreeNode callStat = nCallStatNode.make(parser);

            if(!parser.peekAndConsume(Token.TRPAR)){
                parser.syntacticError("Expected a right parenthesis", parser.peek());
                return new TreeNode();
            }
            else
                return callStat;
        }
        else{
            //<asgnStat>
            return nAsgnStatNode.make(parser);
        }

    }
}

