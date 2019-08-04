import States.InitState;
import States.State;

import java.util.List;

public class Scanner {

    private List<String> codeLines;
    private State state;

    public Scanner(List<String> codeLines){
        this.codeLines = codeLines;
        this.state = new InitState();
    }

    public void getAllTokens(){

    }

    public void getToken(){

    }

    public boolean reachedEndOfFile(){

        return false; //todo
    }

    private void setState(State state){
        this.state = state;
    }
}
