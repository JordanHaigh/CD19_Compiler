package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NExprNode implements Node{
    //NADD | NSUB	<expr>	::=	<term><exprTail>
	//<exprTail>	::=	ε | +<term><exprTail> | -<term><exprTail>
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
        TreeNode tail = tail(parser,term);

        if(tail != null)
            return tail;
        else
            return term;
    }

    private TreeNode tail(Parser parser, TreeNode leftNode){
        //<exprTail>	::=	ε | +<expr> | -<expr>
        if(parser.peekAndConsume(Token.TPLUS)){
            return buildLeftDerivedTree(parser, TreeNode.NADD, leftNode);

            //TreeNode expr = this.make(parser);
            //return new TreeNode(TreeNode.NADD, expr,null); //todo probs wrong
        }
        else if(parser.peekAndConsume(Token.TMINS)){
            return buildLeftDerivedTree(parser, TreeNode.NSUB, leftNode);

            //TreeNode expr = this.make(parser);
            //return new TreeNode(TreeNode.NSUB, expr,null);
        }
        else
            return null; //eps - todo error handle
    }

    private TreeNode buildLeftDerivedTree(Parser parser, int expectedTreeNodeValue, TreeNode leftNode){
        TreeNode returnTreeNode = new TreeNode(expectedTreeNodeValue);
        returnTreeNode.setLeft(leftNode);

        TreeNode fact = nTermNode.make(parser);
        TreeNode tail = tail(parser, returnTreeNode);

        returnTreeNode.setRight(fact);
        if(tail == null)
            return returnTreeNode;
        else
            return tail;

    }
}

