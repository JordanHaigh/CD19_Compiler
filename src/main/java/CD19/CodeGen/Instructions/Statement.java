package CD19.CodeGen.Instructions;

import CD19.CodeGen.CodeGenerator;
import CD19.CodeGen.OpCodes;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

import java.util.List;

public class Statement{

    private static Statement instance;

    /**
     * Singleton method used so only one instance of the class is created throughout the entire program
     * @return - Instance of the class
     */
    public static Statement INSTANCE() {
        if (instance == null) {
            instance = new Statement();
        }
        return instance;
    }

    public static void generate(CodeGenerator generator, TreeNode node){
        int nodeValue= node.getValue();
        switch(nodeValue){
            //------------------------IOSTATS----------------------------
            case TreeNode.NPRLN: {
                generatePrintLineStatement(generator,node);
                break;
            }
            case TreeNode.NPRINT: {
                generatePrintStatement(generator,node);
                break;
            }
            case TreeNode.NINPUT: {
                generateInputStatement(generator,node);
                break;
            }
            //------------------------ASGNSTATS----------------------------
            case TreeNode.NASGN:{
                generateAssignStatement(generator,node);
                break;
            }
            case TreeNode.NPLEQ : {
                generatePlusEqualsStatement(generator,node);
                break;
            }
            case TreeNode.NMNEQ : {
                generateMinusEqualsStatement(generator,node);
                break;
            }
            case TreeNode.NSTEQ: {
                generateStarEqualsStatement(generator,node);
                break;
            }
            case TreeNode.NDVEQ: {
                generateDivideEqualsStatement(generator,node);
            }
            //------------------------RETNSTAT----------------------------
            case TreeNode.NRETN :{
                generateReturnStatement(generator,node);
                break;
            }
            //------------------------REPTSTAT----------------------------
            case TreeNode.NREPT:{
                generateRepeatStatement(generator, node);
                break;
            }
        }
    }

    public static void generateRel(CodeGenerator generator, TreeNode node){
        TreeNode leftSide = node.getLeft();
        String LV = "LV" + leftSide.getSymbol().getBaseRegister(); //load value of x to be div'd at end
        generator.generate5Bytes(OpCodes.valueOf(LV), leftSide.getSymbol().getOffset());

        TreeNode rightSide = node.getRight();
        if(rightSide.getValue() == TreeNode.NSIMV){ //variable
            LV = "";
            LV = "LV" + rightSide.getSymbol().getBaseRegister();
            generator.generate5Bytes(OpCodes.valueOf(LV), rightSide.getSymbol().getOffset());
        }
        else{ //number
            if(rightSide.getValue() == TreeNode.NILIT){ //int
                generator.integerLiteral(rightSide.getSymbol());
            }
            else{// real
                generator.realLiteral(rightSide.getSymbol());
            }
        }

        //for == use sub
        generator.generate1Byte(OpCodes.SUB);
        //todo run programs and check all these
        switch(node.getValue()){
            case TreeNode.NEQL:{ //==
                generator.generate1Byte(OpCodes.EQ);
                break;
            }
            case TreeNode.NNEQ:{ //!=
                generator.generate1Byte(OpCodes.NE);
                break;
            }
            case TreeNode.NGRT:{ //>
                generator.generate1Byte(OpCodes.GT);
                break;
            }
            case TreeNode.NLEQ:{ //<=
                generator.generate1Byte(OpCodes.LE);
                break;
            }
            case TreeNode.NLSS:{ //<
                generator.generate1Byte(OpCodes.LT);
                break;
            }
            case TreeNode.NGEQ:{ // >=
                generator.generate1Byte(OpCodes.GE);
                break;
            }
        }

    }


    public static void generateRepeatStatement(CodeGenerator generator, TreeNode node){
        //--------------------------ASGNLIST-----------------------------------
        //MULTI ASGN WORKS
        TreeNode asgnlistNode = node.getLeft();
        if(asgnlistNode.getValue() == TreeNode.NASGNS){ //more than 1 assign
            List<TreeNode> asgnList = asgnlistNode.detreeify();
            for(TreeNode n : asgnList){
                generateAssignStatement(generator, n);
            }
        }
        else{ //only one assign
            generateAssignStatement(generator, asgnlistNode);
        }
        //--------------------------STATS-----------------------------------
        //MULTI STATS WORK
        TreeNode stats = node.getMiddle();
        TreeNode firstStat = stats.getLeft();

        if(stats.getValue() == TreeNode.NSTATS){ //more than 1 stat
            List<TreeNode> statList = stats.detreeify();
            for(TreeNode n : statList){
                Statement.generate(generator, n);
            }
        }
        else{ //1 stat
            Statement.generate(generator, stats);
        }
        //--------------------------LOAD FIRST ADDR-----------------------------------
        //load address of first stat
        generator.generate5Bytes(OpCodes.LA0, firstStat.getSymbol().getOffset());

        //--------------------------BOOL-----------------------------------
        TreeNode bool = node.getRight();
        if(bool.getValue() == TreeNode.NBOOL){//more than 1 bool
            List<TreeNode> boolList = bool.detreeifyLogops();
            for(TreeNode n : boolList){
                //if is neql node, do rel
                //if is and or xor, do something else
                //not not supported
                switch(n.getValue()){
                    case TreeNode.NEQL: case TreeNode.NNEQ :
                        case TreeNode.NGRT: case TreeNode.NLEQ :
                            case TreeNode.NLSS : case TreeNode.NGEQ: {
                                generateRel(generator,n);
                                break;
                    }
                    case TreeNode.NAND: {
                        generator.generate1Byte(OpCodes.AND);
                        break;
                    }
                    case TreeNode.NOR : {
                        generator.generate1Byte(OpCodes.OR);
                        break;
                    }
                    case TreeNode.NXOR:{
                        generator.generate1Byte(OpCodes.XOR);
                        break;
                    }
                }
            }
        }
        else{ //1 bool
            generateRel(generator, bool);
        }

        //--------------------------BF-----------------------------------

        generator.generate1Byte(OpCodes.BF);
    }

    public static void generateReturnStatement(CodeGenerator generator, TreeNode node){
        generator.generate1Byte(OpCodes.RETN);
        generator.stopProcessing();
    }

    public static void generateDivideEqualsStatement(CodeGenerator generator, TreeNode node){
        //equivalent of x = x / <expr>
        SymbolTableRecord assignee = node.getLeft().getSymbol();
        String LA = "LA" + assignee.getBaseRegister();
        generator.generate5Bytes(OpCodes.valueOf(LA),assignee.getOffset());

        String LV = "LV" + assignee.getBaseRegister(); //load value of x to be div'd at end
        generator.generate5Bytes(OpCodes.valueOf(LV), assignee.getOffset());

        TreeNode exprNode = node.getRight(); //generate tree as assign statement
        postOrderOpCodesAssignStatement(generator, exprNode);

        generator.generate1Byte(OpCodes.DIV); //final div opcode
        generator.generate1Byte(OpCodes.ST); //store as normal

    }

    public static void generateStarEqualsStatement(CodeGenerator generator, TreeNode node){
        //equivalent of x = x * <expr>
        SymbolTableRecord assignee = node.getLeft().getSymbol();
        String LA = "LA" + assignee.getBaseRegister();
        generator.generate5Bytes(OpCodes.valueOf(LA),assignee.getOffset());

        String LV = "LV" + assignee.getBaseRegister(); //load value of x to be timesed at end
        generator.generate5Bytes(OpCodes.valueOf(LV), assignee.getOffset());

        TreeNode exprNode = node.getRight(); //generate tree as assign statement
        postOrderOpCodesAssignStatement(generator, exprNode);

        generator.generate1Byte(OpCodes.MUL); //final * opcode
        generator.generate1Byte(OpCodes.ST); //store as normal

    }

    public static void generateMinusEqualsStatement(CodeGenerator generator, TreeNode node){
        //equivalent of x = x - <expr>
        SymbolTableRecord assignee = node.getLeft().getSymbol();
        String LA = "LA" + assignee.getBaseRegister();
        generator.generate5Bytes(OpCodes.valueOf(LA),assignee.getOffset());

        String LV = "LV" + assignee.getBaseRegister(); //load value of x to be subbed at end
        generator.generate5Bytes(OpCodes.valueOf(LV), assignee.getOffset());

        TreeNode exprNode = node.getRight(); //generate tree as assign statement
        postOrderOpCodesAssignStatement(generator, exprNode);

        generator.generate1Byte(OpCodes.SUB); //final sub opcode
        generator.generate1Byte(OpCodes.ST); //store as normal
    }

    public static void generatePlusEqualsStatement(CodeGenerator generator, TreeNode node){
        //equivalent of x = x + <expr>
        SymbolTableRecord assignee = node.getLeft().getSymbol();
        String LA = "LA" + assignee.getBaseRegister();
        generator.generate5Bytes(OpCodes.valueOf(LA),assignee.getOffset());

        String LV = "LV" + assignee.getBaseRegister(); //load value of x to be summed at end
        generator.generate5Bytes(OpCodes.valueOf(LV), assignee.getOffset());

        TreeNode exprNode = node.getRight(); //generate tree as assign statement
        postOrderOpCodesAssignStatement(generator, exprNode);

        generator.generate1Byte(OpCodes.ADD); //final add opcode
        generator.generate1Byte(OpCodes.ST); //store as normal
    }

    public static void generateAssignStatement(CodeGenerator generator, TreeNode node){
//        Example:
//        k = i + j;    LA “k”
//                      LV “i”
//                      LV “j”
//                      ADD
//                      ST

        //we have the main root which is the NASGN
        //the left child will be the thing to assign to
        //the right child will be the expr
        // the expr will be a list of asgnops (add,mul,pow,etc.) and can be a mixed list. you will need to change detreeify to accommodate this
        SymbolTableRecord assignee = node.getLeft().getSymbol();
        String LA = "LA" + assignee.getBaseRegister();
        generator.generate5Bytes(OpCodes.valueOf(LA), assignee.getOffset());

        //Start working with right side of tree
        TreeNode exprNode = node.getRight();

        //post order and build
        postOrderOpCodesAssignStatement(generator, exprNode);
        generator.generate1Byte(OpCodes.ST);
    }

    private static void postOrderOpCodesAssignStatement(CodeGenerator generator, TreeNode root){
        //post order traversal
        if (root == null)
            return;

        postOrderOpCodesAssignStatement(generator, root.getLeft());
        postOrderOpCodesAssignStatement(generator, root.getRight());

        // now deal with the node
        if(root.hasNoChildren()){
            //then is leaf node
            if(root.getValue() == TreeNode.NSIMV){ //variable
                SymbolTableRecord record = root.getSymbol();
                String LV = "LV" + record.getBaseRegister();

                generator.generate5Bytes(OpCodes.valueOf(LV), record.getOffset());
            }
            else{ //value
                if(root.getValue() == TreeNode.NILIT){ //INT
                    generator.integerLiteral(root.getSymbol());
                }
                else{//must be NFLIT
                    generator.realLiteral(root.getSymbol());
                }
            }
        }
        else{
            //is intermediate node
            if(root.getValue() == TreeNode.NADD)
                generator.generate1Byte(OpCodes.ADD);
            else if(root.getValue() == TreeNode.NSUB)
                generator.generate1Byte(OpCodes.SUB);
            else if(root.getValue() == TreeNode.NMUL)
                generator.generate1Byte(OpCodes.MUL);
            else if(root.getValue() == TreeNode.NDIV)
                generator.generate1Byte(OpCodes.DIV);
            else if(root.getValue() == TreeNode.NMOD)
                generator.generate1Byte(OpCodes.REM);
            else if(root.getValue() == TreeNode.NPOW)
                generator.generate1Byte(OpCodes.POW);
        }
    }

    public static void generatePrintLineStatement(CodeGenerator generator, TreeNode node){
        List<TreeNode> leafNodes = node.getLeafNodes(); //checks if we're dealing with PLIST or not. Deforest regardless.
        for(TreeNode leaf : leafNodes){
            generatePrintStatement(generator,leaf);
            generator.generate1Byte(OpCodes.NEWLN);
        }
    }

    public static void generatePrintStatement(CodeGenerator generator, TreeNode node){
        List<TreeNode> leafNodes = node.getLeafNodes();//checks if we're dealing with PLIST or not. Deforest regardless.
        for(TreeNode leaf : leafNodes){
            generatePrintOpCodes(generator, leaf);
        }
    }

    private static void generatePrintOpCodes(CodeGenerator generator, TreeNode node){
        //LA0 - get constant
        String dataType = node.getType();
        if(dataType.equals("String")){
            generator.generateInstructionOverrideMessage(OpCodes.LV0, 5, node.getSymbol());
            generator.generate5Bytes(OpCodes.LA0,-99);
            generator.generate1Byte(OpCodes.STRPR);
        }
        else if(dataType.equals("Integer")){
            //check if its an integer variable or just an ilit
            int nodeValue = node.getValue();
            if(nodeValue == TreeNode.NSIMV){ //integer variable
                SymbolTableRecord record = node.getSymbol();

                int baseRegister = record.getBaseRegister();
                int offset = record.getOffset();
                String LV = "LV" + baseRegister;
                generator.generate5Bytes(OpCodes.valueOf(LV), offset);
                generator.generate1Byte(OpCodes.VALPR);
            }

            else{//straight number
                generator.integerLiteral(node.getSymbol());
                generator.generate1Byte(OpCodes.VALPR);
            }
        }
        else if(dataType.equals("Real")){
            //check if real variable or flit
            int nodeValue = node.getValue();
            if(nodeValue == TreeNode.NSIMV){//variable
                SymbolTableRecord record = node.getSymbol();
                int baseRegister = record.getBaseRegister();
                int offset = record.getOffset();

                String LV = "LV" + baseRegister;
                generator.generate5Bytes(OpCodes.valueOf(LV), offset);
                generator.generate1Byte(OpCodes.VALPR);
            }
            else{ //number
                generator.realLiteral(node.getSymbol());
                generator.generate1Byte(OpCodes.VALPR);
            }

        }
        else if(dataType.equals("Boolean")){
            if(node.getValue() == TreeNode.NTRUE)
                generator.generate1Byte(OpCodes.TRUE);
            else
                generator.generate1Byte(OpCodes.FALSE);

            generator.generate1Byte(OpCodes.VALPR);
        }
    }

    public static void generateInputStatement(CodeGenerator generator, TreeNode node){
        //could be list
        List<TreeNode> leafNodes = node.getLeafNodes();
        if(leafNodes.size() == 1){
            generateInputOpCodes(generator, node.getLeft()); //only 1 NINPUT
        }
        else{
            for(TreeNode leaf : leafNodes){
                generateInputOpCodes(generator, leaf); //probs VLIST
            }
        }
    }

    private static void generateInputOpCodes(CodeGenerator generator, TreeNode node){
        SymbolTableRecord record = node.getSymbol();

        int baseRegister = record.getBaseRegister();
        int offset = record.getOffset();

        String LA = "LA" + baseRegister;

        generator.generate5Bytes(OpCodes.valueOf(LA), offset);

        if(record.getDataType().equals("Integer"))
            generator.generate1Byte(OpCodes.READI);
        else
            generator.generate1Byte(OpCodes.READF);

        generator.generate1Byte(OpCodes.ST);
    }
}
