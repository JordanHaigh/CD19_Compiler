package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;

public class NParamTypeTailNode implements Node {
    //	<paramTypeTail>	::=	<stype> | <typeid> //todo struct or primitive

    @Override
    public TreeNode make(Parser parser) {
        TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
        dummy.setType(NodeDataTypes.Array); //todo change this later
        return dummy;
    }


}
