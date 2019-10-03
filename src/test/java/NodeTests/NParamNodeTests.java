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
 * public class NParamNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NParamNodeTests {

    @Test
    public void sunnyday_arr_nonconst(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"type"));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NParamNode nParamNode = new NParamNode();

        TreeNode param = nParamNode.make(parser);

        assertEquals(TreeNode.NARRP, param.getValue());
        assertEquals(TreeNode.NARRD, param.getLeft().getValue());

    }


    @Test
    public void sunnyday_simpint_nonconst(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NParamNode nParamNode = new NParamNode();

        TreeNode param = nParamNode.make(parser);

        assertEquals(TreeNode.NSIMP, param.getValue());
        assertEquals(TreeNode.NSDECL, param.getLeft().getValue());


    }

    @Test
    public void sunnyday_simpreal_nonconst(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TREAL,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NParamNode nParamNode = new NParamNode();

        TreeNode param = nParamNode.make(parser);

        assertEquals(TreeNode.NSIMP, param.getValue());
        assertEquals(TreeNode.NSDECL, param.getLeft().getValue());
    }


    @Test
    public void sunnyday_simpbool_nonconst(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TBOOL,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NParamNode nParamNode = new NParamNode();

        TreeNode param = nParamNode.make(parser);

        assertEquals(TreeNode.NSIMP, param.getValue());
        assertEquals(TreeNode.NSDECL, param.getLeft().getValue());



    }
    @Test
    public void sunnyday_const(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCNST,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"type"));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NParamNode nParamNode = new NParamNode();

        TreeNode param = nParamNode.make(parser);

        assertEquals(TreeNode.NARRC, param.getValue());
        assertEquals(TreeNode.NARRD, param.getLeft().getValue());

    }

    @Test
    public void syntactic_failfirstkeyword(){
        List<Token> tokens= new ArrayList<>();

//        tokens.add(new Token(Token.TCNST,1,1,null));
//        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"type"));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NParamNode nParamNode = new NParamNode();

        TreeNode param = nParamNode.make(parser);

        assertEquals(TreeNode.NUNDEF, param.getValue());

    }

    @Test
    public void syntactic_fail_stypeortypepath_coln(){
        List<Token> tokens= new ArrayList<>();

//        tokens.add(new Token(Token.TCNST,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"type"));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NParamNode nParamNode = new NParamNode();

        TreeNode param = nParamNode.make(parser);

        assertEquals(TreeNode.NUNDEF, param.getValue());

    }

    @Test
    public void syntactic_fail_stypeortypepath_tail(){
        List<Token> tokens= new ArrayList<>();

//        tokens.add(new Token(Token.TCNST,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NParamNode nParamNode = new NParamNode();

        TreeNode param = nParamNode.make(parser);

        assertEquals(TreeNode.NUNDEF, param.getValue());

    }
}
