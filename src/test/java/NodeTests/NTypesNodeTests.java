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

public class NTypesNodeTests {

	//<types>	::=	types <typelist> | eps
    //NTYPEL	<typelist>	::=	<type> <typelistTail>

    @Mock
    NTypeListNode nTypeListNode;

    @InjectMocks
    NTypesNode nTypesNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sunnyday_typelidtlist(){

        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TTYPS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"asgnlist stuff here "));


        Parser parser = new Parser(tokens);

        when(nTypeListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NTYPEL);
        });

        TreeNode types = nTypesNode.make(parser);

        assertEquals(TreeNode.NTYPEL, types.getValue());
    }

    @Test
    public void sunnyday_eps(){

        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        when(nTypeListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NTYPEL);
        });

        TreeNode types = nTypesNode.make(parser);

        assertEquals(null, types);
    }
}
