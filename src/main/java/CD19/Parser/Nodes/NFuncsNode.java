package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a funcs of the form:
 * NFUNCS	<funcs>	::=	eps | <func> <funcs>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NFuncsNode implements Node {
    //NFUNCS	<funcs>	::=	eps | <func> <funcs>

    NFuncNode nFuncNode;
    private static NFuncsNode instance;


    public NFuncsNode(){
        this(NFuncNode.INSTANCE());
    }

    public NFuncsNode(NFuncNode nFuncNode){
        this.nFuncNode = nFuncNode;
    }


    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NFuncsNode INSTANCE() {
        if (instance == null) {
            instance = new NFuncsNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the funcs node
     * @param parser The parser
     * @return A valid funcs TreeNode or null (if epsilon)
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TFUNC){
            TreeNode func = nFuncNode.make(parser);

            TreeNode funcs = this.make(parser);
            if(funcs == null)
                return func;

            return new TreeNode(TreeNode.NFUNCS, func, funcs);
        }
        else //eps transition
            return null;
    }

}

