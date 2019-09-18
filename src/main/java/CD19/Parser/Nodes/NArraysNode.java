package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NArraysNode implements Node {

    //<arrays>	::=	eps | arrays <arrdecls>

    private NArrDeclsNode nArrDeclsNode;


    public NArraysNode(){
        this(new NArrDeclsNode());
    }

    public NArraysNode(NArrDeclsNode nArrDeclsNode){
        this.nArrDeclsNode = nArrDeclsNode;
    }


    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TARRS)){
            Token token = parser.peek();
            if(token.getTokenID() ==Token.TIDEN){
                return nArrDeclsNode.make(parser);
            }
        }
        return null;
    }
}

