package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a rel of the form:
 * NNOT	<rel>	::=	<expr><relExprTail> | not <expr> <relExprTail>
 * 	<relExprTail>	::=	eps | <relop><expr>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NRelNode implements Node{
//NNOT	<rel>	::=	<expr><relExprTail> | not <expr> <relExprTail>
//	<relExprTail>	::=	ε | <relop><expr>

    NExprNode nExprNode;
    NRelopNode nRelopNode;
    private static NRelNode instance;


    public NRelNode() {
        this(null, NRelopNode.INSTANCE());
    }

    public NRelNode(NExprNode nExprNode, NRelopNode nRelopNode) {
        this.nExprNode = nExprNode;
        this.nRelopNode = nRelopNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NRelNode INSTANCE() {
        if (instance == null) {
            instance = new NRelNode();
        }
        return instance;
    }

    /**
     * Sets the nExprNode in the class so cyclic constructors are prevented
     * @param nExprNode - Node to set
     */
    public void setnExprNode(NExprNode nExprNode) {
        this.nExprNode= nExprNode;
    }


    /**
     * Attempts to generate the rel node
     * @param parser The parser
     * @return A valid rel TreeNode
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TNOT){
            return notExprPath(parser);
        }
        else{
            return exprPath(parser);
        }
    }

    /**
     * Attempts to generate the expr path of the rel node
     * @param parser The parser
     * @return A valid rel TreeNode
     */
    private TreeNode exprPath(Parser parser){
        //<expr><relExprTail>
        TreeNode expr = nExprNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail != null){
            tail.setLeft(expr);
            return tail;
        }
        return expr;

    }

    /**
     * Attempts to generate the NOT expr path of the rel node
     * @param parser The parser
     * @return A valid rel TreeNode
     */
    private TreeNode notExprPath(Parser parser){
        // not <expr> <relExprTail>
        parser.peekAndConsume(Token.TNOT);

        Token peek = parser.peek();

        TreeNode expr= nExprNode.make(parser);
        TreeNode tail = tail(parser);

        if(tail != null){
            tail.setLeft(expr);
            TreeNode returnTreeNode = new TreeNode(TreeNode.NNOT, tail,null);
            return returnTreeNode;
        }

        String idDataType;
        if(expr.getValue() == TreeNode.NSIMV){
            idDataType = expr.getSymbol().getDataType();
        }
        else{
            idDataType = expr.getType();
        }

        if(!idDataType.equals("Boolean")){
            parser.semanticError("Expected a boolean expression",peek);
        }


        return new TreeNode(TreeNode.NNOT, expr,null);
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent rel nodes, or a TreeNode containing tailing rel nodes
     */
    private TreeNode tail(Parser parser){
        ////	<relExprTail>	::=	ε | <relop><expr>
        Token token = parser.peek();
        if(token.getTokenID() == Token.TEQEQ ||
                token.getTokenID() == Token.TNEQL ||
                token.getTokenID() == Token.TGRTR ||
                token.getTokenID() == Token.TGEQL ||
                token.getTokenID() == Token.TLESS ||
                token.getTokenID() == Token.TLEQL){


            TreeNode relop = nRelopNode.make(parser);
            TreeNode expr = nExprNode.make(parser);
            return new TreeNode(relop.getValue(), relop, expr);
        }
        else{
            return null; //epsilon
        }
    }


}

