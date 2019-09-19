package NodeTests;


import CD19.Parser.Nodes.NSTypeNode;
import CD19.Parser.Nodes.NodeDataTypes;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import com.sun.corba.se.impl.orbutil.graph.NodeData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NStypeNodeTests {

    @Test
    public void integer(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NSTypeNode  nsTypeNode = new NSTypeNode();

        TreeNode stype = nsTypeNode.make(parser);

        assertEquals(NodeDataTypes.Integer, stype.getType());
    }

    @Test
    public void real(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TREAL,1,1,null));

        Parser parser = new Parser(tokens);

        NSTypeNode  nsTypeNode = new NSTypeNode();

        TreeNode stype = nsTypeNode.make(parser);

        assertEquals(NodeDataTypes.Real, stype.getType());
    }

    @Test
    public void bool(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TBOOL,1,1,null));

        Parser parser = new Parser(tokens);

        NSTypeNode  nsTypeNode = new NSTypeNode();

        TreeNode stype = nsTypeNode.make(parser);

        assertEquals(NodeDataTypes.Boolean, stype.getType());
    }
}
