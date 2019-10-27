package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTable;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;
import sun.awt.Symbol;

/**
 * Generates a vartail of the form:
 * <varTail>	::=	eps | [<expr>] . <id>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NVarTailNode implements Node {

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
     *
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
     *
     * @param exprNode - Node to set
     */
    public void setnExprNode(NExprNode exprNode) {
        this.nExprNode = exprNode;
    }

    /**
     * Attempts to generate the vartail node
     *
     * @param parser The parser
     * @return A valid vartail TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        //this isn;t used in actual program. only used by tests. dw about empty record
        Token token = parser.peek();

        if (token.getTokenID() == Token.TLBRK) {
            TreeNode vartail = new TreeNode();

            parser.consume();

            TreeNode expr = nExprNode.make(parser);

            if (!parser.peekAndConsume(Token.TRBRK)) {
                parser.syntacticError("Expected a Right Bracket", parser.peek());
                return vartail;
            }

            if (!parser.peekAndConsume(Token.TDOT)) {
                parser.syntacticError("Expected a Dot", parser.peek());
                return vartail;
            }

            if (!parser.peekAndConsume(Token.TIDEN)) {
                parser.syntacticError("Expected an Identifier", parser.peek());
                return vartail;
            }


            SymbolTableRecord record = new SymbolTableRecord();
            vartail = new TreeNode(TreeNode.NARRV, expr,null);
            vartail.setSymbol(record);
            return vartail;
        } else {
            return new TreeNode(TreeNode.NSIMV, null, null);
        }
    }

    public TreeNode makeWithIdFromVar(Parser parser, Token id) {
        Token token = parser.peek();

        if (token.getTokenID() == Token.TLBRK) {
            TreeNode vartail = new TreeNode();

            parser.consume();

            TreeNode expr = nExprNode.make(parser);

            if (!parser.peekAndConsume(Token.TRBRK)) {
                parser.syntacticError("Expected a Right Bracket", parser.peek());
                return vartail;
            }

            if (!parser.peekAndConsume(Token.TDOT)) {
                parser.syntacticError("Expected a Dot", parser.peek());
                return vartail;
            }
            Token secondId = parser.peek();
            if (!parser.peekAndConsume(Token.TIDEN)) {
                parser.syntacticError("Expected an Identifier", parser.peek());
                return vartail;
            }

            //need to get struct to see if the variable is in the struct
            //currently have the id of the array
            //we use the id of the array to get the strec for the array

            //check if we are using the array from global scope
            SymbolTableRecord arrayRecord = parser.lookupTypeRecord(new SymbolTableRecord(id.getStr(), null, parser.getProgramScope()));
            if (arrayRecord == null) {
                //check if we are using array inside function
                SymbolTableRecord checker = new SymbolTableRecord(id.getStr(), null, parser.getScope());
                arrayRecord = parser.lookupIdentifierRecord(checker);
                if (arrayRecord == null) {
                    parser.semanticError("Array variable doesn't exist", id);
                }
            }
            SymbolTableRecord variable = null;
            //the strec will reveal the lexeme, scope and data type
            if (arrayRecord != null) {
                String arrayDataType = arrayRecord.getDataType();

                //do different things depending if we are trying to strip out struct or not
                if (arrayDataType.contains("IsArrayOf")) {
                    //strip out the "array of "bit to get struct name
                    arrayDataType = arrayDataType.replaceAll("IsArrayOf", " ");
                    String[] split = arrayDataType.split(" ");
                    String structDataType = split[1];
                    SymbolTableRecord checker = new SymbolTableRecord(secondId.getStr(), null, structDataType + "_struct");
                    variable = parser.lookupIdentifierRecord(checker);
                    if (variable == null) {
                        parser.semanticError("Could not find variable " + secondId.getStr() + " in Struct " + id.getStr(), secondId);
                    }
                } else {
                    //accessing array variable inside function
                    String[] split = arrayDataType.split("Array");
                    String arrayType = split[0];
                    SymbolTableRecord arrayTypeRecord = parser.lookupIdentifierRecord(new SymbolTableRecord(arrayType, null, parser.getProgramScope()));
                    if (arrayTypeRecord == null) {
                        parser.semanticError("Could not find Array Type", id);
                    } else {
                        //we have the ArrayOf_XX
                        //now we need to get struct of the array
                        String arrayOfStruct = arrayTypeRecord.getDataType();
                        split = arrayOfStruct.split("ArrayOf");
                        String structName = split[1];
                        SymbolTableRecord structRecord = parser.lookupTypeRecord(new SymbolTableRecord(structName, null, parser.getProgramScope()));
                        if (structRecord == null) {
                            parser.semanticError("Struct type doesn't exist", id);
                        } else {
                            //so struct id exists
                            //now (jesus finally) we need to check that the variable inside the struct exists, as well as type
                            //use the second Id that we grabbed eons ago to see if it exists in the struct
                            SymbolTableRecord variableInsideStruct = parser.lookupIdentifierRecord(new SymbolTableRecord(secondId.getStr(), null, structName + "_struct"));
                            if (variableInsideStruct == null){
                                parser.semanticError("Variable " + secondId.getStr() + " doesn't exist inside struct", secondId);
                            }
                            else{

                                // OMG YOU DID IT!!!!!
                                //VARIABLE EXISTS INSIDE THE STRUCT INSIDE THE ARRAY INSIDE THE VARIABLE ARRAY INSIDE THE FUNCTION YOU ARE CALLING IT FROM
                                //WHY IS THIS SO MUCH WORK
                                //check variable type
                                variable = variableInsideStruct;
                                variable.setScope(parser.getScope());
                            }
                        }

                    }
                }

            }

            vartail = new TreeNode(TreeNode.NARRV, expr,null);
            vartail.setSymbol(variable);
            return vartail;
        } else {
            return new TreeNode(TreeNode.NSIMV, null, null); //you just working with a variable boy
        }
    }
}
