package CD19.Parser.Nodes;

import CD19.Parser.*;
import CD19.Scanner.Token;

public class NIoStatNode implements Node{

    //NINPUT | NPRINT| NPRLN	<iostat>	::=	input <vlist> | print <prlist> | printline <prlist>

    NVListNode nvListNode;
    NPrListNode nPrListNode;

    public NIoStatNode() {
        this(NVListNode.INSTANCE(), NPrListNode.INSTANCE());
    }

    public NIoStatNode(NVListNode nvListNode, NPrListNode nPrListNode) {
        this.nvListNode = nvListNode;
        this.nPrListNode = nPrListNode;
    }


    private static NIoStatNode instance;
    public static NIoStatNode INSTANCE() {
        if (instance == null) {
            instance = new NIoStatNode();
        }
        return instance;
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
        else{
            parser.syntacticError("Expected Valid IoStat keyword (input, print, printline)", parser.peek());
            return new TreeNode(); //todo error recover
        }
    }
}

