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
 * public class NIfStatNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NIfStatNodeTests {
//NIFTH | NIFTE	<ifstat>	::=	if ( <bool> ) <stats> <ifStatTail>
//	<ifStatTail>	::=	end |  else <stats> end

    @Mock
    NBoolNode nBoolNode;
    @Mock
    NStatsNode nStatsNode;

    @InjectMocks
    NIfStatNode nIfStatNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void sunnyday_if_no_else(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIFTH,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
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

        TreeNode ifstat = nIfStatNode.make(parser);
        assertEquals(TreeNode.NIFTH, ifstat.getValue());
        assertEquals(TreeNode.NBOOL, ifstat.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, ifstat.getRight().getValue());

    }

    @Test
    public void sunnyday_if_else(){
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

        TreeNode ifstat = nIfStatNode.make(parser);
        assertEquals(TreeNode.NIFTE, ifstat.getValue());
        assertEquals(TreeNode.NBOOL, ifstat.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, ifstat.getMiddle().getValue());
        assertEquals(TreeNode.NSTATS, ifstat.getRight().getValue());

    }

    @Test
    public void syntactic_failif(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        //tokens.add(new Token(Token.TIFTH,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
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

        TreeNode ifstat = nIfStatNode.make(parser);
        assertEquals(TreeNode.NUNDEF, ifstat.getValue());

    }

    @Test
    public void syntactic_faillpar(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIFTH,1,1,null));
//        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
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

        TreeNode ifstat = nIfStatNode.make(parser);
        assertEquals(TreeNode.NUNDEF, ifstat.getValue());

    }

    @Test
    public void syntactic_failrpar(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIFTH,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
//        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
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

        TreeNode ifstat = nIfStatNode.make(parser);
        assertEquals(TreeNode.NUNDEF, ifstat.getValue());

    }

//    @Test
//    public void syntactic_failend(){
//        SetupMocks.setup();
//        List<Token> tokens= new ArrayList<>();
//
//        tokens.add(new Token(Token.TIFTH,1,1,null));
//        tokens.add(new Token(Token.TLPAR,1,1,null));
//        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
//        tokens.add(new Token(Token.TRPAR,1,1,null));
//        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
//        tokens.add(new Token(Token.TIFTH,1,1,null));
//
//        Parser parser = new Parser(tokens);
//
//
//        when(nBoolNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
//            parser.consume();
//            return new TreeNode(TreeNode.NBOOL);
//        });
//
//        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
//            parser.consume();
//            return new TreeNode(TreeNode.NSTATS);
//        });
//
//        TreeNode ifstat = nIfStatNode.make(parser);
//        assertEquals(TreeNode.NUNDEF, ifstat.getValue());
//
//    }



}
