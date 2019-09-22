package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NVarNode implements Node{

    //NSIMV | NARRV	<var>	::=	<id> <varTail>
    //	<varTail>	::=	ε | [<expr>] . <id>

    NVarTailNode nVarTailNode;

    public NVarNode() {
        this(NVarTailNode.INSTANCE());
    }

    public NVarNode(NVarTailNode nVarTailNode) {
        this.nVarTailNode = nVarTailNode;
    }

    private static NVarNode instance;
    public static NVarNode INSTANCE() {
        if (instance == null) {
            instance = new NVarNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token id = parser.peek();
        if(id.getTokenID() == Token.TIDEN){
            parser.consume();
            TreeNode tail = nVarTailNode.make(parser);
            SymbolTableRecord record = tail.getSymbol();
            return new TreeNode(tail.getValue(),record); //todo probs wrong
        }
        else
            return new TreeNode(TreeNode.NUNDEF); //error detect
    }


}
