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

public class NIoStatNodeTests {

    //NINPUT | NPRINT| NPRLN	<iostat>	::=	input <vlist> | print <prlist> | printline <prlist>

    @Test
    public void sunnyday_inputvlist(){
        //input <id>
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TINPT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"variable"));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);


        NIoStatNode nIoStatNode = new NIoStatNode();

        TreeNode iostat = nIoStatNode.make(parser);

        assertEquals(TreeNode.NINPUT, iostat.getValue());
        assertEquals(TreeNode.NSIMV, iostat.getLeft().getValue());


    }

    @Test
    public void sunnyday_inputvlist_twovars(){
        //input <id>
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TINPT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"variable"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"myarr"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));


        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NVarNode nVarNode = new NVarNode(nVarTailNode);

        NVListNode nvListNode = new NVListNode(nVarNode);
        NIoStatNode nIoStatNode = new NIoStatNode(nvListNode,null);


        TreeNode iostat = nIoStatNode.make(parser);

        assertEquals(TreeNode.NINPUT, iostat.getValue());
        assertEquals(TreeNode.NVLIST, iostat.getLeft().getValue());

        assertEquals(TreeNode.NSIMV, iostat.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NARRV, iostat.getLeft().getRight().getValue());

    }

    @Test
    public void sunnyday_inputvlist_threevars(){
        //input <id>
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TINPT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"variable"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"myarr"));
        tokens.add(new Token(Token.TLBRK,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TRBRK,1,1,null));
        tokens.add(new Token(Token.TDOT,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"variable"));

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NVarNode nVarNode = new NVarNode(nVarTailNode);

        NVListNode nvListNode = new NVListNode(nVarNode);
        NIoStatNode nIoStatNode = new NIoStatNode(nvListNode,null);


        TreeNode iostat = nIoStatNode.make(parser);

        assertEquals(TreeNode.NINPUT, iostat.getValue());
        assertEquals(TreeNode.NVLIST, iostat.getLeft().getValue());

        assertEquals(TreeNode.NSIMV, iostat.getLeft().getLeft().getValue());
        assertEquals(TreeNode.NVLIST, iostat.getLeft().getRight().getValue());


        assertEquals(TreeNode.NARRV, iostat.getLeft().getRight().getLeft().getValue());
        assertEquals(TreeNode.NSIMV, iostat.getLeft().getRight().getRight().getValue());


    }

    @Test
    public void sunnyday_print(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TPRIN,1,1,null));

        ////////////////////////prlist////////////////////////
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TSTRG,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));
        ////////////////////////prlist////////////////////////


        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NVarNode nVarNode = new NVarNode(nVarTailNode);

        NVListNode nvListNode = new NVListNode(nVarNode);
        NPrListNode nPrListNode = new NPrListNode();
        NIoStatNode nIoStatNode = new NIoStatNode(nvListNode,nPrListNode);


        TreeNode iostat = nIoStatNode.make(parser);

        assertEquals(TreeNode.NPRINT, iostat.getValue());
        assertEquals(TreeNode.NPRLST, iostat.getLeft().getValue());
    }

    @Test
    public void sunnyday_printline(){
        SetupMocks.setup();

        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TPRLN,1,1,null));

        ////////////////////////prlist////////////////////////
        tokens.add(new Token(Token.TILIT,1,1,null));
        tokens.add(new Token(Token.TPLUS,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TSTRG,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));
        ////////////////////////prlist////////////////////////


        Parser parser = new Parser(tokens);

        NVarTailNode nVarTailNode = new NVarTailNode();
        nVarTailNode.setnExprNode(NExprNode.INSTANCE());
        NVarNode nVarNode = new NVarNode(nVarTailNode);

        NVListNode nvListNode = new NVListNode(nVarNode);
        NPrListNode nPrListNode = new NPrListNode();
        NIoStatNode nIoStatNode = new NIoStatNode(nvListNode,nPrListNode);


        TreeNode iostat = nIoStatNode.make(parser);

        assertEquals(TreeNode.NPRLN, iostat.getValue());
        assertEquals(TreeNode.NPRLST, iostat.getLeft().getValue());
    }
}
