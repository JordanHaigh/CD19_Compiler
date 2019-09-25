package CD19.Observer;

public class LexicalErrorMessage extends CompilerErrorMessage {
    public LexicalErrorMessage(String errorMessage, int line, int column){
        super(errorMessage, line, column);
    }
}
