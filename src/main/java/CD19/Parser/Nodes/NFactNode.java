package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NFactNode implements Node{

    //NPOW	<fact>	::=	<exponent><factTail>
    //	<factTail>	::=	ε | ^<exponent><factTail>


    NExponentNode nExponentNode;

    public NFactNode() {
        this(NExponentNode.INSTANCE());
    }

    public NFactNode(NExponentNode nExponentNode) {
        this.nExponentNode = nExponentNode;
    }


    private static NFactNode instance;
    public static NFactNode INSTANCE() {
        if (instance == null) {
            instance = new NFactNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode exponent = nExponentNode.make(parser);
        TreeNode tail = tail(parser,exponent);
        //left derivation
        if(tail == null)
            return exponent;
        else
            return tail;

    }

    private TreeNode tail(Parser parser, TreeNode leftNode){
        if(parser.peekAndConsume(Token.TCART)){
            TreeNode returnTreeNode = new TreeNode(TreeNode.NPOW);
            returnTreeNode.setLeft(leftNode);

            TreeNode fact = nExponentNode.make(parser);
            TreeNode tail = tail(parser,returnTreeNode);

            returnTreeNode.setRight(fact);
            if(tail == null)
                return returnTreeNode;
            else
                return tail;
        }
        else //eps trans
            return null;

    }
}

