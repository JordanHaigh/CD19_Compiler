package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NArrDeclNode implements Node{
    //NARRD	<arrdecl>	::=	<id> : <typeid>

    @Override
    public TreeNode make(Parser parser) {
        Token id = parser.peek();
        if(id.getTokenID() == Token.TIDEN){
            parser.peekAndConsume(Token.TSEMI);
            Token type = parser.peek();
            if(type.getTokenID() == Token.TIDEN){
                SymbolTableRecord record = new SymbolTableRecord(id.getStr(),NodeDataTypes.Array);
                TreeNode treeNode = new TreeNode(TreeNode.NARRD, record);
                return treeNode;
            }
        }


        return null; //todo should error

    }
}
