package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import sun.reflect.generics.tree.Tree;

public class NTermNode implements Node{
    //NMUL | NDIV | NMOD	<term>	::=	<fact><termTail>
    //	<termTail	::=	ε | *<term> | /<term> | %<term>

    NFactNode nFactNode;

    public NTermNode() {
        this(new NFactNode());
    }

    public NTermNode(NFactNode nFactNode) {
        this.nFactNode = nFactNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode fact = nFactNode.make(parser);
        TreeNode tail = tail(parser);

        return new TreeNode(tail.getValue(), fact, tail);
    }

    private TreeNode tail(Parser parser){
        //	<termTail	::=	ε | *<term> | /<term> | %<term>
        if(parser.peekAndConsume(Token.TSTAR)){
            TreeNode term = this.make(parser);
            return new TreeNode(TreeNode.NMUL, term, null);
        }
        else if(parser.peekAndConsume(Token.TDIVD)){
            TreeNode term = this.make(parser);
            return new TreeNode(TreeNode.NDIV, term, null);
        }
        else if(parser.peekAndConsume(Token.TPERC)){
            TreeNode term = this.make(parser);
            return new TreeNode(TreeNode.NMOD, term, null);
        }
        else
            return null; //todo error handle


    }
}


