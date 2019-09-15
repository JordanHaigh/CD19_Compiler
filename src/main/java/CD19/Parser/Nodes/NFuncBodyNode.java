package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NFuncBodyNode implements Node{
	//<funcbody>	::=	<locals> begin <stats> end

    NLocalsNode nLocalsNode;
    NStatsNode nStatsNode;


    public NFuncBodyNode() {
        this(new NLocalsNode(), new NStatsNode());
    }

    public NFuncBodyNode(NLocalsNode nLocalsNode, NStatsNode nStatsNode) {
        this.nLocalsNode = nLocalsNode;
        this.nStatsNode = nStatsNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode locals = nLocalsNode.make(parser);
        parser.peekAndConsume(Token.TBEGN);
        TreeNode stats = nStatsNode.make(parser);
        parser.peekAndConsume(Token.TEND);
        TreeNode dummy = new TreeNode(TreeNode.NUNDEF, locals, stats);
        return dummy;// todo ask dan about this. i dont understand what to do here

    }
}

