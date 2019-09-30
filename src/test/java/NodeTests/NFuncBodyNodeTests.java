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

public class NFuncBodyNodeTests {
    //	<funcbody>	::=	<locals> begin <stats> end
    @Mock
    NLocalsNode nlocalsNode;
    @Mock
    NStatsNode nStatsNode;

    @InjectMocks
    NFuncBodyNode nFuncBodyNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sunnyday(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"locals here"));
        tokens.add(new Token(Token.TBEGN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"Stats stuff here"));
        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        when(nlocalsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NDLIST);
        });

        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });

        nFuncBodyNode = new NFuncBodyNode(nlocalsNode, nStatsNode);

        TreeNode funcbody = nFuncBodyNode.make(parser);

        assertEquals(TreeNode.NDLIST, funcbody.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, funcbody.getRight().getValue());

    }

    @Test
    public void syntactic_begin(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"locals here"));
        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"Stats stuff here"));
        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);

        when(nlocalsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NDLIST);
        });

        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });


        TreeNode funcbody = nFuncBodyNode.make(parser);

        assertEquals(TreeNode.NUNDEF, funcbody.getValue());
        assertEquals(TreeNode.NDLIST, funcbody.getLeft().getValue());
        assertEquals(null, funcbody.getRight());

    }

    @Test
    public void syntactic_end(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"locals here"));
        tokens.add(new Token(Token.TBEGN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"Stats stuff here"));
        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        when(nlocalsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NDLIST);
        });

        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });


        TreeNode funcbody = nFuncBodyNode.make(parser);

        assertEquals(TreeNode.NUNDEF, funcbody.getValue());
        assertEquals(TreeNode.NDLIST, funcbody.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, funcbody.getRight().getValue());

    }
}
