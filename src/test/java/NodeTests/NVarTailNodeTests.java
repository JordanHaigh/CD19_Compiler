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
 * public class NVarTailNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NVarTailNodeTests {
    //	<varTail>	::=	ε | [<expr>] . <id>


    @Test
    public void sunnyday_eps(){

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
        NExprNode nExprNode1 = new NExprNode(nTermNode);

        NVarTailNode nVarTailNode1 = new NVarTailNode(nExprNode1);
        TreeNode vartail = nVarTailNode1.make(parser);

        assertEquals(TreeNode. NSIMV, vartail.getValue());
    }


    @Test
    public void sunnyday_arrdec(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);
        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);
        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode = new NExponentNode(nBoolNode, nVarTailNode, neListNode);
        NFactNode nFactNode = new NFactNode(nExponentNode);
        NTermNode nTermNode = new NTermNode(nFactNode);
        NExprNode nExprNode1 = new NExprNode(nTermNode);

        NVarTailNode nVarTailNode1 = new NVarTailNode(nExprNode1);
        TreeNode vartail = nVarTailNode1.make(parser);

        assertEquals(TreeNode.NARRV, vartail.getValue());
    }

    @Test
    public void syntactic_failrbrk(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
//        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
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

        NVarTailNode nVarTailNode1 = new NVarTailNode(nExprNode1);
        TreeNode vartail = nVarTailNode1.make(parser);

        assertEquals(TreeNode.NUNDEF, vartail.getValue());
    }


    @Test
    public void syntactic_faildot(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
//        tokens.add(new Token(Token.TDOT,1,1,null));
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

        NVarTailNode nVarTailNode1 = new NVarTailNode(nExprNode1);
        TreeNode vartail = nVarTailNode1.make(parser);

        assertEquals(TreeNode.NUNDEF, vartail.getValue());
    }


    @Test
    public void syntactic_failsecondiden(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
//        tokens.add(new Token(Token.TIDEN,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);
        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);
        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode = new NExponentNode(nBoolNode, nVarTailNode, neListNode);
        NFactNode nFactNode = new NFactNode(nExponentNode);
        NTermNode nTermNode = new NTermNode(nFactNode);
        NExprNode nExprNode1 = new NExprNode(nTermNode);

        NVarTailNode nVarTailNode1 = new NVarTailNode(nExprNode1);
        TreeNode vartail = nVarTailNode1.make(parser);

        assertEquals(TreeNode.NUNDEF, vartail.getValue());
    }
}
