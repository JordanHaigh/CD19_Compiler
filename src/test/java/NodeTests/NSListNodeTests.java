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
 * public class NSListNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NSListNodeTests {
//    //NSDLST	<slist>	::=	<sdecl> <slistTail>
//	//<slistTail>	::=	ε | ,<sdecl> <slistTail>
    @Test
    public void sunnyday_singlesdecl(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));



        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NSListNode nsListNode = new NSListNode();

        TreeNode slist = nsListNode.make(parser);

        assertEquals(TreeNode.NSDECL, slist.getValue());


    }

    @Test
    public void sunnyday_twosdecls(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"variable"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));



        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NSListNode nsListNode = new NSListNode();

        TreeNode slist = nsListNode.make(parser);

        assertEquals(TreeNode.NSDLST, slist.getValue());
        assertEquals(TreeNode.NSDECL, slist.getLeft().getValue());
        assertEquals(TreeNode.NSDECL, slist.getRight().getValue());


    }

    @Test
    public void sunnyday_threesdecls(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"variable"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"variable"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));


        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NSListNode nsListNode = new NSListNode();

        TreeNode slist = nsListNode.make(parser);

        assertEquals(TreeNode.NSDLST, slist.getValue());
        assertEquals(TreeNode.NSDECL, slist.getLeft().getValue());
        assertEquals(TreeNode.NSDLST, slist.getRight().getValue());
        assertEquals(TreeNode.NSDECL, slist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NSDECL, slist.getRight().getRight().getValue());


    }




}
