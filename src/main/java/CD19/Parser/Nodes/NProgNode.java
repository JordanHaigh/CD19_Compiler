package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NProgNode implements Node{

    //NPROG	<program>	::=	CD19 <id> <globals> <funcs> <mainbody>
    private NGlobNode nGlobNode;
    private NFuncsNode nFuncsNode;
    private NMainNode nMainNode;

    public NProgNode() {
        this(new NGlobNode(), new NFuncsNode(), new NMainNode());
    }

    public NProgNode(NGlobNode nGlobNode, NFuncsNode nFuncsNode, NMainNode nMainNode) {
        this.nGlobNode = nGlobNode;
        this.nFuncsNode = nFuncsNode;
        this.nMainNode = nMainNode;
    }

    public TreeNode make(Parser parser) {
        parser.peekAndConsume(Token.TCD19);
        //error check

        parser.peekAndConsume(Token.TIDEN);
        //error check

        TreeNode nGlobTreeNode = nGlobNode.make(parser);
        TreeNode nFuncsTreeNode = nFuncsNode.make(parser);
        TreeNode nMainTreeNode = nMainNode.make(parser);

        TreeNode nProgTreeNode = new TreeNode(TreeNode.NPROG,nGlobTreeNode,nFuncsTreeNode, nMainTreeNode);

        return nProgTreeNode;
    }

}
