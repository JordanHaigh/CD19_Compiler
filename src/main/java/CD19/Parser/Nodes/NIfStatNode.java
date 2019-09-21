package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NIfStatNode implements Node{
    //NIFTH | NIFTE	<ifstat>	::=	if ( <bool> ) <stats> <ifStatTail>
    //	<ifStatTail>	::=	end |  else <stats> end

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
        return new TreeNode(tail.getValue(), bool, stats, tail); //todo check if tail null??
    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TELSE)){
            TreeNode stats = nStatsNode.make(parser);
            parser.peekAndConsume(Token.TEND);
            return new TreeNode(TreeNode.NIFTE, stats,null);
        }
        else if(parser.peekAndConsume(Token.TEND)){
            return new TreeNode(TreeNode.NIFTH,null,null);
        }
        return null; //todo error here

    }
}
