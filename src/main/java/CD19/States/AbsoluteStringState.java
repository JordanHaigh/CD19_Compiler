package CD19.States;

/*
 * Jordan Haigh c3256730 CD19
 * public class AbsoluteStringState.java
 * Building an string literal. Character inputs determine state transitions
 * */
public class AbsoluteStringState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == ' ' || c == '\n')
            sm.setCurrentState(new CompletedTokenState());
        else
            sm.setCurrentState(new InvalidState()); //todo double check this
    }
}
