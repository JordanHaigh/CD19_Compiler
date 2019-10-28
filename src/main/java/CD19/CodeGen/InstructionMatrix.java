package CD19.CodeGen;

import java.util.ArrayList;
import java.util.List;

public class InstructionMatrix {

    List<int[]> matrix;
    List<int[]> stringMatrix;

    ProgramCounter programCounter;
    ProgramCounter stringProgramCounter;

    public InstructionMatrix() {
        initialiseMatrices();
    }

    private void initialiseMatrices(){
        matrix = new ArrayList<>();
        stringMatrix = new ArrayList<>();

        programCounter = new ProgramCounter();
        stringProgramCounter = new ProgramCounter();

        matrix.add(new int[ProgramCounter.ROWLENGTH]);
        stringMatrix.add(new int[ProgramCounter.ROWLENGTH]);
    }

    public ProgramCounter getProgramCounter(){return programCounter;}
    public void setProgramCounter(ProgramCounter programCounter){this.programCounter = programCounter;}


    public void addByte(int byteToAdd){
        int rowIndex = programCounter.getRow();
        int byteIndex = programCounter.getByte();
        matrix.get(rowIndex)[byteIndex] = byteToAdd;

        programCounter.incrementByte();

        if(programCounter.getByte() > 7){
            //new line
            programCounter.incrementRow();
            programCounter.setByte(0);
            matrix.add(new int[programCounter.ROWLENGTH]);
        }
    }

    public void addByteToStringMatrix(int byteToAdd){
        int rowIndex = stringProgramCounter.getRow();
        int byteIndex = stringProgramCounter.getByte();
        stringMatrix.get(rowIndex)[byteIndex] = byteToAdd;

        stringProgramCounter.incrementByte();

        if(stringProgramCounter.getByte() > 7){
            //new line
            stringProgramCounter.incrementRow();
            stringProgramCounter.setByte(0);
            stringMatrix.add(new int[stringProgramCounter.ROWLENGTH]);
        }
    }

    public void addString(String string){
        string = string.replaceAll("\"","");
        byte[] bytearray = string.getBytes();
        for(byte b : bytearray){
            addByteToStringMatrix(b);
        }
        addByteToStringMatrix(0); //cap off with zero
    }


    public void printMatrices(){
        printMatrix(matrix);
        printMatrix(stringMatrix);



    }

    private void printMatrix(List<int[]> matrix){
        System.out.println(matrix.size());
        for(int[] row : matrix){
            for(int i = 0; i < row.length;i++){
                System.out.print(String.format("%02d ", row[i]));
            }
            System.out.println();
        }
    }
}
