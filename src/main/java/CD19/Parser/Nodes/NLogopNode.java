package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NLogopNode implements Node{

    private static NLogopNode instance;
    public static NLogopNode INSTANCE() {
        if (instance == null) {
            instance = new NLogopNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TAND))
            return new TreeNode(TreeNode.NAND, null);
        else if(parser.peekAndConsume(Token.TOR))
            return new TreeNode(TreeNode.NOR, null);
        else if(parser.peekAndConsume(Token.TXOR))
            return new TreeNode(TreeNode.NXOR, null);
        return null; //todo error check
    }
}
