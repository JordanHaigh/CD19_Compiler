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

public class NTermNodeTests {
    //NMUL | NDIV | NMOD	<term>	::=	<fact><termTail>
    //	<termTail	::=	ε | *<term> | /<term> | %<term>

    @Test
    public void sunnyDay_justFact(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
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
        TreeNode term = nTermNode.make(parser);


        assertEquals(TreeNode.NPOW, term.getValue());
        assertEquals(TreeNode.NILIT, term.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NPOW, term.getLeft().getRight().getValue());
    }

    @Test
    public void sunnyDay_divd(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TDIVD,1,1,null));
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
        TreeNode term = nTermNode.make(parser);

        assertEquals(TreeNode.NDIV, term.getValue());
        assertEquals(TreeNode.NILIT, term.getLeft().getValue());
        assertEquals(TreeNode.NDIV, term.getRight().getValue());
        assertEquals(TreeNode.NILIT, term.getRight().getLeft().getValue());
    }


    @Test
    public void sunnyDay_multiply(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TSTAR,1,1,null));
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
        TreeNode term = nTermNode.make(parser);

        assertEquals(TreeNode.NMUL, term.getValue());
        assertEquals(TreeNode.NILIT, term.getLeft().getValue());
        assertEquals(TreeNode.NMUL, term.getRight().getValue());
        assertEquals(TreeNode.NILIT, term.getRight().getLeft().getValue());
    }


    @Test
    public void sunnyDay_mod(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPERC,1,1,null));
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
        TreeNode term = nTermNode.make(parser);

        assertEquals(TreeNode.NMOD, term.getValue());
        assertEquals(TreeNode.NILIT, term.getLeft().getValue());
        assertEquals(TreeNode.NMOD, term.getRight().getValue());
        assertEquals(TreeNode.NILIT, term.getRight().getLeft().getValue());
    }
}
