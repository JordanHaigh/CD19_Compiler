package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NRelNode implements Node{
//NNOT	<rel>	::=	<expr><relExprTail> | not <expr> <relExprTail>
//	<relExprTail>	::=	ε | <relop><expr>

    NExprNode nExprNode;
    NRelopNode nRelopNode;

    public NRelNode() {
        this(null, NRelopNode.INSTANCE());
    }

    public NRelNode(NExprNode nExprNode, NRelopNode nRelopNode) {
        this.nExprNode = nExprNode;
        this.nRelopNode = nRelopNode;
    }

    private static NRelNode instance;
    public static NRelNode INSTANCE() {
        if (instance == null) {
            instance = new NRelNode();
        }
        return instance;
    }

    public void setnExprNode(NExprNode nExprNode) {
        this.nExprNode= nExprNode;
    }


    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TNOT){
            return notExprPath(parser);
        }
        else{
            return exprPath(parser);
        }
    }


    private TreeNode exprPath(Parser parser){
        //<expr><relExprTail>
        TreeNode expr = nExprNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail != null){
            tail.setLeft(expr);
            return tail;
        }
        return expr;

    }

    private TreeNode notExprPath(Parser parser){
        // not <expr> <relExprTail>
        parser.peekAndConsume(Token.TNOT);
        TreeNode expr= nExprNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail != null){
            tail.setLeft(expr);
            TreeNode returnTreeNode = new TreeNode(TreeNode.NNOT, tail,null);
            return returnTreeNode;
        }


        return new TreeNode(TreeNode.NNOT, expr,null);

//
//        TreeNode relop = nRelopNode.make(parser);
//        TreeNode secondExpr = nExprNode.make(parser);
//
//        relop.setLeft(firstExpr);
//        relop.setRight(secondExpr);
//        return new TreeNode(TreeNode.NNOT, relop, null);
    }

    private TreeNode tail(Parser parser){
        ////	<relExprTail>	::=	ε | <relop><expr>
        Token token = parser.peek();
        if(token.getTokenID() == Token.TEQEQ ||
                token.getTokenID() == Token.TNEQL ||
                token.getTokenID() == Token.TGRTR ||
                token.getTokenID() == Token.TGEQL ||
                token.getTokenID() == Token.TLESS ||
                token.getTokenID() == Token.TLEQL){


            TreeNode relop = nRelopNode.make(parser);
            TreeNode expr = nExprNode.make(parser);
            return new TreeNode(relop.getValue(), relop, expr);
        }
        else{
            return null; //epsilon
        }
    }


}

