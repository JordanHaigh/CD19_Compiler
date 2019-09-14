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
    public void NGlobNode_SunnyDayData_constNodeNotEps_TypesNodeNotEps_ArraysNodeNotEps() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TCONS,1,1,null));
        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TMAIN,1,1,null));

        Parser parser = new Parser(tokens);

        when(consts.make(parser)).thenReturn(new TreeNode(TreeNode.NILIST));
        when(types.make(parser)).thenReturn(new TreeNode(TreeNode.NTYPEL));
        when(arrays.make(parser)).thenReturn(new TreeNode(TreeNode.NALIST));

        TreeNode globs = glob.make(parser);

        assertEquals(TreeNode.NGLOB, globs.getValue());
        assertEquals(TreeNode.NILIST, globs.getLeft().getValue());
        assertEquals(TreeNode.NTYPEL, globs.getMiddle().getValue());
        assertEquals(TreeNode.NALIST, globs.getRight().getValue());
    }

    @Test
    public void NGlobNode_SunnyDayData_constNodeEps_TypesNodeEps_ArraysNodeEps() {
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TCD19,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"prog"));
        tokens.add(new Token(Token.TCONS,1,1,null));
        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TMAIN,1,1,null));

        Parser parser = new Parser(tokens);

        when(consts.make(parser)).thenReturn(null);
        when(types.make(parser)).thenReturn(null);
        when(arrays.make(parser)).thenReturn(null);

        TreeNode globs = glob.make(parser);

        assertEquals(TreeNode.NGLOB, globs.getValue());
        assertEquals(null, globs.getLeft());
        assertEquals(null, globs.getMiddle());
        assertEquals(null, globs.getRight());
    }

}
