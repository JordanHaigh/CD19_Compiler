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

public class NGlobNodeTests {

    //NGLOB	<globals>	::=	<consts> <types> <arrays>
    @Mock
    NConstsNode consts;
    @Mock
    NTypesNode types;
    @Mock
    NArraysNode arrays;

    @InjectMocks
    NGlobNode glob;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void NGlobNode_SunnyDayData() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TCONS,1,1,null));
        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TMAIN,1,1,null));

        Parser parser = new Parser(tokens);

        when(consts.make(parser)).thenReturn(new TreeNode(TreeNode.NUNDEF)); //todo is this right? spec says its a "special" token
        when(types.make(parser)).thenReturn(new TreeNode(TreeNode.NUNDEF)); //todo same here
        when(arrays.make(parser)).thenReturn(new TreeNode(TreeNode.NUNDEF)); //todo and here

        TreeNode globs = glob.make(parser);

        assertEquals(TreeNode.NGLOB, globs.getValue());
        assertEquals(TreeNode.NUNDEF, globs.getLeft().getValue());
        assertEquals(TreeNode.NUNDEF, globs.getMiddle().getValue());
        assertEquals(TreeNode.NUNDEF, globs.getRight().getValue());
    }

}
