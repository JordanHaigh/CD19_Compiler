package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NParamTypeTailNode implements Node {
    //	<paramTypeTail>	::=	<stype> | <typeid> //todo struct or primitive

    NSTypeNode nsTypeNode;

    public NParamTypeTailNode() {
        this(new NSTypeNode());
    }

    public NParamTypeTailNode(NSTypeNode nsTypeNode) {
        this.nsTypeNode = nsTypeNode;
    }

    private static NParamTypeTailNode instance;
    public static NParamTypeTailNode INSTANCE() {
        if (instance == null) {
            instance = new NParamTypeTailNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token type = parser.peek();
        if(type.getTokenID() == Token.TINTG ||
            type.getTokenID() == Token.TREAL ||
            type.getTokenID() == Token.TBOOL)
        {
            return nsTypeNode.make(parser);
        }
        else if(parser.peekAndConsume(Token.TIDEN)){
            //parser.consume(); //consume the type id
            TreeNode dummy = new TreeNode(TreeNode.NARRD);
            dummy.setType(NodeDataTypes.Array);
            return dummy;
        }
        else{
            //error
            parser.syntacticError("Expected a <stype> | <typeid>", type.getLine(),type.getCol());
            return new TreeNode(TreeNode.NUNDEF);
        }

    }


}
