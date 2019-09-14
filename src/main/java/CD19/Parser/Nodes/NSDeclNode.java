package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import com.sun.corba.se.impl.orbutil.graph.NodeData;
import sun.reflect.generics.tree.Tree;

public class NSDeclNode implements Node{
    //NSDECL	<sdecl>	::=	<id> : <stype>

    NSTypeNode nsTypeNode;

    public NSDeclNode(){
        this(new NSTypeNode());
    }

    public NSDeclNode(NSTypeNode nsTypeNode){
        this.nsTypeNode = nsTypeNode;
    }


    @Override
    public TreeNode make(Parser parser) {
        Token id =  parser.peek();
        parser.consume();
        parser.peekAndConsume(Token.TSEMI);
        TreeNode stype = nsTypeNode.make(parser);

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), stype.getType());
        return new TreeNode(TreeNode.NSDECL, record);
    }
}

