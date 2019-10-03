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
 * public class NAsgnOpNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NAsgnOpNodeTests {

    //NASGN, NPLEQ, NMNEQ, NSTEQ, NDVEQ	<asgnop>	::=	 = | += | -= | *= | /=

    @Test
    public void sunnyday_tequl_nasgn(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NAsgnOpNode nAsgnOpNode = new NAsgnOpNode();

        TreeNode asgnop= nAsgnOpNode.make(parser);

        assertEquals(TreeNode.NASGN,asgnop.getValue());
    }


    @Test
    public void sunnyday_tpleq_NPLEQ(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TPLEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NAsgnOpNode nAsgnOpNode = new NAsgnOpNode();

        TreeNode asgnop= nAsgnOpNode.make(parser);

        assertEquals(TreeNode.NPLEQ,asgnop.getValue());
    }


    @Test
    public void sunnydaday_tmneq_nmneq(){
        //NMNEQ
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TMNEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NAsgnOpNode nAsgnOpNode = new NAsgnOpNode();

        TreeNode asgnop= nAsgnOpNode.make(parser);

        assertEquals(TreeNode.NMNEQ,asgnop.getValue());
    }

    @Test
    public void sunnydaday_tsteq_nsteq(){
        //NSTEQ
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TSTEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NAsgnOpNode nAsgnOpNode = new NAsgnOpNode();

        TreeNode asgnop= nAsgnOpNode.make(parser);

        assertEquals(TreeNode.NSTEQ,asgnop.getValue());
    }


    @Test
    public void sunnydaday_tdveq_ndveq(){

        //NDVEQ
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TDVEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NAsgnOpNode nAsgnOpNode = new NAsgnOpNode();

        TreeNode asgnop= nAsgnOpNode.make(parser);

        assertEquals(TreeNode.NDVEQ,asgnop.getValue());
    }

    @Test
    public void syntactic_bad(){

        //NDVEQ
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        NAsgnOpNode nAsgnOpNode = new NAsgnOpNode();

        TreeNode asgnop= nAsgnOpNode.make(parser);

        assertEquals(TreeNode.NUNDEF,asgnop.getValue());
    }


}
