package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NFuncBodyNode implements Node{
	//<funcbody>	::=	<locals> begin <stats> end

    NLocalsNode nLocalsNode;
    NStatsNode nStatsNode;


    public NFuncBodyNode() {
        this(NLocalsNode.INSTANCE(),null);
    }

    public NFuncBodyNode(NLocalsNode nLocalsNode, NStatsNode nStatsNode) {
        this.nLocalsNode = nLocalsNode;
        this.nStatsNode = nStatsNode;
    }


    private static NFuncBodyNode instance;
    public static NFuncBodyNode INSTANCE() {
        if (instance == null) {
            instance = new NFuncBodyNode();
        }
        return instance;
    }

    public void setnStatsNode(NStatsNode nStatsNode) {
        this.nStatsNode = nStatsNode;
    }



    @Override
    public TreeNode make(Parser parser) {
        TreeNode funcBody = new TreeNode();

        TreeNode locals = nLocalsNode.make(parser);

        funcBody.setLeft(locals);

        if(!parser.peekAndConsume(Token.TBEGN)){
            parser.syntacticError("Expected a Begin Keyword", parser.peek());
            return funcBody;
        }

        TreeNode stats = nStatsNode.make(parser);

        funcBody.setRight(stats);

        if(!parser.peekAndConsume(Token.TEND)){
            parser.syntacticError("Expected an End Keyword", parser.peek());
            return funcBody;
        }


        return funcBody;
    }
}

