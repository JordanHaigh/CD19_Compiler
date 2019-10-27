package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a funcbody of the form:
 * <funcbody>	::=	<locals> begin <stats> end
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NFuncBodyNode implements Node{
	//<funcbody>	::=	<locals> begin <stats> end

    NLocalsNode nLocalsNode;
    NStatsNode nStatsNode;
    private static NFuncBodyNode instance;


    public NFuncBodyNode() {
        this(NLocalsNode.INSTANCE(),null);
    }

    public NFuncBodyNode(NLocalsNode nLocalsNode, NStatsNode nStatsNode) {
        this.nLocalsNode = nLocalsNode;
        this.nStatsNode = nStatsNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NFuncBodyNode INSTANCE() {
        if (instance == null) {
            instance = new NFuncBodyNode();
        }
        return instance;
    }

    /**
     * Sets the nStatsNode in the class so cyclic constructors are prevented
     * @param nStatsNode - Node to set
     */
    public void setnStatsNode(NStatsNode nStatsNode) {
        this.nStatsNode = nStatsNode;
    }



    /**
     * Attempts to generate the funcbody node
     * @param parser The parser
     * @return A valid funcbody TreeNode or NUNDEF if syntactic error
     */
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
        int numberOfReturns = stats.getNumberOfReturns();
//        System.out.println("number of returns is" + numberOfReturns);

        Token peek = parser.peek();
        if(numberOfReturns == 0){
            parser.semanticError("Function has no return statements", peek);
        }

        funcBody.setRight(stats);

        if(!parser.peekAndConsume(Token.TEND)){
            parser.syntacticError("Expected an End Keyword", parser.peek());
            return funcBody;
        }


        return funcBody;

    }

    public TreeNode makeWithReturnType(Parser parser, TreeNode returnType){
        TreeNode funcBody = new TreeNode();

        TreeNode locals = nLocalsNode.make(parser);

        funcBody.setLeft(locals);

        if(!parser.peekAndConsume(Token.TBEGN)){
            parser.syntacticError("Expected a Begin Keyword", parser.peek());
            return funcBody;
        }

        TreeNode stats = nStatsNode.make(parser);
        int numberOfReturns = stats.getNumberOfReturns();
//        System.out.println("number of returns is" + numberOfReturns);

        Token peek = parser.peek();
        if(numberOfReturns == 0){
            parser.semanticError("Function has no return statements", peek);
        }

        funcBody.setRight(stats);

        if(!parser.peekAndConsume(Token.TEND)){
            parser.syntacticError("Expected an End Keyword", parser.peek());
            return funcBody;
        }


        return funcBody;



    }
}

