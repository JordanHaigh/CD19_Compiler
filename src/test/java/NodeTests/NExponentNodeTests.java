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


public class NExponentNodeTests {

    //NILIT | NFLIT | NTRUE | NFALS	<exponent>	::=	<id> <varOrFNCallTail>|  <intlit> | <reallit>  | TRUE | FALSE | (<bool>)
    //	<varOrFNCallTail>	::=	<varTail> | <fnCallTail>
    //	<fncallTail>	::=	( <fnCallElistTail>)
    //	<fnCallElistTail>	::=	ε | <elist>
    @Test
    public void sunnyday_tiden_nsimv(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);

        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);

        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode= new NExponentNode(nBoolNode, nVarTailNode, neListNode );

        TreeNode exponent = nExponentNode.make(parser);

        assertEquals(TreeNode.NSIMV, exponent.getValue());

    }

    @Test
    public void sunnyday_tidenarr_narrv(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);

        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);

        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode= new NExponentNode(nBoolNode, nVarTailNode, neListNode );

        TreeNode exponent = nExponentNode.make(parser);

        assertEquals(TreeNode.NARRV, exponent.getValue());
    }


    @Test
    public void sunnyday_tilit_nilit(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,"aa"));
        tokens.add(new Token(Token.TPLUS,1,1,null));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);

        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);

        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode= new NExponentNode(nBoolNode, nVarTailNode, neListNode );

        TreeNode exponent = nExponentNode.make(parser);

        assertEquals(TreeNode.NILIT, exponent.getValue());
    }

    @Test
    public void sunnyday_tflit_nflit(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFLIT,1,1,"aa"));
        tokens.add(new Token(Token.TPLUS,1,1,null));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);

        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);

        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode= new NExponentNode(nBoolNode, nVarTailNode, neListNode );

        TreeNode exponent = nExponentNode.make(parser);

        assertEquals(TreeNode.NFLIT, exponent.getValue());
    }



    @Test
    public void sunnyday_ttrue_ntrue(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TTRUE,1,1,"aa"));
        tokens.add(new Token(Token.TOR,1,1,null));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);

        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);

        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode= new NExponentNode(nBoolNode, nVarTailNode, neListNode );

        TreeNode exponent = nExponentNode.make(parser);

        assertEquals(TreeNode.NTRUE, exponent.getValue());
    }

    @Test
    public void sunnyday_tfalse_nfalse(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFALS,1,1,"aa"));
        tokens.add(new Token(Token.TOR,1,1,null));

        Parser parser = new Parser(tokens);

        NBoolNode nBoolNode = mock(NBoolNode.class);
        NExprNode nExprNode = mock(NExprNode.class);

        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);

        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode= new NExponentNode(nBoolNode, nVarTailNode, neListNode );

        TreeNode exponent = nExponentNode.make(parser);

        assertEquals(TreeNode.NFALS, exponent.getValue());
    }

    @Test
    public void sunnyday_bool_nbool(){
        List<Token> tokens= new ArrayList<>();
        //(5+5)
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRPAR,1,1,null));

        Parser parser = new Parser(tokens);

        NRelNode nRelNode = mock(NRelNode.class);
        NLogopNode nLogopNode = mock(NLogopNode.class);
        NBoolNode nBoolNode = new NBoolNode(nRelNode, nLogopNode);
        NExprNode nExprNode = mock(NExprNode.class);

        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);

        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode= new NExponentNode(nBoolNode, nVarTailNode, neListNode );

        TreeNode exponent = nExponentNode.make(parser);

        assertEquals(TreeNode.NBOOL, exponent.getValue());
    }



    @Test
    public void sunnyday_fncall(){
        List<Token> tokens= new ArrayList<>();
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TRPAR,1,1,null));

        Parser parser = new Parser(tokens);

        NRelNode nRelNode = mock(NRelNode.class);
        NLogopNode nLogopNode = mock(NLogopNode.class);
        NBoolNode nBoolNode = new NBoolNode(nRelNode, nLogopNode);
        NExprNode nExprNode = mock(NExprNode.class);

        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);

        NEListNode neListNode = mock(NEListNode.class);
        NExponentNode nExponentNode= new NExponentNode(nBoolNode, nVarTailNode, neListNode );

        TreeNode exponent = nExponentNode.make(parser);

        assertEquals(TreeNode.NFCALL, exponent.getValue());
    }


    @Test
    public void sunnyday_fncall_args(){
        List<Token> tokens= new ArrayList<>();
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TRPAR,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        Parser parser = new Parser(tokens);

        NRelNode nRelNode = mock(NRelNode.class);
        NLogopNode nLogopNode = mock(NLogopNode.class);
        NBoolNode nBoolNode = new NBoolNode(nRelNode, nLogopNode);
        NExprNode nExprNode = mock(NExprNode.class);

        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);

        NEListNode neListNode = new NEListNode(nBoolNode);
        NExponentNode nExponentNode= new NExponentNode(nBoolNode, nVarTailNode, neListNode );

        TreeNode exponent = nExponentNode.make(parser);

        assertEquals(TreeNode.NFCALL, exponent.getValue());
        assertEquals(TreeNode.NEXPL, exponent.getLeft().getValue());
    }



}
