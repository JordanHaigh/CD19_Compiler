package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NSTypeNode implements Node{

    private static NSTypeNode instance;
    public static NSTypeNode INSTANCE() {
        if (instance == null) {
            instance = new NSTypeNode();
        }
        return instance;
    }


    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        TreeNode dummy = new TreeNode(TreeNode.NUNDEF);

        if(token.getTokenID() == Token.TINTG){
            parser.consume();
            dummy.setType(NodeDataTypes.Integer);
        }
        else if(token.getTokenID() == Token.TREAL){
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
