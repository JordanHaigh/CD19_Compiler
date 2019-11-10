package CD19.CodeGen.Instructions;

import CD19.CodeGen.CodeGenerator;
import CD19.CodeGen.OpCodes;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;

import java.util.List;

/**
 * Generates any statement nodes (iostat, asgnstat, reptstat, retnstat, ifstat, ifelsestat, forstat)
 * not callstat because we arent doing any functions
 *
 * @author Jordan Haigh c3256730
 * @since 6/11/19
 */
public class Statement{

    /**
     * Generate opcodes based on specific statement
     * @param generator - Code Generator
     * @param node - Node to generate data from
     */
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
            case TreeNode.NINIT:{
                generateNinitStatement(generator,node);
                break;
            }
            case TreeNode.NASGN: {
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
                break;
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
            //------------------------FORSTAT----------------------------
            case TreeNode.NFOR:{
                generateForStatement(generator,node);
                break;
            }
            case TreeNode.NIFTH:{
                generateIfStatement(generator,node);
                break;
            }
            case TreeNode.NIFTE:{
                generateIfElseStatement(generator,node);
                break;
            }
            //can't use any fncalls because the function needs to exist semantics wise, and if function does exist its alredy cancelled out by compiler error
        }
    }

    public static void generateNinitStatement(CodeGenerator generator, TreeNode node){
        SymbolTableRecord assignee = node.getSymbol();
        String LA = "LA" + assignee.getBaseRegister();
        generator.generate5Bytes(OpCodes.valueOf(LA), assignee.getOffset());
        
        TreeNode exprNode = node.getLeft();
        postOrderOpCodesAssignStatement(generator, exprNode);
        generator.generate1Byte(OpCodes.ST);

    }

    /**
     * Generates opcodes for if else statement
     * @param generator - Code Generator
     * @param node - IFTHE node to expand
     */
    public static void generateIfElseStatement(CodeGenerator generator, TreeNode node){
        //--------------------------BOOL-----------------------------------
        int startRowOfBool = generator.getProgram().getProgramCounter().getRow();
        int startByteOfBool = generator.getProgram().getProgramCounter().getByte();
        TreeNode bool = node.getLeft();

        generator.generate5Bytes(OpCodes.LA0,-99); //placeholder for when we need to return to start of loop for next iteration

        generateBoolSection(generator,bool);

        generator.generate1Byte(OpCodes.BF);

        //--------------------------IFSTATS-----------------------------------
        TreeNode ifstats = node.getMiddle();
        generateStatsInsideLoop(generator, ifstats);

        int startRowOfElseStatement = generator.getProgram().getProgramCounter().getRow();
        int startByteOfElseStatement = generator.getProgram().getProgramCounter().getByte();
        generator.generate5Bytes(OpCodes.LA0,-99); //placeholder for when we need to return to start of loop for next iteration
        generator.generate1Byte(OpCodes.BR);


        //--------------------------ELSESTATS-----------------------------------
        int currentPosition = generator.getProgram().getProgramCounter().getProgramCounterPosition();

        //fill in remember 1 (location of else statement)
        moveProgramCounterOverwriteMoveBack(generator, startRowOfBool, startByteOfBool, currentPosition);

        TreeNode elseStats = node.getRight();

        generateStatsInsideLoop(generator,elseStats);


        currentPosition = generator.getProgram().getProgramCounter().getProgramCounterPosition();

        //fill in remember 2 (location of after the "then" part)
        moveProgramCounterOverwriteMoveBack(generator, startRowOfElseStatement, startByteOfElseStatement, currentPosition);

    }

    /**
     * Generates opcodes for if  statement
     * @param generator - Code Generator
     * @param node - NIFTH node to expand
     */
    public static void generateIfStatement(CodeGenerator generator, TreeNode node){
        //--------------------------BOOL-----------------------------------
        int startRowOfBool = generator.getProgram().getProgramCounter().getRow();
        int startByteOfBool = generator.getProgram().getProgramCounter().getByte();

        generator.generate5Bytes(OpCodes.LA0,-99); //placeholder for when we need to return to start of loop for next iteration

        TreeNode bool = node.getLeft();
       generateBoolSection(generator,bool);


        generator.generate1Byte(OpCodes.BF);


        //--------------------------STATS-----------------------------------
        TreeNode stats = node.getRight();
        if(stats.getValue() == TreeNode.NSTATS){ //more than 1 stat
            List<TreeNode> statList = stats.detreeify();
            for(TreeNode n : statList){
                Statement.generate(generator, n);
            }
        }
        else{ //1 stat
            Statement.generate(generator, stats);
        }

        if (generator.hasStoppedProcessing()) {
            return;
        }

        //section after stats
        int currentPosition = generator.getProgram().getProgramCounter().getProgramCounterPosition();
        moveProgramCounterOverwriteMoveBack(generator, startRowOfBool, startByteOfBool, currentPosition);

    }

    /**
     * Generates opcodes for a "for statement"
     * @param generator - Code Generator
     * @param node - NFOR node to expand
     */
    public static void generateForStatement(CodeGenerator generator, TreeNode node){
        //--------------------------ASGNLIST-----------------------------------
        TreeNode asgnlistNode = node.getLeft();
        //make asgnlist
        if(asgnlistNode.getValue() == TreeNode.NASGNS){ //more than 1 assign
            List<TreeNode> asgnList = asgnlistNode.detreeify();
            for(TreeNode n : asgnList){
                generateAssignStatement(generator, n);
            }
        }
        else{ //only one assign
            generateAssignStatement(generator, asgnlistNode);
        }
        //--------------------------BOOL-----------------------------------
        //get address in case loop needs to repeat
        int startRowOfBool = generator.getProgram().getProgramCounter().getRow();
        int startByteOfBool = generator.getProgram().getProgramCounter().getByte();
        int startBoolPosition = generator.getProgram().getProgramCounter().getProgramCounterPosition();

        generator.generate5Bytes(OpCodes.LA0,-99); //placeholder for when we need to return to start of loop for next iteration

        TreeNode bool = node.getMiddle();
        generateBoolSection(generator,bool);
        generator.generate1Byte(OpCodes.BF);

        //--------------------------STATS-----------------------------------
        TreeNode stats = node.getRight();
        if(stats.getValue() == TreeNode.NSTATS){ //more than 1 stat
            List<TreeNode> statList = stats.detreeify();
            for(TreeNode n : statList){
                Statement.generate(generator, n);
            }
        }
        else{ //1 stat
            Statement.generate(generator, stats);
        }

        if (generator.hasStoppedProcessing()) {
            return;
        }


        generator.generate5Bytes(OpCodes.LA0, startBoolPosition);
        generator.generate1Byte(OpCodes.BR);

        int currentRow = generator.getProgram().getProgramCounter().getRow();
        int currentByte = generator.getProgram().getProgramCounter().getByte();
        int currentPosition = generator.getProgram().getProgramCounter().getProgramCounterPosition();

        generator.getProgram().moveProgramCounter(startRowOfBool,startByteOfBool);
        //update operand
        generator.generateXBytes(OpCodes.LA0, currentPosition, 5, true);

        //move back to position
        generator.getProgram().moveProgramCounter(currentRow,currentByte);

    }

    /**
     * Generates opcodes for a "repeat statement"
     * @param generator - Code Generator
     * @param node - NREPT node to expand
     */
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
        //TreeNode firstStat = stats.getLeft();
        int programCounterIndex = generator.getProgram().getProgramCounter().getProgramCounterPosition();

        generateStatsInsideLoop(generator, stats);

//        if(stats.getValue() == TreeNode.NSTATS){ //more than 1 stat
//            List<TreeNode> statList = stats.detreeify();
//            for(TreeNode n : statList){
//                Statement.generate(generator, n);
//            }
//        }
//        else{ //1 stat
//            Statement.generate(generator, stats);
//        }
//
//        if (generator.hasStoppedProcessing()) {
//            return;
//        }
        //--------------------------LOAD FIRST ADDR-----------------------------------
        //load address of first stat
        generator.generate5Bytes(OpCodes.LA0, programCounterIndex);

        //--------------------------BOOL-----------------------------------
        TreeNode bool = node.getRight();
       generateBoolSection(generator,bool);

        //--------------------------BF-----------------------------------

        generator.generate1Byte(OpCodes.BF);
    }

    /**
     * Generates opcodes for a "return statement"
     * @param generator - Code Generator
     * @param node - NRETN node to expand
     */
    public static void generateReturnStatement(CodeGenerator generator, TreeNode node){
        generator.generate1Byte(OpCodes.RETN);
        generator.stopProcessing();
    }

    /**
     * Generic version to generate a PLEQ, MNEQ, STEQ, DVEQ statement and its opcodes
     * @param generator - Code Generator
     * @param node - Node to expand
     */
    private static void generate__EqualsStatement(CodeGenerator generator, TreeNode node, OpCodes X){
        //equivalent of x = x ?? <expr>
        SymbolTableRecord assignee = node.getLeft().getSymbol();
        String LA = "LA" + assignee.getBaseRegister();
        generator.generate5Bytes(OpCodes.valueOf(LA),assignee.getOffset());

        String LV = "LV" + assignee.getBaseRegister(); //load value of x to be ??'d at end
        generator.generate5Bytes(OpCodes.valueOf(LV), assignee.getOffset());

        TreeNode exprNode = node.getRight(); //generate tree as assign statement
        postOrderOpCodesAssignStatement(generator, exprNode);

        generator.generate1Byte(X); //final ?? opcode
        generator.generate1Byte(OpCodes.ST); //store as normal

    }

    /**
     * Generates a DVEQ statement and its opcodes
     * @param generator - Code Generator
     * @param node - DVEQ Node to expand
     */
    public static void generateDivideEqualsStatement(CodeGenerator generator, TreeNode node){
       generate__EqualsStatement(generator,node,OpCodes.DIV);
    }

    /**
     * Generates a STEQ statement and its opcodes
     * @param generator - Code Generator
     * @param node - NSTEQ Node to expand
     */
    public static void generateStarEqualsStatement(CodeGenerator generator, TreeNode node){
        generate__EqualsStatement(generator,node,OpCodes.MUL);
    }

    /**
     * Generates a MNEQ statement and its opcodes
     * @param generator - Code Generator
     * @param node - MNEQ Node to expand
     */
    public static void generateMinusEqualsStatement(CodeGenerator generator, TreeNode node){
        generate__EqualsStatement(generator,node,OpCodes.SUB);
    }

    /**
     * Generates a PLEQ statement and its opcodes
     * @param generator - Code Generator
     * @param node - PLEQ Node to expand
     */
    public static void generatePlusEqualsStatement(CodeGenerator generator, TreeNode node){
        generate__EqualsStatement(generator,node,OpCodes.ADD);
    }

    /**
     * Generates an assignment statement and its opcodes
     * @param generator - Code Generator
     * @param node - NASGN to expand
     */
    public static void generateAssignStatement(CodeGenerator generator, TreeNode node){
        //we have the main root which is the NASGN
        //the left child will be the thing to assign to
        SymbolTableRecord assignee = node.getLeft().getSymbol();
        String LA = "LA" + assignee.getBaseRegister();
        generator.generate5Bytes(OpCodes.valueOf(LA), assignee.getOffset());

        //the right child will be the expr
        //Start working with right side of tree
        TreeNode exprNode = node.getRight();
        // the expr will be a list of asgnops (add,mul,pow,etc.) and can be a mixed list. you will need to change detreeify to accommodate this
        //post order and build
        postOrderOpCodesAssignStatement(generator, exprNode);
        generator.generate1Byte(OpCodes.ST);
    }


    /**
     * Generates everything that is apart of an expression node. e.g. 5+8*6/a+1
     * @param generator - Code Generator
     * @param root - Expression Node to expand
     */
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
                else if(root.getValue() == TreeNode.NFLIT){//must be NFLIT
                    generator.realLiteral(root.getSymbol());
                }
                else{ //must be boolean
                    if(root.getValue() == TreeNode.NTRUE){
                        generator.generate1Byte(OpCodes.TRUE);
                    }
                    else{
                        generator.generate1Byte(OpCodes.FALSE);
                    }
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

    /**
     * Generates a printline statement and its opcodes
     * @param generator  - Code Generator
     * @param node - Printline node
     */
    public static void generatePrintLineStatement(CodeGenerator generator, TreeNode node){
        List<TreeNode> leafNodes = node.getLeafNodes(); //checks if we're dealing with PLIST or not. Deforest regardless.
        for(TreeNode leaf : leafNodes){
            generatePrintOpCodes(generator,leaf);
        }
        generator.generate1Byte(OpCodes.NEWLN);
    }

    /**
     * Generates a print statement and its opcodes
     * @param generator  - Code Generator
     * @param node - Print statement node
     */
    public static void generatePrintStatement(CodeGenerator generator, TreeNode node){
        List<TreeNode> leafNodes = node.getLeafNodes();//checks if we're dealing with PLIST or not. Deforest regardless.
        for(TreeNode leaf : leafNodes){
            generatePrintOpCodes(generator, leaf);
        }
        generator.generate1Byte(OpCodes.NEWLN);

    }

    /**
     * Main heavy lifting of generating a print statement. gathers opcodes from records if necessary
     * @param generator - Code Generator
     * @param node - Node to expand
     */
    private static void generatePrintOpCodes(CodeGenerator generator, TreeNode node){
        //LA0 - get constant
        String dataType = node.getType();
        if(dataType.equals("String")){
            generator.generateInstructionOverrideMessage(OpCodes.LA0, 5, node.getSymbol());
            generator.generate5Bytes(OpCodes.LA0,-88);
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

    /**
     * Generates input statement and associates opcodes
     * @param generator - Code Generator
     * @param node - Node to expand
     */
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

    /**
     * Generates input opcodes. Main heavy lifting
     * @param generator - Code Generator
     * @param node - Node to expand
     */
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

    /**
     * Moves program counter, overwrites the bytes at that location, and moves back to current position
     * @param generator  - Code Generator
     * @param startRow - Position to move to
     * @param startByte - Position to move to
     * @param overwritingValue - Value to overwrite at (row,byte) location
     */
    private static void moveProgramCounterOverwriteMoveBack(CodeGenerator generator, int startRow, int startByte, int overwritingValue){
        int currentRow = generator.getProgram().getProgramCounter().getRow();
        int currentByte = generator.getProgram().getProgramCounter().getByte();

        generator.getProgram().moveProgramCounter(startRow,startByte);
        //update operand
        generator.generateXBytes(OpCodes.LA0, overwritingValue, 5, true);
        //move back to position
        generator.getProgram().moveProgramCounter(currentRow,currentByte);
    }

    /**
     * Generates any extra stats that may exist from a node (used inside any repeating structures or if statements)
     * @param generator - Code Generator
     * @param node - Node to expand (NSTATS, NSTAT)
     */
    private static void generateStatsInsideLoop(CodeGenerator generator, TreeNode node){
        if(node.getValue() == TreeNode.NSTATS){ //more than 1 stat
            List<TreeNode> statList = node.detreeify();
            for(TreeNode n : statList){
                Statement.generate(generator, n);
            }
        }
        else{ //1 stat
            Statement.generate(generator, node);
        }

        if (generator.hasStoppedProcessing()) {
            return;
        }
    }


    private static void generateBoolSection(CodeGenerator generator, TreeNode node){
        if(node.getValue() == TreeNode.NBOOL){
            List<TreeNode> boolList = node.detreeifyLogops();
            for(TreeNode n : boolList){
                generateLogop(generator, n);
            }
        }
        else{
            //one bool
            generateRelop(generator, node);
        }
    }


    /**
     * Generates a logop from node and associated opcodes
     * @param generator  - Code Generator
     * @param node - Node to expand (NAND, NOR, NNOT, NXOR)
     */
    public static void generateLogop(CodeGenerator generator, TreeNode node){
        //root is logop
        //children are either more logops or relops
        if(node.nodeIsRelop())
            generateRelop(generator, node);

        switch(node.getValue()){
            case TreeNode.NAND: {
                generator.generate1Byte(OpCodes.AND);
                break;
            }
            case TreeNode.NOR:{
                generator.generate1Byte(OpCodes.OR);
                break;
            }
            case TreeNode.NXOR:{
                generator.generate1Byte(OpCodes.XOR);
                break;
            }
            case TreeNode.NNOT:{
                generator.generate1Byte(OpCodes.NOT);
                break;
            }
        }

    }

    /**
     * Generates a relop from node and associated opcodes
     * @param generator  - Code Generator
     * @param node - Node to expand
     */
    public static void generateRelop(CodeGenerator generator, TreeNode node){
        TreeNode leftSide = node.getLeft();
        if(node.getValue() == TreeNode.NTRUE){
            generator.generate1Byte(OpCodes.TRUE);
            return;
        }
        if(node.getValue() == TreeNode.NFALS){
            generator.generate1Byte(OpCodes.FALSE);
            return;
        }
        if(leftSide.nodeIsLogop()){
            return; //already handled previously
        }

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


}
