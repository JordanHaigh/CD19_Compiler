package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NExprNode implements Node{
    //NADD | NSUB	<expr>	::=	<term><exprTail>
	//<exprTail>	::=	ε | +<expr> | -<expr>
    NTermNode nTermNode;

    public NExprNode() {
        this(new NTermNode());
    }

    public NExprNode(NTermNode nTermNode) {
        this.nTermNode = nTermNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode term = nTermNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return new TreeNode(term.getValue(), term, null);
        else
            return new TreeNode(tail.getValue(), term, tail);
    }

    private TreeNode tail(Parser parser){
        //<exprTail>	::=	ε | +<expr> | -<expr>
        if(parser.peekAndConsume(Token.TPLUS)){
            TreeNode expr = this.make(parser);
            return new TreeNode(TreeNode.NADD, expr,null); //todo probs wrong
        }
        else if(parser.peekAndConsume(Token.TMINS)){
            TreeNode expr = this.make(parser);
            return new TreeNode(TreeNode.NSUB, expr,null);
        }
        else
            return null; //todo error handle
    }
}

