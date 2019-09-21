package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import sun.reflect.generics.tree.Tree;

public class NTermNode implements Node{
    //NMUL | NDIV | NMOD	<term>	::=	<fact><termTail>
    //	<termTail	::=	ε | *<fact><termTail> | /<fact><termTail> | %<fact><termTail>

    NFactNode nFactNode;

    public NTermNode() {
        this(NFactNode.INSTANCE());
    }

    public NTermNode(NFactNode nFactNode) {
        this.nFactNode = nFactNode;
    }

    private static NTermNode instance;
    public static NTermNode INSTANCE() {
        if (instance == null) {
            instance = new NTermNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode fact = nFactNode.make(parser);
        TreeNode tail = tail(parser, fact);
        if(tail != null) //whens its just a fact
            return tail;
        else
            return fact;
    }

    private TreeNode tail(Parser parser, TreeNode leftNode){
        //	<termTail	::=	ε | *<term> | /<term> | %<term>
        if(parser.peekAndConsume(Token.TSTAR)){
            return buildLeftDerivedTree(parser, TreeNode.NMUL, leftNode);

            //TreeNode term = this.make(parser);
            //return new TreeNode(TreeNode.NMUL, term, null);
        }
        else if(parser.peekAndConsume(Token.TDIVD)){
            return buildLeftDerivedTree(parser, TreeNode.NDIV, leftNode);

            //TreeNode term = this.make(parser);
            //return new TreeNode(TreeNode.NDIV, term, null);
        }
        else if(parser.peekAndConsume(Token.TPERC)){
            return buildLeftDerivedTree(parser, TreeNode.NMOD, leftNode);

            //TreeNode term = this.make(parser);
            //return new TreeNode(TreeNode.NMOD, term, null);
        }
        else
            return null; //epsilon transition
    }


    private TreeNode buildLeftDerivedTree(Parser parser, int expectedTreeNodeValue, TreeNode leftNode){
        TreeNode returnTreeNode = new TreeNode(expectedTreeNodeValue);
        returnTreeNode.setLeft(leftNode);

        TreeNode fact = nFactNode.make(parser);
        TreeNode tail = tail(parser, returnTreeNode);

        returnTreeNode.setRight(fact);
        if(tail == null)
            return returnTreeNode;
        else
            return tail;

    }
}


