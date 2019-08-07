package CD19.States;
import CD19.CharacterClassification;

/*
 * Jordan Haigh c3256730 CD19
 * public class SingleOperatorState.java
 * Building either a single or double operator (+ or +=) Character input determines state transitions
 * */
public class SingleOperatorState implements State{

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == '=')
            sm.setCurrentState(new CompletedTokenState()); //double operator
        else if(c == ' ')
            sm.setCurrentState(new CompletedTokenState());
        else
            sm.setCurrentState(new InvalidState());
    }
}
