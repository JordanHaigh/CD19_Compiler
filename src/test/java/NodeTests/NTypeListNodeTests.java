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

public class NTypeListNodeTests {

    //NTYPEL	<typelist>	::=	<type> <typelistTail>
    //	<typelistTail>	::=	eps |  <type> <typelistTail>

    @Mock NFieldsNode nFieldsNode;
    @Mock NExprNode nExprNode;

    @InjectMocks NTypeNode nTypeNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void typelist_onetype() {
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

        NTypeListNode nTypeListNode = new NTypeListNode(nTypeNode);

        TreeNode typelist = nTypeListNode.make(parser);

        assertEquals(TreeNode.NATYPE, typelist.getValue());

    }

    @Test
    public void typelist_twotype() {
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"struct"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"fields here"));
        tokens.add(new Token(Token.TEND,1,1,null));

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

        NTypeListNode nTypeListNode = new NTypeListNode(nTypeNode);

        TreeNode typelist = nTypeListNode.make(parser);

        assertEquals(TreeNode.NTYPEL, typelist.getValue());
        assertEquals(TreeNode.NRTYPE, typelist.getLeft().getValue());
        assertEquals(TreeNode.NRTYPE, typelist.getRight().getValue());

    }

    @Test
    public void typelist_threetype() {
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"struct"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"fields here"));
        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"struct"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"fields here"));
        tokens.add(new Token(Token.TEND,1,1,null));

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

        NTypeListNode nTypeListNode = new NTypeListNode(nTypeNode);

        TreeNode typelist = nTypeListNode.make(parser);

        assertEquals(TreeNode.NTYPEL, typelist.getValue());
        assertEquals(TreeNode.NRTYPE, typelist.getLeft().getValue());
        assertEquals(TreeNode.NTYPEL, typelist.getRight().getValue());
        assertEquals(TreeNode.NRTYPE, typelist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NRTYPE, typelist.getRight().getRight().getValue());

    }

}
