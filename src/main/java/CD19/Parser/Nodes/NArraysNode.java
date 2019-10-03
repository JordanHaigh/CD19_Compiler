package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
/**
 * Jordan Haigh c3256730 CD19
 * Generates an arrays node of the form
 * <arrays>	::=	eps | arrays <arrdecls>
 */

public class NArraysNode implements Node {

    //<arrays>	::=	eps | arrays <arrdecls>

    private NArrDeclsNode nArrDeclsNode;
    private static NArraysNode instance;


    public NArraysNode(){
        this(NArrDeclsNode.INSTANCE());
    }

    public NArraysNode(NArrDeclsNode nArrDeclsNode){
        this.nArrDeclsNode = nArrDeclsNode;
    }


    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NArraysNode INSTANCE() {
        if (instance == null) {
            instance = new NArraysNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the arrays node
     * @param parser The parser
     * @return A valid arrays TreeNode or null
     */
    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TARRS)){
            return nArrDeclsNode.make(parser);
        }
        return null;
    }

}

