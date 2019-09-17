package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NForStatNode implements Node{

    //NFOR	<forstat>	::=	for ( <asgnlist> ; <bool> ) <stats> end
    NAsgnListNode nAsgnListNode;
    NBoolNode nBoolNode;
    NStatsNode nStatsNode;

    public NForStatNode() {
        this(new NAsgnListNode(), new NBoolNode(), new NStatsNode());
    }

    public NForStatNode(NAsgnListNode nAsgnListNode, NBoolNode nBoolNode, NStatsNode nStatsNode) {
        this.nAsgnListNode = nAsgnListNode;
        this.nBoolNode = nBoolNode;
        this.nStatsNode = nStatsNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        parser.peekAndConsume(Token.TFOR);
        parser.peekAndConsume(Token.TLPAR);
        TreeNode asgnlist = nAsgnListNode.make(parser);
        parser.peekAndConsume(Token.TSEMI);
        TreeNode bool = nBoolNode.make(parser);
        parser.peekAndConsume(Token.TRPAR);
        TreeNode stats = nStatsNode.make(parser);
        parser.peekAndConsume(Token.TEND);

        return new TreeNode(TreeNode.NFOR, asgnlist, bool, stats);

    }
}
