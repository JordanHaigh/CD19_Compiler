package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NAlistNode implements Node{

    //NASGNS	<alist>	::=	<id><asgnstat> <alistTail>
	//<alistTail>	::=	eps | , <alist>

    NAsgnStatNode nAsgnStatNode;

    public NAlistNode() {
        this(new NAsgnStatNode());
    }

    public NAlistNode(NAsgnStatNode nAsgnStatNode) {
        this.nAsgnStatNode = nAsgnStatNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        parser.consume();
        TreeNode asgnStat = nAsgnStatNode.make(parser);
        TreeNode tail = tail(parser);
        //SymbolTableRecord record = new SymbolTableRecord(token.getStr(), ) //todo do this later
        return new TreeNode(TreeNode.NASGNS, asgnStat, tail); //todo wrong. fix later


    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            return this.make(parser); //alist tran
        }
        return null; //eps trans

    }
}
