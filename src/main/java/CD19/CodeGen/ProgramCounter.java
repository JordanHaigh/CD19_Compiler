package CD19.CodeGen;


/**
 * ProgramCounter Class used to keep track of where the current position is to add bytes.
 * Also used to determine start of integer, real and string sections.
 *
 * This class is a Plain Old Java Object
 * @author Jordan Haigh c3256730
 * @since 6/11/19
 */
public class ProgramCounter {
    private int byte_;
    private int row_;

    private int startOfIntegerRow;
    private int startOfRealRow;
    private int startOfStringRow;

    public static final int ROWLENGTH = 8;

    public ProgramCounter() {
        this.byte_ = 0;
        this.row_ = 0;
    }

    public int getRow() { return row_; }
    public int getByte() { return byte_; }

    public void setRow(int row_) { this.row_ = row_; }
    public void setByte(int byte_) { this.byte_ = byte_; }

    public int getStartOfIntegerRow() { return startOfIntegerRow; }
    public int getStartOfRealRow() { return startOfRealRow; }
    public int getStartOfStringRow() { return startOfStringRow;}

    public void setStartOfIntegerRow(int startOfIntegerRow) { this.startOfIntegerRow = startOfIntegerRow; }
    public void setStartOfRealRow(int startOfRealRow) { this.startOfRealRow = startOfRealRow; }
    public void setStartOfStringRow(int startOfStringRow) { this.startOfStringRow = startOfStringRow; }

    public void incrementByte(){byte_++;}
    public void incrementRow(){ row_++;}

    /**
     * Gets offset from start of matrix
     * @return - Total offset from start of matrix
     */
    public int getProgramCounterPosition(){
        return (row_ * ROWLENGTH) + byte_;
    }


    @Override
    public String toString() {
        return "ProgramCounter{" +
                "ROW:" + row_ +
                " BYTE:" + byte_ +
                '}';
    }
}
