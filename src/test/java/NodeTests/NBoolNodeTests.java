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

public class NBoolNodeTests {
//NBOOL	<bool>	::=	<rel><boolTail>
//	<boolTail>	::=	ε | <logop><rel><boolTail>

    @Test
    public void sunnyday_onlyrel(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TNOT,1,1,null));

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
        NBoolNode nBoolNode1 = new NBoolNode(nRelNode,new NLogopNode());
        TreeNode bool = nBoolNode1.make(parser);

        assertEquals(TreeNode.NNOT, bool.getValue());
        assertEquals(TreeNode.NEQL, bool.getLeft().getValue());
        assertEquals(TreeNode.NADD, bool.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NPOW, bool.getLeft().getRight().getValue());

    }

    @Test
    public void sunnyday_bool_rel_logop_rel(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));

        tokens.add(new Token(Token.TAND,1,1,null));

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
        NBoolNode nBoolNode1 = new NBoolNode(nRelNode,new NLogopNode());
        TreeNode bool = nBoolNode1.make(parser);

        assertEquals(TreeNode.NBOOL, bool.getValue());
        assertEquals(TreeNode.NADD, bool.getLeft().getValue());
        assertEquals(TreeNode.NPOW, bool.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NPOW, bool.getRight().getValue());

    }

    @Test
    public void sunnyday_bool_bool(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));

        tokens.add(new Token(Token.TAND,1,1,null));

        tokens.add(new Token(Token.TTRUE,1,1,null));
        tokens.add(new Token(Token.TCART,1,1,null));
        tokens.add(new Token(Token.TFALS,1,1,null));

        tokens.add(new Token(Token.TOR,1,1,null));

        tokens.add(new Token(Token.TTRUE,1,1,null));
        tokens.add(new Token(Token.TPERC,1,1,null));
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
        NBoolNode nBoolNode1 = new NBoolNode(nRelNode,new NLogopNode());
        TreeNode bool = nBoolNode1.make(parser);

        assertEquals(TreeNode.NBOOL, bool.getValue());
        assertEquals(TreeNode.NADD, bool.getLeft().getValue());
        assertEquals(TreeNode.NBOOL, bool.getRight().getValue());
        assertEquals(TreeNode.NPOW, bool.getRight().getLeft().getValue());
        assertEquals(TreeNode.NMOD, bool.getRight().getRight().getValue());

    }
}
