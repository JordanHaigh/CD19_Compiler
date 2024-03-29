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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Jordan Haigh c3256730 CD19
 * public class NInitNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NInitNodeTests {
    //    //NINIT	<init>	::=	<id> = <expr>
    @Mock
    NExprNode nExprNode;

    @InjectMocks
    NInitNode nInitNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void NINIT_sunnyday() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        when(nExprNode.make(parser)).thenReturn(new TreeNode(TreeNode.NILIT));

        TreeNode init = nInitNode.make(parser);

        assertEquals(TreeNode.NINIT, init.getValue());
    }

    @Test
    public void syntactic_failId() {
        List<Token> tokens= new ArrayList<>();

        //tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TEQEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        when(nExprNode.make(parser)).thenReturn(new TreeNode(TreeNode.NILIT));

        TreeNode init = nInitNode.make(parser);

        assertEquals(TreeNode.NUNDEF, init.getValue());
    }

    @Test
    public void syntactic_faileq() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
//        tokens.add(new Token(Token.TEQEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        when(nExprNode.make(parser)).thenReturn(new TreeNode(TreeNode.NILIT));

        TreeNode init = nInitNode.make(parser);

        assertEquals(TreeNode.NUNDEF, init.getValue());
    }


}
