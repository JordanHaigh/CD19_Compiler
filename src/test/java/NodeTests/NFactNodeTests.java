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

/**
 * Jordan Haigh c3256730 CD19
 * public class NFactNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NFactNodeTests {
    //    //NPOW	<fact>	::=	<exponent><factTail>
    //    //	<factTail>	::=	ε | ^<fact>

    @Test
    public void sunnyday_singleexponent_simv(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);
        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);
        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode = new NExponentNode(nBoolNode, nVarTailNode, neListNode);

        NFactNode nFactNode = new NFactNode(nExponentNode);

        TreeNode fact = nFactNode.make(parser);

        assertEquals(TreeNode.NSIMV, fact.getValue());
    }

    @Test
    public void sunnyday_singleexponent_ilit(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,"aa"));


        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);
        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);
        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode = new NExponentNode(nBoolNode, nVarTailNode, neListNode);

        NFactNode nFactNode = new NFactNode(nExponentNode);

        TreeNode fact = nFactNode.make(parser);

        assertEquals(TreeNode.NILIT, fact.getValue());

    }

    @Test
    public void sunnyday_pow(){
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

        TreeNode fact = nFactNode.make(parser);

        assertEquals(TreeNode.NPOW, fact.getValue());
        assertEquals(TreeNode.NILIT, fact.getLeft().getValue());
        assertEquals(TreeNode.NILIT, fact.getRight().getValue());
    }

    @Test
    public void sunnyday_powpow(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
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

        TreeNode fact = nFactNode.make(parser);

        assertEquals(TreeNode.NPOW, fact.getValue());
        assertEquals(TreeNode.NPOW, fact.getLeft().getValue());
        assertEquals(TreeNode.NILIT, fact.getRight().getValue());
        assertEquals(TreeNode.NILIT, fact.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NILIT, fact.getLeft().getRight().getValue());
    }


    @Test
    public void sunnyday_powpowpow(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
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

        TreeNode fact = nFactNode.make(parser);

        assertEquals(TreeNode.NPOW, fact.getValue());
        assertEquals(TreeNode.NPOW, fact.getLeft().getValue());
        assertEquals(TreeNode.NILIT, fact.getRight().getValue());
        assertEquals(TreeNode.NPOW, fact.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NILIT, fact.getLeft().getLeft().getLeft().getValue());
        assertEquals(TreeNode.NILIT, fact.getLeft().getLeft().getRight().getValue());

    }

}
