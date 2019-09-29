package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class NPlistNodeTests {
    //	<plist>	::=	<params> | ε
    @Test
    public void sunnyday_params(){
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
        NPListNode npListNode = new NPListNode();
        TreeNode plist = npListNode.make(parser);

        assertEquals(TreeNode.NPLIST, plist.getValue());
        assertEquals(TreeNode.NSIMP, plist.getLeft().getValue());
        assertEquals(TreeNode.NPLIST, plist.getRight().getValue());


        assertEquals(TreeNode.NARRC, plist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NARRP, plist.getRight().getRight().getValue());
    }

    @Test
    public void sunnyday_noparams(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();
        //aa : integer , bb : const iden
        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NPListNode npListNode = new NPListNode();
        TreeNode plist = npListNode.make(parser);

        assertEquals(null, plist);


    }
}
