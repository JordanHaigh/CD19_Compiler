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
        assertEquals(null, exponent.getLeft());
        assertEquals(null, exponent.getRight());

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
        assertEquals(null, exponent.getLeft());
        assertEquals(null, exponent.getRight());
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
        assertEquals(null, exponent.getLeft());
        assertEquals(null, exponent.getRight());
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
        assertEquals(null, exponent.getLeft());
        assertEquals(null, exponent.getRight());
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
        assertEquals(null, exponent.getLeft());
        assertEquals(null, exponent.getRight());
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
        assertEquals(null, exponent.getLeft());
        assertEquals(null, exponent.getRight());
    }

    @Test
    public void sunnyday_bool(){
        List<Token> tokens= new ArrayList<>();
        //(5+5)
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRPAR,1,1,null));

        Parser parser = new Parser(tokens);


        // Create exponent node separately or else we get a recursive loop
        NExponentNode nExponentNode = NExponentNode.INSTANCE();
        NReptStatNode nReptStatNode = NReptStatNode.INSTANCE();
        NAsgnStatNode nAsgnStatNode= NAsgnStatNode.INSTANCE();
        NEListNode neListNode = NEListNode.INSTANCE();
        NForStatNode nForStatNode = NForStatNode.INSTANCE();
        NIfStatNode nIfStatNode = NIfStatNode.INSTANCE();


        NInitNode nInitNode = NInitNode.INSTANCE();
        NTypeNode nTypeNode = NTypeNode.INSTANCE();
        NRelNode nRelNode = NRelNode.INSTANCE();
        NPrintItemNode nPrintItemNode = NPrintItemNode.INSTANCE();
        NReturnStatNode nReturnStatNode = NReturnStatNode.INSTANCE();

        NProgNode nProgNode = NProgNode.INSTANCE();

        // Fix broken recursive loop
        nExponentNode.setnBoolNode(NBoolNode.INSTANCE());
        nReptStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());
        neListNode.setnBoolNode(NBoolNode.INSTANCE());
        nForStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nIfStatNode.setnBoolNode(NBoolNode.INSTANCE());

        nInitNode.setnExprNode(NExprNode.INSTANCE());
        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        nRelNode.setnExprNode(NExprNode.INSTANCE());
        nPrintItemNode.setnExprNode(NExprNode.INSTANCE());
        nReturnStatNode.setnExprNode(NExprNode.INSTANCE());




        TreeNode bool = nExponentNode.make(parser);


        assertEquals(TreeNode.NADD, bool.getValue());
    }


    @Test
    public void sunnyday_bool2(){
        List<Token> tokens= new ArrayList<>();
        //(5+(5*20)
        //todo cant be tested due to recursive looping shit


        tokens.add(new Token(Token.TLPAR,1,1,null)); //(
        tokens.add(new Token(Token.TILIT,1,1,null)); //5
        tokens.add(new Token(Token.TPLUS,1,1,null)); //+
        tokens.add(new Token(Token.TLPAR,1,1,null));//(
        tokens.add(new Token(Token.TILIT,1,1,null));//5
        tokens.add(new Token(Token.TSTAR,1,1,null));//*
        tokens.add(new Token(Token.TILIT,1,1,null));// 20
        tokens.add(new Token(Token.TRPAR,1,1,null));//)
        tokens.add(new Token(Token.TRPAR,1,1,null));//)

        Parser parser = new Parser(tokens);


        // Create exponent node separately or else we get a recursive loop
        NExponentNode nExponentNode = NExponentNode.INSTANCE();
        NReptStatNode nReptStatNode = NReptStatNode.INSTANCE();
        NAsgnStatNode nAsgnStatNode= NAsgnStatNode.INSTANCE();
        NEListNode neListNode = NEListNode.INSTANCE();
        NForStatNode nForStatNode = NForStatNode.INSTANCE();
        NIfStatNode nIfStatNode = NIfStatNode.INSTANCE();


        NInitNode nInitNode = NInitNode.INSTANCE();
        NTypeNode nTypeNode = NTypeNode.INSTANCE();
        NRelNode nRelNode = NRelNode.INSTANCE();
        NPrintItemNode nPrintItemNode = NPrintItemNode.INSTANCE();
        NReturnStatNode nReturnStatNode = NReturnStatNode.INSTANCE();

        NProgNode nProgNode = NProgNode.INSTANCE();

        // Fix broken recursive loop
        nExponentNode.setnBoolNode(NBoolNode.INSTANCE());
        nReptStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());
        neListNode.setnBoolNode(NBoolNode.INSTANCE());
        nForStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nIfStatNode.setnBoolNode(NBoolNode.INSTANCE());

        nInitNode.setnExprNode(NExprNode.INSTANCE());
        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        nRelNode.setnExprNode(NExprNode.INSTANCE());
        nPrintItemNode.setnExprNode(NExprNode.INSTANCE());
        nReturnStatNode.setnExprNode(NExprNode.INSTANCE());




        TreeNode bool = nExponentNode.make(parser);

        assertEquals(TreeNode.NADD, bool.getValue());
        assertEquals(TreeNode.NILIT, bool.getLeft().getValue());
        assertEquals(TreeNode.NMUL, bool.getRight().getValue());
        assertEquals(TreeNode.NILIT, bool.getRight().getLeft().getValue());
        assertEquals(TreeNode.NILIT, bool.getRight().getRight().getValue());

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
        assertEquals(null, exponent.getLeft());
        assertEquals(null, exponent.getRight());
    }


    @Test
    public void sunnyday_fncall_args(){
        //todo HELP
        List<Token> tokens= new ArrayList<>();
        //func(func2())
        tokens.add(new Token(Token.TIDEN,1,1,"func"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"func2"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TRPAR,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        Parser parser = new Parser(tokens);


        // Create exponent node separately or else we get a recursive loop
        NExponentNode nExponentNode = NExponentNode.INSTANCE();
        NReptStatNode nReptStatNode = NReptStatNode.INSTANCE();
        NAsgnStatNode nAsgnStatNode= NAsgnStatNode.INSTANCE();
        NEListNode neListNode = NEListNode.INSTANCE();
        NForStatNode nForStatNode = NForStatNode.INSTANCE();
        NIfStatNode nIfStatNode = NIfStatNode.INSTANCE();


        NInitNode nInitNode = NInitNode.INSTANCE();
        NTypeNode nTypeNode = NTypeNode.INSTANCE();
        NRelNode nRelNode = NRelNode.INSTANCE();
        NPrintItemNode nPrintItemNode = NPrintItemNode.INSTANCE();
        NReturnStatNode nReturnStatNode = NReturnStatNode.INSTANCE();

        NProgNode nProgNode = NProgNode.INSTANCE();

        // Fix broken recursive loop
        nExponentNode.setnBoolNode(NBoolNode.INSTANCE());
        nReptStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());
        neListNode.setnBoolNode(NBoolNode.INSTANCE());
        nForStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nIfStatNode.setnBoolNode(NBoolNode.INSTANCE());

        nInitNode.setnExprNode(NExprNode.INSTANCE());
        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        nRelNode.setnExprNode(NExprNode.INSTANCE());
        nPrintItemNode.setnExprNode(NExprNode.INSTANCE());
        nReturnStatNode.setnExprNode(NExprNode.INSTANCE());

        NBoolNode nBoolNode = new NBoolNode(nRelNode, new NLogopNode());
        NVarTailNode nVarTailNode = new NVarTailNode(new NExprNode());

        NExponentNode nExponentNode1= new NExponentNode(nBoolNode, nVarTailNode, neListNode );

        TreeNode exponent = nExponentNode1.make(parser);

        assertEquals(TreeNode.NFCALL, exponent.getValue());
        assertEquals(TreeNode.NFCALL, exponent.getLeft().getValue());
    }




}
