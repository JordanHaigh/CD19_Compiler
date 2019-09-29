package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * NALIST	<arrdecls>	::=	<arrdecl> <arrDeclTail>
 * <arrdeclsTail>	::=	eps |  , <arrdecl> <arrDeclTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NArrDeclsNode implements Node{


    NArrDeclNode nArrDeclNode;

    public NArrDeclsNode(){
        this(NArrDeclNode.INSTANCE());
    }

    public NArrDeclsNode(NArrDeclNode node){
        this.nArrDeclNode = node;
    }

    private static NArrDeclsNode instance;
    public static NArrDeclsNode INSTANCE() {
        if (instance == null) {
            instance = new NArrDeclsNode();
        }
        return instance;
    }


    /**
     *  Attemps to generate a Arrdecls node
     * @param parser The Parser
     * @return Valid TreeNode for ArrDecls or NUNDEF
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode arrdecl = nArrDeclNode.make(parser);
        TreeNode tail = tail(parser);

        if(tail == null)
            return arrdecl;

        return new TreeNode(TreeNode.NALIST, arrdecl, tail);
    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            TreeNode arrdecl = nArrDeclNode.make(parser);
            TreeNode tail = tail(parser);

            if(tail == null)
                return arrdecl;

            return new TreeNode(TreeNode.NALIST, arrdecl, tail);

        }
        return null; //epsilon transition
    }

}

