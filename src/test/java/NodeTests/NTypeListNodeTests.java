package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NTypeListNodeTests {

    //NTYPEL	<typelist>	::=	<type> <typelistTail>
    //	<typelistTail>	::=	eps |  <typelist>

    @Mock
    NTypeNode nTypeNode;

    @InjectMocks
    NTypeListNode nTypeListNode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void typelist_epsTest(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TIS,1,1,"aa"));

        Parser parser = new Parser(tokens);

        when(nTypeNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            return new TreeNode(TreeNode.NRTYPE);
        });

        TreeNode typelist = nTypeListNode.make(parser);



        assertEquals(TreeNode.NTYPEL, typelist.getValue());
        assertEquals(TreeNode.NRTYPE, typelist.getLeft().getValue());
        assertEquals(null, typelist.getRight());

    }

    @Test
    public void typeList_notEpsTest(){
        List<Token> tokens= new ArrayList<>();
        //person is age : integer, height : real end
        //people is array[5] of person

        tokens.add(new Token(Token.TIDEN,1,1,"person"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"age"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));
        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"height"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TREAL,1,1,null));
        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"person"));
        tokens.add(new Token(Token.TIS,1,1,null));
        tokens.add(new Token(Token.TARAY,1,1,null));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,"5"));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TOF,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        // Token at the end to stop array out of bounds error
        tokens.add(new Token(Token.TARRS,1,1,null));

        Parser parser = new Parser(tokens);


        NExprNode nExprNode = mock(NExprNode.class);
        NSTypeNode nSTypeNode = new NSTypeNode();
        NSDeclNode nSDeclNode = new NSDeclNode(nSTypeNode);
        NFieldsNode nFieldsNode = new NFieldsNode(nSDeclNode);
        NTypeNode nTypeNode = new NTypeNode(nFieldsNode, nExprNode);
        NTypeListNode nTypeListNode = new NTypeListNode(nTypeNode);

        when(nExprNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume();
            SymbolTableRecord rec = new SymbolTableRecord("5", NodeDataTypes.Integer);
            return new TreeNode(TreeNode.NILIT, rec);
        });

        TreeNode typelist = nTypeListNode.make(parser);

        assertEquals(TreeNode.NTYPEL, typelist.getValue());
        assertEquals(TreeNode.NRTYPE, typelist.getLeft().getValue());
        assertEquals(TreeNode.NTYPEL, typelist.getRight().getValue());
    }
}
