package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NFieldsNode implements Node{


    //NFLIST	<fields>	::=	<sdecl> <fieldsTail>
    //	<fieldsTail>	::=	eps  | , <fields>

    NSDeclNode sDeclNode;

    public NFieldsNode(){
        this(new NSDeclNode());
    }

    public NFieldsNode(NSDeclNode sDeclNode){
        this.sDeclNode = sDeclNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode sDecl = sDeclNode.make(parser);
        TreeNode tail = tail(parser);

        return new TreeNode(TreeNode.NFLIST, sDecl, tail);
    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            return this.make(parser); //back to fields transition
        }

        return null; //eps trans


    }
}
