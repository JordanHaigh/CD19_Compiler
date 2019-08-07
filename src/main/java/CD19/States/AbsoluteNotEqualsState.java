package CD19.States;

/*
 * Jordan Haigh c3256730 CD19
 * public class AbsoluteNotEqualsState.java
 * Building a not equals (!=) token. Character input determines state transitions
 * */
public class AbsoluteNotEqualsState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == ' ')
            sm.setCurrentState(new CompletedTokenState());
        else
            sm.setCurrentState(new InvalidState());
    }
}
