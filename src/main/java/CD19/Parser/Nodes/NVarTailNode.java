package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NVarTailNode implements Node{

    //	<varTail>	::=	ε | [<expr>] . <id>
    NExprNode nExprNode;

    public NVarTailNode() {
        this(null);
    }

    public NVarTailNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    private static NVarTailNode instance;
    public static NVarTailNode INSTANCE() {
        if (instance == null) {
            instance = new NVarTailNode();
        }
        return instance;
    }


    public void setnExprNode(NExprNode exprNode) {
        this.nExprNode= exprNode;
    }
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();

        if(token.getTokenID() == Token.TLBRK){
            TreeNode vartail = new TreeNode();

            parser.consume();

            TreeNode expr = nExprNode.make(parser); //todo whats this used for

            if(!parser.peekAndConsume(Token.TRBRK)){
                parser.syntacticError("Expected a Right Bracket", parser.peek());
                return vartail;
            }

            if(!parser.peekAndConsume(Token.TDOT)){
                parser.syntacticError("Expected a Dot", parser.peek());
                return vartail;
            }

            if(!parser.peekAndConsume(Token.TIDEN)){
                parser.syntacticError("Expected an Identifier", parser.peek());
                return vartail;
            }

            SymbolTableRecord record = new SymbolTableRecord(); //todo fill this in
            vartail = new TreeNode(TreeNode.NARRV, record);
            return vartail;
        }
        else{
            return new TreeNode(TreeNode.NSIMV, null,null);
        }
    }
}
