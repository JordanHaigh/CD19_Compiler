package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NInitNode implements Node{

    //NINIT	<init>	::=	<id> = <expr>
    NExprNode nExprNode;

    public NInitNode(){
        this(new NExprNode());
    }

    public NInitNode(NExprNode nExprNode){
        this.nExprNode = nExprNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token id = parser.peek(); //id
        parser.peekAndConsume(Token.TEQUL);
        TreeNode exprTreeNode = nExprNode.make(parser);

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(),exprTreeNode.getType());

        return new TreeNode(TreeNode.NINIT, record);

    }
}


