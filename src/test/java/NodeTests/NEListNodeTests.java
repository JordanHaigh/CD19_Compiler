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


public class NEListNodeTests {
    //NEXPL	<elist>	::=	<bool> <elistTail>
    //	<elistTail>	::=	ε | , <elist>

    @Test
    public void sunnyday_justbool(){

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
        NEListNode neListNode1 = new NEListNode(nBoolNode1);
        TreeNode elist = neListNode1.make(parser);

        assertEquals(TreeNode.NBOOL, elist.getValue());
        assertEquals(TreeNode.NADD, elist.getLeft().getValue());
        assertEquals(TreeNode.NBOOL, elist.getRight().getValue());
        assertEquals(TreeNode.NPOW, elist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NMOD, elist.getRight().getRight().getValue());
    }

    @Test
    public void sunnyday_bool_coma_bool(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TAND,1,1,null));

        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TTRUE,1,1,null));
        tokens.add(new Token(Token.TMINS,1,1,null));
        tokens.add(new Token(Token.TTRUE,1,1,null));

        tokens.add(new Token(Token.TOR,1,1,null));

        tokens.add(new Token(Token.TFALS, 1,1,null));
        tokens.add(new Token(Token.TMINS,1,1,null));
        tokens.add(new Token(Token.TFALS,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));

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
        NEListNode neListNode1 = new NEListNode(nBoolNode1);
        TreeNode elist = neListNode1.make(parser);

        assertEquals(TreeNode.NEXPL, elist.getValue());
        assertEquals(TreeNode.NBOOL, elist.getLeft().getValue());
        assertEquals(TreeNode.NBOOL, elist.getRight().getValue());

        assertEquals(TreeNode.NADD, elist.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NADD, elist.getLeft().getRight().getValue());


        assertEquals(TreeNode.NSUB, elist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NSUB, elist.getRight().getRight().getValue());

    }

    @Test
    public void sunnyday_bool_coma_bool_coma_bool(){
        List<Token> tokens= new ArrayList<>();

        // 1 + 1 and 2.0 + 2.0 , true - true or false - false , 1 + 1 xor 2.0 + 2.0

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TAND,1,1,null));

        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));
///////////////
        tokens.add(new Token(Token.TCOMA,1,1,null));
///////////////
        tokens.add(new Token(Token.TTRUE,1,1,null));
        tokens.add(new Token(Token.TMINS,1,1,null));
        tokens.add(new Token(Token.TTRUE,1,1,null));

        tokens.add(new Token(Token.TOR,1,1,null));

        tokens.add(new Token(Token.TFALS, 1,1,null));
        tokens.add(new Token(Token.TMINS,1,1,null));
        tokens.add(new Token(Token.TFALS,1,1,null));
/////////////
        tokens.add(new Token(Token.TCOMA,1,1,null));
////////////
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TXOR,1,1,null));

        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));



        tokens.add(new Token(Token.TFUNC,1,1,null));

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
        NEListNode neListNode1 = new NEListNode(nBoolNode1);
        TreeNode elist = neListNode1.make(parser);

        assertEquals(TreeNode.NEXPL, elist.getValue());
        assertEquals(TreeNode.NBOOL, elist.getLeft().getValue());
        assertEquals(TreeNode.NEXPL, elist.getRight().getValue());

        assertEquals(TreeNode.NADD, elist.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NAND, elist.getLeft().getMiddle().getValue());
        assertEquals(TreeNode.NADD, elist.getLeft().getRight().getValue());

        assertEquals(TreeNode.NBOOL, elist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NBOOL, elist.getRight().getRight().getValue());

        assertEquals(TreeNode.NSUB, elist.getRight().getLeft().getLeft().getValue());
        assertEquals(TreeNode.NOR, elist.getRight().getLeft().getMiddle().getValue());
        assertEquals(TreeNode.NSUB, elist.getRight().getLeft().getRight().getValue());

        assertEquals(TreeNode.NADD, elist.getRight().getRight().getLeft().getValue());
        assertEquals(TreeNode.NADD, elist.getRight().getRight().getRight().getValue());
        assertEquals(TreeNode.NXOR, elist.getRight().getRight().getMiddle().getValue());


    }
}
