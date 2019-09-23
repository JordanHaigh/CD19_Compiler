package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NDeclNode implements Node{
    //	<decl>	::=	<id>: <paramTypeTail>
    NParamTypeTailNode nParamTypeTailNode;

    public NDeclNode() {
        this(NParamTypeTailNode.INSTANCE());
    }

    public NDeclNode(NParamTypeTailNode nParamTypeTailNode) {
        this.nParamTypeTailNode = nParamTypeTailNode;
    }


    private static NDeclNode instance;
    public static NDeclNode INSTANCE() {
        if (instance == null) {
            instance = new NDeclNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        parser.peekAndConsume(Token.TIDEN);
        parser.peekAndConsume(Token.TCOLN);
        TreeNode paramTypeTail = nParamTypeTailNode.make(parser);
        NodeDataTypes dataType = paramTypeTail.getType();

        SymbolTableRecord record = new SymbolTableRecord(token.getStr(), dataType,"");
        parser.insertIdentifierRecord(record);

        TreeNode returnTreeNode = new TreeNode(paramTypeTail.getValue(), record); //todo this needs serious error checking
        return returnTreeNode;
    }


}
