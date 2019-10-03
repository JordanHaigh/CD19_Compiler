package NodeTests;

import CD19.Parser.Nodes.NLocalsNode;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Jordan Haigh c3256730 CD19
 * public class NLocalsNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NLocalsNodeTests {
    //	<locals>	::=	<dlist> | ε

    @Test
    public void sunnyday_onedecl(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TINTG,1,1,null));


        Parser parser = new Parser(tokens);
        NLocalsNode nLocalsNode = new NLocalsNode();

        TreeNode locals = nLocalsNode.make(parser);

        assertEquals(TreeNode.NSDECL, locals.getValue());
    }


    @Test
    public void sunnyday_eps(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);
        NLocalsNode nLocalsNode = new NLocalsNode();

        TreeNode locals = nLocalsNode.make(parser);

        assertEquals(null, locals);
    }
}
