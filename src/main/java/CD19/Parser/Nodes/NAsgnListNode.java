package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NAsgnListNode implements Node{

    //	<asgnlist>	::=	<alist> | eps
    NAlistNode nAlistNode;


    public NAsgnListNode() {
        this(NAlistNode.INSTANCE());
    }

    public NAsgnListNode(NAlistNode nAlistNode) {
        this.nAlistNode = nAlistNode;
    }


    private static NAsgnListNode instance;
    public static NAsgnListNode INSTANCE() {
        if (instance == null) {
            instance = new NAsgnListNode();
        }
        return instance;
    }


    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TIDEN){
            return nAlistNode.make(parser); //alist trans (dont consume token alist will need it)
        }
        return null; //eps trans
    }
}


