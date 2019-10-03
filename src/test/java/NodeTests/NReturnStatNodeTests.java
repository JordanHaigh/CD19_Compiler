package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Jordan Haigh c3256730 CD19
 * public class NReturnStatNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NReturnStatNodeTests {
    //NRETN	<returnstat>	::=	return <returnStatTail>
    //	<returnStatTail>	::=	ε | <expr>

    @Test
    public void sunnyday_returnnothing() {

        SetupMocks.setup();

        List<Token> tokens = new ArrayList<>();

        tokens.add(new Token(Token.TRETN, 1, 1, null));

        tokens.add(new Token(Token.TMAIN, 1, 1, null));

        Parser parser = new Parser(tokens);

        NReturnStatNode nreturnStatNode = new NReturnStatNode();
        nreturnStatNode.setnExprNode(NExprNode.INSTANCE());

        TreeNode returnstat = nreturnStatNode.make(parser);

        assertEquals(TreeNode.NRETN, returnstat.getValue());
        assertEquals(null, returnstat.getLeft());
        assertEquals(null, returnstat.getRight());

    }

    @Test
    public void sunnyday_returnexpr() {

        SetupMocks.setup();

        List<Token> tokens = new ArrayList<>();

        tokens.add(new Token(Token.TRETN, 1, 1, null));

        tokens.add(new Token(Token.TILIT, 1, 1, null));
        tokens.add(new Token(Token.TPLUS, 1, 1, null));
        tokens.add(new Token(Token.TILIT, 1, 1, null));


        tokens.add(new Token(Token.TMAIN, 1, 1, null));

        Parser parser = new Parser(tokens);

        NReturnStatNode nreturnStatNode = new NReturnStatNode();
        nreturnStatNode.setnExprNode(NExprNode.INSTANCE());

        TreeNode returnstat = nreturnStatNode.make(parser);

        assertEquals(TreeNode.NRETN, returnstat.getValue());
        assertEquals(TreeNode.NADD, returnstat.getLeft().getValue());

    }

    @Test
    public void sunnyday_returnexpr_expr() {

        SetupMocks.setup();

        List<Token> tokens = new ArrayList<>();

        tokens.add(new Token(Token.TRETN, 1, 1, null));

        tokens.add(new Token(Token.TILIT, 1, 1, null));
        tokens.add(new Token(Token.TPLUS, 1, 1, null));
        tokens.add(new Token(Token.TILIT, 1, 1, null));
        tokens.add(new Token(Token.TMINS, 1, 1, null));
        tokens.add(new Token(Token.TILIT, 1, 1, null));


        tokens.add(new Token(Token.TMAIN, 1, 1, null));

        Parser parser = new Parser(tokens);

        NReturnStatNode nreturnStatNode = new NReturnStatNode();
        nreturnStatNode.setnExprNode(NExprNode.INSTANCE());

        TreeNode returnstat = nreturnStatNode.make(parser);

        assertEquals(TreeNode.NRETN, returnstat.getValue());
        assertEquals(TreeNode.NSUB, returnstat.getLeft().getValue());
        assertEquals(TreeNode.NADD, returnstat.getLeft().getLeft().getValue());

    }

    @Test
    public void syntacticfail() {

        SetupMocks.setup();

        List<Token> tokens = new ArrayList<>();

//        tokens.add(new Token(Token.TRETN, 1, 1, null));
        tokens.add(new Token(Token.TILIT, 1, 1, null));
        tokens.add(new Token(Token.TILIT, 1, 1, null));

        Parser parser = new Parser(tokens);

        NReturnStatNode nreturnStatNode = new NReturnStatNode();
        nreturnStatNode.setnExprNode(NExprNode.INSTANCE());

        TreeNode returnstat = nreturnStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF, returnstat.getValue());

    }
}
