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
 * public class NRTypeNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NRTypeNodeTests {
//	<rtype>	::=	<stype> | void


    @Test
    public void integer(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NRTypeNode nrTypeNode = new NRTypeNode();
        TreeNode rtype = nrTypeNode.make(parser);

        assertEquals(NodeDataTypes.Integer, rtype.getType());
    }

    @Test
    public void real(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TREAL,1,1,null));

        Parser parser = new Parser(tokens);

        NRTypeNode nrTypeNode = new NRTypeNode();
        TreeNode rtype = nrTypeNode.make(parser);

        assertEquals(NodeDataTypes.Real, rtype.getType());
    }

@Test
    public void bool(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TBOOL,1,1,null));

        Parser parser = new Parser(tokens);

        NRTypeNode nrTypeNode = new NRTypeNode();
        TreeNode rtype = nrTypeNode.make(parser);

        assertEquals(NodeDataTypes.Boolean, rtype.getType());
    }

    @Test
    public void voidtest(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TVOID,1,1,null));

        Parser parser = new Parser(tokens);

        NRTypeNode nrTypeNode = new NRTypeNode();
        TreeNode rtype = nrTypeNode.make(parser);

        assertEquals(NodeDataTypes.Void, rtype.getType());
    }

    @Test
    public void syntactic_fail(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        NRTypeNode nrTypeNode = new NRTypeNode();
        TreeNode rtype = nrTypeNode.make(parser);

        assertEquals(NodeDataTypes.Undefined, rtype.getType());
    }




}
