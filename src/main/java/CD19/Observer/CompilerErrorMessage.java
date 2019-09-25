package CD19.Observer;

public class CompilerErrorMessage extends ObservableMessage {
    private String errorMessage;
    private int line;
    private int column;

    public CompilerErrorMessage(String errorMessage,int line, int column){
        this.errorMessage = errorMessage;
        this.line = line;
        this.column = column;
    }

    public String getErrorMessage() {return errorMessage; }
    public int getLine() { return line; }
    public int getColumn() {  return column; }
}
