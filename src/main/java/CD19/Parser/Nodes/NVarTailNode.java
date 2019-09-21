package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NVarTailNode implements Node{

    //	<varTail>	::=	ε | [<expr>] . <id>
    NExprNode nExprNode;

    public NVarTailNode() {
        this(new NExprNode());
    }

    public NVarTailNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TLBRK)){
            TreeNode expr = nExprNode.make(parser);
            parser.peekAndConsume(Token.TRBRK);
            parser.peekAndConsume(Token.TDOT);
            parser.peekAndConsume(Token.TIDEN);
            SymbolTableRecord record = new SymbolTableRecord(); //todo fill this in
            return new TreeNode(TreeNode.NARRV, record);
        }
        else{
            SymbolTableRecord record = new SymbolTableRecord(); //todo fill this in
            return new TreeNode(TreeNode.NSIMV, record);
        }
    }
}