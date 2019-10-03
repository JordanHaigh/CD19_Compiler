package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
/**
 * Node Interface Structure that all Nodes require
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */

public interface Node {
    TreeNode make(Parser parser);
    //void errorRecovery();
}
