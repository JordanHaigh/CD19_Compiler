package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a decl of the form:
 * <decl>	::=	<id>: <paramTypeTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NDeclNode implements Node{
    //	<decl>	::=	<id>: <paramTypeTail>
    NParamTypeTailNode nParamTypeTailNode;
    private static NDeclNode instance;

    public NDeclNode() {
        this(NParamTypeTailNode.INSTANCE());
    }

    public NDeclNode(NParamTypeTailNode nParamTypeTailNode) {
        this.nParamTypeTailNode = nParamTypeTailNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NDeclNode INSTANCE() {
        if (instance == null) {
            instance = new NDeclNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the decl node
     * @param parser The parser
     * @return A valid decl TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode decl = new TreeNode();

        Token token = parser.peek();

        if(!parser.peekAndConsume(Token.TIDEN)){
            parser.syntacticError("Expected an Identifier", parser.peek()); //todo recover
            return decl;
        }

        if(!parser.peekAndConsume(Token.TCOLN)){
            parser.syntacticError("Expected a Colon", parser.peek()); //todo recover
            return decl;
        }

        TreeNode paramTypeTail = nParamTypeTailNode.make(parser);

        if(paramTypeTail.getValue() == TreeNode.NUNDEF){
            //we cant get datatype or make the strec
            return decl;
        }

        NodeDataTypes dataType = paramTypeTail.getType();

        SymbolTableRecord record = new SymbolTableRecord(token.getStr(), dataType, parser.getScope());
        parser.insertIdentifierRecord(record);

        TreeNode returnTreeNode = new TreeNode(paramTypeTail.getValue(), record);
        return returnTreeNode;
    }


}
