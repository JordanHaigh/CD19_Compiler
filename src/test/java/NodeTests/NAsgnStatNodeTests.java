package NodeTests;


import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Jordan Haigh c3256730 CD19
 * public class NAsgnStatNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NAsgnStatNodeTests {
    //	<asgnstat>	::=	 <varTail> <asgnop> <bool>
    //NSIMV | NARRV	<varTail>	::=	ε | [<expr>] . <id>
    //NASGN, NPLEQ, NMNEQ, NSTEQ, NDVEQ	<asgnop>	::=	 = | += | -= | *= | /=
    @Test
    public void sunnyday__epsvartail_asgnop_bool(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));


        tokens.add(new Token(Token.TIDEN,1,1,"fake"));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NBoolNode nBoolNode = new NBoolNode();
        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode(nVarTailNode, new NAsgnOpNode(), nBoolNode);

        TreeNode asgnstat = nAsgnStatNode.makeWithId(parser, new Token(Token.TIDEN,1,1,"aa"));


        assertEquals(TreeNode.NASGN,asgnstat.getValue());
        assertEquals(TreeNode.NSIMV, asgnstat.getLeft().getValue());
        assertEquals(TreeNode.NSIMV,asgnstat.getRight().getValue());
    }

    @Test
    public void sunnyday__vartailarr_asgnop_bool(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bb"));


        tokens.add(new Token(Token.TIDEN,1,1,"fake"));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NBoolNode nBoolNode = new NBoolNode();
        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode(nVarTailNode, new NAsgnOpNode(), nBoolNode);

        TreeNode asgnstat = nAsgnStatNode.makeWithId(parser, new Token(Token.TIDEN,1,1,"aa"));

        assertEquals(TreeNode.NASGN,asgnstat.getValue());
        assertEquals(TreeNode.NARRV, asgnstat.getLeft().getValue());
        assertEquals(TreeNode.NSIMV,asgnstat.getRight().getValue());
    }

}
