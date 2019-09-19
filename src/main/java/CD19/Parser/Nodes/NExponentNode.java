package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NExponentNode implements Node{

//NILIT | NFLIT | NTRUE | NFALS	   <exponent>	::=	<id> <varOrFNCallTail>|  <intlit> | <reallit>  |
//                                                  TRUE | FALSE | (<bool>)
//	<varOrFNCallTail>	::=	<varTail> | <fnCallTail>
//	<fncallTail>	::=	( <fnCallElistTail>)
    NBoolNode nBoolNode;
    NVarTailNode nVarTailNode;
    NEListNode nEListNode;

    public NExponentNode() {
        this(new NBoolNode(), new NVarTailNode(), new NEListNode());
    }

    public NExponentNode(NBoolNode nBoolNode, NVarTailNode nVarTailNode, NEListNode neListNode) {
        this.nBoolNode = nBoolNode;
        this.nVarTailNode = nVarTailNode;
        this.nEListNode = neListNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TIDEN)){
            return varOrFnCallTail(parser);
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
        //todo else error
        return null;
    }

    private TreeNode varOrFnCallTail(Parser parser){
        //	<varOrFNCallTail>	::=	<varTail> | <fnCallTail>
        Token token = parser.peek();
        if(token.getTokenID() == Token.TLPAR) {
            return fnCallTail(parser); //fncalltail
        }
        else
            return nVarTailNode.make(parser);
    }

    private TreeNode fnCallTail(Parser parser){
        //	<fncallTail>	::=	( <fnCallElistTail>)
        parser.peekAndConsume(Token.TLPAR);
        TreeNode fncallelisttail = fnCallElistTail(parser);
        parser.peekAndConsume(Token.TRPAR);
        return new TreeNode(TreeNode.NFCALL, fncallelisttail, null);//todo probs wrong
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


