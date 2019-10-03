package NodeTests;


import CD19.Parser.Nodes.NDeclNode;
import CD19.Parser.Nodes.NParamTypeTailNode;
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
 * public class NParamTypeTailNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NParamTypeTailNodeTests {
    @Test
    public void sunnyday_arrpath(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"mytype"));


        Parser parser = new Parser(tokens);

        NParamTypeTailNode nparamTypeTailNode= new NParamTypeTailNode();

        TreeNode paramtypetail = nparamTypeTailNode.make(parser);

        assertEquals(TreeNode.NARRD, paramtypetail.getValue());
    }

    @Test
    public void sunnyday_simplepath(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TINTG,1,1,null));


        Parser parser = new Parser(tokens);

        NParamTypeTailNode nparamTypeTailNode= new NParamTypeTailNode();

        TreeNode paramtypetail = nparamTypeTailNode.make(parser);

        assertEquals(TreeNode.NSDECL, paramtypetail.getValue());
    }

    @Test
    public void syntacticfail(){

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCOMA,1,1,null));

        Parser parser = new Parser(tokens);

        NParamTypeTailNode nparamTypeTailNode= new NParamTypeTailNode();

        TreeNode paramtypetail = nparamTypeTailNode.make(parser);

        assertEquals(TreeNode.NUNDEF, paramtypetail.getValue());
    }


}
