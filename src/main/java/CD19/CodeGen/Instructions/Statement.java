package CD19.CodeGen.Instructions;

import CD19.CodeGen.CodeGenerator;
import CD19.CodeGen.OpCodes;
import CD19.Parser.TreeNode;

public class Statement {

    public static void generate(CodeGenerator generator, TreeNode node){
        int nodeValue = node.getValue();
        switch(nodeValue){
            case TreeNode.NPRINT: {
                generatePrintStatement(generator,node);
            }
            case TreeNode.NPRLN : {
                generatePrintLineStatement(generator,node);
            }
        }
    }

    private static void generatePrintStatement(CodeGenerator generator, TreeNode node){
        //LA0 - get constant
        generator.generate5Bytes(OpCodes.LA0,1);


    }

    private static void generatePrintLineStatement(CodeGenerator generator, TreeNode node){
        generatePrintStatement(generator, node);
        generator.generate1Byte(OpCodes.NEWLN);
    }
}
