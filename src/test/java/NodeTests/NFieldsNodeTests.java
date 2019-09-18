package NodeTests;

import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NFieldsNodeTests {

    //NFLIST	<fields>	::=	<sdecl> <fieldsTail>
    //	<fieldsTail>	::=	ε  | , <fields>



    @Test
    public void fieldsNode_epsTest(){
        List<Token> tokens= new ArrayList<>();

        // age : integer    end

        tokens.add(new Token(Token.TIDEN,1,1,"age"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));
        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);

        NFieldsNode nFieldsNode = new NFieldsNode();
        TreeNode fieldsList = nFieldsNode.make(parser);

        assertEquals(TreeNode.NFLIST, fieldsList.getValue());
        assertEquals(TreeNode.NSDECL, fieldsList.getLeft().getValue());
        assertEquals(null, fieldsList.getRight());

    }

    @Test
    public void typeList_notEpsTest(){
        List<Token> tokens= new ArrayList<>();

        // age : integer , height : real, deceased: boolean   end

        tokens.add(new Token(Token.TIDEN,1,1,"age"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));
        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"height"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TREAL,1,1,null));
        tokens.add(new Token(Token.TCOMA,1,1,null));

        tokens.add(new Token(Token.TIDEN,1,1,"deceased"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TBOOL,1,1,null));

        tokens.add(new Token(Token.TEND,1,1,null));

        Parser parser = new Parser(tokens);

        NFieldsNode nFieldsNode = new NFieldsNode();
        TreeNode fieldsList = nFieldsNode.make(parser);

        assertEquals(TreeNode.NFLIST, fieldsList.getValue());
        assertEquals(TreeNode.NSDECL, fieldsList.getLeft().getValue());

        assertEquals(TreeNode.NFLIST, fieldsList.getRight().getValue());
        assertEquals(TreeNode.NSDECL, fieldsList.getRight().getLeft().getValue());

        assertEquals(TreeNode.NFLIST, fieldsList.getRight().getRight().getValue());
        assertEquals(TreeNode.NSDECL, fieldsList.getRight().getRight().getLeft().getValue());
        assertEquals(null, fieldsList.getRight().getRight().getRight());
    }
}
