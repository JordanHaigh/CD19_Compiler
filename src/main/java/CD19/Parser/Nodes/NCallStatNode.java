package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NCallStatNode implements Node{
    //NCALL	<callStat>	::=	ε | <elist>
    NEListNode neListNode;


    public NCallStatNode() {
        this(NEListNode.INSTANCE());
    }

    public NCallStatNode(NEListNode neListNode) {
        this.neListNode = neListNode;
    }

    private static NCallStatNode instance;
    public static NCallStatNode INSTANCE() {
        if (instance == null) {
            instance = new NCallStatNode();
        }
        return instance;
    }



    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN || //elist ->bool->rel->expr->term->fact->exponent
                token.getTokenID() == Token.TILIT ||
                token.getTokenID() == Token.TFLIT ||
                token.getTokenID() == Token.TTRUE ||
                token.getTokenID() == Token.TFALS ||
                token.getTokenID() == Token.TLPAR){

            TreeNode elist = neListNode.make(parser);
            return new TreeNode(TreeNode.NCALL, elist,null);
        }
        return new TreeNode(TreeNode.NCALL, null,null);
    }

}
