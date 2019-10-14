package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates an expr of the form:
 * NADD | NSUB	<expr>	::=	<term><exprTail>
 * <exprTail>	::=	ε | +<term><exprTail> | -<term><exprTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NExprNode implements Node{
    //NADD | NSUB	<expr>	::=	<term><exprTail>
	//<exprTail>	::=	ε | +<term><exprTail> | -<term><exprTail>
    NTermNode nTermNode;
    private static NExprNode instance;


    public NExprNode() {
        this(NTermNode.INSTANCE());
    }

    public NExprNode(NTermNode nTermNode) {
        this.nTermNode = nTermNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NExprNode INSTANCE() {
        if (instance == null) {
            instance = new NExprNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the expr node. Builds left derived tree structure
     * @param parser The parser
     * @return A valid expr TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        Token peek = parser.peek();

        TreeNode term = nTermNode.make(parser);
        TreeNode tail = tail(parser,term);

        if(tail == null)
            return term;

        if(tail.getType() != null){
            if(tail.getType().equals("Boolean") || tail.getType().equals("Mixed")){
                parser.semanticError("Cannot use booleans in expression", peek);
            }
        }

        return tail;
    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @param leftNode - Left Node to pass around and make tree left derived
     * @return - Null if there are no subsequent expr nodes, or a TreeNode containing tailing expr nodes
     */
    private TreeNode tail(Parser parser, TreeNode leftNode){
        //<exprTail>	::=	ε | +<expr> | -<expr>
        if(parser.peekAndConsume(Token.TPLUS)){
            return buildLeftDerivedTree(parser, TreeNode.NADD, leftNode);
        }
        else if(parser.peekAndConsume(Token.TMINS)){
            return buildLeftDerivedTree(parser, TreeNode.NSUB, leftNode);
        }
        else
            return null; //eps
    }

    /**
     * Builds tree in left derived form (good for code gen later on)
     * @param parser the Parser
     * @param expectedTreeNodeValue TreeNode value that will be the end result
     * @param leftNode - Left Node to pass around and make tree left derived
     * @return Left Derived TreeNode
     */
    private TreeNode buildLeftDerivedTree(Parser parser, int expectedTreeNodeValue, TreeNode leftNode){
        TreeNode returnTreeNode = new TreeNode(expectedTreeNodeValue);
        returnTreeNode.setLeft(leftNode);

        TreeNode fact = nTermNode.make(parser);
        TreeNode tail = tail(parser, returnTreeNode);

        returnTreeNode.setRight(fact);

        String firstType = returnTreeNode.getLeft().getType();
        String secondType = fact.getType();

        if(tail == null){
            returnTreeNode.updateType(firstType,secondType);
            return returnTreeNode;
        }
        else{
            tail.updateType(firstType, secondType);
            return tail;
        }

    }

}

