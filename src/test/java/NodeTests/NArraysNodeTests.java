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


public class NArraysNodeTests{

    //<arrays>	::=	eps | arrays <arrdecls>
    @Mock
    private NArrDeclsNode nArrDeclsNode;

    @InjectMocks
    NArraysNode nArraysNode;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void NArrays_SunnyDayData_eps() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TEQEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        when(nArrDeclsNode.make(parser)).thenReturn(new TreeNode(TreeNode.NALIST));

        TreeNode alist = nArraysNode.make(parser);

        assertEquals(null, alist);
    }

    @Test
    public void NArrays_SunnyDayData_noteps() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TARRS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        Parser parser = new Parser(tokens);

        when(nArrDeclsNode.make(parser)).thenReturn(new TreeNode(TreeNode.NALIST));

        TreeNode arrays = nArraysNode.make(parser);

        assertEquals(TreeNode.NALIST, arrays.getValue());
    }
}
