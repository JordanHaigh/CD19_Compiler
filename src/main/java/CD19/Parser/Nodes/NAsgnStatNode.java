package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates an asgnstat of the form:
 * <asgnstat>	::=	 <varTail> <asgnop> <bool>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NAsgnStatNode implements Node {
    //	<asgnstat>	::=	 <varTail> <asgnop> <bool>

    NVarTailNode nVarTailNode;
    NAsgnOpNode nAsgnOpNode;
    NBoolNode nBoolNode;
    private static NAsgnStatNode instance;

    public NAsgnStatNode() {
        this(NVarTailNode.INSTANCE(), NAsgnOpNode.INSTANCE(), null);
    }

    public NAsgnStatNode(NVarTailNode nVarTailNode, NAsgnOpNode nAsgnOpNode, NBoolNode nBoolNode) {
        this.nVarTailNode = nVarTailNode;
        this.nAsgnOpNode = nAsgnOpNode;
        this.nBoolNode = nBoolNode;
    }

    /**
     * Sets the BoolNode in the class so cyclic constructors are prevented
     *
     * @param boolNode - Node to set
     */
    public void setnBoolNode(NBoolNode boolNode) {
        this.nBoolNode = boolNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     *
     * @return - Instance of the class
     */
    public static NAsgnStatNode INSTANCE() {
        if (instance == null) {
            instance = new NAsgnStatNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the asgnstat node
     *
     * @param parser The parser
     * @return A valid asgnstat TreeNode
     */
    @Override
    public TreeNode make(Parser parser) {
        //NOBODY USES ME BOOHOO

//        TreeNode vartail = nVarTailNode.make(parser);
//        TreeNode asgnop = nAsgnOpNode.make(parser);
//        TreeNode bool = nBoolNode.make(parser);
//        //nodetype will be what is returned from the nasgnop node (nasgn, npleq...)
//
//        //return new TreeNode(asgnop.getValue(), vartail, bool);
//        //asgnop.setType(bool.getType()); //todo data types come later
//        asgnop.setLeft(vartail);
//        asgnop.setRight(bool);
//        return asgnop;
        return new TreeNode();
    }


    public TreeNode makeWithId(Parser parser, Token id) {
        TreeNode vartail = nVarTailNode.makeWithIdFromVar(parser, id);

        SymbolTableRecord idRecord;

        if (vartail.getValue() == TreeNode.NSIMV) {
            //then its just a variable
            idRecord = parser.lookupIdentifierRecord(new SymbolTableRecord(id.getStr(), null, parser.getScope())); //get the current scope - could be function or main
            if (idRecord == null) {
                //ok ok just check to see if we are working with a predefined array here
                idRecord = parser.lookupTypeRecord(new SymbolTableRecord(id.getStr(),null,parser.getProgramScope())); //target global scope to get array
                if(idRecord == null){
                    parser.semanticError("Variable " + id.getStr() + " doesn't exist", id);
                }
                else{
                    //hurray it exists
                    System.out.println();
                }
            }
        } else {
            //then its a a struct array
            idRecord = parser.lookupTypeRecord(new SymbolTableRecord(id.getStr(), null, parser.getProgramScope()));//get the current scope - could be function or main
            if (idRecord == null) {
                parser.semanticError("Array Variable " + id.getStr() + " doesn't exist", id);
            }
            //change idRecord to the variable we are trying to assign to
            idRecord = vartail.getSymbol();

        }


        TreeNode asgnop = nAsgnOpNode.make(parser);
        TreeNode bool = nBoolNode.make(parser);

        if (idRecord != null) { //this is here for the unit tests (don't remove it)

            String idRecordType = idRecord.getDataType();
            String boolType = bool.getType();

            if (idRecordType != null && boolType != null) {
                if(idRecordType.equals("Real") && boolType.equals("Integer")){
                    //allow it - type promotion
                }
                else if (!idRecordType.equals(boolType)) {
                    parser.semanticError("Invalid assignment to variable " + idRecord.getLexeme(), id);
                }
            }
        }


        asgnop.setLeft(vartail);
        asgnop.setRight(bool);
        return asgnop;
    }


}

