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


public class NStatsNodeTests {

    //NSTATS	<stats>	::=	<stat> ;  <StatsTail> | <strstat> <statsTail>
	//<statsTail>	::=	ε |  {<stat> ;  <StatsTail> | <strstat> <statsTail>}

    //todo just strstat, two strstats, threestrstats

    @Mock NBoolNode nBoolNode;
    @Mock NStatsNode nStatsNode;

    @InjectMocks
    NStrStatNode nStrStatNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sunnyday_justStat_returnstat(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TRETN,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TSEMI,1,1,null));


        tokens.add(new Token(Token.TMAIN,1,1,null));

        Parser parser = new Parser(tokens);

        NReturnStatNode nreturnStatNode = new NReturnStatNode();
        nreturnStatNode.setnExprNode(NExprNode.INSTANCE());


        NStatNode nStatNode = new NStatNode(null,null,nreturnStatNode,null );
        NStatsNode nStatsNode = new NStatsNode(nStatNode, null);
        TreeNode stats = nStatsNode.make(parser);


        assertEquals(TreeNode.NRETN, stats.getValue());
        assertEquals(TreeNode.NADD, stats.getLeft().getValue());

    }

    @Test
    public void sunnyday_twostats(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TRETN,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TSEMI,1,1,null));

        tokens.add(new Token(Token.TRETN,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TSEMI,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NReturnStatNode nreturnStatNode = new NReturnStatNode();
        nreturnStatNode.setnExprNode(NExprNode.INSTANCE());


        NStatNode nStatNode = new NStatNode(null,null,nreturnStatNode,null );
        NStatsNode nStatsNode = new NStatsNode(nStatNode, null);
        TreeNode stats = nStatsNode.make(parser);

        assertEquals(TreeNode.NSTATS, stats.getValue());
        assertEquals(TreeNode.NRETN, stats.getLeft().getValue());
        assertEquals(TreeNode.NRETN, stats.getRight().getValue());

    }

    @Test
    public void sunnyday_threestats(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TRETN,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TSEMI,1,1,null));

        tokens.add(new Token(Token.TRETN,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TSEMI,1,1,null));

        tokens.add(new Token(Token.TRETN,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TSEMI,1,1,null));


        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NReturnStatNode nreturnStatNode = new NReturnStatNode();
        nreturnStatNode.setnExprNode(NExprNode.INSTANCE());


        NStatNode nStatNode = new NStatNode(null,null,nreturnStatNode,null );
        NStatsNode nStatsNode = new NStatsNode(nStatNode, null);
        TreeNode stats = nStatsNode.make(parser);

        assertEquals(TreeNode.NSTATS, stats.getValue());
        assertEquals(TreeNode.NRETN, stats.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, stats.getRight().getValue());


        assertEquals(TreeNode.NRETN, stats.getRight().getLeft().getValue());
        assertEquals(TreeNode.NRETN, stats.getRight().getRight().getValue());

    }

    @Test
    public void sunnyday_onestrstat(){
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
        NStatsNode nStatsNode = new NStatsNode(null, nStrStatNode);

        TreeNode stats = nStatsNode.make(parser);


        assertEquals(TreeNode.NIFTE, stats.getValue());
        assertEquals(TreeNode.NBOOL, stats.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, stats.getMiddle().getValue());
        assertEquals(TreeNode.NSTATS, stats.getRight().getValue());


    }

    @Test
    public void sunnyday_twostrstat(){
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


        tokens.add(new Token(Token.TIFTH,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TELSE,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TEND,1,1,null));


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
        NStatsNode nStatsNode = new NStatsNode(null, nStrStatNode);

        TreeNode stats = nStatsNode.make(parser);

        assertEquals(TreeNode.NSTATS, stats.getValue());
        assertEquals(TreeNode.NIFTE, stats.getLeft().getValue());
        assertEquals(TreeNode.NIFTE, stats.getRight().getValue());

    }

    @Test
    public void sunnyday_threestrstat(){
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


        tokens.add(new Token(Token.TIFTH,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TELSE,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TIFTH,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TELSE,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TEND,1,1,null));


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
        NStatsNode nStatsNode = new NStatsNode(null, nStrStatNode);

        TreeNode stats = nStatsNode.make(parser);

        assertEquals(TreeNode.NSTATS, stats.getValue());
        assertEquals(TreeNode.NIFTE, stats.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, stats.getRight().getValue());
        assertEquals(TreeNode.NIFTE, stats.getRight().getLeft().getValue());
        assertEquals(TreeNode.NIFTE, stats.getRight().getRight().getValue());

    }

    @Test
    public void sunnyday_stat_strstat_stat(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TRETN,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TSEMI,1,1,null));

        tokens.add(new Token(Token.TIFTH,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TELSE,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bool stuff here"));
        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TRETN,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TSEMI,1,1,null));



        tokens.add(new Token(Token.TSEMI,1,1,null));

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
        NReturnStatNode nreturnStatNode = new NReturnStatNode();
        nreturnStatNode.setnExprNode(NExprNode.INSTANCE());


        NStatNode nStatNode = new NStatNode(null,null,nreturnStatNode,null );


        NStatsNode nStatsNode = new NStatsNode(nStatNode, nStrStatNode);

        TreeNode stats = nStatsNode.make(parser);

        assertEquals(TreeNode.NSTATS, stats.getValue());
        assertEquals(TreeNode.NRETN, stats.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, stats.getRight().getValue());
        assertEquals(TreeNode.NIFTE, stats.getRight().getLeft().getValue());
        assertEquals(TreeNode.NRETN, stats.getRight().getRight().getValue());

    }
}
