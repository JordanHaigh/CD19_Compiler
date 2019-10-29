package CD19.CodeGen;

import CD19.Parser.SymbolTable;
import CD19.Parser.SymbolTableRecord;

import java.util.ArrayList;
import java.util.List;

public class InstructionMatrix {

    List<int[]> matrix;
    ProgramCounter programCounter;

    public InstructionMatrix() {
        initialiseMatrices();
    }

    private void initialiseMatrices(){
        matrix = new ArrayList<>();
        programCounter = new ProgramCounter();
        matrix.add(new int[ProgramCounter.ROWLENGTH]);
    }

    public ProgramCounter getProgramCounter(){return programCounter;}
    public void setProgramCounter(ProgramCounter programCounter){this.programCounter = programCounter;}

    public void addByte(int byteToAdd, boolean overridingAddress){
        int rowIndex = programCounter.getRow();
        int byteIndex = programCounter.getByte();
        matrix.get(rowIndex)[byteIndex] = byteToAdd;

        programCounter.incrementByte();

        if(programCounter.getByte() > 7){
            //new line
            programCounter.incrementRow();
            programCounter.setByte(0);
            if(!overridingAddress)
                matrix.add(new int[programCounter.ROWLENGTH]);
        }
    }

    public void addNumber(int number){
        int rowIndex = programCounter.getRow();
        int byteIndex = programCounter.getByte();
        matrix.get(rowIndex)[byteIndex] = number;
    }

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

    public void moveProgramCounter(int rowTomoveTo, int byteToMoveTo){
        if(rowTomoveTo < 0 || rowTomoveTo > matrix.size()-1){
            return;
        }
        if(byteToMoveTo < 0 || byteToMoveTo > programCounter.ROWLENGTH){
            return;
        }
        //todo probs error here
        programCounter.setRow(rowTomoveTo);
        programCounter.setByte(byteToMoveTo);


    }

    public void populateConstants(SymbolTable constants, List<SymbolTableRecord> integerConstants, List<SymbolTableRecord> realConstants){
        //add new row for integer constants
        //--------------INTS--------------
        programCounter.setStartOfIntegerRow(matrix.size());

        for(SymbolTableRecord record : integerConstants){
            int i = Integer.parseInt(record.getLexeme());

            matrix.add(new int[programCounter.ROWLENGTH]);
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

            matrix.add(new int[programCounter.ROWLENGTH]);
            programCounter.incrementRow();
            programCounter.setByte(0);

            record.setBaseRegister(0);
            record.setOffset(programCounter.getRow() * 8 + programCounter.getByte());

            //todo - current matrix doesnt support doubles. UGHHHHHHHHHHHHHHH
            addNumber(d.intValue());//TODO CHANGE THIS THIS IS CRITICAL
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
            matrix.add(new int[ProgramCounter.ROWLENGTH]);
            programCounter.incrementRow();
            programCounter.setByte(0);

            for(SymbolTableRecord record : stringConstants){
                addString(record);
            }
        }
    }

    public void printMatrix(boolean printByteAsChar){
        int instructionSize = programCounter.getStartOfIntegerRow();
        int integerSize = programCounter.getStartOfRealRow() - programCounter.getStartOfIntegerRow();
        int realSize = programCounter.getStartOfStringRow() - programCounter.getStartOfRealRow();
        int stringSize = matrix.size() - programCounter.getStartOfStringRow();

        //--------------INSTRUCTIONS--------------
        System.out.println(instructionSize);
        for(int i = 0; i < instructionSize; i++){
            for(int j = 0; j < matrix.get(i).length;j++){
                System.out.print(String.format("%02d", matrix.get(i)[j]));
                if(printByteAsChar)
                    System.out.print("(" + (char)matrix.get(i)[j]+")");
                System.out.print(" ");
            }
            System.out.println();
        }

        //--------------INTEGERS--------------
        System.out.println(integerSize);
        for(int i = programCounter.getStartOfIntegerRow(); i < programCounter.getStartOfIntegerRow()+integerSize; i++){
            System.out.println(matrix.get(i)[0]);
        }
        //--------------REAL--------------
        System.out.println(realSize);
        for(int i = programCounter.getStartOfRealRow(); i < programCounter.getStartOfRealRow()+realSize; i++){
            System.out.println(matrix.get(i)[0]);
        }

        //--------------STRINGS--------------
        System.out.println(stringSize);
        for(int i = programCounter.getStartOfStringRow(); i < programCounter.getStartOfStringRow()+stringSize; i++){
            for(int j = 0; j < matrix.get(i).length;j++){
                System.out.print(String.format("%02d", matrix.get(i)[j]));
                if(printByteAsChar)
                    System.out.print("(" + (char)matrix.get(i)[j]+")");
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
