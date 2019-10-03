package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Jordan Haigh c3256730 CD19
 * public class NProgramNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NProgramNodeTests {
    //NPROG	<program>	::=	CD19 <id> <globals> <funcs> <mainbody>
    @Mock NGlobNode globs;
    @Mock NFuncsNode funcs;
    @Mock NMainBodyNode main;

    @InjectMocks NProgNode prog;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void NProgNode_SunnyDayData() {
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));

        tokens.add(new Token(Token.TIDEN,1,1,"constants here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcs here"));
        tokens.add(new Token(Token.TIDEN,1,1,"main here"));

        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));

        Parser parser = new Parser(tokens);

        when(globs.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NGLOB);
        });
        when(funcs.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFUNCS);
        });
        when(main.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NMAIN);
        });


        TreeNode progs = prog.make(parser);

        assertEquals(TreeNode.NPROG, progs.getValue());
        assertEquals(TreeNode.NGLOB, progs.getLeft().getValue());
        assertEquals(TreeNode.NFUNCS, progs.getMiddle().getValue());
        assertEquals(TreeNode.NMAIN, progs.getRight().getValue());
    }

    @Test
    public void syntactic_failfirstcd19() {
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

//        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));

        tokens.add(new Token(Token.TIDEN,1,1,"constants here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcs here"));
        tokens.add(new Token(Token.TIDEN,1,1,"main here"));

        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));

        Parser parser = new Parser(tokens);

        when(globs.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NGLOB);
        });
        when(funcs.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFUNCS);
        });
        when(main.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NMAIN);
        });


        TreeNode progs = prog.make(parser);

        assertEquals(TreeNode.NUNDEF, progs.getValue());
    }

    @Test
    public void syntactic_failfirstiden() {
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCD19,1,1,null));
//        tokens.add(new Token(Token.TIDEN,1,1,"prog"));

        tokens.add(new Token(Token.TIDEN,1,1,"constants here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcs here"));
        tokens.add(new Token(Token.TIDEN,1,1,"main here"));

        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));

        Parser parser = new Parser(tokens);

        when(globs.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NGLOB);
        });
        when(funcs.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFUNCS);
        });
        when(main.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NMAIN);
        });

        TreeNode progs = prog.make(parser);

        assertEquals(TreeNode.NUNDEF, progs.getValue());

    }

    @Test
    public void syntactic_failsecondcd19() {
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));

        tokens.add(new Token(Token.TIDEN,1,1,"constants here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcs here"));
        tokens.add(new Token(Token.TIDEN,1,1,"main here"));

//        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));

        Parser parser = new Parser(tokens);

        when(globs.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NGLOB);
        });
        when(funcs.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFUNCS);
        });
        when(main.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NMAIN);
        });

        TreeNode progs = prog.make(parser);

        assertEquals(TreeNode.NUNDEF, progs.getValue());

    }

    @Test
    public void syntactic_failsecondiden() {
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));

        tokens.add(new Token(Token.TIDEN,1,1,"constants here"));
        tokens.add(new Token(Token.TIDEN,1,1,"funcs here"));
        tokens.add(new Token(Token.TIDEN,1,1,"main here"));

        tokens.add(new Token(Token.TCD19,1,1,null));
//        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TCD19,1,1,null));


        Parser parser = new Parser(tokens);

        when(globs.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NGLOB);
        });
        when(funcs.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFUNCS);
        });
        when(main.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NMAIN);
        });

        TreeNode progs = prog.make(parser);

        assertEquals(TreeNode.NUNDEF, progs.getValue());

    }
}
