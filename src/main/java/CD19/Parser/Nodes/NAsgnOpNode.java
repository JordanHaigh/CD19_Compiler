package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NAsgnOpNode implements Node{
    //NASGN, NPLEQ, NMNEQ, NSTEQ, NDVEQ	<asgnop>	::=	 = | += | -= | *= | /=

    private static NAsgnOpNode instance;
    public static NAsgnOpNode INSTANCE() {
        if (instance == null) {
            instance = new NAsgnOpNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TEQUL)) //=
            return new TreeNode(TreeNode.NASGN, null);
        else if(parser.peekAndConsume(Token.TPLEQ)) //+=
            return new TreeNode(TreeNode.NPLEQ, null);
        else if(parser.peekAndConsume(Token.TMNEQ)) //-=
            return new TreeNode(TreeNode.NMNEQ, null);
        else if(parser.peekAndConsume(Token.TSTEQ)) //*=
            return new TreeNode(TreeNode.NSTEQ, null);
        else if(parser.peekAndConsume(Token.TDVEQ)) // /=
            return new TreeNode(TreeNode.NDVEQ, null);
        return null; //todo error check






    }
}
