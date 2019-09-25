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
        this(NSTypeNode.INSTANCE());
    }

    public NSDeclNode(NSTypeNode nsTypeNode){
        this.nsTypeNode = nsTypeNode;
    }

    private static NSDeclNode instance;
    public static NSDeclNode INSTANCE() {
        if (instance == null) {
            instance = new NSDeclNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token id =  parser.peek();
        parser.consume();
        parser.peekAndConsume(Token.TCOLN);
        TreeNode stype = nsTypeNode.make(parser);

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), stype.getType(),parser.getScope());//todo scope later
        parser.insertIdentifierRecord(record);

        return new TreeNode(TreeNode.NSDECL, record);
    }
}

