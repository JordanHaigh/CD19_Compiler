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
            if(tail.getType().equals("Mixed")){
                parser.semanticError("Mixed variable types in expression", peek);
            }
            if(tail.getType().equals("Boolean")){
                parser.semanticError("Cannot use booleans in math operators", peek);
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
        returnTreeNode.setRight(fact);

        String firstType = getDataTypeOfNode(returnTreeNode.getLeft());
        String secondType = getDataTypeOfNode(returnTreeNode.getRight());

        returnTreeNode.updateType(firstType,secondType);

        TreeNode tail = tail(parser, returnTreeNode);

        if(tail == null){
            return returnTreeNode;
        }
        else{
            return tail;
        }

    }

    private String getDataTypeOfNode(TreeNode node){
        if(node.getValue() == TreeNode.NSIMV){
            //its a variable
            //get symbol table record data type
            if(node.getSymbol() != null){
                return node.getSymbol().getDataType();
            }else{
                return null;
            }
        }
        else{
            //probs a number or boolean
            return node.getType();
        }
    }

}

