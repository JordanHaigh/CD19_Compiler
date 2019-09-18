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

public class NAlistNodeTests {



    //NASGNS	<alist>	::=	<id><asgnstat> <alistTail>
    //<alistTail>	::=	eps | , <alist>


    @Mock
    NAsgnStatNode nAsgnStatNode;

    @InjectMocks
    NAlistNode nAlistNode;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void NALIST_SunnyDayData_taileps() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TEQEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        when(nAsgnStatNode.make(parser)).thenReturn(new TreeNode(TreeNode.NEQL));

        TreeNode alist = nAlistNode.make(parser);

        assertEquals(TreeNode.NASGNS, alist.getValue());
    }

    @Test
    public void NALIST_SunnyDayData_tailNotEps() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TEQEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bb"));
        tokens.add(new Token(Token.TEQEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));


        Parser parser = new Parser(tokens);

        when(nAsgnStatNode.make(parser)).thenReturn(new TreeNode(TreeNode.NEQL));

        TreeNode alist = nAlistNode.make(parser);

        assertEquals(TreeNode.NASGNS, alist.getValue());
    }
}

