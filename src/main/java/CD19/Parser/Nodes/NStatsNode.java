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
        //<stat> ;  <StatsTail>
        TreeNode stat = nStatNode.make(parser);
        parser.peekAndConsume(Token.TSEMI);
        TreeNode tail = tail(parser);

        if(tail == null)
            return stat;

        return new TreeNode(TreeNode.NSTATS, stat, tail);
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
        Token token = parser.peek();
        if(token.getTokenID() == Token.TREPT  || token.getTokenID() == Token.TIDEN
                || token.getTokenID() == Token.TINPT || token.getTokenID() == Token.TPRIN
                || token.getTokenID() == Token.TPRLN || token.getTokenID() == Token.TRETN
        ){
           TreeNode stat = nStatNode.make(parser);
           parser.peekAndConsume(Token.TSEMI);
           TreeNode tail = tail(parser);

           if(tail == null)
               return stat;
            return new TreeNode(TreeNode.NSTATS, stat, tail);
        }
        else if(token.getTokenID() == Token.TIFTH  || token.getTokenID() == Token.TFOR){
            TreeNode strstat = nStrStatNode.make(parser);
            TreeNode tail = tail(parser);

            if(tail == null)
                return strstat;
            return new TreeNode(TreeNode.NSTATS, strstat, tail);
        }
        else
            return null; //eps trans

    }
}


