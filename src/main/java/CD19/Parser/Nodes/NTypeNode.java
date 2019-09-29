package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NTypeNode implements Node{

    //NRTYPE|NATYPE	<type>	::=	<anyFuckingID> is <typeTail>
    //	<typeTail>	::=	<fields> end |  array [<expr>] of <structid>


    NFieldsNode nFieldsNode;
    NExprNode nExprNode;


    public NTypeNode() {
        this(NFieldsNode.INSTANCE(), null);
    }

    public NTypeNode(NFieldsNode nFieldsNode, NExprNode nExprNode) {
        this.nFieldsNode = nFieldsNode;
        this.nExprNode = nExprNode;
    }

    private static NTypeNode instance;
    public static NTypeNode INSTANCE() {
        if (instance == null) {
            instance = new NTypeNode();
        }
        return instance;
    }

    public void setnExprNode(NExprNode nExprNode) {
        this.nExprNode= nExprNode;
    }


    @Override
    public TreeNode make(Parser parser) {
        TreeNode type = new TreeNode();

        Token id = parser.peek();
        if(!parser.peekAndConsume(Token.TIDEN)){ //todo semantic check data type
            parser.syntacticError("Expected an Identifier", parser.peek());
            return type;
        }

        if(!parser.peekAndConsume(Token.TIS)){
            parser.syntacticError("Exoected an Is keyword", parser.peek());
            return type;
        }

        parser.enterScope(id.getStr()+"_struct");

        TreeNode tail = tail(parser);

        parser.leaveScope();

        NodeDataTypes dataType;

        if(tail.getValue() == TreeNode.NATYPE)
            dataType = NodeDataTypes.Array;
        else
            dataType = NodeDataTypes.Struct;


        SymbolTableRecord record = new SymbolTableRecord(id.getStr(),dataType,parser.getScope()); //global (program) scope
        tail.setSymbol(record);
        return tail;
    }

    private TreeNode tail(Parser parser){
        Token token = parser.peek();
        if(token.getTokenID() == Token.TARAY){
            return arrayTail(parser);
        }
        else{
            return structTail(parser);
        }
    }

    private TreeNode arrayTail(Parser parser){
        //array [<expr>] of <structid>
        TreeNode arrayTail = new TreeNode();
        if(!parser.peekAndConsume(Token.TARAY)){
            parser.syntacticError("Expected Array keyword", parser.peek());
            return arrayTail;
        }

        if(!parser.peekAndConsume(Token.TLBRK)){
            parser.syntacticError("Expected Left Bracket", parser.peek());
            return arrayTail;
        }

        TreeNode exprTNode = nExprNode.make(parser);

        if(!parser.peekAndConsume(Token.TRBRK)){
            parser.syntacticError("Expected Right Bracket", parser.peek());
            return arrayTail;
        }

        if(!parser.peekAndConsume(Token.TOF)){
            parser.syntacticError("Expected Of keyword", parser.peek());
            return arrayTail;
        }

        if(!parser.peekAndConsume(Token.TIDEN)){ //todo check struct id is legit
            parser.syntacticError("Expected Identifier", parser.peek());
            return arrayTail;
        }

        arrayTail = new TreeNode(TreeNode.NATYPE, exprTNode,null);
        return arrayTail;
    }

    private TreeNode structTail(Parser parser){
        //<fields> end

        TreeNode nFieldsTNode = nFieldsNode.make(parser);

        if(!parser.peekAndConsume(Token.TEND)){
            parser.syntacticError("Expected End keyword",parser.peek());
            return new TreeNode();
        }

        return new TreeNode(TreeNode.NRTYPE, nFieldsTNode, null);
    }
}

