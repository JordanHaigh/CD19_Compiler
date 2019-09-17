package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


public class NProgNodeTests {
    //NPROG	<program>	::=	CD19 <id> <globals> <funcs> <mainbody>
    @Mock NGlobNode globs;
    @Mock NFuncsNode funcs;
    @Mock
    NMainBodyNode main;

    @InjectMocks NProgNode prog;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void NProgNode_SunnyDayData() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TCONS,1,1,null));
        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TMAIN,1,1,null));

        Parser parser = new Parser(tokens);

        when(globs.make(parser)).thenReturn(new TreeNode(TreeNode.NGLOB));
        when(funcs.make(parser)).thenReturn(new TreeNode(TreeNode.NFUNCS));
        when(main.make(parser)).thenReturn(new TreeNode(TreeNode.NMAIN));

        TreeNode progs = prog.make(parser);

        assertEquals(TreeNode.NPROG, progs.getValue());
        assertEquals(TreeNode.NGLOB, progs.getLeft().getValue());
        assertEquals(TreeNode.NFUNCS, progs.getMiddle().getValue());
        assertEquals(TreeNode.NMAIN, progs.getRight().getValue());
    }
}
