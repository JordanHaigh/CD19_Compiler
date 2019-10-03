package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Jordan Haigh c3256730 CD19
 * public class NInitListNodeTests
 * Tests determine if TreeNode creation works as intended
 * */
public class NInitListNodeTests {

    //NILIST	<initlist>	::=	<init> <initListTail>
    //	<initListTail>	::=	eps | ,<initlist>

    @Test
    public void initlist_oneinit(){
        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));


        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);
        NInitListNode nInitListNode = new NInitListNode();
        TreeNode initlist = nInitListNode.make(parser);

        assertEquals(TreeNode.NINIT,initlist.getValue());
    }

    @Test
    public void initlist_twoinit(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"bb"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));



        Parser parser = new Parser(tokens);
        NInitNode nInitNode = new NInitNode();
        nInitNode.setnExprNode(NExprNode.INSTANCE());
        NInitListNode nInitListNode = new NInitListNode(nInitNode);
        TreeNode initlist = nInitListNode.make(parser);

        assertEquals(TreeNode.NILIST,initlist.getValue());
        assertEquals(TreeNode.NINIT,initlist.getLeft().getValue());
        assertEquals(TreeNode.NINIT,initlist.getRight().getValue());
    }

    @Test
    public void initList_threeinits(){

        SetupMocks.setup();
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TIDEN,1,1,"aa"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"bb"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"cc"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,null));

        tokens.add(new Token(Token.TILIT,1,1,null));

        Parser parser = new Parser(tokens);
        NInitNode nInitNode = new NInitNode();
        nInitNode.setnExprNode(NExprNode.INSTANCE());
        NInitListNode nInitListNode = new NInitListNode(nInitNode);
        TreeNode initlist = nInitListNode.make(parser);

        assertEquals(TreeNode.NILIST,initlist.getValue());
        assertEquals(TreeNode.NINIT,initlist.getLeft().getValue());
        assertEquals(TreeNode.NILIST ,initlist.getRight().getValue());
        assertEquals(TreeNode.NINIT ,initlist.getRight().getLeft().getValue());
        assertEquals(TreeNode.NINIT ,initlist.getRight().getRight().getValue());
    }

}




