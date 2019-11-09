package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a term of the form:
 * NMUL | NDIV | NMOD	<term>	::=	<fact><termTail>
 *  <termTail>	::=	eps | *<fact><termTail> | /<fact><termTail> | %<fact><termTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NTermNode implements Node{
    //NMUL | NDIV | NMOD	<term>	::=	<fact><termTail>
    //	<termTail	::=	ε | *<fact><termTail> | /<fact><termTail> | %<fact><termTail>

    NFactNode nFactNode;
    private static NTermNode instance;


    public NTermNode() {
        this(NFactNode.INSTANCE());
    }

    public NTermNode(NFactNode nFactNode) {
        this.nFactNode = nFactNode;
    }
    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NTermNode INSTANCE() {
        if (instance == null) {
            instance = new NTermNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the term node
     * @param parser The parser
     * @return A valid term TreeNode
     */
    @Override
    public TreeNode make(Parser parser) {
        Token peek = parser.peek();

        TreeNode fact = nFactNode.make(parser);
        TreeNode tail = tail(parser, fact);

        if(tail == null){
            return fact;
        }

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
        //	<termTail	::=	ε | *<term> | /<term> | %<term>
        if(parser.peekAndConsume(Token.TSTAR)){
            return buildLeftDerivedTree(parser, TreeNode.NMUL, leftNode);
        }
        else if(parser.peekAndConsume(Token.TDIVD)){
            return buildLeftDerivedTree(parser, TreeNode.NDIV, leftNode);
        }
        else if(parser.peekAndConsume(Token.TPERC)){
            return buildLeftDerivedTree(parser, TreeNode.NMOD, leftNode);
        }
        else
            return null; //epsilon transition
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

        Token peek = parser.peek();

        TreeNode fact = nFactNode.make(parser);
        returnTreeNode.setRight(fact);

        String firstType = getDataTypeOfNode(returnTreeNode.getLeft());
        String secondType = getDataTypeOfNode(returnTreeNode.getRight());

        if(expectedTreeNodeValue == TreeNode.NMOD){
            if((firstType.equals("Integer") && secondType.equals("Real")) || (firstType.equals("Real") && secondType.equals("Integer"))){
                parser.semanticError("Cannot complete modulo operation on real", peek);
            }
        }

        returnTreeNode.updateType(firstType,secondType);

        TreeNode tail = tail(parser, returnTreeNode);

//        String firstType = returnTreeNode.getLeft().getType();
//        String secondType = fact.getType();

        if(tail == null){
           // returnTreeNode.updateType(firstType,secondType);
            return returnTreeNode;
        }
        else{
            //tail.updateType(firstType, secondType);
            return tail;
        }
    }

    private String getDataTypeOfNode(TreeNode node){
        if(node.getValue() == TreeNode.NSIMV){
            //its a variable
            //get symbol table record data type
            return node.getSymbol().getDataType();
        }
        else{
            //probs a number or boolean
            return node.getType();
        }
    }
}


