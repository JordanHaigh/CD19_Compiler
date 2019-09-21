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

public class NAsgnStatNodeTests {
    //	<asgnstat>	::=	 <varTail> <asgnop> <bool>
    //NSIMV | NARRV	<varTail>	::=	ε | [<expr>] . <id>
    //NASGN, NPLEQ, NMNEQ, NSTEQ, NDVEQ	<asgnop>	::=	 = | += | -= | *= | /=
    @Test
    public void sunnyday__epsvartail_asgnop_bool(){
//        List<Token> tokens= new ArrayList<>();
//
//        tokens.add(new Token(Token.TEQUL,1,1,null));
//        tokens.add(new Token(Token.TILIT,1,1,null));
//
//        Parser parser = new Parser(tokens);
//
//        NBoolNode nBoolNode = mock(NBoolNode.class);
//        NExprNode nExprNode = mock(NExprNode.class);
//        NVarTailNode nVarTailNode= new NVarTailNode(nExprNode);
//        NEListNode neListNode = mock(NEListNode.class);
//        NExponentNode nExponentNode = new NExponentNode(nBoolNode, nVarTailNode, neListNode);
//        NFactNode nFactNode = new NFactNode(nExponentNode);
//        NTermNode nTermNode = new NTermNode(nFactNode);
//        NExprNode nExprNode1 = new NExprNode(nTermNode);
//
//        NVarTailNode nVarTailNode1 = new NVarTailNode(nExprNode1);
//        NAsgnOpNode nAsgnOpNode = new NAsgnOpNode();
//        NBoolNode nBoolNode1 = new NBoolNode();


    }
}
