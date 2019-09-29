package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NFuncsNode implements Node {
    //NFUNCS	<funcs>	::=	eps | <func> <funcs>

    NFuncNode nFuncNode;


    public NFuncsNode(){
        this(NFuncNode.INSTANCE());
    }

    public NFuncsNode(NFuncNode nFuncNode){
        this.nFuncNode = nFuncNode;
    }


    private static NFuncsNode instance;
    public static NFuncsNode INSTANCE() {
        if (instance == null) {
            instance = new NFuncsNode();
        }
        return instance;
    }


    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TFUNC){
            TreeNode func = nFuncNode.make(parser);

            TreeNode funcs = this.make(parser);
            if(funcs == null)
                return func;

            return new TreeNode(TreeNode.NFUNCS, func, funcs);
        }
        else
            return null;
    }

}

