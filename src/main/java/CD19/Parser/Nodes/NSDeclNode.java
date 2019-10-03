package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import com.sun.corba.se.impl.orbutil.graph.NodeData;
import sun.reflect.generics.tree.Tree;

/**
 * Generates a sdecl of the form:
 * <sdecl>	::=	<id> : <stype>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NSDeclNode implements Node{
    //NSDECL	<sdecl>	::=	<id> : <stype>

    NSTypeNode nsTypeNode;
    private static NSDeclNode instance;


    public NSDeclNode(){
        this(NSTypeNode.INSTANCE());
    }

    public NSDeclNode(NSTypeNode nsTypeNode){
        this.nsTypeNode = nsTypeNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NSDeclNode INSTANCE() {
        if (instance == null) {
            instance = new NSDeclNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the sdecl node
     * @param parser The parser
     * @return A valid sdecl TreeNode or NUNDEF if syntactic error
     */
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

