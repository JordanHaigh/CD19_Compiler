package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Jordan Haigh c3256730 CD19
 * public class NArraysNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NArraysNodeTests{

    //<arrays>	::=	eps | arrays <arrdecls>

    NArrDeclsNode nArrDeclsNode = new NArrDeclsNode();
    NArraysNode nArraysNode = new NArraysNode();

    @Test
    public void NArrays_SunnyDayData_eps() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TEQEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);


        TreeNode alist = nArraysNode.make(parser);

        assertEquals(null, alist);
    }

    @Test
    public void NArrays_SunnyDayData_noteps() {
        //arrays people : person, class : person

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TARRS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"class"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        //start of functions declaration
        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);
        NExprNode mockExprNode = mock(NExprNode.class);

        TreeNode arrays = nArraysNode.make(parser);

        assertEquals(TreeNode.NALIST, arrays.getValue());
        assertEquals(TreeNode.NARRD, arrays.getLeft().getValue());
        assertEquals(TreeNode.NARRD, arrays.getRight().getValue());
    }

}
