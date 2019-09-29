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
        TreeNode param = new TreeNode();

        Token token = parser.peek();
        if(parser.peekAndConsume(Token.TCNST)){
            return constPath(parser);
        }
        else if(token.getTokenID() == Token.TIDEN){ //don't consume it yet
            return sTypeOrTypeIdPath(parser);
        }
        else{
            parser.syntacticError("Expected Const Keyword or Identifier for param", parser.peek());
            return param;
        }
    }

    private TreeNode constPath(Parser parser){
        //const <arrdecl>
        //already consumed const

        NArrDeclNode nArrDeclNode = new NArrDeclNode();
        TreeNode arrdecl =  nArrDeclNode.make(parser); //get narrd

        TreeNode returnTreeNode = new TreeNode(TreeNode.NARRC, arrdecl, null);

        SymbolTableRecord constRecord = arrdecl.getSymbol();
        parser.insertConstantRecord(constRecord);

        return returnTreeNode; //map to narrc

    }

    private TreeNode sTypeOrTypeIdPath(Parser parser){
        //<id> : <paramTypeTail>
        Token id  = parser.peek();
        parser.consume(); //already know its an ident, so its cool

        if(!parser.peekAndConsume(Token.TCOLN)){
            parser.syntacticError("Expected a Colon", parser.peek());
            return new TreeNode();
        }

        TreeNode tail = nParamTypeTailNode.make(parser);

        if(tail.getValue() == TreeNode.NUNDEF){
            return new TreeNode(); //todo check this.
        }

        //since we LL(1), we already have the id of the variable, but we need to call param type tail to get the data type
        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), tail.getType(),id.getStr()+"_"+parser.getScope());

        parser.insertIdentifierRecord(record);

        if(tail.getValue() == TreeNode.NARRD){
            TreeNode returnTreeNode = new TreeNode(TreeNode.NARRP, tail, null);
            returnTreeNode.setSymbol(record);
            return returnTreeNode;
        }
        else{
            TreeNode returnTreeNode = new TreeNode(TreeNode.NSIMP, tail, null);
            returnTreeNode.setSymbol(record);
            return returnTreeNode;
        }
    }





}
