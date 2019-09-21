package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NReptStatNode implements Node{
    //NREPT	<repstat>	::=	repeat ( <asgnlist> ) <stats> until <bool>
    NAsgnListNode nAsgnListNode;
    NStatsNode nstatsNode;
    NBoolNode nBoolNode;

    public NReptStatNode() {
        this(NAsgnListNode.INSTANCE(),null, null);}

    public NReptStatNode(NAsgnListNode nAsgnListNode, NStatsNode nstatsNode, NBoolNode nBoolNode) {
        this.nAsgnListNode = nAsgnListNode;
        this.nstatsNode = nstatsNode;
        this.nBoolNode = nBoolNode;
    }

    private static NReptStatNode instance;
    public static NReptStatNode INSTANCE() {
        if (instance == null) {
            instance = new NReptStatNode();
        }
        return instance;
    }

    public void setnBoolNode(NBoolNode boolNode) {
        this.nBoolNode = boolNode;
    }

    public void setnStatsNode(NStatsNode nStatsNode) {
        this.nstatsNode = nstatsNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        parser.peekAndConsume(Token.TREPT);
        parser.peekAndConsume(Token.TLPAR);
        TreeNode asgnList = nAsgnListNode.make(parser);
        parser.peekAndConsume(Token.TRPAR);
        TreeNode stats = nstatsNode.make(parser);
        parser.peekAndConsume(Token.TUNTL);
        TreeNode bool = nBoolNode.make(parser);

        return new TreeNode(TreeNode.NREPT, asgnList, stats, bool);

    }
}


