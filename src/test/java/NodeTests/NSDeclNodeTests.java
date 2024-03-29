package NodeTests;

import CD19.Parser.Nodes.NSDeclNode;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Jordan Haigh c3256730 CD19
 * public class NSDeclNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NSDeclNodeTests {

    //
    //NSDECL	<sdecl>	::=	<id> : <stype>


    @Test
    public void sunnyday(){
        List<Token> tokens= new ArrayList<>();
        //person : integer
        tokens.add(new Token(Token.TIDEN,1,1,"age"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NSDeclNode nsDeclNode = new NSDeclNode();
        TreeNode sdecl = nsDeclNode.make(parser);

        assertEquals(TreeNode.NSDECL, sdecl.getValue());

    }

    @Test
    public void syntactic_failiden(){
        List<Token> tokens= new ArrayList<>();
//        tokens.add(new Token(Token.TIDEN,1,1,"age"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NSDeclNode nsDeclNode = new NSDeclNode();
        TreeNode sdecl = nsDeclNode.make(parser);

        assertEquals(TreeNode.NUNDEF, sdecl.getValue());

    }

    @Test
    public void syntactic_failcoln(){
        List<Token> tokens= new ArrayList<>();
        tokens.add(new Token(Token.TIDEN,1,1,"age"));
//        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NSDeclNode nsDeclNode = new NSDeclNode();
        TreeNode sdecl = nsDeclNode.make(parser);

        assertEquals(TreeNode.NUNDEF, sdecl.getValue());

    }


    @Test
    public void syntactic_failstype(){
        List<Token> tokens= new ArrayList<>();
        tokens.add(new Token(Token.TIDEN,1,1,"age"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        NSDeclNode nsDeclNode = new NSDeclNode();
        TreeNode sdecl = nsDeclNode.make(parser);

        assertEquals(TreeNode.NUNDEF, sdecl.getValue());

    }

}
