package CD19;

import CD19.Observer.*;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler implements Observer {

    private List<String> errorMessages = new ArrayList<>();

    public void printErrorMessages(){
        for(String s : errorMessages){
            System.out.println(s);
        }
    }

    @Override
    public void handleMessage(ObservableMessage message) {
        if(message instanceof ObservableErrorMessage){
            String newErrorMessage = ((ObservableErrorMessage)message).getErrorMessage();
            errorMessages.add(newErrorMessage);
        }

        if(message instanceof ObservableImmediateErrorMessage){
            String newErrorMessage = ((ObservableErrorMessage)message).getErrorMessage();
            System.out.println(newErrorMessage);
            errorMessages.add(newErrorMessage);

        }
    }
}
