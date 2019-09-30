package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NExponentNode implements Node{

//NILIT | NFLIT | NTRUE | NFALS	   <exponent>	::=	<varorfncall> |  <intlit> | <reallit>  | TRUE | FALSE | (<bool>)
//  <varorfncall> ::= <id> <varOrFNCallTail>
//	<varOrFNCallTail>	::=	<varTail> | <fnCallTail>
//	<fncallTail>	::=	( <fnCallElistTail>)
    NBoolNode nBoolNode;
    NVarTailNode nVarTailNode;
    NEListNode nEListNode;

    public NExponentNode() {
        this(null, NVarTailNode.INSTANCE(), NEListNode.INSTANCE());
    }

    public NExponentNode(NBoolNode nBoolNode, NVarTailNode nVarTailNode, NEListNode neListNode) {
        this.nBoolNode = nBoolNode;
        this.nVarTailNode = nVarTailNode;
        this.nEListNode = neListNode;
    }

    private static NExponentNode instance;
    public static NExponentNode INSTANCE() {
        if (instance == null) {
            instance = new NExponentNode();
        }
        return instance;
    }

    public void setnBoolNode(NBoolNode boolNode) {
        this.nBoolNode = boolNode;
    }

    public void setnVarTailNode(NVarTailNode varTailNode) {
        this.nVarTailNode = varTailNode;
    }

    public void setnEListNode(NEListNode elistNode) {
        this.nEListNode = elistNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peek().getTokenID() == Token.TIDEN){
            return varOrFnCall(parser);
        }
        else if(parser.peekAndConsume(Token.TILIT)){
            return new TreeNode(TreeNode.NILIT, null,null);
        }
        else if(parser.peekAndConsume(Token.TFLIT)){
            return new TreeNode(TreeNode.NFLIT, null,null);
        }
        else if(parser.peekAndConsume(Token.TTRUE)){
            return new TreeNode(TreeNode.NTRUE, null,null);
        }
        else if(parser.peekAndConsume(Token.TFALS)){
            return new TreeNode(TreeNode.NFALS, null,null);
        }
        else if(parser.peekAndConsume(Token.TLPAR)){
            TreeNode bool = nBoolNode.make(parser);
            parser.peekAndConsume(Token.TRPAR);
            return bool;
        }
        else{
            parser.syntacticError("Expected a valid Exponent starting token", parser.peek());//todo recover
            return new TreeNode();

        }
    }

    private TreeNode varOrFnCall(Parser parser){
        Token id = parser.peek();
        parser.peekAndConsume(Token.TIDEN); //already seen thats its an iden

        TreeNode tail = varOrFnCallTail(parser);

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), tail.getType(), id.getStr()+"_"+parser.getScope());
        tail.setSymbol(record);
        return tail;
    }

    private TreeNode varOrFnCallTail(Parser parser){
        //	<varOrFNCallTail>	::=	<varTail> | <fnCallTail>
        Token token = parser.peek();
        if(token.getTokenID() == Token.TLPAR) {
            //todo semantic check it exists and it returns the right thing
            TreeNode treeNode= fnCallTail(parser); //fncalltail
            //treeNode.setType(); //todo whatever type it returns - semantic lookup on the identifier to determine what the return type is
            return treeNode;
        }
        else
            return nVarTailNode.make(parser); //todo fix data types, maybe speak to dan??
    }

    private TreeNode fnCallTail(Parser parser){
        //	<fncallTail>	::=	( <fnCallElistTail>)
        parser.peekAndConsume(Token.TLPAR);
        TreeNode fncallelisttail = fnCallElistTail(parser);
        parser.peekAndConsume(Token.TRPAR); //todo return data type
        TreeNode treeNode = new TreeNode(TreeNode.NFCALL, fncallelisttail, null);
        return treeNode;
    }

    private TreeNode fnCallElistTail(Parser parser){
        //	<fnCallElistTail>	::=	ε | <elist>
        Token token = parser.peek();
        if(token.getTokenID() == Token.TNOT || //elist trans
                token.getTokenID() == Token.TIDEN ||
                token.getTokenID() == Token.TILIT ||
                token.getTokenID() == Token.TFLIT ||
                token.getTokenID() == Token.TTRUE ||
                token.getTokenID() == Token.TFALS ||
                token.getTokenID() == Token.TLPAR){

            return nEListNode.make(parser);
        }
        else
            return null;

    }
}


