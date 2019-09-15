package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NDeclNode implements Node{
    //	<decl>	::=	<id>: <paramTypeTail>
    NParamTypeTailNode nParamTypeTailNode;

    public NDeclNode() {
        this(new NParamTypeTailNode());
    }

    public NDeclNode(NParamTypeTailNode nParamTypeTailNode) {
        this.nParamTypeTailNode = nParamTypeTailNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        parser.consume();

        parser.peekAndConsume(Token.TSEMI);
        TreeNode paramTypeTail = nParamTypeTailNode.make(parser);
        NodeDataTypes dataType = paramTypeTail.getType();


        SymbolTableRecord record = new SymbolTableRecord(token.getStr(), dataType);
        return new TreeNode(TreeNode.NUNDEF, record);
    }


}
