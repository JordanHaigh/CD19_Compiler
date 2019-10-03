package NodeTests;

import CD19.Parser.Nodes.NDListNode;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Jordan Haigh c3256730 CD19
 * public class NDListNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NDListNodeTests {
    //NDLIST	<dlist>	::=	<decl> <dlistTail>
    //	<dlistTail>	::=	ε | , <decl> <dlistTail>

    @Test
    public void sunnyday_onedecl(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TINTG,1,1,null));


        Parser parser = new Parser(tokens);

        NDListNode ndListNode= new NDListNode();
        TreeNode dlist = ndListNode.make(parser);

        assertEquals(TreeNode.NSDECL, dlist.getValue());
    }

    @Test
    public void sunnyday_twodecl(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"variable b"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NDListNode ndListNode= new NDListNode();
        TreeNode dlist = ndListNode.make(parser);

        assertEquals(TreeNode.NDLIST, dlist.getValue());
        assertEquals(TreeNode.NSDECL, dlist.getLeft().getValue());
        assertEquals(TreeNode.NSDECL, dlist.getRight().getValue());
    }

    @Test
    public void sunnyday_threedecl(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"variable b"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));


        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"variable c"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NDListNode ndListNode= new NDListNode();
        TreeNode dlist = ndListNode.make(parser);

        assertEquals(TreeNode.NDLIST, dlist.getValue());
        assertEquals(TreeNode.NSDECL, dlist.getLeft().getValue());
        assertEquals(TreeNode.NDLIST, dlist.getRight().getValue());
        assertEquals(TreeNode.NSDECL, dlist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NSDECL, dlist.getRight().getRight().getValue());
    }



}
