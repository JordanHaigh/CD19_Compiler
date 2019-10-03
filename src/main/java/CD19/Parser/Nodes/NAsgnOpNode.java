package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates an asgnop of the form:
 *  <asgnop>	::=	 = | += | -= | *= | /=
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NAsgnOpNode implements Node{
    //NASGN, NPLEQ, NMNEQ, NSTEQ, NDVEQ	<asgnop>	::=	 = | += | -= | *= | /=

    private static NAsgnOpNode instance;

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NAsgnOpNode INSTANCE() {
        if (instance == null) {
            instance = new NAsgnOpNode();
        }
        return instance;
    }


    /**
     * Attempts to generate the asgnop node
     * @param parser The parser
     * @return A valid asgnop TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TEQUL)) //=
            return new TreeNode(TreeNode.NASGN);
        else if(parser.peekAndConsume(Token.TPLEQ)) //+=
            return new TreeNode(TreeNode.NPLEQ);
        else if(parser.peekAndConsume(Token.TMNEQ)) //-=
            return new TreeNode(TreeNode.NMNEQ);
        else if(parser.peekAndConsume(Token.TSTEQ)) //*=
            return new TreeNode(TreeNode.NSTEQ);
        else if(parser.peekAndConsume(Token.TDVEQ)) // /=
            return new TreeNode(TreeNode.NDVEQ);
        else{
            parser.syntacticError("Expected an AsgnOp", parser.peek());
            return new TreeNode();
        }






    }
}
