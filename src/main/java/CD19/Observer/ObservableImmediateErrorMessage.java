package CD19.Observer;

/**
 * Immediate error message that will print to the console as soon as it is received
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class ObservableImmediateErrorMessage extends ObservableMessage {
    private String errorMessage;

    public ObservableImmediateErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {return errorMessage; }
}
