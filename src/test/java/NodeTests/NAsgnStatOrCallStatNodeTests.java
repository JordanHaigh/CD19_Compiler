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
 * public class NAsgnStatOrCallStatNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NAsgnStatOrCallStatNodeTests {
    //	<asgnStatOrCallStat>		<id><asgnStatOrCallStatTail>
    //	<asgnStatOrCallStatTail>	::= 	(<callStat>) | <asgnStat>
    @Test
    public void sunnyday_callstat(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
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
        tokens.add(new Token(Token.TRPAR,1,1,null));


        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);


        NAsgnStatOrCallStatNode nAsgnStatOrCallStatNode = new NAsgnStatOrCallStatNode();
        TreeNode asgnOrcall = nAsgnStatOrCallStatNode.make(parser);

        assertEquals(TreeNode.NCALL, asgnOrcall.getValue());
        assertEquals(TreeNode.NEXPL, asgnOrcall.getLeft().getValue());

    }

    @Test
    public void sunnyday_asgnstat(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));


        tokens.add(new Token(Token.TIDEN,1,1,"fake"));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NBoolNode nBoolNode = new NBoolNode();
        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode(nVarTailNode, new NAsgnOpNode(), nBoolNode);
        NAsgnStatOrCallStatNode nAsgnStatOrCallStatNode = new NAsgnStatOrCallStatNode(null, nAsgnStatNode);
        TreeNode asgnstat = nAsgnStatOrCallStatNode.make(parser);

        assertEquals(TreeNode.NASGN,asgnstat.getValue());
        assertEquals(TreeNode.NARRV, asgnstat.getLeft().getValue());
        assertEquals(TreeNode.NSIMV,asgnstat.getRight().getValue());

    }

    @Test
    public void syntactic_failfirstid(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));


        tokens.add(new Token(Token.TIDEN,1,1,"fake"));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NBoolNode nBoolNode = new NBoolNode();
        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode(nVarTailNode, new NAsgnOpNode(), nBoolNode);
        NAsgnStatOrCallStatNode nAsgnStatOrCallStatNode = new NAsgnStatOrCallStatNode(null, nAsgnStatNode);
        TreeNode asgnstat = nAsgnStatOrCallStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF,asgnstat.getValue());
        assertEquals(null, asgnstat.getLeft());
        assertEquals(null,asgnstat.getRight());

    }

    @Test
    public void syntactic_failcallstatendbrace(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
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

        tokens.add(new Token(Token.TRBRK,1,1,null));


        tokens.add(new Token(Token.TFUNC,1,1,null));


        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NBoolNode nBoolNode = new NBoolNode();
        NCallStatNode nCallStatNode = new NCallStatNode();
        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode(nVarTailNode, new NAsgnOpNode(), nBoolNode);

        NAsgnStatOrCallStatNode nAsgnStatOrCallStatNode = new NAsgnStatOrCallStatNode(nCallStatNode, nAsgnStatNode);
        TreeNode asgnstat = nAsgnStatOrCallStatNode.make(parser);

        assertEquals(TreeNode.NUNDEF,asgnstat.getValue());
        assertEquals(null, asgnstat.getLeft());
        assertEquals(null,asgnstat.getRight());

    }

}
