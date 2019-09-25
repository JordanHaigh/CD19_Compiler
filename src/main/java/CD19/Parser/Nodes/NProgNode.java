package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NProgNode implements Node{

    //NPROG	<program>	::=	CD19 <id> <globals> <funcs> <mainbody>
    private NGlobNode nGlobNode;
    private NFuncsNode nFuncsNode;
    private NMainBodyNode nMainBodyNode;

    public NProgNode() {
        this(NGlobNode.INSTANCE(), NFuncsNode.INSTANCE(), NMainBodyNode.INSTANCE());
    }

    public NProgNode(NGlobNode nGlobNode, NFuncsNode nFuncsNode, NMainBodyNode nMainBodyNode) {
        this.nGlobNode = nGlobNode;
        this.nFuncsNode = nFuncsNode;
        this.nMainBodyNode = nMainBodyNode;
    }

    private static NProgNode instance;
    public static NProgNode INSTANCE() {
        if (instance == null) {
            instance = new NProgNode();
        }
        return instance;
    }

    public TreeNode make(Parser parser) {
        parser.peekAndConsume(Token.TCD19); //CD19 keyword
        //error check
        Token startId = parser.peek();
        parser.peekAndConsume(Token.TIDEN); //name of program

        //error check
        parser.enterScope(startId.getStr());

        TreeNode nGlobTreeNode = nGlobNode.make(parser);
        TreeNode nFuncsTreeNode = nFuncsNode.make(parser);
        TreeNode nMainTreeNode = nMainBodyNode.make(parser);
        parser.peekAndConsume(Token.TCD19);

        Token endId = parser.peek();
        parser.peekAndConsume(Token.TIDEN);

        SymbolTableRecord startRecord = new SymbolTableRecord(startId.getStr(), null, "");

        parser.insertIdentifierRecord(startRecord);

        SymbolTableRecord endRecord = new SymbolTableRecord(endId.getStr(), null, "");

        parser.insertIdentifierRecord(endRecord);

        TreeNode nProgTreeNode = new TreeNode(TreeNode.NPROG,nGlobTreeNode,nFuncsTreeNode, nMainTreeNode);

        nProgTreeNode.setSymbol(startRecord); //todo fix endrecord in semantic

        parser.leaveScope();

        return nProgTreeNode;
    }

}
