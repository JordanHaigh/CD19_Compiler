package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NArrDeclsNode implements Node{
    //NALIST	<arrdecls>	::=	<arrdecl> <arrDeclTail>
    //	<arrdeclsTail>	::=	eps |  , <arrdecls>

    NArrDeclNode nArrDeclNode;

    public NArrDeclsNode(){
        this(new NArrDeclNode());
    }

    public NArrDeclsNode(NArrDeclNode node){
        this.nArrDeclNode = node;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode arrDeclNode = nArrDeclNode.make(parser);
        TreeNode tail = arrDeclTail(parser);

        return new TreeNode(TreeNode.NALIST, arrDeclNode, tail);
    }

    private TreeNode arrDeclTail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            return this.make(parser); //<arrdecls>
        }
        return null; //epsilon transition
    }

}

