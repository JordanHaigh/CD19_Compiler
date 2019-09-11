package CD19.Observer;

public class LexicalErrorMessage extends ObservableMessage {
    private String errorMessage;

    public LexicalErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {return errorMessage; }

}
