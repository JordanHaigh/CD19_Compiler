package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NEListNode implements Node{
    //NEXPL	<elist>	::=	<bool> <elistTail>
    //	<elistTail>	::=	ε | , <bool> <elistTail>

    private NBoolNode nBoolNode;

    public NEListNode() {
        this(null);
    }

    public NEListNode(NBoolNode nBoolNode) {
        this.nBoolNode = nBoolNode;
    }


    private static NEListNode instance;
    public static NEListNode INSTANCE() {
        if (instance == null) {
            instance = new NEListNode();
        }
        return instance;
    }

    public void setnBoolNode(NBoolNode boolNode) {
        this.nBoolNode = boolNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        //NEXPL	<elist>	::=	<bool> <elistTail>
        TreeNode bool = nBoolNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null){
            return bool;
        }
        else{
            return new TreeNode(TreeNode.NEXPL, bool, tail);
        }
    }

    private TreeNode tail(Parser parser){
        //	<elistTail>	::=	ε | , <bool> <elistTail>
        if((parser).peekAndConsume(Token.TCOMA)){
            TreeNode bool = nBoolNode.make(parser);
            TreeNode tail = tail(parser);
            if(tail == null)
                return bool;

            return new TreeNode(TreeNode.NEXPL, bool, tail);
        }

        return null;
    }

}
