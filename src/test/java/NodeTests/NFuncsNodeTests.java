package NodeTests;


import CD19.Parser.Nodes.*;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NFuncsNodeTests {
//    //NFUNCS	<funcs>	::=	eps | <func> <funcs>
    @Test
    public void sunnyday_uno(){
        //function aaa (a : integer, const myarray : people) : void begin a=5; end
        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aaa"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TCNST,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"myarray"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TVOID,1,1,null));
        tokens.add(new Token(Token.TBEGN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"a"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,"5"));
        tokens.add(new Token(Token.TSEMI,1,1,null));
        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TMAIN,1,1,null));

        Parser parser = new Parser(tokens);


        NStatNode nStatNode = mock(NStatNode.class);
        NStrStatNode nStrStatNode = mock(NStrStatNode.class);

        NStatsNode nStatsNode = new NStatsNode(nStatNode, nStrStatNode);

        NFuncBodyNode nFuncBodyNode = new NFuncBodyNode(new NLocalsNode(), nStatsNode);
        NFuncNode nFuncNode = new NFuncNode(new NPListNode(), new NRTypeNode(), nFuncBodyNode);
        NFuncsNode nFuncsNode = new NFuncsNode(nFuncNode);

        when(nStatNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume(3);
            return new TreeNode(TreeNode.NASGN);
        });

        TreeNode funcs = nFuncsNode.make(parser);

        assertEquals(TreeNode.NFUNCS, funcs.getValue());
        assertEquals(TreeNode.NFUND, funcs.getLeft().getValue());
        assertEquals(null, funcs.getRight());
    }


    @Test
    public void sunnyday_dos(){
        //function aaa (a : integer, const myarray : people) : void begin a=5; end
        // function bbb (a : integer, const myarray : people) : void begin a=5; end



        List<Token> tokens= new ArrayList<>();

        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"aaa"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TCNST,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"myarray"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TVOID,1,1,null));
        tokens.add(new Token(Token.TBEGN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"a"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,"5"));
        tokens.add(new Token(Token.TSEMI,1,1,null));
        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TFUNC,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"bbb"));
        tokens.add(new Token(Token.TLPAR,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"a"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TINTG,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TCNST,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"scrrscrr"));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"people"));
        tokens.add(new Token(Token.TRPAR,1,1,null));
        tokens.add(new Token(Token.TCOLN,1,1,null));
        tokens.add(new Token(Token.TVOID,1,1,null));
        tokens.add(new Token(Token.TBEGN,1,1,null));
        tokens.add(new Token(Token.TIDEN,1,1,"a"));
        tokens.add(new Token(Token.TEQUL,1,1,null));
        tokens.add(new Token(Token.TILIT,1,1,"5"));
        tokens.add(new Token(Token.TSEMI,1,1,null));
        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TMAIN,1,1,null));

        Parser parser = new Parser(tokens);


        NStatNode nStatNode = mock(NStatNode.class);
        NStrStatNode nStrStatNode = mock(NStrStatNode.class);

        NStatsNode nStatsNode = new NStatsNode(nStatNode, nStrStatNode);

        NFuncBodyNode nFuncBodyNode = new NFuncBodyNode(new NLocalsNode(), nStatsNode);
        NFuncNode nFuncNode = new NFuncNode(new NPListNode(), new NRTypeNode(), nFuncBodyNode);
        NFuncsNode nFuncsNode = new NFuncsNode(nFuncNode);

        when(nStatNode.make(parser)).thenAnswer((Answer) invocationOnMock -> {
            parser.consume(3);
            return new TreeNode(TreeNode.NASGN);
        });

        TreeNode funcs = nFuncsNode.make(parser);

        assertEquals(TreeNode.NFUNCS, funcs.getValue());
        assertEquals(TreeNode.NFUND, funcs.getLeft().getValue());
        assertEquals(TreeNode.NFUNCS, funcs.getRight().getValue());

    }
}
