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
        this(new NAsgnListNode(), new NStatsNode(), new NBoolNode());
    }

    public NReptStatNode(NAsgnListNode nAsgnListNode, NStatsNode nstatsNode, NBoolNode nBoolNode) {
        this.nAsgnListNode = nAsgnListNode;
        this.nstatsNode = nstatsNode;
        this.nBoolNode = nBoolNode;
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


