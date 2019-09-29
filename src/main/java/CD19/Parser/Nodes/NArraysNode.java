package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NArraysNode implements Node {

    //<arrays>	::=	eps | arrays <arrdecls>

    private NArrDeclsNode nArrDeclsNode;


    public NArraysNode(){
        this(NArrDeclsNode.INSTANCE());
    }

    public NArraysNode(NArrDeclsNode nArrDeclsNode){
        this.nArrDeclsNode = nArrDeclsNode;
    }


    private static NArraysNode instance;
    public static NArraysNode INSTANCE() {
        if (instance == null) {
            instance = new NArraysNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TARRS)){
            return nArrDeclsNode.make(parser);
        }
        return null;
    }

}

