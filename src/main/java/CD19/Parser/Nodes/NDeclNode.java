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
            return decl; //todo is this right?
        }

        NodeDataTypes dataType = paramTypeTail.getType();

        SymbolTableRecord record = new SymbolTableRecord(token.getStr(), dataType, parser.getScope());
        parser.insertIdentifierRecord(record);

        TreeNode returnTreeNode = new TreeNode(paramTypeTail.getValue(), record);
        return returnTreeNode;
    }


}
