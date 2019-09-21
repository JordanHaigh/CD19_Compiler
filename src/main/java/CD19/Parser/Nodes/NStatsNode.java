package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NStatsNode implements Node{
    //NSTATS	<stats>	::=	<stat> ;  <StatsTail> |
	//<statsTail>	::=	eps | <stats>

    NStatNode nStatNode;
    NStrStatNode nStrStatNode;

    public NStatsNode() {
        this(NStatNode.INSTANCE(), NStrStatNode.INSTANCE());
    }

    public NStatsNode(NStatNode nStatNode, NStrStatNode nStrStatNode) {
        this.nStatNode = nStatNode;
        this.nStrStatNode = nStrStatNode;
    }

    private static NStatsNode instance;
    public static NStatsNode INSTANCE() {
        if (instance == null) {
            instance = new NStatsNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TFOR || token.getTokenID() == Token.TIFTH){
            return strstatPath(parser);
        }
        else{
            return statSemiColonPath(parser);
        }
    }


    private TreeNode statSemiColonPath(Parser parser){
        //<stat> ;  <StatsTail>
        TreeNode stat = nStatNode.make(parser);
        parser.peekAndConsume(Token.TSEMI);
        TreeNode tail = tail(parser);

        return new TreeNode(TreeNode.NSTATS, stat, tail);
    }


    private TreeNode strstatPath(Parser parser){
        //<strstat> <statsTail>
        TreeNode strstat = nStrStatNode.make(parser);
        TreeNode tail = tail(parser);

        return new TreeNode(TreeNode.NSTATS, strstat, tail);

    }


    private TreeNode tail(Parser parser){
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIFTH //ifstat
                || token.getTokenID() == Token.TFOR //forstat
                || token.getTokenID() == Token.TREPT //reptstat
                || token.getTokenID() == Token.TIDEN //asgnstat
                || token.getTokenID() == Token.TINPT //iostat
                || token.getTokenID() == Token.TRETN //retnstat
        ){
            return this.make(parser); //stat transition
        }
        return null; //eps trans

    }
}


