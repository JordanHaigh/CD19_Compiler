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

public class NMainBodyNodeTests {
    //NMAIN	<mainbody>	::=	main <slist> begin <stats> end
    @Mock
    NSListNode nsListNode;
    @Mock
    NStatsNode nStatsNode;

    @InjectMocks
    NMainBodyNode nMainBodyNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void sunnyday(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TMAIN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"slist stuff here"));
        tokens.add(new Token(Token.TBEGN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"stats stuff here"));
        tokens.add(new Token(Token.TEND,1,1,null));


        Parser parser = new Parser(tokens);

        when(nsListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSDLST);
        });
        when(nStatsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NSTATS);
        });

        nMainBodyNode = new NMainBodyNode(nsListNode, nStatsNode);
        TreeNode mainbody = nMainBodyNode.make(parser);

        assertEquals(TreeNode.NMAIN, mainbody.getValue());
        assertEquals(TreeNode.NSDLST, mainbody.getLeft().getValue());
        assertEquals(TreeNode.NSTATS, mainbody.getRight().getValue());
    }


}
