package NodeTests;

import CD19.Parser.Nodes.*;

/**
 * Jordan Haigh c3256730 CD19
 * public class SetupMocks
 * Used to remove cyclic constructors when making unit tests
 * */
public class SetupMocks {

    public static void setup(){

        //fuck you cyclic constructors! not today!
        NExponentNode nExponentNode = NExponentNode.INSTANCE();
        NReptStatNode nReptStatNode = NReptStatNode.INSTANCE();
        NAsgnStatNode nAsgnStatNode= NAsgnStatNode.INSTANCE();
        NEListNode neListNode = NEListNode.INSTANCE();
        NForStatNode nForStatNode = NForStatNode.INSTANCE();
        NIfStatNode nIfStatNode = NIfStatNode.INSTANCE();

        NInitNode nInitNode = NInitNode.INSTANCE();
        NTypeNode nTypeNode = NTypeNode.INSTANCE();
        NRelNode nRelNode = NRelNode.INSTANCE();
        NPrintItemNode nPrintItemNode = NPrintItemNode.INSTANCE();
        NReturnStatNode nReturnStatNode = NReturnStatNode.INSTANCE();


        // Fix broken recursive loop
        nExponentNode.setnBoolNode(NBoolNode.INSTANCE());
        nReptStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nAsgnStatNode.setnBoolNode(NBoolNode.INSTANCE());
        neListNode.setnBoolNode(NBoolNode.INSTANCE());
        nForStatNode.setnBoolNode(NBoolNode.INSTANCE());
        nIfStatNode.setnBoolNode(NBoolNode.INSTANCE());

        nInitNode.setnExprNode(NExprNode.INSTANCE());
        nTypeNode.setnExprNode(NExprNode.INSTANCE());
        nRelNode.setnExprNode(NExprNode.INSTANCE());
        nPrintItemNode.setnExprNode(NExprNode.INSTANCE());
        nReturnStatNode.setnExprNode(NExprNode.INSTANCE());
    }
}
