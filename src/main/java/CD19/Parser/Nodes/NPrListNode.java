package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NPrListNode implements Node{
    //NPRLST	<prlist>	::=	<printitem><prlistTail>
    //	<prlistTail>	::=	ε |  , <prlist>

    NPrintItemNode nPrintItemNode;


    public NPrListNode() {
        this(new NPrintItemNode());
    }

    public NPrListNode(NPrintItemNode nPrintItemNode) {
        this.nPrintItemNode = nPrintItemNode;
    }

    @Override
    public TreeNode make(Parser parser) {

        TreeNode printitem = nPrintItemNode.make(parser);
        TreeNode tail = tail(parser);

        return new TreeNode(TreeNode.NPRLST, printitem, tail);
    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            return this.make(parser); //recurse trans
        }
        return null; //eps trans

    }
}

