package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NArrdeclNodeTests {

    @Test
    public void sunnyday(){
        //people : person
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NArrDeclNode nArrDeclNode = new NArrDeclNode();

        TreeNode arrdecl = nArrDeclNode.make(parser);

        assertEquals(TreeNode.NARRD, arrdecl.getValue());

    }

    @Test
    public void syntacticerror_badfirstID(){
        //people : person
        List<Token> tokens= new ArrayList<>();
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NArrDeclNode nArrDeclNode = new NArrDeclNode();

        TreeNode arrdecl = nArrDeclNode.make(parser);

        assertEquals(TreeNode.NUNDEF, arrdecl.getValue());

    }

    @Test
    public void syntacticerror_badcolon(){
        //people : person
        List<Token> tokens= new ArrayList<>();
        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NArrDeclNode nArrDeclNode = new NArrDeclNode();

        TreeNode arrdecl = nArrDeclNode.make(parser);

        assertEquals(TreeNode.NUNDEF, arrdecl.getValue());

    }

    @Test
    public void syntacticerror_badsecondid(){
        //people : person
        List<Token> tokens= new ArrayList<>();
        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NArrDeclNode nArrDeclNode = new NArrDeclNode();

        TreeNode arrdecl = nArrDeclNode.make(parser);

        assertEquals(TreeNode.NUNDEF, arrdecl.getValue());

    }
}
