package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NPrintItemNode implements Node{
    //NSTRG	<printitem>	::=	<expr> | <string>
    NExprNode nExprNode;

    public NPrintItemNode() {
        this(null);
    }

    public NPrintItemNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    private static NPrintItemNode instance;
    public static NPrintItemNode INSTANCE() {
        if (instance == null) {
            instance = new NPrintItemNode();
        }
        return instance;
    }


    public void setnExprNode(NExprNode nExprNode) {
        this.nExprNode= nExprNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN || //expr check
            token.getTokenID() == Token.TILIT ||
            token.getTokenID() == Token.TFLIT ||
            token.getTokenID() == Token.TTRUE ||
            token.getTokenID() == Token.TFALS ||
            token.getTokenID() == Token.TLPAR
            ){
            return nExprNode.make(parser);
        }
        else{ //return string
            parser.consume(); //consume string token. we done with it
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(), NodeDataTypes.String, token.getStr()+"_"+parser.getScope());
            return new TreeNode(TreeNode.NSTRG,record);
        }
    }
}
