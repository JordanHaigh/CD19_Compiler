package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NRelopNodeTests {

    //NEQL, NNEQ, NGRT, NLEQ, NLSS, NGEQ	<relop>	::=	 == | != | > | <= | < | >=
    @Test
    public void sunnyday_eqeq_neql(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TEQEQ,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NRelopNode nRelopNode = new NRelopNode();

        TreeNode relop = nRelopNode.make(parser);

        assertEquals(TreeNode.NEQL,relop.getValue());
    }

    @Test
    public void sunnyday_tneql_nneq(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TNEQL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NRelopNode nRelopNode = new NRelopNode();

        TreeNode relop = nRelopNode.make(parser);

        assertEquals(TreeNode.NNEQ,relop.getValue());
    }


    @Test
    public void sunnyday_tgrtr_ngrt(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TGRTR,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NRelopNode nRelopNode = new NRelopNode();

        TreeNode relop = nRelopNode.make(parser);

        assertEquals(TreeNode.NGRT,relop.getValue());
    }

    @Test
    public void sunnyday_tleql_nleq(){
        //NLEQ
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TLEQL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NRelopNode nRelopNode = new NRelopNode();

        TreeNode relop = nRelopNode.make(parser);

        assertEquals(TreeNode.NLEQ,relop.getValue());
    }


    @Test
    public void sunnyday_tless_nlss(){
        //NLSS
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TLESS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NRelopNode nRelopNode = new NRelopNode();

        TreeNode relop = nRelopNode.make(parser);

        assertEquals(TreeNode.NLSS,relop.getValue());
    }



    @Test
    public void sunnyday_tgreq_ngeq(){
        //NLSS
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TGEQL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NRelopNode nRelopNode = new NRelopNode();

        TreeNode relop = nRelopNode.make(parser);

        assertEquals(TreeNode.NGEQ,relop.getValue());
    }


    @Test
    public void syntactic_badrelop(){
        //NLSS
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NRelopNode nRelopNode = new NRelopNode();

        TreeNode relop = nRelopNode.make(parser);

        assertEquals(TreeNode.NUNDEF,relop.getValue());
    }
}

