package CD19.Observer;

public class ObservableImmediateErrorMessage extends ObservableMessage {
    private String errorMessage;

    public ObservableImmediateErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {return errorMessage; }
}
