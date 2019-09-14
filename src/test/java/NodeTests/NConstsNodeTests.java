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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class NConstsNodeTests {
	//<consts>	::=	constants <initlist> | eps
    @Mock
    NInitListNode nInitListNode;

    @InjectMocks
    NConstsNode constsNode;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void NConstsNode_SunnyDay_constantsInitlistTrans(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCONS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aaaa"));

        Parser parser = new Parser(tokens);

        when(nInitListNode.make(parser)).thenReturn(new TreeNode(TreeNode.NILIST));

        TreeNode constsTreeNode = constsNode.make(parser);

        assertEquals(TreeNode.NILIST, constsTreeNode.getValue());
    }

    @Test
    public void NConstsNode_SunnyDay_eps(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        when(nInitListNode.make(parser)).thenReturn(new TreeNode(TreeNode.NILIST));

        TreeNode constsTreeNode = constsNode.make(parser);

        assertEquals(null, constsTreeNode);
    }
}
