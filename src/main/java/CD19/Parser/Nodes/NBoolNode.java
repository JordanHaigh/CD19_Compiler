package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NBoolNode implements Node{
//NBOOL	<bool>	::=	<rel><boolTail>
//	<boolTail>	::=	ε | <logop><rel><boolTail>


    NRelNode nRelNode;
    NLogopNode nLogopNode;

    public NBoolNode() {
        this(NRelNode.INSTANCE(), NLogopNode.INSTANCE());
    }

    public NBoolNode(NRelNode nRelNode, NLogopNode nLogopNode) {
        this.nRelNode = nRelNode;
        this.nLogopNode = nLogopNode;
    }



    private static NBoolNode instance;
    public static NBoolNode INSTANCE() {
        if (instance == null) {
            instance = new NBoolNode();
        }
        return instance;
    }



    @Override
    public TreeNode make(Parser parser) {
        TreeNode rel = nRelNode.make(parser);
        TreeNode tail = tail(parser);
        if (tail != null) {
            tail.setLeft(rel);
            return tail;
        }
        else
            return rel;
    }

    private TreeNode tail(Parser parser){
        Token token = parser.peek();
        if(token.getTokenID() == Token.TAND ||
                token.getTokenID() == Token.TOR ||
                token.getTokenID() == Token.TXOR){

            TreeNode logop = nLogopNode.make(parser);
            TreeNode bool = this.make(parser);
            return new TreeNode (TreeNode.NBOOL, null, logop, bool);
        }
        else
          return null; //eps trans

    }

}


