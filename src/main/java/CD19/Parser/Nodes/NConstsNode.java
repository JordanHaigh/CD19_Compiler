package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NConstsNode implements Node{
    //Special	<consts>	::=	constants <initlist> | ε

    private NInitListNode nInitListNode;

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN){
            //initlist transition
            TreeNode nInitListTreeNode = nInitListNode.make(parser);
            TreeNode nConstsTreeNode = new TreeNode(TreeNode.NUNDEF, null, nInitListTreeNode); //todo only one child???
            return nConstsTreeNode;
        }
        else{
            //epsilon transition //todo is this right?
            TreeNode nConstsTreeNode = new TreeNode(TreeNode.NUNDEF);
            return nConstsTreeNode;
        }
    }



}

