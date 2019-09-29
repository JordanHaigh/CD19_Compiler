package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NInitNode implements Node{

    //NINIT	<init>	::=	<id> = <expr>
    NExprNode nExprNode;

    public NInitNode(){
        this(null);
    }

    public NInitNode(NExprNode nExprNode){
        this.nExprNode = nExprNode;
    }


    private static NInitNode instance;
    public static NInitNode INSTANCE() {
        if (instance == null) {
            instance = new NInitNode();
        }
        return instance;
    }

    public void setnExprNode(NExprNode nExprNode) {
        this.nExprNode= nExprNode;
    }


    @Override
    public TreeNode make(Parser parser) {
        TreeNode init = new TreeNode();

        Token id = parser.peek(); //id
        if(!parser.peekAndConsume(Token.TIDEN)){
            parser.syntacticError("Expected an Identifier", parser.peek());
            return init;
        }

        if(!parser.peekAndConsume(Token.TEQUL)){
            parser.syntacticError("Expected Equals Sign", parser.peek());
            return init;
        }

        TreeNode exprTreeNode = nExprNode.make(parser);

        if(exprTreeNode.getValue() == TreeNode.NUNDEF){
            return init; //todo bail here if expr is nundef?
        }

        SymbolTableRecord record = new SymbolTableRecord(id.getStr(),exprTreeNode.getType(),parser.getScope()); //global (program) scope
        parser.insertConstantRecord(record);

        return new TreeNode(TreeNode.NINIT, record);

    }
}


