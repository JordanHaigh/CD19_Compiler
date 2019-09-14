package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NTypesNode implements Node {
    //<types>	::=	types <typelist> | ε

    private NTypeListNode nTypeListNode;

    public NTypesNode(){
        this(new NTypeListNode());
    }

    public NTypesNode(NTypeListNode nTypeListNode){
        this.nTypeListNode = nTypeListNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        if(parser.peekAndConsume(Token.TTYPS)){
            Token token = parser.peek();
            if(token.getTokenID() == Token.TIDEN){
                return nTypeListNode.make(parser);
            }
        }
        return null; //eps transition
    }
}

