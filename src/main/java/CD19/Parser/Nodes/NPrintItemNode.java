package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a printitem of the form:
 * NSTRG	<printitem>	::=	<expr> | <string>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NPrintItemNode implements Node{
    //NSTRG	<printitem>	::=	<expr> | <string>
    NExprNode nExprNode;
    private static NPrintItemNode instance;

    public NPrintItemNode() {
        this(null);
    }

    public NPrintItemNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NPrintItemNode INSTANCE() {
        if (instance == null) {
            instance = new NPrintItemNode();
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
     * Attempts to generate the printitem node
     * @param parser The parser
     * @return A valid printitem TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode printitem = new TreeNode();

        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN || //expr check
            token.getTokenID() == Token.TILIT ||
            token.getTokenID() == Token.TFLIT ||
            token.getTokenID() == Token.TTRUE ||
            token.getTokenID() == Token.TFALS ||
            token.getTokenID() == Token.TLPAR
            ){
            printitem = nExprNode.make(parser);
            return printitem;
        }
        else if(token.getTokenID() == Token.TSTRG){ //return string
            parser.consume(); //consume string token. we done with it
            SymbolTableRecord checker = new SymbolTableRecord(token.getStr(), "String", ""); //scope not relevant
            SymbolTableRecord record = parser.lookupConstantRecord(checker);
            if(record == null){
                parser.insertConstantRecord(checker);
                printitem = new TreeNode(TreeNode.NSTRG,checker);
                return printitem;
            }
            else{
                printitem = new TreeNode(TreeNode.NSTRG,record);
                return printitem;
            }
        }
        else{
            parser.syntacticError("Expected a valid PrintItem Token", parser.peek());
            return printitem;
        }
    }
}
