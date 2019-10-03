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
 * public class NStatNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NStatNodeTests {
    //	<stat>	::=	<reptstat> | <iostat> | <returnstat> |  <asgnStatOrCallStat>

    @Mock
    NAsgnListNode nAsgnListNode;
    @Mock NStatsNode nStatsNode;
    @Mock NBoolNode nBoolNode;

    @InjectMocks
    NReptStatNode nReptStatNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sunnyday_reptstat(){
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
        NStatNode nStatNode = new NStatNode(nReptStatNode, null,null,null);

        TreeNode stat = nStatNode.make(parser);

        assertEquals(TreeNode.NREPT, stat.getValue());
        assertEquals(TreeNode.NASGN, stat.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, stat.getMiddle().getValue());
        assertEquals(TreeNode.NBOOL, stat.getRight().getValue());
    }



    @Test
    public void sunnyday_iostat(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TPRIN,1,1,null));

        ////////////////////////prlist////////////////////////
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TSTRG,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));
        ////////////////////////prlist////////////////////////


        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NVarNode nVarNode = new NVarNode(nVarTailNode);

        NVListNode nvListNode = new NVListNode(nVarNode);
        NPrListNode nPrListNode = new NPrListNode();
        NIoStatNode nIoStatNode = new NIoStatNode(nvListNode,nPrListNode);
        NStatNode nStatNode = new NStatNode(null,nIoStatNode,null,null );

        TreeNode stat = nStatNode.make(parser);

        assertEquals(TreeNode.NPRINT, stat.getValue());
        assertEquals(TreeNode.NPRLST, stat.getLeft().getValue());
    }

    @Test
    public void sunnyday_returnstat(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TRETN,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));


        tokens.add(new Token(Token.TMAIN,1,1,null));

        Parser parser = new Parser(tokens);

        NReturnStatNode nreturnStatNode = new NReturnStatNode();
        nreturnStatNode.setnExprNode(NExprNode.INSTANCE());


        NStatNode nStatNode = new NStatNode(null,null,nreturnStatNode,null );
        TreeNode stat = nStatNode.make(parser);


        assertEquals(TreeNode.NRETN, stat.getValue());
        assertEquals(TreeNode.NADD, stat.getLeft().getValue());

    }


    @Test
    public void sunnyday_id_callstat(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
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
        tokens.add(new Token(Token.TRPAR,1,1,null));


        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NStatNode nStatNode = new NStatNode();
        TreeNode stat = nStatNode.make(parser);

        assertEquals(TreeNode.NCALL, stat.getValue());
        assertEquals(TreeNode.NEXPL, stat.getLeft().getValue());
    }

    @Test
    public void sunnyday_id_asgnstat(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variaboe"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"member"));

        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NBoolNode nBoolNode = new NBoolNode();
        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode(nVarTailNode, new NAsgnOpNode(), nBoolNode);
        NAsgnStatOrCallStatNode nAsgnStatOrCallStatNode = new NAsgnStatOrCallStatNode(null, nAsgnStatNode);
        NStatNode nStatNode = new NStatNode(null,null,null,nAsgnStatOrCallStatNode);
        TreeNode stat = nStatNode.make(parser);

        assertEquals(TreeNode.NASGN,stat.getValue());
        assertEquals(TreeNode.NARRV, stat.getLeft().getValue());
        assertEquals(TreeNode.NSIMV,stat.getRight().getValue());

    }

    @Test
    public void syntactic_fail(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCOMA,1,1,"variaboe"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"member"));

        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NBoolNode nBoolNode = new NBoolNode();
        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode(nVarTailNode, new NAsgnOpNode(), nBoolNode);
        NAsgnStatOrCallStatNode nAsgnStatOrCallStatNode = new NAsgnStatOrCallStatNode(null, nAsgnStatNode);
        NStatNode nStatNode = new NStatNode(null,null,null,nAsgnStatOrCallStatNode);
        TreeNode stat = nStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF,stat.getValue());

    }
}
