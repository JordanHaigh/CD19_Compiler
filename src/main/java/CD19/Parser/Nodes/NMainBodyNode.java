package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NMainBodyNode implements Node {

    //NMAIN	<mainbody>	::=	main <slist> begin <stats> end CD19 <id>

    NSListNode nsListNode;
    NStatsNode nStatsNode;

    public NMainBodyNode() {
        this(new NSListNode(), new NStatsNode());
    }

    public NMainBodyNode(NSListNode nsListNode, NStatsNode nStatsNode) {
        this.nsListNode = nsListNode;
        this.nStatsNode = nStatsNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        parser.peekAndConsume(Token.TMAIN);
        TreeNode slist = nsListNode.make(parser);
        parser.peekAndConsume(Token.TBEGN);
        TreeNode stats = nStatsNode.make(parser);
        parser.peekAndConsume(Token.TEND);
        parser.peekAndConsume(Token.TCD19);
        parser.peekAndConsume(Token.TIDEN);

        return new TreeNode(TreeNode.NMAIN, slist, stats);
    }

}

