package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NIoStatNode implements Node{

    //NINPUT | NPRINT| NPRLN	<iostat>	::=	input <vlist> | print <prlist> | printline <prlist>

    NVListNode nvListNode;
    NPrListNode nPrListNode;

    public NIoStatNode() {
        this(new NVListNode(), new NPrListNode());
    }

    public NIoStatNode(NVListNode nvListNode, NPrListNode nPrListNode) {
        this.nvListNode = nvListNode;
        this.nPrListNode = nPrListNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TINPT)){
            TreeNode vlist = nvListNode.make(parser);
            return new TreeNode(TreeNode.NINPUT, vlist,null);
        }
        else if(parser.peekAndConsume(Token.TPRIN)){
            TreeNode prlist = nPrListNode.make(parser);
            return new TreeNode(TreeNode.NPRINT, prlist, null);
        }
        else if(parser.peekAndConsume(Token.TPRLN)){
            TreeNode prlist = nPrListNode.make(parser);
            return new TreeNode(TreeNode.NPRLN, prlist, null);
        }
        else return null; //todo error check

    }
}

