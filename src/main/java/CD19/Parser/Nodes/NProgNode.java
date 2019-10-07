package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

/**
 * Entry point of parse tree
 * Generates a program of the form:
 * NPROG	<program>	::=	CD19 <id> <globals> <funcs> <mainbody>
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */


public class NProgNode implements Node{

    //NPROG	<program>	::=	CD19 <id> <globals> <funcs> <mainbody>
    private NGlobNode nGlobNode;
    private NFuncsNode nFuncsNode;
    private NMainBodyNode nMainBodyNode;
    private static NProgNode instance;


    public NProgNode() {
        this(NGlobNode.INSTANCE(), NFuncsNode.INSTANCE(), NMainBodyNode.INSTANCE());
    }

    public NProgNode(NGlobNode nGlobNode, NFuncsNode nFuncsNode, NMainBodyNode nMainBodyNode) {
        this.nGlobNode = nGlobNode;
        this.nFuncsNode = nFuncsNode;
        this.nMainBodyNode = nMainBodyNode;
    }

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static NProgNode INSTANCE() {
        if (instance == null) {
            instance = new NProgNode();
        }
        return instance;
    }

    /**
     * Attempts to generate the program node
     * @param parser The parser
     * @return A valid program TreeNode or NUNDEF if syntactic error
     */
    public TreeNode make(Parser parser) {
        TreeNode program = new TreeNode();

        if(!parser.peekAndConsume(Token.TCD19)){
            parser.syntacticError("Expected Starting CD19 keyword", parser.peek());
            return program;
        }

        Token startId = parser.peek();

        if(!parser.peekAndConsume(Token.TIDEN)){
            parser.syntacticError("Expected an Identifier", parser.peek());
            return program;
        }

        //error check
        parser.enterScope(startId.getStr());

        TreeNode nGlobTreeNode = nGlobNode.make(parser);
        program.setLeft(nGlobTreeNode);

        TreeNode nFuncsTreeNode = nFuncsNode.make(parser);
        program.setMiddle(nFuncsTreeNode);

        TreeNode nMainTreeNode = nMainBodyNode.make(parser);
        program.setRight(nMainTreeNode);


        if(!parser.peekAndConsume(Token.TCD19)){
            parser.syntacticError("Expected Ending CD19 keyword" , parser.peek());
            return program;
        }

        Token endId = parser.peek();

        if(!parser.peekAndConsume(Token.TIDEN)){
            parser.syntacticError("Expected an Identifier", parser.peek());
            return program;
        }

        if(!startId.getStr().equals(endId.getStr())){
            parser.semanticError("Program Start and End names do not match", endId);
        }

        SymbolTableRecord startRecord = new SymbolTableRecord(startId.getStr(), null, "");

        parser.insertIdentifierRecord(startRecord);

        SymbolTableRecord endRecord = new SymbolTableRecord(endId.getStr(), null, "");

        parser.insertIdentifierRecord(endRecord);

        TreeNode nProgTreeNode = new TreeNode(TreeNode.NPROG,nGlobTreeNode,nFuncsTreeNode, nMainTreeNode);

        nProgTreeNode.setSymbol(startRecord);

        parser.leaveScope();

        return nProgTreeNode;
    }

}
