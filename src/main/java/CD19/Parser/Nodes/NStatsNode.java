package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NStatsNode implements Node{
    //NSTATS	<stats>	::=	<stat> ;  <StatsTail> | <strstat> end <statsTail>
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

        if(stat.getValue() == TreeNode.NUNDEF){
            errorRecovery_stat(parser); //error recovery to next semi
        }

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

        if(strstat.getValue() == TreeNode.NUNDEF){
            errorRecovery_strstat(parser);
        }

        if (!parser.peekAndConsume(Token.TEND)) {
            parser.syntacticError("Expected End Keyword", parser.peek());
            return new TreeNode();
        }

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


    private void errorRecovery_stat(Parser parser){
        while(parser.peek().getTokenID() != Token.TSEMI && !parser.outOfTokens()){
            parser.consume(); //PANIC MODE!!! PANIC MODE!!! THROW FECES AT THE WALLS WE ARE IN A CRISIS
        }

        //oh wait we good now
        if(!parser.outOfTokens()){
            //then we've seen a semi. consume it.
            parser.consume();
        }
        else{
            //todo idk..
        }
    }

    private void errorRecovery_strstat(Parser parser){
        while(parser.peek().getTokenID() != Token.TEND && !parser.outOfTokens()){
            parser.consume(); //PANIC MODE!!! PANIC MODE!!! THROW FECES AT THE WALLS WE ARE IN A CRISIS
        }

        //oh wait we good now
        if(!parser.outOfTokens()){
            //then we've seen a semi. consume it.
            parser.consume();
        }
        else{
            //todo idk..
        }
    }
}


