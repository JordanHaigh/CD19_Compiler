package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NArrDeclsNodeTests {

    //NALIST	<arrdecls>	::=	<arrdecl> <arrDeclTail>
    //	<arrdeclsTail>	::=	eps |  , <arrdecls>


    NArrDeclsNode nArrDeclsNode = new NArrDeclsNode();

    @Test
    public void sunnyday_uno(){
        //arrays person : people
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        TreeNode arrdecls = nArrDeclsNode.make(parser);

        assertEquals(TreeNode.NARRD, arrdecls.getValue());

    }


    @Test
    public void sunnyday_dos(){
        //arrays person : people
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"class"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        TreeNode arrdecls = nArrDeclsNode.make(parser);

        assertEquals(TreeNode.NALIST, arrdecls.getValue());
        assertEquals(TreeNode.NARRD, arrdecls.getLeft().getValue());
        assertEquals(TreeNode.NARRD, arrdecls.getRight().getValue());

    }

    @Test
    public void sunnyday_three(){
        //arrays person : people
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"class"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"class"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"person"));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        TreeNode arrdecls = nArrDeclsNode.make(parser);

        assertEquals(TreeNode.NALIST, arrdecls.getValue());
        assertEquals(TreeNode.NARRD, arrdecls.getLeft().getValue());
        assertEquals(TreeNode.NALIST, arrdecls.getRight().getValue());
        assertEquals(TreeNode.NARRD, arrdecls.getRight().getLeft().getValue());
        assertEquals(TreeNode.NARRD, arrdecls.getRight().getRight().getValue());

    }

}
