package CD19.Observer;

public class ObservableErrorMessage extends ObservableMessage {
    private String errorMessage;

    public ObservableErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {return errorMessage; }

}
