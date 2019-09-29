package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NStatsNode implements Node{
    //NSTATS	<stats>	::=	<stat> ;  <StatsTail> | <strstat> <statsTail>
	//<statsTail>	::=	 eps |  {<stat> ;  <StatsTail> | <strstat> <statsTail>}

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
        TreeNode stats = new TreeNode();
        //<stat> ;  <StatsTail>
        TreeNode stat = nStatNode.make(parser);
        if(!parser.peekAndConsume(Token.TSEMI)){
            parser.syntacticError("Expected a Semicolon", parser.peek());
            return stats;
        }
        TreeNode tail = tail(parser);

        if(tail == null)
            return stat;

        stats = new TreeNode(TreeNode.NSTATS, stat, tail);
        return stats;
    }


    private TreeNode strstatPath(Parser parser){
        //<strstat> <statsTail>
        TreeNode strstat = nStrStatNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return strstat;
        return new TreeNode(TreeNode.NSTATS, strstat, tail);

    }


    private TreeNode tail(Parser parser){
        //	//<statsTail>	::=	 eps |  {<stat> ;  <StatsTail> | <strstat> <statsTail>}
        TreeNode stats = new TreeNode();

        Token token = parser.peek();
        if(token.getTokenID() == Token.TREPT  || token.getTokenID() == Token.TIDEN
                || token.getTokenID() == Token.TINPT || token.getTokenID() == Token.TPRIN
                || token.getTokenID() == Token.TPRLN || token.getTokenID() == Token.TRETN
        ){
           return statSemiColonPath(parser);
        }
        else if(token.getTokenID() == Token.TIFTH  || token.getTokenID() == Token.TFOR){
            return strstatPath(parser);
        }
        else
            return null; //eps trans

    }
}


