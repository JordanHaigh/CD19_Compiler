package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NTypeNode implements Node{

    //NRTYPE|NATYPE	<type>	::=	<anyFuckingID> is <typeTail>
    //	<typeTail>	::=	<fields> end |  array [<expr>] of <structid>


    NFieldsNode nFieldsNode;
    NExprNode nExprNode;


    public NTypeNode() {
        this(new NFieldsNode(),new NExprNode());
    }

    public NTypeNode(NFieldsNode nFieldsNode, NExprNode nExprNode) {
        this.nFieldsNode = nFieldsNode;
        this.nExprNode = nExprNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token id = parser.peek();
        parser.consume();
        parser.peekAndConsume(Token.TIS);
        TreeNode tail = tail(parser);
        NodeDataTypes dataType;
        if(tail.getValue() == TreeNode.NATYPE)
            dataType = NodeDataTypes.Array;
        else
            dataType = NodeDataTypes.Struct;

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(),dataType);
        return new TreeNode(tail.getValue(), record);
    }

    private TreeNode tail(Parser parser){
        //todo struct must go "<fields> end" and arrayid must go to "array [<expr>]"...
        Token token = parser.peek();
        if(token.getTokenID() == Token.TARAY){
            return arrayTail(parser);
        }
        else{
            return structTail(parser);
        }
    }

    private TreeNode arrayTail(Parser parser){
        //array [<expr>] of <structid>
        parser.peekAndConsume(Token.TARAY);
        parser.peekAndConsume(Token.TLBRK);
        TreeNode exprTNode = nExprNode.make(parser);
        parser.peekAndConsume(Token.TRBRK);
        parser.peekAndConsume(Token.TOF);
        parser.peekAndConsume(Token.TIDEN); //check struct id is legit

        return new TreeNode(TreeNode.NATYPE, exprTNode,null);
    }

    private TreeNode structTail(Parser parser){
        //<fields> end

        TreeNode nFieldsTNode = nFieldsNode.make(parser);
        parser.peekAndConsume(Token.TEND);

        return new TreeNode(TreeNode.NRTYPE, nFieldsTNode, null);
    }
}

