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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NInitListNodeTests {


    //NILIST	<initlist>	::=	<init> <initListTail>
    //	<initListTail>	::=	eps | ,<initlist>

    @Mock
    NInitNode nInitNode;

    @InjectMocks
    NInitListNode nInitListNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void initlist_epsTest(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TEQEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        when(nInitNode.make(parser)).thenReturn(new TreeNode(TreeNode.NINIT));

        TreeNode initlist = nInitListNode.make(parser);

        assertEquals(TreeNode.NILIST,initlist.getValue());
        assertEquals(TreeNode.NINIT,initlist.getLeft().getValue());
        assertEquals(null ,initlist.getRight());
    }

    @Test
    public void initList_notEpsTest(){

        // Create partial mock
        NExprNode mockExprNode = mock(NExprNode.class);
        NInitNode nInitNode = new NInitNode(mockExprNode);
        NInitListNode nInitListNode = new NInitListNode(nInitNode);

        List<Token> tokens= new ArrayList<>();

        // x = 1, y = 2
        tokens.add(new Token(Token.TIDEN,1,1,"x"));
        tokens.add(new Token(Token.TEQUL,1,1, null));
        tokens.add(new Token(Token.TILIT,1,1, "1"));
        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"y"));
        tokens.add(new Token(Token.TEQUL,1,1, null));
        tokens.add(new Token(Token.TILIT,1,1, "2"));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        when(mockExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        TreeNode initlist = nInitListNode.make(parser);

        assertEquals(TreeNode.NILIST,initlist.getValue());
        assertEquals(TreeNode.NINIT,initlist.getLeft().getValue());
        assertEquals(TreeNode.NILIST ,initlist.getRight().getValue());
    }
}




