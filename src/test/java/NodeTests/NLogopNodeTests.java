package NodeTests;


import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NLogopNodeTests {

    //NAND, NOR, NXOR	<logop>	::=	and | or | xor

    @Test
    public void sunnyday_tand_nand(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TAND,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NLogopNode nLogopNode = new NLogopNode();

        TreeNode logop = nLogopNode.make(parser);

        assertEquals(TreeNode.NAND, logop.getValue());
    }


    @Test
    public void sunnyday_tor_NOR(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TOR,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NLogopNode nLogopNode = new NLogopNode();

        TreeNode logop = nLogopNode.make(parser);

        assertEquals(TreeNode.NOR, logop.getValue());
    }


    @Test
    public void syntactic_fail(){
        List<Token> tokens= new ArrayList<>();

//        tokens.add(new Token(Token.TXOR,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NLogopNode nLogopNode = new NLogopNode();

        TreeNode logop = nLogopNode.make(parser);

        assertEquals(TreeNode.NUNDEF, logop.getValue());
    }
}
