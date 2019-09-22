package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class NVlistNodeTests {
////NVLIST	<vlist>	::=	<var> <vlistTail>
////	<vlistTail>	::=	ε | ,<vlist>
    @Test
    public void sunnyday_justvar(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NVarNode nVarNode = new NVarNode(nVarTailNode);
        NVListNode nvListNode = new NVListNode(nVarNode);

        TreeNode vlist = nvListNode.make(parser);

        assertEquals(TreeNode.NARRV, vlist.getValue());


    }

    @Test
    public void sunnyday_varcomavar(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"myarrayvar"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"variable"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"next variable"));
        tokens.add(new Token(Token.TFUNC,1,1,null));


        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NVarNode nVarNode = new NVarNode(nVarTailNode);
        NVListNode nvListNode = new NVListNode(nVarNode);

        TreeNode vlist = nvListNode.make(parser);

        assertEquals(TreeNode.NVLIST, vlist.getValue());
        assertEquals(TreeNode.NARRV, vlist.getLeft().getValue());
        assertEquals(TreeNode.NSIMV, vlist.getRight().getValue());


    }

    @Test
    public void sunnyday_varcomavarcomavar(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"myarrayvar"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"variable"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"next variable"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"myarrayvar2"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"variable2"));


        tokens.add(new Token(Token.TFUNC,1,1,null));


        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NVarNode nVarNode = new NVarNode(nVarTailNode);
        NVListNode nvListNode = new NVListNode(nVarNode);

        TreeNode vlist = nvListNode.make(parser);

        assertEquals(TreeNode.NVLIST, vlist.getValue());
        assertEquals(TreeNode.NARRV, vlist.getLeft().getValue());
        assertEquals(TreeNode.NVLIST, vlist.getRight().getValue());

        assertEquals(TreeNode.NSIMV, vlist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NARRV, vlist.getRight().getRight().getValue());


    }
}
