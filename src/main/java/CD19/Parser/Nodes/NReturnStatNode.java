package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NReturnStatNode implements Node{

    //NRETN	<returnstat>	::=	return <returnStatTail>
    //	<returnStatTail>	::=	ε | <expr>

    NExprNode nExprNode;

    public NReturnStatNode() {
        this(new NExprNode());
    }

    public NReturnStatNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        parser.peekAndConsume(Token.TRETN);
        TreeNode tail = tail(parser);

        return new TreeNode(TreeNode.NRETN, tail,null);
    }

    private TreeNode tail(Parser parser){
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN || //expr->term->fact->exponent
                token.getTokenID() == Token.TILIT ||
                token.getTokenID() == Token.TFLIT ||
                token.getTokenID() == Token.TTRUE ||
                token.getTokenID() == Token.TFALS ||
                token.getTokenID() == Token.TLPAR){

            return nExprNode.make(parser);
        }
        return null; //eps trans

    }
}
