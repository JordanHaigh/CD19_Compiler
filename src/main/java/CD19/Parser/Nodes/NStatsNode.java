package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a stats of the form:
 * <stats>	::=	<arrstats> <arrstatsTail>
 * <statsTail>	::=	eps |  , <arrstats> <arrstatsTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NStatsNode implements Node {
    //NSTATS	<stats>	::=	<stat> ;  <StatsTail> | <strstat> end <statsTail>
    //<statsTail>	::=	 eps |  {<stat> ;  <StatsTail> | <strstat> <statsTail>}

    NStatNode nStatNode;
    NStrStatNode nStrStatNode;
    private static NStatsNode instance;

    public NStatsNode() {
        this(NStatNode.INSTANCE(), NStrStatNode.INSTANCE());
    }

    public NStatsNode(NStatNode nStatNode, NStrStatNode nStrStatNode) {
        this.nStatNode = nStatNode;
        this.nStrStatNode = nStrStatNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NStatsNode INSTANCE() {
        if (instance == null) {
            instance = new NStatsNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the stats node
     * @param parser The parser
     * @return A valid stats TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if (token.getTokenID() == Token.TFOR || token.getTokenID() == Token.TIFTH) {
            return strstatPath(parser);
        } else if(token.getTokenID() == Token.TREPT || token.getTokenID() == Token.TIDEN
                || token.getTokenID() == Token.TINPT || token.getTokenID() == Token.TPRIN
                || token.getTokenID() == Token.TPRLN || token.getTokenID() == Token.TRETN) {
            return statSemiColonPath(parser);
        }else{
            return null;
        }
    }

    /**
     * Attempts to generate the stats node following the stat path
     * @param parser The parser
     * @return A valid stat treeNode with tail information
     */
    private TreeNode statSemiColonPath(Parser parser) {
        TreeNode stats;

        //<stat> ;  <StatsTail>

        TreeNode stat = nStatNode.make(parser);

        if (!parser.peekAndConsume(Token.TSEMI)) {
            parser.syntacticError("Expected a Semicolon. Parser will perform Panic Mode Recovery to the next semi colon", parser.peek());
            errorRecovery_stat(parser); //error recovery to next semi
        }

        TreeNode tail = tail(parser);

        if (tail == null)
            return stat;

        stats = new TreeNode(TreeNode.NSTATS, stat, tail);
        return stats;
    }

    /**
     * Attempts to generate the stats node following the strstat path
     * @param parser The parser
     * @return A valid strstat treeNode with tail information
     */
    private TreeNode strstatPath(Parser parser) {
        //<strstat> <statsTail>
        TreeNode strstat = nStrStatNode.make(parser);

        if (!parser.peekAndConsume(Token.TEND)) {
            parser.syntacticError("Expected an end Keyword. Parser will perform Panic Mode Recovery to the next end", parser.peek());
            errorRecovery_strstat(parser); //error recovery to next semi
        }

        TreeNode tail = tail(parser);

        if (tail == null)
            return strstat;
        return new TreeNode(TreeNode.NSTATS, strstat, tail);

    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent stats nodes, or a TreeNode containing tailing stats nodes
     */
    private TreeNode tail(Parser parser) {
        //	//<statsTail>	::=	 eps |  {<stat> ;  <StatsTail> | <strstat> <statsTail>}
        TreeNode stats = new TreeNode();

        Token token = parser.peek();
        if (token.getTokenID() == Token.TREPT || token.getTokenID() == Token.TIDEN
                || token.getTokenID() == Token.TINPT || token.getTokenID() == Token.TPRIN
                || token.getTokenID() == Token.TPRLN || token.getTokenID() == Token.TRETN
        ) {
            return statSemiColonPath(parser);
        } else if (token.getTokenID() == Token.TIFTH || token.getTokenID() == Token.TFOR) {
            return strstatPath(parser);
        } else
            return null; //eps trans

    }


    /**
     * Recover from error by searching for the next semi colon or until we are out of tokens (TEOF)
     * @param parser The parser
     */
    private void errorRecovery_stat(Parser parser) {
        while (parser.peek().getTokenID() != Token.TSEMI && parser.peek().getTokenID() != Token.TEOF) {
            parser.consume(); //PANIC MODE!!! PANIC MODE!!! THROW FECES AT THE WALLS WE ARE IN A CRISIS
        }

        //oh wait we good now
        if (parser.peek().getTokenID() != Token.TEOF) {

            //then we've seen a semi. consume it.
            parser.consume();
        } else {
            throw new IllegalStateException("Out of Tokens whilst still trying to recover from previous error");
        }
    }

    /**
     * Recover from error by searching for the next "end" keyword or until we are out of tokens (TEOF)
     * @param parser The parser
     */
    private void errorRecovery_strstat(Parser parser) {
        while (parser.peek().getTokenID() != Token.TEND && parser.peek().getTokenID() != Token.TEOF) {
            parser.consume(); //PANIC MODE!!! PANIC MODE!!! THROW FECES AT THE WALLS WE ARE IN A CRISIS
        }

        //oh wait we good now
        if (parser.peek().getTokenID() != Token.TEOF) {
            //then we've seen a end. consume it.
            parser.consume();
        } else {
            throw new IllegalStateException("Out of Tokens whilst still trying to recover from previous error");
        }
    }
}


