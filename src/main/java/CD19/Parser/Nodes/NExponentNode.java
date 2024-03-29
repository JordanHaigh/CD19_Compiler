package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates an exponent of the form:
 * <exponent>	::=	<varorfncall> |  <intlit> | <reallit>  | TRUE | FALSE | (<bool>)
 * <varorfncall> ::= <id> <varOrFNCallTail>
 * <varOrFNCallTail>	::=	<varTail> | <fnCallTail>
 * <fncallTail>	::=	( <fnCallElistTail>)
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public class NExponentNode implements Node {

    //NILIT | NFLIT | NTRUE | NFALS	   <exponent>	::=	<varorfncall> |  <intlit> | <reallit>  | TRUE | FALSE | (<bool>)
//  <varorfncall> ::= <id> <varOrFNCallTail>
//	<varOrFNCallTail>	::=	<varTail> | <fnCallTail>
//	<fncallTail>	::=	( <fnCallElistTail>)
    NBoolNode nBoolNode;
    NVarTailNode nVarTailNode;
    NEListNode nEListNode;
    private static NExponentNode instance;

    public NExponentNode() {
        this(null, NVarTailNode.INSTANCE(), NEListNode.INSTANCE());
    }

    public NExponentNode(NBoolNode nBoolNode, NVarTailNode nVarTailNode, NEListNode neListNode) {
        this.nBoolNode = nBoolNode;
        this.nVarTailNode = nVarTailNode;
        this.nEListNode = neListNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     *
     * @return - Instance of the class
     */
    public static NExponentNode INSTANCE() {
        if (instance == null) {
            instance = new NExponentNode();
        }
        return instance;
    }

    /**
     * Sets the boolNode in the class so cyclic constructors are prevented
     *
     * @param boolNode - Node to set
     */
    public void setnBoolNode(NBoolNode boolNode) {
        this.nBoolNode = boolNode;
    }

    /**
     * Sets the varTailNode in the class so cyclic constructors are prevented
     *
     * @param varTailNode - Node to set
     */
    public void setnVarTailNode(NVarTailNode varTailNode) {
        this.nVarTailNode = varTailNode;
    }

    /**
     * Sets the elistNode in the class so cyclic constructors are prevented
     *
     * @param elistNode - Node to set
     */
    public void setnEListNode(NEListNode elistNode) {
        this.nEListNode = elistNode;
    }

    /**
     * Attempts to generate the exponent token
     *
     * @param parser The parser
     * @return A valid exponent TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode exponent = new TreeNode();

        Token token = parser.peek();
        if (parser.peek().getTokenID() == Token.TIDEN) {
            return varOrFnCall(parser);
        } else if (parser.peekAndConsume(Token.TILIT)) { //integer literal
            exponent = new TreeNode(TreeNode.NILIT, null, null);
            exponent.setType("Integer");
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(),"Integer", parser.getScope());
            exponent.setSymbol(record);
            return exponent;
        } else if (parser.peekAndConsume(Token.TFLIT)) {//float literal
            exponent = new TreeNode(TreeNode.NFLIT, null, null);
            exponent.setType("Real");
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(),"Real", parser.getScope());
            exponent.setSymbol(record);
            return exponent;
        } else if (parser.peekAndConsume(Token.TTRUE)) {//true keyword
            exponent = new TreeNode(TreeNode.NTRUE, null, null);
            exponent.setType("Boolean");
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(),"Boolean", parser.getScope());
            exponent.setSymbol(record);
            return exponent;
        } else if (parser.peekAndConsume(Token.TFALS)) {//false keyword
            exponent = new TreeNode(TreeNode.NFALS, null, null);
            exponent.setType("Boolean");
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(),"Boolean", parser.getScope());
            exponent.setSymbol(record);
            return exponent;
        } else if (parser.peekAndConsume(Token.TLPAR)) { //left parenthesis
            TreeNode bool = nBoolNode.make(parser);
            parser.peekAndConsume(Token.TRPAR);
            return bool;
        } else {
            parser.syntacticError("Expected a valid Exponent starting token", parser.peek());
            return new TreeNode();

        }
    }

    /**
     * Attempts to generate a var node or fncall node
     *
     * @param parser The parser
     * @return A valid exponent TreeNode or NUNDEF if syntactic error
     */
    private TreeNode varOrFnCall(Parser parser) {
        Token id = parser.peek();
        parser.peekAndConsume(Token.TIDEN); //already seen thats its an iden

        SymbolTableRecord checker = new SymbolTableRecord(id.getStr(), null, parser.getScope());
        SymbolTableRecord idRecord = parser.lookupIdentifierRecord(checker);
        if (idRecord == null) {
            //print out different message depending on if its a function (look at next token and see if its a left par)
            //check that it might be a fncall
            idRecord = parser.lookupIdentifierRecord(new SymbolTableRecord(id.getStr(), null, parser.getProgramScope()));
            if (idRecord == null) {
                //if variable is not in this scope, check that it might be a constant
                idRecord = parser.lookupConstantRecord(new SymbolTableRecord(id.getStr(), null, parser.getProgramScope()));
                if (idRecord == null) {
                    //oh no! it's not a constant? check to see if its a predefined array
                    idRecord = parser.lookupTypeRecord(new SymbolTableRecord(id.getStr(), null, parser.getProgramScope()));
                    if (idRecord == null) {
                        Token peek = parser.peek();
                        if (peek.getTokenID() == Token.TLPAR) {
                            parser.semanticError("Function " + id.getStr() + " doesn't exist", id);
                        } else {
                            parser.semanticError("Variable " + id.getStr() + " doesn't exist", id);
                        }
                    } else {
                        //hurray it exists!
                    }
                }
            } else {
                //hurray it exists
            }
        }
        Token peek = parser.peek();
        TreeNode tail = varOrFnCallTail(parser, id);

        //now if its a function we're trying to call, we need to check and see if the number of variables and args in ordermatch
        if (tail.getValue() == TreeNode.NFCALL) {
            if (idRecord != null) {
                //get the number of args we expect from the st rec
                List<String> expectedFunctionArgs = idRecord.getFunctionVariableTypesAndOrdering();
                List<String> actualFunctionArgs = tail.getDataTypeOrderingForFunctions();

                if (expectedFunctionArgs.size() != actualFunctionArgs.size()) {
                    parser.semanticError("Function argument size doesn't match", peek);
                }

                if (expectedFunctionArgs.size() > 0 && actualFunctionArgs.size() > 0) { //prevents index out of bounds exceptiopn
                    //check if args are in same order
                    for (int i = 0; i < expectedFunctionArgs.size(); i++) {
                        String expected = expectedFunctionArgs.get(i);
                        String actual = actualFunctionArgs.get(i);

                        if(expected.contains("Array")){
                            expected = expected.replaceAll("Array","");
                        }
                        if(actual.contains("IsArrayOf")){
                            actual = actual.replaceAll("IsArrayOf", " ");
                            String[] split = actual.split(" ");
                            actual = split[0];
                        }

                        if (!expected.equals(actual)) {
                            parser.semanticError("Argument Types do not match. Expected: " + expected + ", Actual: " + actual, peek);
                        }
                    }
                }


            }

        }

        if (tail.getValue() != TreeNode.NARRV) {
            tail.setSymbol(idRecord);
            if (idRecord != null) {
                tail.setType(idRecord.getDataType());
            }
        }
        //SymbolTableRecord record = new SymbolTableRecord(id.getStr(), tail.getType(), id.getStr()+"_"+parser.getScope());

        return tail;
    }

    /**
     * Tail method that can parse more of the same node type or not
     *
     * @param parser The parser
     * @return - Null if there are no subsequent vartail or fncall tail nodes, or proper treenode
     */
    private TreeNode varOrFnCallTail(Parser parser, Token idFromVar) {
        //	<varOrFNCallTail>	::=	<varTail> | <fnCallTail>
        Token token = parser.peek();
        if (token.getTokenID() == Token.TLPAR) {
            TreeNode treeNode = fnCallTail(parser); //fncalltail
            //treeNode.setType();
            return treeNode;
        } else
            return nVarTailNode.makeWithIdFromVar(parser, idFromVar);
    }

    /**
     * Tail method that reads the parentheses of a function call and the elist between the parantheses
     *
     * @param parser The parser
     * @return - Null if there are no subsequent vartail or fncall tail nodes, or proper treenode
     */
    private TreeNode fnCallTail(Parser parser) {
        //	<fncallTail>	::=	( <fnCallElistTail>)
        if (!parser.peekAndConsume(Token.TLPAR)) {
            parser.syntacticError("Expected a Left Parenthesis'", parser.peek());
            return new TreeNode();
        }
        TreeNode fncallelisttail = fnCallElistTail(parser);

        List<String> dataTypesInOrder = new ArrayList<>();
        if (fncallelisttail != null) {
            dataTypesInOrder = fncallelisttail.getDataTypeOrderingForFunctions();
        }

        if (!parser.peekAndConsume(Token.TRPAR)) {
            parser.syntacticError("Expected a Right Parenthesis'", parser.peek());
            return new TreeNode();
        }

        TreeNode treeNode = new TreeNode(TreeNode.NFCALL, fncallelisttail, null);

        if (fncallelisttail != null) {
            treeNode.setDataTypeOrderingForFunctions(dataTypesInOrder);
        }

        return treeNode;
    }

    /**
     * Tail method that can parse more elist nodes or not
     *
     * @param parser The parser
     * @return - Null if there are no subsequent elist nodes, or a TreeNode containing tailing elist nodes
     */
    private TreeNode fnCallElistTail(Parser parser) {
        //	<fnCallElistTail>	::=	ε | <elist>
        Token token = parser.peek();
        if (token.getTokenID() == Token.TNOT || //elist trans
                token.getTokenID() == Token.TIDEN ||
                token.getTokenID() == Token.TILIT ||
                token.getTokenID() == Token.TFLIT ||
                token.getTokenID() == Token.TTRUE ||
                token.getTokenID() == Token.TFALS ||
                token.getTokenID() == Token.TLPAR) {

            TreeNode node = nEListNode.make(parser);
            node.calculateNumberChildren(node);
            //List<String> argumentList = node.getDataTypeOrderingForFunctions();
            return node;
        } else
            return null;

    }
}


