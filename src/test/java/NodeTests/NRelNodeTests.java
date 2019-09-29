package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class NRelNodeTests {
//NNOT	<rel>	::=	<expr><relExprTail> | not <expr> <relop> <expr>
//	<relExprTail>	::=	ε | <relop><expr>

    @Test
    public void sunnyday_expr_eps(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);
        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);
        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode = new NExponentNode(nBoolNode, nVarTailNode, neListNode);
        NFactNode nFactNode = new NFactNode(nExponentNode);
        NTermNode nTermNode = new NTermNode(nFactNode);
        NExprNode nExprNode1 = new NExprNode(nTermNode);

        NRelNode nRelNode = new NRelNode(nExprNode1, new NRelopNode());
        TreeNode rel = nRelNode.make(parser);
        assertEquals(TreeNode. NPOW, rel.getValue());
    }

    @Test
    public void sunnyday_expr_relop_expr(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));

        tokens.add(new Token(Token.TEQEQ,1,1,null));

        tokens.add(new Token(Token.TTRUE,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
        tokens.add(new Token(Token.TFALS,1,1,null));


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

        NRelNode nRelNode = new NRelNode(nExprNode1, new NRelopNode());
        TreeNode rel = nRelNode.make(parser);

        assertEquals(TreeNode.NEQL, rel.getValue());
        assertEquals(TreeNode.NADD, rel.getLeft().getValue());
        assertEquals(TreeNode.NPOW, rel.getRight().getValue());

    }

    @Test
    public void sunnyday_not_expr_eps(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TNOT,1,1,null));
        tokens.add(new Token(Token.TTRUE,1,1,null));

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

        NRelNode nRelNode = new NRelNode(nExprNode1, new NRelopNode());
        TreeNode rel = nRelNode.make(parser);

        assertEquals(TreeNode.NNOT, rel.getValue());
        assertEquals(TreeNode.NTRUE, rel.getLeft().getValue());
        assertEquals(null, rel.getRight());

    }


    @Test
    public void sunnyday_not_expr_relop_expr(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TNOT,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));

        tokens.add(new Token(Token.TEQEQ,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TMINS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPERC,1,1,null));
        tokens.add(new Token(Token.TTRUE,1,1,null));

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

        NRelNode nRelNode = new NRelNode(nExprNode1, new NRelopNode());
        TreeNode rel = nRelNode.make(parser);

        assertEquals(TreeNode.NNOT, rel.getValue());
        assertEquals(TreeNode.NEQL, rel.getLeft().getValue());
        assertEquals(TreeNode.NADD, rel.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NSUB, rel.getLeft().getRight().getValue());

    }

    @Test
    public void sunnyday_not_expr_relop_expr_relop_expr(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TNOT,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));

        tokens.add(new Token(Token.TEQEQ,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TMINS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPERC,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));


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

        NRelNode nRelNode = new NRelNode(nExprNode1, new NRelopNode());
        TreeNode rel = nRelNode.make(parser);

        assertEquals(TreeNode.NNOT, rel.getValue());

    }
}
