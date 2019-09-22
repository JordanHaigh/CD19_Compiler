package NodeTests;


import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NPrintItemNodeTests {

    //NSTRG	<printitem>	::=	<expr> | <string>



    @Test
    public void sunnyday_expr(){

        SetupMocks.setup();

        //5+5

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NPrintItemNode nPrintItemNode = new NPrintItemNode();
        nPrintItemNode.setnExprNode(NExprNode.INSTANCE());

        TreeNode printitem = nPrintItemNode.make(parser);

        assertEquals(TreeNode.NADD, printitem.getValue());


    }

    @Test
    public void sunnyday_string(){
        //"so you're trying to block me huh. whats your stoyle?"
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TSTRG,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NPrintItemNode nPrintItemNode = new NPrintItemNode();
        nPrintItemNode.setnExprNode(NExprNode.INSTANCE());

        TreeNode printitem = nPrintItemNode.make(parser);

        assertEquals(TreeNode.NSTRG, printitem.getValue());

    }

}
