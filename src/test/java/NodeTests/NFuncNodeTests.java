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
 * public class NFuncNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NFuncNodeTests {

    //NFUND	<func>	::=	function <id> ( <plist> ) : <rtype> <funcbody>

    @Mock
    NPListNode npListNode;
    @Mock
    NRTypeNode nrTypeNode;
    @Mock
    NFuncBodyNode nFuncBodyNode;

    @InjectMocks
    NFuncNode nFuncNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void sunnyday(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"id"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"plist here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"rtype here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcbody"));

        Parser parser = new Parser(tokens);

        when(npListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NPLIST);
        });

        when(nrTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType(NodeDataTypes.Void);
            return dummy;
        });
        when(nFuncBodyNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NUNDEF, new TreeNode(TreeNode.NDLIST), new TreeNode(TreeNode.NSTATS));
        });

        TreeNode func = nFuncNode.make(parser);

        assertEquals(TreeNode.NFUND, func.getValue());
        assertEquals(TreeNode.NPLIST, func.getLeft().getValue());
        assertEquals(TreeNode.NDLIST, func.getMiddle().getValue());
        assertEquals(TreeNode.NSTATS, func.getRight().getValue());

    }


    @Test
    public void syntactic_failfunc(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"id"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"plist here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"rtype here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcbody"));

        Parser parser = new Parser(tokens);

        when(npListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NPLIST);
        });

        when(nrTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType(NodeDataTypes.Void);
            return dummy;
        });
        when(nFuncBodyNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NUNDEF, new TreeNode(TreeNode.NDLIST), new TreeNode(TreeNode.NSTATS));
        });

        TreeNode func = nFuncNode.make(parser);

        assertEquals(TreeNode.NUNDEF, func.getValue());

    }

    @Test
    public void syntactic_failiden(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TLPAR,1,1,null));

        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"plist here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"rtype here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcbody"));

        Parser parser = new Parser(tokens);

        when(npListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NPLIST);
        });

        when(nrTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType(NodeDataTypes.Void);
            return dummy;
        });
        when(nFuncBodyNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NUNDEF, new TreeNode(TreeNode.NDLIST), new TreeNode(TreeNode.NSTATS));
        });

        TreeNode func = nFuncNode.make(parser);

        assertEquals(TreeNode.NUNDEF, func.getValue());

    }

    @Test
    public void syntactic_faillpar(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
//        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"plist here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"rtype here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcbody"));

        Parser parser = new Parser(tokens);

        when(npListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NPLIST);
        });

        when(nrTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType(NodeDataTypes.Void);
            return dummy;
        });
        when(nFuncBodyNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NUNDEF, new TreeNode(TreeNode.NDLIST), new TreeNode(TreeNode.NSTATS));
        });

        TreeNode func = nFuncNode.make(parser);

        assertEquals(TreeNode.NUNDEF, func.getValue());

    }

    @Test
    public void syntactic_failrpar(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"plist here"));
//        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"rtype here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcbody"));

        Parser parser = new Parser(tokens);

        when(npListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NPLIST);
        });

        when(nrTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType(NodeDataTypes.Void);
            return dummy;
        });
        when(nFuncBodyNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NUNDEF, new TreeNode(TreeNode.NDLIST), new TreeNode(TreeNode.NSTATS));
        });

        TreeNode func = nFuncNode.make(parser);

        assertEquals(TreeNode.NUNDEF, func.getValue());

    }


    @Test
    public void syntactic_failcoln(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"plist here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
//        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"rtype here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcbody"));

        Parser parser = new Parser(tokens);

        when(npListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NPLIST);
        });

        when(nrTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType(NodeDataTypes.Void);
            return dummy;
        });
        when(nFuncBodyNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NUNDEF, new TreeNode(TreeNode.NDLIST), new TreeNode(TreeNode.NSTATS));
        });

        TreeNode func = nFuncNode.make(parser);

        assertEquals(TreeNode.NUNDEF, func.getValue());

    }
}
