package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NInitListNode implements Node {
    //NILIST	<initlist>	::=	<init> <initListTail>
    //	<initListTail>	::=	eps | ,<initlist>

    private NInitNode nInitNode;

    public NInitListNode(){
        this(NInitNode.INSTANCE());
    }

    public NInitListNode(NInitNode nInitNode){
        this.nInitNode = nInitNode;
    }


    private static NInitListNode instance;
    public static NInitListNode INSTANCE() {
        if (instance == null) {
            instance = new NInitListNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        //NILIST	<initlist>	::=	<init> <initListTail>
        TreeNode init = nInitNode.make(parser);
        TreeNode tail = tail(parser);

        return new TreeNode(TreeNode.NILIST, init, tail);
    }


    private TreeNode tail(Parser parser){
        //	<initListTail>	::=	ε | ,<initlist>
        if(parser.peekAndConsume(Token.TCOMA)){
            return this.make(parser);
        }
        return null; //eps transition

    }
}

