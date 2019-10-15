package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTable;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import sun.awt.Symbol;

/**
 * Generates a vartail of the form:
 *	<varTail>	::=	eps | [<expr>] . <id>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NVarTailNode implements Node{

    //	<varTail>	::=	eps | [<expr>] . <id>
    NExprNode nExprNode;
    private static NVarTailNode instance;

    public NVarTailNode() {
        this(null);
    }

    public NVarTailNode(NExprNode nExprNode) {
        this.nExprNode = nExprNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NVarTailNode INSTANCE() {
        if (instance == null) {
            instance = new NVarTailNode();
        }
        return instance;
    }

    /**
     * Sets the exprNode in the class so cyclic constructors are prevented
     * @param exprNode - Node to set
     */
    public void setnExprNode(NExprNode exprNode) {
        this.nExprNode= exprNode;
    }

    /**
     * Attempts to generate the vartail node
     * @param parser The parser
     * @return A valid vartail TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();

        if(token.getTokenID() == Token.TLBRK){
            TreeNode vartail = new TreeNode();

            parser.consume();

            TreeNode expr = nExprNode.make(parser); //todo whats this used for

            if(!parser.peekAndConsume(Token.TRBRK)){
                parser.syntacticError("Expected a Right Bracket", parser.peek());
                return vartail;
            }

            if(!parser.peekAndConsume(Token.TDOT)){
                parser.syntacticError("Expected a Dot", parser.peek());
                return vartail;
            }

            if(!parser.peekAndConsume(Token.TIDEN)){
                parser.syntacticError("Expected an Identifier", parser.peek());
                return vartail;
            }



            SymbolTableRecord record = new SymbolTableRecord(); //todo fill this in
            vartail = new TreeNode(TreeNode.NARRV, record);
            return vartail;
        }
        else{
            return new TreeNode(TreeNode.NSIMV, null,null);
        }
    }

    public TreeNode makeWithIdFromVar(Parser parser, Token id) {
        Token token = parser.peek();

        if(token.getTokenID() == Token.TLBRK){
            TreeNode vartail = new TreeNode();

            parser.consume();

            TreeNode expr = nExprNode.make(parser); //todo whats this used for

            if(!parser.peekAndConsume(Token.TRBRK)){
                parser.syntacticError("Expected a Right Bracket", parser.peek());
                return vartail;
            }

            if(!parser.peekAndConsume(Token.TDOT)){
                parser.syntacticError("Expected a Dot", parser.peek());
                return vartail;
            }
            Token secondId = parser.peek();
            if(!parser.peekAndConsume(Token.TIDEN)){
                parser.syntacticError("Expected an Identifier", parser.peek());
                return vartail;
            }

            //need to get struct to see if the variable is in the struct
            //currently have the id of the array
            //we use the id of the array to get the strec for the array
            //arrays defined globally
            SymbolTableRecord arrayRecord = parser.lookupTypeRecord(new SymbolTableRecord(id.getStr(), null, parser.getProgramScope()));
            SymbolTableRecord variableInArrayRecord = null;
            //the strec will reveal the lexeme, scope and data type
            if(arrayRecord != null){
                String arrayDataType = arrayRecord.getDataType();

                //strip out the "array of "bit to get struct name
                arrayDataType = arrayDataType.replaceAll("IsArrayOf"," ");
                String[] split = arrayDataType.split(" ");
                String structDataType = split[1];
                variableInArrayRecord = new SymbolTableRecord(secondId.getStr(),null,structDataType+"_struct");
                if(parser.lookupIdentifierRecord(variableInArrayRecord) == null){
                    parser.semanticError("Could not find variable " + secondId.getStr() + " in Struct " + id.getStr(), secondId);
                }
            }

            //SymbolTableRecord record = new SymbolTableRecord(); //todo fill this in
            vartail = new TreeNode(TreeNode.NARRV, variableInArrayRecord);
            return vartail;
        }
        else{
            return new TreeNode(TreeNode.NSIMV, null,null); //you just working with a variable boy
        }
    }
}
