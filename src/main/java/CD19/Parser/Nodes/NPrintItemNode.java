package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NPrintItemNode implements Node{
    //NSTRG	<printitem>	::=	<expr> | <string>
    NExprNode nExprNode;

    public NPrintItemNode() {
        this(new NExprNode());
    }

    public NPrintItemNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN || //expr check
            token.getTokenID() == Token.TILIT ||
            token.getTokenID() == Token.TFLIT ||
            token.getTokenID() == Token.TTRUE ||
            token.getTokenID() == Token.TFALS ||
            token.getTokenID() == Token.TLPAR
            ){
            return nExprNode.make(parser);
        }
        else{ //return string
            return new TreeNode(TreeNode.NSTRG,null,null);
        }
    }
}
