package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NProgNode implements Node{

    //NPROG	<program>	::=	CD19 <id> <globals> <funcs> <mainbody>
    private NGlobNode nGlobNode;
    private NFuncsNode nFuncsNode;
    private NMainBodyNode nMainBodyNode;

    public NProgNode() {
        this(new NGlobNode(), new NFuncsNode(), new NMainBodyNode());
    }

    public NProgNode(NGlobNode nGlobNode, NFuncsNode nFuncsNode, NMainBodyNode nMainBodyNode) {
        this.nGlobNode = nGlobNode;
        this.nFuncsNode = nFuncsNode;
        this.nMainBodyNode = nMainBodyNode;
    }

    public TreeNode make(Parser parser) {
        parser.peekAndConsume(Token.TCD19); //CD19 keyword
        //error check

        parser.peekAndConsume(Token.TIDEN); //name of program
        //error check

        TreeNode nGlobTreeNode = nGlobNode.make(parser);
        TreeNode nFuncsTreeNode = nFuncsNode.make(parser);
        TreeNode nMainTreeNode = nMainBodyNode.make(parser);

        TreeNode nProgTreeNode = new TreeNode(TreeNode.NPROG,nGlobTreeNode,nFuncsTreeNode, nMainTreeNode);

        return nProgTreeNode;
    }

}
