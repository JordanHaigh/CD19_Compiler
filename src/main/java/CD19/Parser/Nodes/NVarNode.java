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
        this(new NVarTailNode());
    }

    public NVarNode(NVarTailNode nVarTailNode) {
        this.nVarTailNode = nVarTailNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token id = parser.peek();
        parser.consume();

        TreeNode tail = nVarTailNode.make(parser);
        SymbolTableRecord record = tail.getSymbol();
        return new TreeNode(tail.getValue(),record); //todo probs wrong
    }


}
