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
 * public class NVarNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NVarNodeTests {
//	<var>	::=	<id> <varTail>
    @Test
    public void sunnyday_id_eps(){
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
        NTermNode nTermNode = new NTermNode(nFactNode);
        NExprNode nExprNode1 = new NExprNode(nTermNode);

        NVarTailNode nVarTailNode1 = new NVarTailNode(nExprNode1);
        NVarNode nVarNode = new NVarNode(nVarTailNode1);

        TreeNode var = nVarNode.make(parser);

        assertEquals(TreeNode.NSIMV, var.getValue());
    }

    @Test
    public void sunnyday_id_arr(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aaa"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
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
        NVarNode nVarNode = new NVarNode(nVarTailNode1);

        TreeNode var = nVarNode.make(parser);

        assertEquals(TreeNode.NARRV, var.getValue());
    }

    @Test
    public void syntactic_failiden(){
        List<Token> tokens= new ArrayList<>();

//        tokens.add(new Token(Token.TIDEN,1,1,"aaa"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
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
        NVarNode nVarNode = new NVarNode(nVarTailNode1);

        TreeNode var = nVarNode.make(parser);

        assertEquals(TreeNode.NUNDEF, var.getValue());
    }
}
