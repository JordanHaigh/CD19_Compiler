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
        TreeNode sdecl = new TreeNode();

        Token id =  parser.peek();
        if(!parser.peekAndConsume(Token.TIDEN)){
            parser.syntacticError("Expected an Identifier", parser.peek());
            return sdecl;
        }

        if(!parser.peekAndConsume(Token.TCOLN)){
            parser.syntacticError("Expected a Colon", parser.peek());
            return sdecl;
        }

        TreeNode stype = nsTypeNode.make(parser);

        if(stype.getValue() == TreeNode.NUNDEF){
            return sdecl;
        }

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(), stype.getType(),parser.getScope());
        parser.insertIdentifierRecord(record);

        sdecl = new TreeNode(TreeNode.NSDECL, record);
        return sdecl;
    }
}

