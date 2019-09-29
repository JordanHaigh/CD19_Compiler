package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class NStrStatNodeTests {
//	<strstat>	::=	<forstat> | <ifstat>
//NFOR	<forstat>	::=	for ( <asgnlist> ; <bool> ) <stats> end
//NIFTH | NIFTE	<ifstat>	::=	if ( <bool> ) <stats> <ifStatTail> end
//	<ifStatTail>	::=	ε|  else <stats>


    @Mock NAsgnListNode nAsgnListNode;
    @Mock NBoolNode nBoolNode;
    @Mock NStatsNode nStatsNode;

    @InjectMocks NStrStatNode nStrStatNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sunnyday_forloop(){

        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFOR,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here"));
        tokens.add(new Token(Token.TSEMI,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TEND,1,1,null));


        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);


        when(nAsgnListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NASGNS);
        });
        when(nBoolNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NBOOL);
        });

        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });


        NForStatNode nForStatNode = new NForStatNode(nAsgnListNode, nBoolNode, nStatsNode);
        nStrStatNode = new NStrStatNode(nForStatNode,null);
        TreeNode strstat = nStrStatNode.make(parser);

        assertEquals(TreeNode.NFOR, strstat.getValue());
        assertEquals(TreeNode.NASGNS, strstat.getLeft().getValue());
        assertEquals(TreeNode.NBOOL, strstat.getMiddle().getValue());
        assertEquals(TreeNode.NSTATS, strstat.getRight().getValue());

    }

    @Test
    public void sunnyday_ifstat(){

        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIFTH,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TELSE,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);

        when(nBoolNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NBOOL);
        });

        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });


        NIfStatNode nIfStatNode = new NIfStatNode(nBoolNode, nStatsNode);
        nStrStatNode = new NStrStatNode(null, nIfStatNode);

        TreeNode strstat = nStrStatNode.make(parser);

        assertEquals(TreeNode.NIFTE, strstat.getValue());
        assertEquals(TreeNode.NBOOL, strstat.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, strstat.getMiddle().getValue());
        assertEquals(TreeNode.NSTATS, strstat.getRight().getValue());


    }


    @Test
    public void syntactic_fail(){

        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TELSE,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);

        when(nBoolNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NBOOL);
        });

        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });


        NIfStatNode nIfStatNode = new NIfStatNode(nBoolNode, nStatsNode);
        nStrStatNode = new NStrStatNode(null, nIfStatNode);

        TreeNode strstat = nStrStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF, strstat.getValue());


    }

}
