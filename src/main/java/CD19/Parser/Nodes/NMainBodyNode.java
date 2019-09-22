package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NMainBodyNode implements Node {

    //NMAIN	<mainbody>	::=	main <slist> begin <stats> end

    NSListNode nsListNode;
    NStatsNode nStatsNode;

    public NMainBodyNode() {
        this(NSListNode.INSTANCE(),null);
    }

    public NMainBodyNode(NSListNode nsListNode, NStatsNode nStatsNode) {
        this.nsListNode = nsListNode;
        this.nStatsNode = nStatsNode;
    }

    private static NMainBodyNode instance;
    public static NMainBodyNode INSTANCE() {
        if (instance == null) {
            instance = new NMainBodyNode();
        }
        return instance;
    }

    public void setnStatsNode(NStatsNode nStatsNode) {
        this.nStatsNode = nStatsNode;
    }



    @Override
    public TreeNode make(Parser parser) {
        parser.peekAndConsume(Token.TMAIN);
        TreeNode slist = nsListNode.make(parser);
        parser.peekAndConsume(Token.TBEGN);
        TreeNode stats = nStatsNode.make(parser);
        parser.peekAndConsume(Token.TEND);


        return new TreeNode(TreeNode.NMAIN, slist, stats);
    }

}

