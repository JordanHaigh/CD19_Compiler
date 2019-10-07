package CD19.Observer;

public class SemanticErrorMessage extends CompilerErrorMessage {
    public SemanticErrorMessage(String errorMessage, int line, int column) {
        super(errorMessage, line, column);
    }
}
