package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NInitListNode implements Node {
    //NILIST	<initlist>	::=	<init> <initListTail>
    //	<initListTail>	::=	ε | ,<initlist>

    private NInitNode nInitNode;

    public NInitListNode(){
        this(new NInitNode());
    }

    public NInitListNode(NInitNode nInitNode){
        this.nInitNode = nInitNode;
    }


    @Override
    public TreeNode make(Parser parser) {
        //NILIST	<initlist>	::=	<init> <initListTail>
        TreeNode nInitTreeNode = nInitNode.make(parser);
        TreeNode tailTreeNode = tail(parser);

        return new TreeNode(TreeNode.NILIST, nInitTreeNode, tailTreeNode);
    }


    private TreeNode tail(Parser parser){
        //	<initListTail>	::=	ε | ,<initlist>
        if(parser.peekAndConsume(Token.TCOMA)){
            return this.make(parser);
        }
        return null; //eps transition

    }
}

