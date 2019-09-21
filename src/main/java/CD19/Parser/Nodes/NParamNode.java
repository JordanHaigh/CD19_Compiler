package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NParamNode implements Node{
    //NSIMP | NARRP | NARRC	<param>	::=	<id> : <paramTypeTail> | const <arrdecl>
    //	<paramTypeTail>	::=	<stype> | <typeid>

    NParamTypeTailNode nParamTypeTailNode;

    public NParamNode() {
        this(NParamTypeTailNode.INSTANCE());
    }

    public NParamNode(NParamTypeTailNode nParamTypeTailNode) {
        this.nParamTypeTailNode = nParamTypeTailNode;
    }

    private static NParamNode instance;
    public static NParamNode INSTANCE() {
        if (instance == null) {
            instance = new NParamNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TCNST){
            return constPath(parser);
        }
        else{
            return sTypeOrTypeIdPath(parser);
        }
    }

    private TreeNode constPath(Parser parser){
        //const <arrdecl>
        NArrDeclNode nArrDeclNode = new NArrDeclNode();

        parser.peekAndConsume(Token.TCNST);
        TreeNode arrdecl =  nArrDeclNode.make(parser);
        SymbolTableRecord record = new SymbolTableRecord(arrdecl.getSymbol().getLexeme(), NodeDataTypes.Array);
        return new TreeNode(TreeNode.NARRC, record);

    }

    private TreeNode sTypeOrTypeIdPath(Parser parser){
        //<id> : <paramTypeTail>
        Token token  = parser.peek();
        parser.consume();

        parser.peekAndConsume(Token.TSEMI);

        TreeNode tail = nParamTypeTailNode.make(parser);

        SymbolTableRecord record = new SymbolTableRecord(token.getStr(), tail.getType());

        if(tail.getType().equals(NodeDataTypes.Array)){
            return new TreeNode(TreeNode.NARRP, record);
        }
        else{
            return new TreeNode(TreeNode.NSIMP, record);
        }
    }





}
