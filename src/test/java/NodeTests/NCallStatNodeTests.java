package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NCallStatNodeTests {
    //NCALL	<callStat>	::=	ε | <elist>

    @Test
    public void sunnyday_elist(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();
        //
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TAND,1,1,null));

        tokens.add(new Token(Token.TFLIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TFLIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TTRUE,1,1,null));
        tokens.add(new Token(Token.TMINS,1,1,null));
        tokens.add(new Token(Token.TTRUE,1,1,null));

        tokens.add(new Token(Token.TOR,1,1,null));

        tokens.add(new Token(Token.TFALS, 1,1,null));
        tokens.add(new Token(Token.TMINS,1,1,null));
        tokens.add(new Token(Token.TFALS,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);


        NCallStatNode nCallStatNode = new NCallStatNode();
        TreeNode callstat = nCallStatNode.make(parser);

        assertEquals(TreeNode.NCALL, callstat.getValue());
        assertEquals(TreeNode.NEXPL, callstat.getLeft().getValue());
    }

    @Test
    public void sunnyday_eps(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TRPAR,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NCallStatNode nCallStatNode = new NCallStatNode();

        TreeNode callstat = nCallStatNode.make(parser);


        assertEquals(TreeNode.NCALL, callstat.getValue());
        assertEquals(null, callstat.getLeft());
        assertEquals(null, callstat.getRight());
    }

}
