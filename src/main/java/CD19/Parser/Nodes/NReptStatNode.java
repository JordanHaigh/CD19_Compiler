package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NReptStatNode implements Node {
    //NREPT	<repstat>	::=	repeat ( <asgnlist> ) <stats> until <bool>
    NAsgnListNode nAsgnListNode;
    NStatsNode nstatsNode;
    NBoolNode nBoolNode;

    public NReptStatNode() {
        this(NAsgnListNode.INSTANCE(), null, null);
    }

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
        this.nstatsNode = nStatsNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode repstat = new TreeNode();

        if (!parser.peekAndConsume(Token.TREPT)) {
            parser.syntacticError("Expected a Repeat Keyword", parser.peek());
            return repstat;
        }

        if (!parser.peekAndConsume(Token.TLPAR)){
             parser.syntacticError("Expected a Left Paraethesis", parser.peek());
             return repstat;
        }

        TreeNode asgnList = nAsgnListNode.make(parser);


        if(!parser.peekAndConsume(Token.TRPAR)){
            parser.syntacticError("Expected a Right Parenthesis", parser.peek());
            return repstat;
        }

        TreeNode stats = nstatsNode.make(parser);

        if(!parser.peekAndConsume(Token.TUNTL)){
            parser.syntacticError("Expected an Until keyword", parser.peek());
            return repstat;
        }

        TreeNode bool = nBoolNode.make(parser);

        repstat = new TreeNode(TreeNode.NREPT, asgnList, stats, bool);
        return repstat;

    }
}


