package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NProgNode{

    //NPROG	<program>	::=	CD19 <id> <globals> <funcs> <mainbody>
    public static TreeNode make(Parser parser){
        if(!parser.peekAndConsume(Token.TCD19)){
            //error
            return null;
        }

        return new TreeNode(TreeNode.NPROG);
    }

}
