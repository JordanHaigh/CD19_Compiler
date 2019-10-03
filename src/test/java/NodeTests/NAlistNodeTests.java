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
 * public class NAlistNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NAlistNodeTests {



    //NASGNS	<alist>	::=	<id><asgnstat> <alistTail>
    //<alistTail>	::=	eps | , <id><asgnstat> <alistTail>


    @Test
    public void NALIST_SunnyDayData_justid_asgnstat() {
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());

        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode();
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());

        NAlistNode nAlistNode = new NAlistNode();

        TreeNode alist = nAlistNode.make(parser);

        assertEquals(TreeNode.NASGN,alist.getValue());
        assertEquals(TreeNode.NSIMV, alist.getLeft().getValue());
        assertEquals(TreeNode.NILIT,alist.getRight().getValue());

    }

    @Test
    public void NALIST_SunnyDayData_twoasgns() {
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"VARIABLE A"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"variable b"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());

        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode();
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());

        NAlistNode nAlistNode = new NAlistNode();

        TreeNode alist = nAlistNode.make(parser);

        assertEquals(TreeNode.NASGNS, alist.getValue());

        assertEquals(TreeNode.NASGN, alist.getLeft().getValue());
        assertEquals(TreeNode.NASGN, alist.getRight().getValue());

        assertEquals(TreeNode.NSIMV, alist.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NILIT, alist.getLeft().getRight().getValue());


        assertEquals(TreeNode.NSIMV, alist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NILIT, alist.getRight().getRight().getValue());

    }


    @Test
    public void NALIST_SunnyDayData_threeasgns() {
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"VARIABLE A"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"variable b"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"VARIABLE C"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());

        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode();
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());

        NAlistNode nAlistNode = new NAlistNode();

        TreeNode alist = nAlistNode.make(parser);

        assertEquals(TreeNode.NASGNS, alist.getValue());

        assertEquals(TreeNode.NASGN, alist.getLeft().getValue());
        assertEquals(TreeNode.NASGNS, alist.getRight().getValue());

        assertEquals(TreeNode.NSIMV, alist.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NILIT, alist.getLeft().getRight().getValue());

        assertEquals(TreeNode.NASGN, alist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NASGN, alist.getRight().getRight().getValue());

        assertEquals(TreeNode.NSIMV, alist.getRight().getLeft().getLeft().getValue());
        assertEquals(TreeNode.NILIT, alist.getRight().getLeft().getRight().getValue());

        assertEquals(TreeNode.NSIMV, alist.getRight().getRight().getLeft().getValue());
        assertEquals(TreeNode.NILIT, alist.getRight().getRight().getRight().getValue());

    }

    @Test
    public void NALIST_errordetect_firstID() {
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"variable"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());

        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode();
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());

        NAlistNode nAlistNode = new NAlistNode();

        TreeNode alist = nAlistNode.make(parser);

        assertEquals(TreeNode.NUNDEF,alist.getValue());
        assertEquals(null,alist.getLeft());
        assertEquals(null,alist.getRight());

    }

    @Test
    public void NALIST_errordetect_aftercoma_in_tail() {
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"variable"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TCOMA,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null)); //fail here

        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());

        NAsgnStatNode nAsgnStatNode = new NAsgnStatNode();
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());

        NAlistNode nAlistNode = new NAlistNode();

        TreeNode alist = nAlistNode.make(parser);

        assertEquals(TreeNode.NASGNS,alist.getValue());
        assertEquals(TreeNode.NASGN,alist.getLeft().getValue());
        assertEquals(TreeNode.NUNDEF,alist.getRight().getValue());

    }
}

