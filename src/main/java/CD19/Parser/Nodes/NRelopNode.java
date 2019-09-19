package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NRelopNode implements Node{

    //NEQL, NNEQ, NGRT, NLEQ, NLSS, NGEQ	<relop>	::=	 == | != | > | <= | < | >=
    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TEQEQ)) //==
            return new TreeNode(TreeNode.NEQL, null);
        else if(parser.peekAndConsume(Token.TNEQL)) //!=
            return new TreeNode(TreeNode.NNEQ, null);
        else if(parser.peekAndConsume(Token.TGRTR)) //>
            return new TreeNode(TreeNode.NGRT, null);
        else if(parser.peekAndConsume(Token.TGEQL)) // >=
            return new TreeNode(TreeNode.NGEQ);
        else if(parser.peekAndConsume(Token.TLESS)) // <
            return new TreeNode(TreeNode.NLSS);
        else if(parser.peekAndConsume(Token.TLEQL)) //<=
            return new TreeNode(TreeNode.NLEQ);
        return null; //todo error check


    }
}
