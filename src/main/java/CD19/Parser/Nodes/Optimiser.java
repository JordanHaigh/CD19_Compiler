package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

public class Optimiser {

    public static TreeNode constantFolding(Parser parser, TreeNode node){
        if(node.getLeft().getValue() == TreeNode.NILIT && node.getRight().getValue() == TreeNode.NILIT){
            //fue shun  HAAAAAAAAAAAAAAAA
            SymbolTableRecord left = node.getLeft().getSymbol();
            SymbolTableRecord right = node.getRight().getSymbol();

            if(left == null || right == null){
                return node; //cant do it chief
            }

            String leftLexeme = left.getLexeme();
            String rightLexeme = right.getLexeme();

            if(leftLexeme == null || rightLexeme == null){
                return node; //still cant do it chief
            }

            int leftLexemeToInt = Integer.parseInt(leftLexeme);
            int rightLexemeToInt = Integer.parseInt(rightLexeme);


            switch(node.getValue()){
                case TreeNode.NADD:{
                    int fusionedChildren = leftLexemeToInt + rightLexemeToInt;
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Integer", parser.getScope());
                    node = new TreeNode(TreeNode.NILIT, record);
                    break;
                }
                case TreeNode.NSUB:{
                    int fusionedChildren = leftLexemeToInt - rightLexemeToInt;
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Integer", parser.getScope());
                    node = new TreeNode(TreeNode.NILIT, record);
                    break;
                }
                case TreeNode.NMUL:{
                    int fusionedChildren = leftLexemeToInt * rightLexemeToInt;
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Integer", parser.getScope());
                    node = new TreeNode(TreeNode.NILIT, record);
                    break;
                }
                case TreeNode.NDIV:{
                    int fusionedChildren = leftLexemeToInt / rightLexemeToInt;
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Integer", parser.getScope());
                    node = new TreeNode(TreeNode.NILIT, record);
                    break;
                }
                case TreeNode.NPOW:{
                    int fusionedChildren = (int)Math.pow(leftLexemeToInt,rightLexemeToInt);
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Integer", parser.getScope());
                    node = new TreeNode(TreeNode.NILIT, record);
                    break;
                }
                case TreeNode.NMOD:{
                    int fusionedChildren = leftLexemeToInt % rightLexemeToInt;
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Integer", parser.getScope());
                    node = new TreeNode(TreeNode.NILIT, record);
                    break;
                }
            }
        }

        else if(node.getLeft().getValue() == TreeNode.NFLIT || node.getRight().getValue() == TreeNode.NFLIT){
            SymbolTableRecord left = node.getLeft().getSymbol();
            SymbolTableRecord right = node.getRight().getSymbol();

            if(left == null || right == null){
                return node; //cant do it chief
            }

            String leftLexeme = left.getLexeme();
            String rightLexeme = right.getLexeme();

            if(leftLexeme == null || rightLexeme == null){
                return node; //still cant do it chief
            }

            double leftLexemeToInt = Double.parseDouble(leftLexeme);
            double rightLexemeToInt = Double.parseDouble(rightLexeme);

            switch(node.getValue()){
                case TreeNode.NADD:{
                    double fusionedChildren = leftLexemeToInt + rightLexemeToInt;
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Real", parser.getScope());
                    node = new TreeNode(TreeNode.NFLIT, record);
                    break;
                }
                case TreeNode.NSUB:{
                    double fusionedChildren = leftLexemeToInt - rightLexemeToInt;
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Real", parser.getScope());
                    node = new TreeNode(TreeNode.NFLIT, record);
                    break;
                }
                case TreeNode.NMUL:{
                    double fusionedChildren = leftLexemeToInt * rightLexemeToInt;
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Real", parser.getScope());
                    node = new TreeNode(TreeNode.NFLIT, record);
                    break;
                }
                case TreeNode.NDIV:{
                    double fusionedChildren = leftLexemeToInt / rightLexemeToInt;
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Real", parser.getScope());
                    node = new TreeNode(TreeNode.NFLIT, record);
                    break;
                }
                case TreeNode.NPOW:{
                    double fusionedChildren = (int)Math.pow(leftLexemeToInt,rightLexemeToInt);
                    SymbolTableRecord record = new SymbolTableRecord(""+fusionedChildren, "Real", parser.getScope());
                    node = new TreeNode(TreeNode.NFLIT, record);
                    break;
                }
            }
        }

        return node;

    }
}
