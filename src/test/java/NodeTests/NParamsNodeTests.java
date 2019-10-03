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
 * public class NParamsNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NParamsNodeTests {

    //NPLIST	<params>	::=	<param> <paramTail>
    //	<paramsTail>	::=	ε | , <param> <paramTail>

    @Test
    public void sunnyday_justparam(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NParamsNode nParamsNode = new NParamsNode();
        TreeNode params = nParamsNode.make(parser);

        assertEquals(TreeNode.NSIMP, params.getValue());

    }

    @Test
    public void sunnyday_paramparam(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"bb"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));



        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NParamsNode nParamsNode = new NParamsNode();
        TreeNode params = nParamsNode.make(parser);

        assertEquals(TreeNode.NPLIST, params.getValue());
        assertEquals(TreeNode.NSIMP, params.getLeft().getValue());
        assertEquals(TreeNode.NSIMP, params.getRight().getValue());

    }


    @Test
    public void sunnyday_paramparamparam(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();
        //aa : integer , bb : const iden
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TCNST,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bb"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"myarrtype"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"cc"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"myarrtype"));

        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NParamsNode nParamsNode = new NParamsNode();
        TreeNode params = nParamsNode.make(parser);

        assertEquals(TreeNode.NPLIST, params.getValue());
        assertEquals(TreeNode.NSIMP, params.getLeft().getValue());
        assertEquals(TreeNode.NPLIST, params.getRight().getValue());


        assertEquals(TreeNode.NARRC, params.getRight().getLeft().getValue());
        assertEquals(TreeNode.NARRP, params.getRight().getRight().getValue());
    }
}
