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
 * public class NTypeNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NTypeNodeTests {

    //NRTYPE|NATYPE	<type>	::=	<anyFuckingID> is <typeTail>
    //	<typeTail>	::=	<fields> end |  array [<expr>] of <structid>

    @Mock NFieldsNode nFieldsNode;
    @Mock NExprNode nExprNode;

    @InjectMocks NTypeNode nTypeNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void fieldsTest_struct(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"struct"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"fields here"));
        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);


        when(nFieldsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFLIST);
        });

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        TreeNode type = nTypeNode.make(parser);

        assertEquals(TreeNode.NRTYPE, type.getValue());

    }

    @Test
    public void type_array(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"arr"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TARAY,1,1,null));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"expr here "));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TOF,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"struct id here"));

        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);


        when(nFieldsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFLIST);
        });

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        TreeNode type = nTypeNode.make(parser);

        assertEquals(TreeNode.NATYPE, type.getValue());

    }


    @Test
    public void syntactic_fields_missingend(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"struct"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"fields here"));
//        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);


        when(nFieldsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFLIST);
        });

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        TreeNode type = nTypeNode.make(parser);

        assertEquals(TreeNode.NUNDEF, type.getValue());

    }

    @Test
    public void syntactic_missingfirstiden(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

//        tokens.add(new Token(Token.TIDEN,1,1,"arr"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TARAY,1,1,null));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"expr here "));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TOF,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"struct id here"));

        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);


        when(nFieldsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFLIST);
        });

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        TreeNode type = nTypeNode.make(parser);

        assertEquals(TreeNode.NUNDEF, type.getValue());

    }

    @Test
    public void syntactic_missingis(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"arr"));
//        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TARAY,1,1,null));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"expr here "));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TOF,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"struct id here"));

        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);


        when(nFieldsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFLIST);
        });

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        TreeNode type = nTypeNode.make(parser);

        assertEquals(TreeNode.NUNDEF, type.getValue());

    }

    @Test
    public void syntactic_missingarray(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"arr"));
        tokens.add(new Token(Token.TIS,1,1,null));
//        tokens.add(new Token(Token.TARAY,1,1,null));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"expr here "));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TOF,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"struct id here"));

        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);


        when(nFieldsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFLIST);
        });

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        TreeNode type = nTypeNode.make(parser);

        assertEquals(TreeNode.NUNDEF, type.getValue());

    }

    @Test
    public void syntactic_missinglbrk(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"arr"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TARAY,1,1,null));
//        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"expr here "));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TOF,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"struct id here"));

        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);


        when(nFieldsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFLIST);
        });

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        TreeNode type = nTypeNode.make(parser);

        assertEquals(TreeNode.NUNDEF, type.getValue());

    }

    @Test
    public void syntactic_missingrbrk(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"arr"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TARAY,1,1,null));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"expr here "));
//        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TOF,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"struct id here"));

        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);


        when(nFieldsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFLIST);
        });

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        TreeNode type = nTypeNode.make(parser);

        assertEquals(TreeNode.NUNDEF, type.getValue());

    }

    @Test
    public void syntactic_missingof(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"arr"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TARAY,1,1,null));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"expr here "));
        tokens.add(new Token(Token.TRBRK,1,1,null));
//        tokens.add(new Token(Token.TOF,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"struct id here"));

        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);


        when(nFieldsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFLIST);
        });

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        TreeNode type = nTypeNode.make(parser);

        assertEquals(TreeNode.NUNDEF, type.getValue());

    }


    @Test
    public void syntactic_missingsecondiden(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"arr"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TARAY,1,1,null));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"expr here "));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TOF,1,1,null));
//        tokens.add(new Token(Token.TIDEN,1,1,"struct id here"));

        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);


        when(nFieldsNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NFLIST);
        });

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NILIT);
        });

        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        TreeNode type = nTypeNode.make(parser);

        assertEquals(TreeNode.NUNDEF, type.getValue());

    }



}
