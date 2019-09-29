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
        TreeNode mainbody = new TreeNode();

        if(!parser.peekAndConsume(Token.TMAIN)){
            parser.syntacticError("Expecting the Main Keyword",parser.peek());
            return mainbody;
        }

        parser.enterScope("main");

        TreeNode slist = nsListNode.make(parser);

        if(!parser.peekAndConsume(Token.TBEGN)){
            parser.syntacticError("Expecting the Begin Keyword", parser.peek());
            return mainbody;
        }

        TreeNode stats = nStatsNode.make(parser);

        if(!parser.peekAndConsume(Token.TEND)){
            parser.syntacticError("Expecting the End Keyword", parser.peek());
            return mainbody;
        }

        parser.leaveScope();

        mainbody = new TreeNode(TreeNode.NMAIN, slist, stats);
        return mainbody;
    }

}

