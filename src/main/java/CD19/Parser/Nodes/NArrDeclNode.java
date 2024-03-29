package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
/**
 * Jordan Haigh c3256730 CD19
 * Generates an arrdecl of the form:
 * NARRD	<arrdecl>	::=	<id> : <typeid>
 */
public class NArrDeclNode implements Node {
    //NARRD	<arrdecl>	::=	<id> : <typeid>

    private static NArrDeclNode instance;

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */

    public static NArrDeclNode INSTANCE() {
        if (instance == null) {
            instance = new NArrDeclNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the arrdecl node
     * @param parser The parser
     * @return A valid arrdecl TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode arrdecl = new TreeNode();

        Token id = parser.peek();
        if (!parser.peekAndConsume(Token.TIDEN)) {
            parser.syntacticError("Expected an Identifier", id);
            return arrdecl;
        }

        if (!parser.peekAndConsume(Token.TCOLN)) {
            parser.syntacticError("Expected a colon", parser.peek());
            return arrdecl;
        }

        Token typeId = parser.peek();

        if (!parser.peekAndConsume(Token.TIDEN)) {
            parser.syntacticError("Expected an Identifier", id);
            return arrdecl;
        }

        SymbolTableRecord typeIdRecord = new SymbolTableRecord(typeId.getStr(), null, parser.getProgramScope());//typeid is always global scope
        typeIdRecord = parser.lookupIdentifierRecord(typeIdRecord);

        //weird edge case for when trying to access array struct elements - don't start on me this is already a heachache
        String appendForDataType = "";
        if( typeIdRecord == null){
            parser.semanticError("Type Id "+typeId.getStr()+" doesn't exist", typeId);
        }
        else{
            appendForDataType += "Is"+typeIdRecord.getDataType();
        }

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), typeId.getStr()+appendForDataType, parser.getScope());
        boolean insertSuccessful = parser.insertTypeRecord(record);
        if(!insertSuccessful){
            parser.semanticError("Array variable already exists", id);
        }

        arrdecl.setValue(TreeNode.NARRD);
        arrdecl.setSymbol(record);
        return arrdecl;


    }
}
