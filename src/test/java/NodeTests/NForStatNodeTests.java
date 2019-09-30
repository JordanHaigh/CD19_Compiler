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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


public class NForStatNodeTests {
    // NFOR	<forstat>	::=	for ( <asgnlist> ; <bool> ) <stats> end

    @Mock
    NAsgnListNode nAsgnListNode;

    @Mock
    NBoolNode nBoolNode;

    @Mock
    NStatsNode nStatsNode;

    @InjectMocks
    NForStatNode nForStatNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sunnyday(){
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

        TreeNode forstat = nForStatNode.make(parser);

        assertEquals(TreeNode.NFOR, forstat.getValue());
        assertEquals(TreeNode.NASGNS, forstat.getLeft().getValue());
        assertEquals(TreeNode.NBOOL, forstat.getMiddle().getValue());
        assertEquals(TreeNode.NSTATS, forstat.getRight().getValue());

    }

    @Test
    public void syntacticerrorForKeyword(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TFOR,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here"));
        tokens.add(new Token(Token.TSEMI,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
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

        TreeNode forstat = nForStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF, forstat.getValue());

    }

    @Test
    public void syntacticerrorLPar(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFOR,1,1,null));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here"));
        tokens.add(new Token(Token.TSEMI,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
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

        TreeNode forstat = nForStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF, forstat.getValue());

    }

    @Test
    public void syntacticerrorRPar(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFOR,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here"));
        tokens.add(new Token(Token.TSEMI,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));

        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
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

        TreeNode forstat = nForStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF, forstat.getValue());

    }

    @Test
    public void syntacticerrorSemi(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFOR,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here"));
        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));

        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
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

        TreeNode forstat = nForStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF, forstat.getValue());

    }
//
//    @Test
//    public void syntacticerror_end(){
//        SetupMocks.setup();
//        List<Token> tokens= new ArrayList<>();
//
//        tokens.add(new Token(Token.TFOR,1,1,null));
//        tokens.add(new Token(Token.TLPAR,1,1,null));
//
//        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here"));
//        tokens.add(new Token(Token.TSEMI,1,1,null));
//        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
//
//        tokens.add(new Token(Token.TRPAR,1,1,null));
//        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
//        tokens.add(new Token(Token.TCOMA,1,1,null));
//
//        Parser parser = new Parser(tokens);
//
//        when(nAsgnListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
//            parser.consume();
//            return new TreeNode(TreeNode.NASGNS);
//        });
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
//        TreeNode forstat = nForStatNode.make(parser);
//
//        assertEquals(TreeNode.NUNDEF, forstat.getValue());
//
//    }

}
