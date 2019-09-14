package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NSTypeNode implements Node{


    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        TreeNode dummy = new TreeNode(TreeNode.NUNDEF);

        if(token.getTokenID() == Token.TILIT){
            parser.consume();
            dummy.setType(NodeDataTypes.Integer);
        }
        else if(token.getTokenID() == Token.TFLIT){
            parser.consume();
            dummy.setType(NodeDataTypes.Real);
        }
        else if(token.getTokenID() == Token.TBOOL){
            parser.consume();
            dummy.setType(NodeDataTypes.Boolean);
        }
        else
            return null; //todo meant to error

        return dummy;
    }
}
