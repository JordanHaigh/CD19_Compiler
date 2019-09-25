package CD19.Observer;

public class SyntacticErrorMessage extends CompilerErrorMessage {
    public SyntacticErrorMessage(String errorMessage, int line, int column){
        super(errorMessage, line, column);
    }
}
