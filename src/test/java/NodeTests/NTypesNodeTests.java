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

public class NTypesNodeTests {
    //<types>	::=	types <typelist> | ε
    @Mock
    NTypeListNode nTypeList;

    @InjectMocks
    NTypesNode types;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void NConstsNode_SunnyDay_constantsInitlistTrans(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TTYPS,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aa"));

        Parser parser = new Parser(tokens);

        when(nTypeList.make(parser)).thenReturn(new TreeNode(TreeNode.NTYPEL));

        TreeNode NTypesTreeNode = types.make(parser);

        assertEquals(TreeNode.NTYPEL, NTypesTreeNode.getValue());
    }

    @Test
    public void NConstsNode_SunnyDay_epsTrans(){
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));

        Parser parser = new Parser(tokens);

        when(nTypeList.make(parser)).thenReturn(new TreeNode(TreeNode.NTYPEL));

        TreeNode NTypesTreeNode = types.make(parser);

        assertEquals(null, NTypesTreeNode);
    }



}
