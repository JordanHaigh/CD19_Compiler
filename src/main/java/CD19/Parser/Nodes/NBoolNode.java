package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a bool of the form:
 * NBOOL	<bool>	::=	<rel><boolTail>
 * <boolTail>	::=	ε | <logop><rel><boolTail>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NBoolNode implements Node{



    NRelNode nRelNode;
    NLogopNode nLogopNode;
    private static NBoolNode instance;

    public NBoolNode() {
        this(NRelNode.INSTANCE(), NLogopNode.INSTANCE());
    }

    public NBoolNode(NRelNode nRelNode, NLogopNode nLogopNode) {
        this.nRelNode = nRelNode;
        this.nLogopNode = nLogopNode;
    }

    private boolean hasMultipleLogops = false;

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NBoolNode INSTANCE() {
        if (instance == null) {
            instance = new NBoolNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the bool node
     * @param parser The parser
     * @return A valid bool TreeNode or just a rel
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode tree = makeTree(parser);
        if(hasMultipleLogops){
            TreeNode returnNode = new TreeNode(TreeNode.NBOOL, tree, null);
            hasMultipleLogops = false;
            return returnNode;
        }
        else
            return tree;
    }

    private TreeNode makeTree(Parser parser){
//        Token peek = parser.peek();
//        TreeNode rel = nRelNode.make(parser);
//        TreeNode tail = tail(parser,peek);
//
//        if (tail != null) {
//            tail.setLeft(rel); //the tail value will be the relop, reassign the tail left to the first rel
//            return tail;
//        }
//        else
//            return rel;

        Token peek = parser.peek();
        TreeNode rel = nRelNode.make(parser);
        return tail(parser, peek, rel);

    }

    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent bool nodes, or a TreeNode containing tailing bool nodes
     */
    private TreeNode tail(Parser parser, Token id, TreeNode left){

////
////
////
////            return new TreeNode (logop.getValue(), null, bool);
////        }
////        else
////          return null; //eps trans
        Token peek = parser.peek();
        if(peek.getTokenID() == Token.TAND){
            return buildLeftDerivedTree(parser, TreeNode.NAND, left, id);
        }
        else if(peek.getTokenID() == Token.TOR ){
            return buildLeftDerivedTree(parser, TreeNode.NOR, left, id);
        }
        else if(peek.getTokenID() == Token.TXOR) {
            return buildLeftDerivedTree(parser, TreeNode.NXOR, left, id);
        }
        else{
            return left; //epsilon
        }
    }

    private TreeNode buildLeftDerivedTree(Parser parser, int expectedNodeType, TreeNode left, Token id){ //evan helped me out with this. I was having issues trying to build a left derived version of this. check my notebook it was a mess
//        SymbolTableRecord idRecord = parser.lookupIdentifierRecord(new SymbolTableRecord(id.getStr(),null,parser.getScope()));
//
//            if (idRecord != null) { //this is here for the unit tests (don't remove it)
//
//                String idRecordType = idRecord.getDataType();
//                String boolType = bool.getType();
//
//                if (idRecordType != null && boolType != null) {
//                    if (!idRecordType.equals(boolType)) {
//                        parser.semanticError("Invalid logical expression", id);
//                    }
//                }
//            }
        //todo fix semantic checks here
        hasMultipleLogops = true;
        TreeNode logop = nLogopNode.make(parser);

        TreeNode parent, right;
        right = nRelNode.make(parser);
        parent = new TreeNode(expectedNodeType,left,right);
        return tail(parser,id, parent);
    }

}


