package CD19.CodeGen;

public class ProgramCounter {
    private int byte_;
    private int row_;
    public static final int ROWLENGTH = 8;

    public ProgramCounter() {
        this.byte_ = 0;
        this.row_ = 0;
    }

    public int getRow() { return row_; }
    public int getByte() { return byte_; }

    public void setRow(int row_) { this.row_ = row_; }
    public void setByte(int byte_) { this.byte_ = byte_; }

    public void incrementByte(){byte_++;}
    public void incrementRow(){ row_++;}

}
