package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NFuncsNode implements Node {
    //NFUNCS	<funcs>	::=	eps | <func> <funcs>

    NFuncNode nFuncNode;


    public NFuncsNode(){
        this(new NFuncNode());
    }

    public NFuncsNode(NFuncNode nFuncNode){
        this.nFuncNode = nFuncNode;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();
        if(token.getTokenID() == Token.TFUNC){
            TreeNode func = nFuncNode.make(parser);
            TreeNode funcs = this.make(parser);
            return new TreeNode(TreeNode.NFUNCS, func, funcs);
        }
        else
            return null;
    }




}

