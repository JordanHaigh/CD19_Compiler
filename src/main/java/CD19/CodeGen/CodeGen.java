package CD19.CodeGen;

import CD19.Parser.TreeNode;

public class CodeGen {

    private TreeNode tree;
    private int numberOfIntLiterals;
    private int numberOfRealLiterals;
    private int numberOfStringLiterals;

    public CodeGen(TreeNode tree){
        this.tree =tree;
    }


    public void run(){
        printPostorder(tree);

    }

    public void printPostorder(TreeNode root){
        //post order traversal
        if (root == null)
            return;

        // first recur on left subtree
        printPostorder(root.getLeft());

        // then recur on right subtree
        printPostorder(root.getRight());

        // now deal with the node
        System.out.print(root.toString() + " ");
    }
}

