package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates an asgnlist of the form:
 * <asgnlist>	::=	<alist> | eps
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NAsgnListNode implements Node{

    //	<asgnlist>	::=	<alist> | eps
    NAlistNode nAlistNode;
    private static NAsgnListNode instance;


    public NAsgnListNode() {
        this(NAlistNode.INSTANCE());
    }

    public NAsgnListNode(NAlistNode nAlistNode) {
        this.nAlistNode = nAlistNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */

    public static NAsgnListNode INSTANCE() {
        if (instance == null) {
            instance = new NAsgnListNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the asgnlist node
     * @param parser The parser
     * @return A valid asgnlist TreeNode or null if it doesnt see an identifier
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN){
            return nAlistNode.make(parser); //alist trans (dont consume token alist will need it)
        }
        return null; //eps trans
    }

}


