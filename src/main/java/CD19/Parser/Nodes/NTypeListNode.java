package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NTypeListNode implements Node{
    //NTYPEL	<typelist>	::=	<type> <typelistTail>
    //	<typelistTail>	::=	eps |  <type> <typelistTail>


    public NTypeListNode() {
        this(NTypeNode.INSTANCE());
    }

    public NTypeListNode(NTypeNode nTypeNode) {
        this.nTypeNode = nTypeNode;
    }

    private static NTypeListNode instance;
    public static NTypeListNode INSTANCE() {
        if (instance == null) {
            instance = new NTypeListNode();
        }
        return instance;
    }

    NTypeNode nTypeNode;

    @Override
    public TreeNode make(Parser parser) {
        TreeNode type = nTypeNode.make(parser);
        TreeNode tail = tail(parser);
        if(tail == null)
            return type;

        return new TreeNode(TreeNode.NTYPEL, type,tail);
    }

    private TreeNode tail(Parser parser){
        if(parser.peek().getTokenID() == Token.TIDEN){  //<type>
            TreeNode type = nTypeNode.make(parser);
            TreeNode tail = tail(parser);

            if(tail == null)
                return type;

            return new TreeNode(TreeNode.NTYPEL, type,tail);
        }
        return null; //eps transition
    }

}


