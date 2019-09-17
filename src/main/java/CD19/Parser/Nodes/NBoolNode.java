package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NBoolNode implements Node{
    //NBOOL	<bool>	::=	<rel><boolTail>
    //	<boolTail>	::=	ε | <logop><bool>


    NRelNode nRelNode;
    NLogopNode nLogopNode;

    public NBoolNode() {
        this(new NRelNode(), new NLogopNode());
    }

    public NBoolNode(NRelNode nRelNode, NLogopNode nLogopNode) {
        this.nRelNode = nRelNode;
        this.nLogopNode = nLogopNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode rel = nRelNode.make(parser);
        TreeNode tail = tail(parser);

        return new TreeNode(tail.getValue(), rel, tail);
    }

    private TreeNode tail(Parser parser){
        Token token = parser.peek();
        if(token.getTokenID() == Token.TAND ||
                token.getTokenID() == Token.TOR ||
                token.getTokenID() == Token.TXOR){
            TreeNode logop = nLogopNode.make(parser);
            TreeNode bool = this.make(parser);
            return new TreeNode (logop.getValue(), logop, bool); //todo probs wrong
        }
        else
            return new TreeNode(TreeNode.NBOOL, null,null); //eps trans

    }
}


