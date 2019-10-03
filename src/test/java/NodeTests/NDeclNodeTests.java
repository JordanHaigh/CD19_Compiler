package NodeTests;

import CD19.Parser.Nodes.NDeclNode;
import CD19.Parser.Nodes.NodeDataTypes;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Jordan Haigh c3256730 CD19
 * public class NDeclNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NDeclNodeTests {
    //	<decl>	::=	<id>: <paramTypeTail>

    @Test
    public void sunnyday_id_simp(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TINTG,1,1,null));


        Parser parser = new Parser(tokens);

        NDeclNode nDeclNode = new NDeclNode();

        TreeNode decl = nDeclNode.make(parser);

        assertEquals(TreeNode.NSDECL, decl.getValue());

    }

    @Test
    public void sunnyday_id_arr(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"userdefinearrr"));

        tokens.add(new Token(Token.TINTG,1,1,null));


        Parser parser = new Parser(tokens);

        NDeclNode nDeclNode = new NDeclNode();

        TreeNode decl = nDeclNode.make(parser);

        assertEquals(TreeNode.NARRD, decl.getValue());
    }

    @Test
    public void syntactis_firstidwrong(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TINTG,1,1,null));


        Parser parser = new Parser(tokens);

        NDeclNode nDeclNode = new NDeclNode();

        TreeNode decl = nDeclNode.make(parser);

        assertEquals(TreeNode.NUNDEF, decl.getValue());

    }

    @Test
    public void syntactis_badcolon(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        tokens.add(new Token(Token.TINTG,1,1,null));


        Parser parser = new Parser(tokens);

        NDeclNode nDeclNode = new NDeclNode();

        TreeNode decl = nDeclNode.make(parser);

        assertEquals(TreeNode.NUNDEF, decl.getValue());

    }


    @Test
    public void syntactis_badend(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TINTG,1,1,null));


        Parser parser = new Parser(tokens);

        NDeclNode nDeclNode = new NDeclNode();

        TreeNode decl = nDeclNode.make(parser);

        assertEquals(TreeNode.NUNDEF, decl.getValue());

    }

}
