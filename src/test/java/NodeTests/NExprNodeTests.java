package NodeTests;


import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NExprNodeTests {
    //NADD | NSUB	<expr>	::=	<term><exprTail>
    //<exprTail>	::=	ε | +<expr> | -<expr>

    @Test
    public void sunnyDay_justTerm(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TSTAR,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);
        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);
        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode = new NExponentNode(nBoolNode, nVarTailNode, neListNode);
        NFactNode nFactNode = new NFactNode(nExponentNode);
        NTermNode nTermNode = new NTermNode(nFactNode);

        NExprNode nExprNode1 = new NExprNode(nTermNode);
        TreeNode expr = nExprNode1.make(parser);

        assertEquals(TreeNode.NMUL, expr.getValue());
        assertEquals(TreeNode.NMUL, expr.getLeft().getValue());
        assertEquals(TreeNode.NILIT, expr.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NMUL, expr.getLeft().getRight().getValue());
        assertEquals(TreeNode.NILIT, expr.getLeft().getRight().getLeft().getValue());
        assertEquals(null, expr.getLeft().getRight().getRight());
        assertEquals(null, expr.getRight());


    }
}
