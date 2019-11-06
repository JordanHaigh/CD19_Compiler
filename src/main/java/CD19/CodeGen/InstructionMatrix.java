package CD19.CodeGen;

import CD19.Parser.SymbolTable;
import CD19.Parser.SymbolTableRecord;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This is where bytes are added for creating the mod file. The instruction matrix will print the mod file in the
 * appropriate ordering to be read by the stack machine.
 *
 * @author Jordan Haigh c3256730
 * @since 6/11/19
 */
public class InstructionMatrix {

    List<String[]> matrix;
    ProgramCounter programCounter;

    public InstructionMatrix() {
        matrix = new ArrayList<>();
        programCounter = new ProgramCounter();
        addNewRowToMatrix();
    }

    /**
     * Add a new row to the matrix.
     * We need to pre-initialise each cell with "00". This will be updated when bytes are added to the matrix
     */
    private void addNewRowToMatrix(){
        matrix.add(new String[ProgramCounter.ROWLENGTH]);
        int newInt = matrix.size()-1;
        for(int i = 0; i < ProgramCounter.ROWLENGTH;i++){
            matrix.get(newInt)[i] = ""+0;
        }
    }

    /**
     * Get Matrix's program counter
     * @return - Program counter
     */
    public ProgramCounter getProgramCounter(){return programCounter;}


    /**
     * Adds a byte to instruction matrix. Boolean used if we are overriding addresses or not
     * @param byteToAdd - Byte to add to matrix
     * @param overridingAddress - Boolean if we are overriding address. This is useful so we don't add new rows when we are overriding
     */
    public void addByte(int byteToAdd, boolean overridingAddress){
        int rowIndex = programCounter.getRow();
        int byteIndex = programCounter.getByte();
        matrix.get(rowIndex)[byteIndex] = ""+byteToAdd;

        programCounter.incrementByte();

        if(programCounter.getByte() > 7){
            if(byteToAdd == OpCodes.RETN.getValue()){
                return; //this is an edge case where we have added a retn at the very end of a line. we don't need to make a new thingy
            }

            //new line
            programCounter.incrementRow();
            programCounter.setByte(0);
            if(!overridingAddress)
                addNewRowToMatrix();

        }
    }

    /**
     * Add an integer literal to the instruction matrix (int section)
     * @param number - Number to add
     */
    public void addNumber(int number){
        int rowIndex = programCounter.getRow();
        int byteIndex = programCounter.getByte();

        matrix.get(rowIndex)[byteIndex] = ""+number;
    }

    /**
     * Add a real literal to the instruction matrix (reals section)
     * @param number - Number to add
     */
    public void addReal(double number){
        int rowIndex = programCounter.getRow();
        int byteIndex = programCounter.getByte();

        matrix.get(rowIndex)[byteIndex] = ""+number;
    }

    /**
     * Add a string literal to the instruction matrix (string section).
     * Caps off with 00 byte at the end in case of more strings to be added
     *
     * @param record - String record to add
     */
    public void addString(SymbolTableRecord record){
        String string = record.getLexeme();

        string = string.replaceAll("\"","");
        byte[] bytearray = string.getBytes();

        record.setBaseRegister(0);
        record.setOffset(programCounter.getRow() * 8 + programCounter.getByte());

        for(byte b : bytearray){
            addByte(b,false);
        }
        addByte(0,false); //cap off with zero
    }

    /**
     * Moves program counter to a specific location (row, byte)
     * @param rowTomoveTo - Row to move to
     * @param byteToMoveTo - Byte to move to
     */
    public void moveProgramCounter(int rowTomoveTo, int byteToMoveTo){
        if(rowTomoveTo < 0 || rowTomoveTo > matrix.size()-1){
            return;
        }
        if(byteToMoveTo < 0 || byteToMoveTo > programCounter.ROWLENGTH){
            return;
        }
        programCounter.setRow(rowTomoveTo);
        programCounter.setByte(byteToMoveTo);


    }

    /**
     * Populate all the constants relevant to the mod file
     * this happens after the first run of the program (useful for knowing
     * where strings are when they are being called in the main instruction section).
     *
     * @param constants - String constants to be added
     * @param integerConstants - Integer constants to be added
     * @param realConstants - Real constants to be added
     */
    public void populateConstants(SymbolTable constants, List<SymbolTableRecord> integerConstants, List<SymbolTableRecord> realConstants){
        //add new row for integer constants
        //--------------INTS--------------
        programCounter.setStartOfIntegerRow(matrix.size());

        for(SymbolTableRecord record : integerConstants){
            int i = Integer.parseInt(record.getLexeme());

            addNewRowToMatrix();
            programCounter.incrementRow();
            programCounter.setByte(0);

            record.setBaseRegister(0);
            record.setOffset(programCounter.getRow() * 8 + programCounter.getByte());

            addNumber(i);
        }
        //--------------REALS--------------
        programCounter.setStartOfRealRow(matrix.size());

        for(SymbolTableRecord record : realConstants){
            Double d = Double.parseDouble(record.getLexeme());

            addNewRowToMatrix();
            programCounter.incrementRow();
            programCounter.setByte(0);

            record.setBaseRegister(0);
            record.setOffset(programCounter.getRow() * 8 + programCounter.getByte());

            addReal(d);
        }
        //--------------STRINGS--------------
        List<SymbolTableRecord> constantsList = constants.getAllRecords();
        List<SymbolTableRecord> stringConstants  = new ArrayList<>();
        for(SymbolTableRecord record : constantsList){
            if(record.getDataType().equals("String")){
                stringConstants.add(record);
            }
        }


        programCounter.setStartOfStringRow(matrix.size());

        if(stringConstants.size() > 0){
            addNewRowToMatrix();
            programCounter.incrementRow();
            programCounter.setByte(0);

            for(SymbolTableRecord record : stringConstants){
                addString(record);
            }
        }
    }


    /**
     * Prints instruction matrix into format that is used for the stack machine
     * @param printByteAsChar - boolean for human readable output for strings
     * @param printWriter - Print Writer to write to (sys out or file)
     */
    public void printMatrix(boolean printByteAsChar, PrintWriter printWriter){
        if (printWriter == null) {
            printWriter = new PrintWriter(System.out);
        }


        int instructionSize = programCounter.getStartOfIntegerRow();
        int integerSize = programCounter.getStartOfRealRow() - programCounter.getStartOfIntegerRow();
        int realSize = programCounter.getStartOfStringRow() - programCounter.getStartOfRealRow();
        int stringSize = matrix.size() - programCounter.getStartOfStringRow();

        //--------------INSTRUCTIONS--------------
        printWriter.println(instructionSize);
        for(int i = 0; i < instructionSize; i++){
            for(int j = 0; j < matrix.get(i).length;j++){
                printWriter.print(String.format("%02d", Integer.parseInt(matrix.get(i)[j])));
                if(printByteAsChar)
                    printWriter.print("(" + (char)Integer.parseInt(matrix.get(i)[j])+")");
                printWriter.print(" ");
            }
            printWriter.println();
        }

        //--------------INTEGERS--------------
        printWriter.println(integerSize);
        for(int i = programCounter.getStartOfIntegerRow(); i < programCounter.getStartOfIntegerRow()+integerSize; i++){
            printWriter.println(matrix.get(i)[0]);
        }
        //--------------REAL--------------
        printWriter.println(realSize);
        for(int i = programCounter.getStartOfRealRow(); i < programCounter.getStartOfRealRow()+realSize; i++){
            printWriter.println(matrix.get(i)[0]);
        }

        //--------------STRINGS--------------
        printWriter.println(stringSize);
        for(int i = programCounter.getStartOfStringRow(); i < programCounter.getStartOfStringRow()+stringSize; i++){
            for(int j = 0; j < matrix.get(i).length;j++){
                printWriter.print(String.format("%02d",Integer.parseInt(matrix.get(i)[j])));
                if(printByteAsChar)
                    printWriter.print("(" + (char)Integer.parseInt(matrix.get(i)[j])+")");
                printWriter.print(" ");
            }
            printWriter.println();
        }

        printWriter.flush();
        printWriter.close();
    }
}
