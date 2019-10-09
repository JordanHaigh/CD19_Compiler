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
 * public class NFuncsNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NFuncsNodeTests {
//NFUNCS	<funcs>	::=	ε | <func> <funcs>

    @Mock
    NPListNode npListNode;
    @Mock
    NRTypeNode nrTypeNode;
    @Mock
    NFuncBodyNode nFuncBodyNode;

    @InjectMocks
    NFuncsNode nFuncsNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sunnyday_onefunc(){
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


        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        when(npListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NPLIST);
        });

        when(nrTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType("Void");
            return dummy;
        });
        when(nFuncBodyNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NUNDEF, new TreeNode(TreeNode.NDLIST), new TreeNode(TreeNode.NSTATS));
        });

        NFuncNode nFuncNode = new NFuncNode(npListNode, nrTypeNode, nFuncBodyNode);
        nFuncsNode = new NFuncsNode(nFuncNode);

        TreeNode funcs = nFuncsNode.make(parser);

        assertEquals(TreeNode.NFUND, funcs.getValue());
        assertEquals(TreeNode.NPLIST, funcs.getLeft().getValue());
        assertEquals(TreeNode.NDLIST, funcs.getMiddle().getValue());
        assertEquals(TreeNode.NSTATS, funcs.getRight().getValue());

    }


    @Test
    public void sunnyday_twofunc(){
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

        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"id"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"plist here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"rtype here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcbody"));


        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        when(npListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NPLIST);
        });

        when(nrTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType("Void");
            return dummy;
        });
        when(nFuncBodyNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NUNDEF, new TreeNode(TreeNode.NDLIST), new TreeNode(TreeNode.NSTATS));
        });

        NFuncNode nFuncNode = new NFuncNode(npListNode, nrTypeNode, nFuncBodyNode);
        nFuncsNode = new NFuncsNode(nFuncNode);

        TreeNode funcs = nFuncsNode.make(parser);

        assertEquals(TreeNode.NFUNCS, funcs.getValue());
        assertEquals(TreeNode.NFUND, funcs.getLeft().getValue());
        assertEquals(TreeNode.NFUND, funcs.getRight().getValue());


    }


    @Test
    public void sunnyday_threefunc(){
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

        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"id"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"plist here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"rtype here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcbody"));


        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"id"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"plist here"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"rtype here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcbody"));


        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        when(npListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NPLIST);
        });

        when(nrTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType("Void");
            return dummy;
        });
        when(nFuncBodyNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NUNDEF, new TreeNode(TreeNode.NDLIST), new TreeNode(TreeNode.NSTATS));
        });

        NFuncNode nFuncNode = new NFuncNode(npListNode, nrTypeNode, nFuncBodyNode);
        nFuncsNode = new NFuncsNode(nFuncNode);

        TreeNode funcs = nFuncsNode.make(parser);

        assertEquals(TreeNode.NFUNCS, funcs.getValue());
        assertEquals(TreeNode.NFUND, funcs.getLeft().getValue());
        assertEquals(TreeNode.NFUNCS, funcs.getRight().getValue());
        assertEquals(TreeNode.NFUND, funcs.getRight().getLeft().getValue());
        assertEquals(TreeNode.NFUND, funcs.getRight().getRight().getValue());


    }


    @Test
    public void sunnyday_eps(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        when(npListNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NPLIST);
        });

        when(nrTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
            dummy.setType("Void");
            return dummy;
        });
        when(nFuncBodyNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NUNDEF, new TreeNode(TreeNode.NDLIST), new TreeNode(TreeNode.NSTATS));
        });

        NFuncNode nFuncNode = new NFuncNode(npListNode, nrTypeNode, nFuncBodyNode);
        nFuncsNode = new NFuncsNode(nFuncNode);

        TreeNode funcs = nFuncsNode.make(parser);

        assertEquals(null, funcs);

    }


}
