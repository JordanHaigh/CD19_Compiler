package NodeTests;


import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Jordan Haigh c3256730 CD19
 * public class NAsgnListNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NAsgnListNodeTests {
    //	<asgnlist>	::=	<alist> | ε

    @Test
    public void sunnyday_alist() {
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());

        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode();
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());

        NAsgnListNode nAsgnListNode = new NAsgnListNode();
        TreeNode asgnlist = nAsgnListNode.make(parser);

        assertEquals(TreeNode.NASGN,asgnlist.getValue());
        assertEquals(TreeNode.NSIMV, asgnlist.getLeft().getValue());
        assertEquals(TreeNode.NILIT,asgnlist.getRight().getValue());

    }

    @Test
    public void sunnyday_eps() {
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());

        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode();
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());

        NAsgnListNode nAsgnListNode = new NAsgnListNode();
        TreeNode asgnlist = nAsgnListNode.make(parser);

        assertEquals(null,asgnlist);

    }


}
