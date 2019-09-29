package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NReturnStatNode implements Node {

    //NRETN	<returnstat>	::=	return <returnStatTail>
    //	<returnStatTail>	::=	ε | <expr>

    NExprNode nExprNode;

    public NReturnStatNode() {
        this(null);
    }

    public NReturnStatNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    private static NReturnStatNode instance;

    public static NReturnStatNode INSTANCE() {
        if (instance == null) {
            instance = new NReturnStatNode();
        }
        return instance;
    }


    public void setnExprNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode returnstat = new TreeNode();

        if (!parser.peekAndConsume(Token.TRETN)) {
            parser.syntacticError("Expected Return Keyword", parser.peek());
            return returnstat;
        }

        TreeNode tail = tail(parser);

        returnstat = new TreeNode(TreeNode.NRETN, tail, null);
        return returnstat;
    }

    private TreeNode tail(Parser parser) {
        Token token = parser.peek();
        if (token.getTokenID() == Token.TIDEN || //expr->term->fact->exponent
                token.getTokenID() == Token.TILIT ||
                token.getTokenID() == Token.TFLIT ||
                token.getTokenID() == Token.TTRUE ||
                token.getTokenID() == Token.TFALS ||
                token.getTokenID() == Token.TLPAR) {

            return nExprNode.make(parser);
        }
        return null; //eps trans

    }
}
