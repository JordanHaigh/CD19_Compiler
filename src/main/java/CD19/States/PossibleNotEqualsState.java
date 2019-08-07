package CD19.States;


/*
 * Jordan Haigh c3256730 CD19
 * public class PossibleNotEqualsState.java
 * Building a not equals (!=) token. Character input determines state transitions
 * */
public class PossibleNotEqualsState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == '=')
            sm.setCurrentState(new AbsoluteNotEqualsState());
        else
            sm.setCurrentState(new InvalidState());
    }
}
