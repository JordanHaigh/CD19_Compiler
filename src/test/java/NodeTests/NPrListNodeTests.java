package NodeTests;


import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NPrListNodeTests {
    //NPRLST	<prlist>	::=	<printitem><prlistTail>
    //	<prlistTail>	::=	ε |  , <printitem><prlistTail>


    @Test
    public void sunnyday_justprintitem(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NPrintItemNode nPrintItemNode = new NPrintItemNode(NExprNode.INSTANCE());
        NPrListNode nPrListNode = new NPrListNode(nPrintItemNode);

        TreeNode prlist = nPrListNode.make(parser);

        assertEquals(TreeNode.NADD, prlist.getValue());

    }

    @Test
    public void sunnyday_twoprintitems(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TSTRG,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));


        Parser parser = new Parser(tokens);

        NPrintItemNode nPrintItemNode = new NPrintItemNode(NExprNode.INSTANCE());
        NPrListNode nPrListNode = new NPrListNode(nPrintItemNode);

        TreeNode prlist = nPrListNode.make(parser);

        assertEquals(TreeNode.NPRLST, prlist.getValue());
        assertEquals(TreeNode.NADD, prlist.getLeft().getValue());
        assertEquals(TreeNode.NSTRG, prlist.getRight().getValue());

    }

    @Test
    public void sunnyday_threeprintitems(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TSTRG,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TMINS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        /////////////
        tokens.add(new Token(Token.TFUNC,1,1,null));


        Parser parser = new Parser(tokens);

        NPrintItemNode nPrintItemNode = new NPrintItemNode(NExprNode.INSTANCE());
        NPrListNode nPrListNode = new NPrListNode(nPrintItemNode);

        TreeNode prlist = nPrListNode.make(parser);

        assertEquals(TreeNode.NPRLST, prlist.getValue());

        assertEquals(TreeNode.NADD, prlist.getLeft().getValue());
        assertEquals(TreeNode.NPRLST, prlist.getRight().getValue());

        assertEquals(TreeNode.NSTRG, prlist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NSUB, prlist.getRight().getRight().getValue());


    }

}
