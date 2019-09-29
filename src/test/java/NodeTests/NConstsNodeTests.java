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

public class NConstsNodeTests {
    //	<consts>	::=	constants <initlist> | ε
    //NILIST	<initlist>	::=	<init> <initListTail>

    @Mock NInitListNode nInitListNode;

    @InjectMocks NConstsNode nConstsNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sunnyday_initlist(){

        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCONS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here "));


        Parser parser = new Parser(tokens);

        when(nInitListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIST);
        });

        TreeNode consts = nConstsNode.make(parser);

        assertEquals(TreeNode.NILIST, consts.getValue());
    }

    @Test
    public void sunnyday_eps(){

        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        when(nInitListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIST);
        });

        TreeNode consts = nConstsNode.make(parser);

        assertEquals(null, consts);
    }
    
}
