package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Jordan Haigh c3256730 CD19
 * Generates an alist TreeNode of the form:
 * <alist>	::=	<id><asgnstat> <alistTail>
 * <alistTail>	::=	eps | , <id><asgnstat> <alistTail>
 */
public class NAlistNode implements Node {
    //NASGNS	<alist>	::=	<id><asgnstat> <alistTail>
    //<alistTail>	::=	eps | , <id><asgnstat> <alistTail>

    NAsgnStatNode nAsgnStatNode;

    public NAlistNode() {
        this(NAsgnStatNode.INSTANCE());
    }

    public NAlistNode(NAsgnStatNode nAsgnStatNode) {
        this.nAsgnStatNode = nAsgnStatNode;
    }

    private static NAlistNode instance;

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NAlistNode INSTANCE() {
        if (instance == null) {
            instance = new NAlistNode();
        }
        return instance;
    }

    /**
     * Attemps to generate the alist node
     * @param parser The parser
     * @return A valid alist TreeNode or NUNDEF
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();

        if (!parser.peekAndConsume(Token.TIDEN)) {
            //Token is not a TIDEN, throw syntactic error and recover if possible
            parser.syntacticError("<alist> - Expected an Identifier", token);
            return new TreeNode();
        }

        //So we have seen an TIDEN
        SymbolTableRecord idRecord = new SymbolTableRecord(token.getStr(), null, parser.getScope());
        if(parser.lookupIdentifierRecord(idRecord) == null){
            parser.semanticError("Variable " + token.getStr() + " doesn't exist", token);
        }

        TreeNode asgnStat = nAsgnStatNode.makeWithId(parser, token);

        TreeNode tail = tail(parser);

        if (tail == null) {
            //tail is null, so we only have one asgnstat
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(), asgnStat.getRight().getType(), parser.getScope());
            parser.insertIdentifierRecord(record);
            asgnStat.setSymbol(record);

            return asgnStat;
        } else {
            //insert SymbolTable Record for the AList
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(), asgnStat.getRight().getType(), parser.getScope());
            parser.insertIdentifierRecord(record);

            asgnStat.setSymbol(record);

            return new TreeNode(TreeNode.NASGNS, asgnStat, tail);
        }

    }

    /**
     * Tail method that can either include more nodes or not
     * @param parser The parser
     * @return - Null if there are no subsequent alist nodes, or a TreeNode containing tailing alist nodes
     */
    private TreeNode tail(Parser parser) {
        //	//<alistTail>	::=	eps | , <id><asgnstat> <alistTail>

        //Add additional Assignments if seen in the token stream
        if (!parser.peekAndConsume(Token.TCOMA)) //eps transition
            return null;

        //More tokens to be added
        Token token = parser.peek();

        if (!parser.peekAndConsume(Token.TIDEN)) {
            parser.syntacticError("<alist> - Expected an Identifier", token);
            return new TreeNode(TreeNode.NUNDEF);
        }

        SymbolTableRecord idRecord = new SymbolTableRecord(token.getStr(), null, parser.getScope());
        if(parser.lookupIdentifierRecord(idRecord) == null){
            parser.semanticError("Variable " + token.getStr() + " doesn't exist", token);
        }

        TreeNode asgnStat = nAsgnStatNode.makeWithId(parser,token);
        TreeNode tail = tail(parser);

        if (tail == null) {
            return asgnStat;
        }
        return new TreeNode(TreeNode.NASGNS, asgnStat, tail);

    }
}
