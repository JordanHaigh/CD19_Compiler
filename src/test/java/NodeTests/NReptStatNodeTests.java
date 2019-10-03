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

/**
 * Jordan Haigh c3256730 CD19
 * public class NReptStatNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NReptStatNodeTests {
    //NREPT	<repstat>	::=	repeat ( <asgnlist> ) <stats> until <bool>

    @Mock NAsgnListNode nAsgnListNode;
    @Mock NStatsNode nStatsNode;
    @Mock NBoolNode nBoolNode;

    @InjectMocks
    NReptStatNode nReptStatNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sunnyday(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TREPT,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here "));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TUNTL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));


        Parser parser = new Parser(tokens);

        when(nAsgnListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NASGN);
        });
        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });
        when(nBoolNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NBOOL);
        });

        nReptStatNode = new NReptStatNode(nAsgnListNode, nStatsNode, nBoolNode);
        TreeNode reptstat = nReptStatNode.make(parser);

        assertEquals(TreeNode.NREPT, reptstat.getValue());
        assertEquals(TreeNode.NASGN, reptstat.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, reptstat.getMiddle().getValue());
        assertEquals(TreeNode.NBOOL, reptstat.getRight().getValue());
    }

    @Test
    public void syntactic_failrept(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

//        tokens.add(new Token(Token.TREPT,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here "));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TUNTL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));


        Parser parser = new Parser(tokens);

        when(nAsgnListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NASGN);
        });
        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });
        when(nBoolNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NBOOL);
        });

        nReptStatNode = new NReptStatNode(nAsgnListNode, nStatsNode, nBoolNode);
        TreeNode reptstat = nReptStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF, reptstat.getValue());
    }


    @Test
    public void syntactic_failleftpar(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TREPT,1,1,null));
//        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here "));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TUNTL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));


        Parser parser = new Parser(tokens);

        when(nAsgnListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NASGN);
        });
        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });
        when(nBoolNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NBOOL);
        });

        nReptStatNode = new NReptStatNode(nAsgnListNode, nStatsNode, nBoolNode);
        TreeNode reptstat = nReptStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF, reptstat.getValue());
    }

    @Test
    public void syntactic_failrightpar(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TREPT,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here "));
//        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TUNTL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));


        Parser parser = new Parser(tokens);

        when(nAsgnListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NASGN);
        });
        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });
        when(nBoolNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NBOOL);
        });

        nReptStatNode = new NReptStatNode(nAsgnListNode, nStatsNode, nBoolNode);
        TreeNode reptstat = nReptStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF, reptstat.getValue());
    }

    @Test
    public void syntactic_failuntilkeyword(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TREPT,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here "));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
//        tokens.add(new Token(Token.TUNTL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));


        Parser parser = new Parser(tokens);

        when(nAsgnListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NASGN);
        });
        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });
        when(nBoolNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NBOOL);
        });

        nReptStatNode = new NReptStatNode(nAsgnListNode, nStatsNode, nBoolNode);
        TreeNode reptstat = nReptStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF, reptstat.getValue());
    }
}
