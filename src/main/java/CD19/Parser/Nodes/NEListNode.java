package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NEListNode implements Node{
    //NEXPL	<elist>	::=	<bool> <elistTail>
    //	<elistTail>	::=	ε | , <elist>

    private NBoolNode nBoolNode;

    public NEListNode() {
        this(new NBoolNode());
    }

    public NEListNode(NBoolNode nBoolNode) {
        this.nBoolNode = nBoolNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        //NEXPL	<elist>	::=	<bool> <elistTail>
        TreeNode bool = nBoolNode.make(parser);
        TreeNode tail = tail(parser);

        return new TreeNode(TreeNode.NEXPL, bool,tail);
    }

    private TreeNode tail(Parser parser){
        //	<elistTail>	::=	ε | , <elist>
        if((parser).peekAndConsume(Token.TCOMA)){
            return this.make(parser);
        }

        return null;
    }
}
