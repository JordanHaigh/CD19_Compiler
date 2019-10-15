package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a fact of the form:
 * NPOW	<fact>	::=	<exponent><factTail>
 *      <factTail>	::=	ε | ^<exponent><factTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NFactNode implements Node{

    //NPOW	<fact>	::=	<exponent><factTail>
    //	<factTail>	::=	ε | ^<exponent><factTail>

    NExponentNode nExponentNode;
    private static NFactNode instance;

    public NFactNode() {
        this(NExponentNode.INSTANCE());
    }

    public NFactNode(NExponentNode nExponentNode) {
        this.nExponentNode = nExponentNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NFactNode INSTANCE() {
        if (instance == null) {
            instance = new NFactNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the fact node
     * @param parser The parser
     * @return A valid fact TreeNode (may contain NUNDEF children)
     */
    @Override
    public TreeNode make(Parser parser) {
        Token peek = parser.peek();
        TreeNode exponent = nExponentNode.make(parser);
        TreeNode tail = tail(parser,exponent);

        //left derivation
        if(tail == null)
            return exponent;

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
     * @param leftNode Node to make the tree left derived
     * @return - Null if there are no subsequent fact nodes, or a TreeNode containing tailing fact nodes
     */
    private TreeNode tail(Parser parser, TreeNode leftNode){
        if(parser.peekAndConsume(Token.TCART)){
            TreeNode returnTreeNode = new TreeNode(TreeNode.NPOW);
            returnTreeNode.setLeft(leftNode);

            TreeNode fact = nExponentNode.make(parser);
            returnTreeNode.setRight(fact);

            String firstType = returnTreeNode.getLeft().getType();
            String secondType = returnTreeNode.getRight().getType();

            returnTreeNode.updateType(firstType,secondType);

            TreeNode tail = tail(parser,returnTreeNode);


            if(tail == null){
                //returnTreeNode.updateType(firstType,secondType);
                return returnTreeNode;
            }
            else{
                //.updateType(firstType, secondType);
                return tail;
            }
        }
        else //eps trans
            return null;

    }

}

