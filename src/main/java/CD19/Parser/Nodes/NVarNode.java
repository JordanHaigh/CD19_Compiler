package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates a var of the form:
 * NSIMV | NARRV	<var>	::=	<id> <varTail>
 * <varTail>	::=	eps | [<expr>] . <id>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NVarNode implements Node {

    //NSIMV | NARRV	<var>	::=	<id> <varTail>
    //	<varTail>	::=	eps | [<expr>] . <id>

    NVarTailNode nVarTailNode;
    private static NVarNode instance;

    public NVarNode() {
        this(NVarTailNode.INSTANCE());
    }

    public NVarNode(NVarTailNode nVarTailNode) {
        this.nVarTailNode = nVarTailNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NVarNode INSTANCE() {
        if (instance == null) {
            instance = new NVarNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the var node
     * @param parser The parser
     * @return A valid var TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        Token id = parser.peek();
        if (!parser.peekAndConsume(Token.TIDEN)) {
            parser.syntacticError("Expected an Identifier", parser.peek());
            return new TreeNode();
        }

        //check id exists
        SymbolTableRecord idRecord = parser.lookupIdentifierRecord(new SymbolTableRecord(id.getStr(), null, parser.getScope()));
        if((idRecord) == null){
            //then check if we are trying to do something with global array variable
            SymbolTableRecord checker  = new SymbolTableRecord(id.getStr(), null, parser.getProgramScope());
            idRecord = parser.lookupTypeRecord(checker);
            if(idRecord == null){
                parser.semanticError("Variable " + id.getStr() + " doesn't exist", id);
            }
        }

        TreeNode tail = nVarTailNode.makeWithIdFromVar(parser,id);

        if (tail.getValue() == TreeNode.NARRV) {
            //look up types symbol table with id
            //get strec
            //now we have strec of struct
            //now i need to get the members of the struct
            //use the name of the struct (scope) to get the member

            return tail;
        } else { //var tail was null
            SymbolTableRecord record = new SymbolTableRecord(id.getStr(), "String", parser.getScope()); //todo this var is called by vlist and vlist is only called in the "input". the inputs will be made as strings BUT YOU MUST PARSE THEM TO THE RESPECTIVE DATA TYPE
            parser.insertIdentifierRecord(record);// todo remember to parse to appropriate data type.
            tail.setSymbol(record);
            return tail;
        }

    }
}
