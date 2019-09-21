package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;

public class NParamTypeTailNode implements Node {
    //	<paramTypeTail>	::=	<stype> | <typeid> //todo struct or primitive

    private static NParamTypeTailNode instance;
    public static NParamTypeTailNode INSTANCE() {
        if (instance == null) {
            instance = new NParamTypeTailNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        TreeNode dummy = new TreeNode(TreeNode.NUNDEF);
        dummy.setType(NodeDataTypes.Array); //todo change this later
        return dummy;
    }


}
