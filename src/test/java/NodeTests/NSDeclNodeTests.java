package NodeTests;

import CD19.Parser.Nodes.NSDeclNode;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NSDeclNodeTests {

    //
    //NSDECL	<sdecl>	::=	<id> : <stype>


    @Test
    public void sunnyday(){
        List<Token> tokens= new ArrayList<>();
        //person : integer
        tokens.add(new Token(Token.TIDEN,1,1,"age"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));

        Parser parser = new Parser(tokens);

        NSDeclNode nsDeclNode = new NSDeclNode();
        TreeNode sdecl = nsDeclNode.make(parser);

        assertEquals(TreeNode.NSDECL, sdecl.getValue());

    }

}
