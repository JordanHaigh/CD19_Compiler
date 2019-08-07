package CD19.States;

public class DoubleOperatorState implements State {
    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == ' ')
            sm.setCurrentState(new CompletedTokenState());
        else
            sm.setCurrentState(new InvalidState());
    }
}
