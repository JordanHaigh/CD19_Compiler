package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
/**
 * Generates a paramtypetail of the form:
 * <paramTypeTail>	::=	<stype> | <typeid>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NParamTypeTailNode implements Node {
    //	<paramTypeTail>	::=	<stype> | <typeid> //todo struct or primitive

    NSTypeNode nsTypeNode;
    private static NParamTypeTailNode instance;

    public NParamTypeTailNode() {
        this(new NSTypeNode());
    }

    public NParamTypeTailNode(NSTypeNode nsTypeNode) {
        this.nsTypeNode = nsTypeNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */

    public static NParamTypeTailNode INSTANCE() {
        if (instance == null) {
            instance = new NParamTypeTailNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the paramtypetail node
     * @param parser The parser
     * @return A valid paramtypetail TreeNode or NUNDEF if syntactic error
     */

    @Override
    public TreeNode make(Parser parser) {
        Token type = parser.peek();
        if(type.getTokenID() == Token.TINTG ||
            type.getTokenID() == Token.TREAL ||
            type.getTokenID() == Token.TBOOL)
        {
            return nsTypeNode.make(parser);
        }
        else if(parser.peekAndConsume(Token.TIDEN)){

            SymbolTableRecord typeIdRecord = new SymbolTableRecord(type.getStr(), null, parser.getProgramScope());//typeid is always global scope

            if(parser.lookupIdentifierRecord(typeIdRecord) == null){
                parser.semanticError("Array Identifier "+type.getStr()+" doesn't exist in symbol table", type);
            }

            TreeNode dummy = new TreeNode(TreeNode.NARRD);
            dummy.setType(type.getStr()+"Array");
            return dummy;
        }
        else{
            //error
            parser.syntacticError("Expected a <stype> | <typeid>", type.getLine(),type.getCol());
            return new TreeNode(TreeNode.NUNDEF);
        }

    }


}
