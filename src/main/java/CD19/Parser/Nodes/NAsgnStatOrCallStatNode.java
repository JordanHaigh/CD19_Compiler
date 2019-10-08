package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Generates an asgnStatOrCallStat of the form:
 * <asgnStatOrCallStat>	::= 	<id><tail>
 * <tail> ::= (<callStat>) | <asgnStat>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NAsgnStatOrCallStatNode implements Node{

    NCallStatNode nCallStatNode;
    NAsgnStatNode nAsgnStatNode;
    private static NAsgnStatOrCallStatNode instance;

    public NAsgnStatOrCallStatNode() {
        this(NCallStatNode.INSTANCE(), NAsgnStatNode.INSTANCE());
    }

    public NAsgnStatOrCallStatNode(NCallStatNode nCallStatNode, NAsgnStatNode nAsgnStatNode) {
        this.nCallStatNode = nCallStatNode;
        this.nAsgnStatNode = nAsgnStatNode;
    }
    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NAsgnStatOrCallStatNode INSTANCE() {
        if (instance == null) {
            instance = new NAsgnStatOrCallStatNode();
        }
        return instance;
    }


    /**
     * Attempts to generate the asgnStatOrCallStat node
     * @param parser The parser
     * @return A valid asgnStatOrCallStat TreeNode or NUNDEF if syntactic error
     */
    @Override
    public TreeNode make(Parser parser) {
        TreeNode asgnStatOrCallStat = new TreeNode();

        Token token = parser.peek();
        if(!parser.peekAndConsume(Token.TIDEN)){
            parser.syntacticError("Expected an identifier", parser.peek());
            return asgnStatOrCallStat;
        }

        TreeNode tail = tail(parser,token);

        if(tail.getValue() != TreeNode.NUNDEF && tail.getLeft() != null){ //if tail isnt undefined and has function arguments
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(), tail.getType(), token.getStr()+"_"+parser.getScope());
            tail.getLeft().setSymbol(record);
        }
        return tail;
    }


    /**
     * Tail method that can parse more of the same node type or not
     * @param parser The parser
     * @return - Null if there are no subsequent asgnStatOrCallStat nodes, or a TreeNode containing tailing asgnStatOrCallStat nodes
     */
    private TreeNode tail(Parser parser, Token id){
        //<tail> ::= (<callStat>) | <asgnStat>

        if(parser.peekAndConsume(Token.TLPAR)){
            //(<callStat>)

            //check the function id exists
            SymbolTableRecord idRecord = new SymbolTableRecord(id.getStr(),null,parser.getProgramScope());
            if(parser.lookupIdentifierRecord(idRecord) == null){
                parser.semanticError("Function name doesn't exist", id);
            }


            TreeNode callStat = nCallStatNode.make(parser);

            if(!parser.peekAndConsume(Token.TRPAR)){
                parser.syntacticError("Expected a right parenthesis", parser.peek());
                return new TreeNode();
            }

            if(callStat == null){
                return new TreeNode(TreeNode.NCALL);
            }else
                return callStat;
        }
        else{
            //<asgnStat>

            //we do the ssemantic check inside asgnstat because at the moment we don't know if its a variable or array
           return nAsgnStatNode.makeWithId(parser,id);
        }

    }
}

