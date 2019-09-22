package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NIfStatNode implements Node{
    //NIFTH | NIFTE	<ifstat>	::=	if ( <bool> ) <stats> <ifStatTail> end
    //	<ifStatTail>	::=	eps |  else <stats>

    NBoolNode nBoolNode;
    NStatsNode nStatsNode;

    public NIfStatNode() {
        this(null, null);
    }

    public NIfStatNode(NBoolNode nBoolNode, NStatsNode nStatsNode) {
        this.nBoolNode = nBoolNode;
        this.nStatsNode = nStatsNode;
    }

    private static NIfStatNode instance;
    public static NIfStatNode INSTANCE() {
        if (instance == null) {
            instance = new NIfStatNode();
        }
        return instance;
    }

    public void setnBoolNode(NBoolNode boolNode) {
        this.nBoolNode = boolNode;
    }

    public void setnStatsNode(NStatsNode nStatsNode) {
        this.nStatsNode = nStatsNode;
    }

    @Override
    public TreeNode make(Parser parser) {

        parser.peekAndConsume(Token.TIFTH);
        parser.peekAndConsume(Token.TLPAR);

        TreeNode bool = nBoolNode.make(parser);

        parser.peekAndConsume(Token.TRPAR);

        TreeNode stats = nStatsNode.make(parser);

        TreeNode tail = tail(parser);

        parser.peekAndConsume(Token.TEND);


        if(tail==null){ //if just an if statement, then we dont need tail node's stats
            return new TreeNode(TreeNode.NIFTH, bool, stats);
        }
        else{
            return new TreeNode(TreeNode.NIFTE, bool, stats, tail); //contains an else statement so we need tail's stats
        }

    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TELSE)){
            TreeNode stats = nStatsNode.make(parser);
            return stats;
        }
        else
            return null;

    }
}
