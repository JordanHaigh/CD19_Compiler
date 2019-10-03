package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a init of the form:
 * NINIT	<init>	::=	<id> = <expr>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NInitNode implements Node{

    //NINIT	<init>	::=	<id> = <expr>
    NExprNode nExprNode;
    private static NInitNode instance;


    public NInitNode(){
        this(null);
    }

    public NInitNode(NExprNode nExprNode){
        this.nExprNode = nExprNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NInitNode INSTANCE() {
        if (instance == null) {
            instance = new NInitNode();
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
     * Attempts to generate the init node
     * @param parser The parser
     * @return A valid init TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode init = new TreeNode();

        Token id = parser.peek(); //id
        if(!parser.peekAndConsume(Token.TIDEN)){
            parser.syntacticError("Expected an Identifier", parser.peek());
            return init;
        }

        if(!parser.peekAndConsume(Token.TEQUL)){
            parser.syntacticError("Expected Equals Sign", parser.peek());
            return init;
        }

        TreeNode exprTreeNode = nExprNode.make(parser);

        //we need a working expr node from here on, check if its nundef
        if(exprTreeNode.getValue() == TreeNode.NUNDEF){
            return init;
        }

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(),exprTreeNode.getType(),parser.getScope()); //global (program) scope
        parser.insertConstantRecord(record);

        return new TreeNode(TreeNode.NINIT, record);

    }
}


