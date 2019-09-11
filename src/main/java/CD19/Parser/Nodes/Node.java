package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;

public interface Node {
    TreeNode make(Parser parser);
    //void errorRecovery(); //todo implement later
}
