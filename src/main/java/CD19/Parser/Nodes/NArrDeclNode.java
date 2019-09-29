package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NArrDeclNode implements Node {
    //NARRD	<arrdecl>	::=	<id> : <typeid>

    private static NArrDeclNode instance;

    public static NArrDeclNode INSTANCE() {
        if (instance == null) {
            instance = new NArrDeclNode();
        }
        return instance;
    }

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

        Token type = parser.peek(); //todo type isnt being set?

        if (!parser.peekAndConsume(Token.TIDEN)) {
            parser.syntacticError("Expected an Identifier", id);
            return arrdecl;
        }

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), NodeDataTypes.Array, parser.getScope());
        parser.insertTypeRecord(record);

        arrdecl.setValue(TreeNode.NARRD);
        arrdecl.setSymbol(record);
        return arrdecl;


    }
}
