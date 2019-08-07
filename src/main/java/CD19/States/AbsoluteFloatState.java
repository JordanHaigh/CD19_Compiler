package CD19.States;

import CD19.CharacterClassification;

/*
 * Jordan Haigh c3256730 CD19
 * public class AbsoluteFloatState.java
 * Building an absolute Float (<integer>.<integer>)token. Character inputs determine state transitions
 * */
public class AbsoluteFloatState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(CharacterClassification.isCharNumerical(c))
            return; //no state update
        else if(c == ' ' || c == '\n')
            sm.setCurrentState(new CompletedTokenState());
        else
            sm.setCurrentState(new InvalidState());
    }
}
